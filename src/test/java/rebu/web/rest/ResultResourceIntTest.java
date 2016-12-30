package rebu.web.rest;

import rebu.RebuApp;

import rebu.domain.Result;
import rebu.repository.ResultRepository;
import rebu.service.ResultService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResultResource REST controller.
 *
 * @see ResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebuApp.class)
public class ResultResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_REP = 1;
    private static final Integer UPDATED_REP = 2;

    private static final Integer DEFAULT_SETS = 1;
    private static final Integer UPDATED_SETS = 2;

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DUE_DATE_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_DUE_DATE);

    @Inject
    private ResultRepository resultRepository;

    @Inject
    private ResultService resultService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restResultMockMvc;

    private Result result;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResultResource resultResource = new ResultResource();
        ReflectionTestUtils.setField(resultResource, "resultService", resultService);
        this.restResultMockMvc = MockMvcBuilders.standaloneSetup(resultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Result createEntity(EntityManager em) {
        Result result = new Result()
                .description(DEFAULT_DESCRIPTION)
                .rep(DEFAULT_REP)
                .sets(DEFAULT_SETS)
                .dueDate(DEFAULT_DUE_DATE);
        return result;
    }

    @Before
    public void initTest() {
        result = createEntity(em);
    }

    @Test
    @Transactional
    public void createResult() throws Exception {
        int databaseSizeBeforeCreate = resultRepository.findAll().size();

        // Create the Result

        restResultMockMvc.perform(post("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(result)))
                .andExpect(status().isCreated());

        // Validate the Result in the database
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeCreate + 1);
        Result testResult = results.get(results.size() - 1);
        assertThat(testResult.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testResult.getRep()).isEqualTo(DEFAULT_REP);
        assertThat(testResult.getSets()).isEqualTo(DEFAULT_SETS);
        assertThat(testResult.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllResults() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Get all the results
        restResultMockMvc.perform(get("/api/results?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(result.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].rep").value(hasItem(DEFAULT_REP)))
                .andExpect(jsonPath("$.[*].sets").value(hasItem(DEFAULT_SETS)))
                .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getResult() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Get the result
        restResultMockMvc.perform(get("/api/results/{id}", result.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(result.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.rep").value(DEFAULT_REP))
            .andExpect(jsonPath("$.sets").value(DEFAULT_SETS))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingResult() throws Exception {
        // Get the result
        restResultMockMvc.perform(get("/api/results/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResult() throws Exception {
        // Initialize the database
        resultService.save(result);

        int databaseSizeBeforeUpdate = resultRepository.findAll().size();

        // Update the result
        Result updatedResult = resultRepository.findOne(result.getId());
        updatedResult
                .description(UPDATED_DESCRIPTION)
                .rep(UPDATED_REP)
                .sets(UPDATED_SETS)
                .dueDate(UPDATED_DUE_DATE);

        restResultMockMvc.perform(put("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResult)))
                .andExpect(status().isOk());

        // Validate the Result in the database
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeUpdate);
        Result testResult = results.get(results.size() - 1);
        assertThat(testResult.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResult.getRep()).isEqualTo(UPDATED_REP);
        assertThat(testResult.getSets()).isEqualTo(UPDATED_SETS);
        assertThat(testResult.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void deleteResult() throws Exception {
        // Initialize the database
        resultService.save(result);

        int databaseSizeBeforeDelete = resultRepository.findAll().size();

        // Get the result
        restResultMockMvc.perform(delete("/api/results/{id}", result.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(databaseSizeBeforeDelete - 1);
    }
}
