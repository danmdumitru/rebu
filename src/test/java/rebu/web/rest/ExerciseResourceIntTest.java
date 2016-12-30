package rebu.web.rest;

import rebu.RebuApp;

import rebu.domain.Exercise;
import rebu.repository.ExerciseRepository;
import rebu.service.ExerciseService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExerciseResource REST controller.
 *
 * @see ExerciseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebuApp.class)
public class ExerciseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_REP = 1;
    private static final Integer UPDATED_REP = 2;

    private static final Integer DEFAULT_SETS = 1;
    private static final Integer UPDATED_SETS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private ExerciseRepository exerciseRepository;

    @Inject
    private ExerciseService exerciseService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restExerciseMockMvc;

    private Exercise exercise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseResource exerciseResource = new ExerciseResource();
        ReflectionTestUtils.setField(exerciseResource, "exerciseService", exerciseService);
        this.restExerciseMockMvc = MockMvcBuilders.standaloneSetup(exerciseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exercise createEntity(EntityManager em) {
        Exercise exercise = new Exercise()
                .name(DEFAULT_NAME)
                .rep(DEFAULT_REP)
                .sets(DEFAULT_SETS)
                .description(DEFAULT_DESCRIPTION);
        return exercise;
    }

    @Before
    public void initTest() {
        exercise = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercise() throws Exception {
        int databaseSizeBeforeCreate = exerciseRepository.findAll().size();

        // Create the Exercise

        restExerciseMockMvc.perform(post("/api/exercises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exercise)))
                .andExpect(status().isCreated());

        // Validate the Exercise in the database
        List<Exercise> exercises = exerciseRepository.findAll();
        assertThat(exercises).hasSize(databaseSizeBeforeCreate + 1);
        Exercise testExercise = exercises.get(exercises.size() - 1);
        assertThat(testExercise.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExercise.getRep()).isEqualTo(DEFAULT_REP);
        assertThat(testExercise.getSets()).isEqualTo(DEFAULT_SETS);
        assertThat(testExercise.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExercises() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get all the exercises
        restExerciseMockMvc.perform(get("/api/exercises?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(exercise.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].rep").value(hasItem(DEFAULT_REP)))
                .andExpect(jsonPath("$.[*].sets").value(hasItem(DEFAULT_SETS)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get the exercise
        restExerciseMockMvc.perform(get("/api/exercises/{id}", exercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exercise.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.rep").value(DEFAULT_REP))
            .andExpect(jsonPath("$.sets").value(DEFAULT_SETS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExercise() throws Exception {
        // Get the exercise
        restExerciseMockMvc.perform(get("/api/exercises/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercise() throws Exception {
        // Initialize the database
        exerciseService.save(exercise);

        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Update the exercise
        Exercise updatedExercise = exerciseRepository.findOne(exercise.getId());
        updatedExercise
                .name(UPDATED_NAME)
                .rep(UPDATED_REP)
                .sets(UPDATED_SETS)
                .description(UPDATED_DESCRIPTION);

        restExerciseMockMvc.perform(put("/api/exercises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedExercise)))
                .andExpect(status().isOk());

        // Validate the Exercise in the database
        List<Exercise> exercises = exerciseRepository.findAll();
        assertThat(exercises).hasSize(databaseSizeBeforeUpdate);
        Exercise testExercise = exercises.get(exercises.size() - 1);
        assertThat(testExercise.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExercise.getRep()).isEqualTo(UPDATED_REP);
        assertThat(testExercise.getSets()).isEqualTo(UPDATED_SETS);
        assertThat(testExercise.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteExercise() throws Exception {
        // Initialize the database
        exerciseService.save(exercise);

        int databaseSizeBeforeDelete = exerciseRepository.findAll().size();

        // Get the exercise
        restExerciseMockMvc.perform(delete("/api/exercises/{id}", exercise.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Exercise> exercises = exerciseRepository.findAll();
        assertThat(exercises).hasSize(databaseSizeBeforeDelete - 1);
    }
}
