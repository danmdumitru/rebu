package rebu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import rebu.RebuApp;
import rebu.domain.UradMonitor;
import rebu.repository.UradMonitorRepository;
import rebu.service.UradMonitorService;
import rebu.service.dto.UradMonitorDTO;
import rebu.service.mapper.UradMonitorMapper;
import rebu.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the UradMonitorResource REST controller.
 *
 * @see UradMonitorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebuApp.class)
public class UradMonitorResourceIntTest {

    private static final String DEFAULT_UNIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTITUDE = "AAAAAAAAAA";
    private static final String UPDATED_ALTITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_SPEED = "AAAAAAAAAA";
    private static final String UPDATED_SPEED = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Long DEFAULT_VERSIONSW = 1L;
    private static final Long UPDATED_VERSIONSW = 2L;

    private static final Long DEFAULT_VERSIONHW = 1L;
    private static final Long UPDATED_VERSIONHW = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_DETECTOR = "AAAAAAAAAA";
    private static final String UPDATED_DETECTOR = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_FACTOR = new BigDecimal(2);

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    private static final String DEFAULT_CMP = "AAAAAAAAAA";
    private static final String UPDATED_CMP = "BBBBBBBBBB";

    private static final String DEFAULT_DUTY = "AAAAAAAAAA";
    private static final String UPDATED_DUTY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_VOLTAGE = 1D;
    private static final Double UPDATED_VOLTAGE = 2D;

    private static final BigDecimal DEFAULT_PRESSURE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRESSURE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HUMIDITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_HUMIDITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LUMINOSITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_LUMINOSITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CO_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_2 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CH_2_O = new BigDecimal(1);
    private static final BigDecimal UPDATED_CH_2_O = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PM_25 = new BigDecimal(1);
    private static final BigDecimal UPDATED_PM_25 = new BigDecimal(2);

    @Autowired
    private UradMonitorRepository uradMonitorRepository;

    @Autowired
    private UradMonitorMapper uradMonitorMapper;

    @Autowired
    private UradMonitorService uradMonitorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUradMonitorMockMvc;

    private UradMonitor uradMonitor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        UradMonitorResource uradMonitorResource = new UradMonitorResource(uradMonitorService);
//        this.restUradMonitorMockMvc = MockMvcBuilders.standaloneSetup(uradMonitorResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UradMonitor createEntity(EntityManager em) {
        UradMonitor uradMonitor = new UradMonitor()
            .unit_id(DEFAULT_UNIT_ID)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .speed(DEFAULT_SPEED)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .versionsw(DEFAULT_VERSIONSW)
            .versionhw(DEFAULT_VERSIONHW)
            .status(DEFAULT_STATUS)
            .mobile(DEFAULT_MOBILE)
            .detector(DEFAULT_DETECTOR)
            .factor(DEFAULT_FACTOR)
            .temperature(DEFAULT_TEMPERATURE)
            .cmp(DEFAULT_CMP)
            .duty(DEFAULT_DUTY)
            .time(DEFAULT_TIME)
            .voltage(DEFAULT_VOLTAGE)
            .pressure(DEFAULT_PRESSURE)
            .humidity(DEFAULT_HUMIDITY)
            .luminosity(DEFAULT_LUMINOSITY)
            .co2(DEFAULT_CO_2)
            .ch2o(DEFAULT_CH_2_O)
            .pm_25(DEFAULT_PM_25);
        return uradMonitor;
    }

    @Before
    public void initTest() {
        uradMonitor = createEntity(em);
    }

    @Test
    @Transactional
    public void createUradMonitor() throws Exception {
        int databaseSizeBeforeCreate = uradMonitorRepository.findAll().size();

        // Create the UradMonitor
        UradMonitorDTO uradMonitorDTO = uradMonitorMapper.toDto(uradMonitor);
        restUradMonitorMockMvc.perform(post("/api/urad-monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uradMonitorDTO)))
            .andExpect(status().isCreated());

        // Validate the UradMonitor in the database
        List<UradMonitor> uradMonitorList = uradMonitorRepository.findAll();
        assertThat(uradMonitorList).hasSize(databaseSizeBeforeCreate + 1);
        UradMonitor testUradMonitor = uradMonitorList.get(uradMonitorList.size() - 1);
        assertThat(testUradMonitor.getUnit_id()).isEqualTo(DEFAULT_UNIT_ID);
        assertThat(testUradMonitor.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testUradMonitor.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testUradMonitor.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testUradMonitor.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testUradMonitor.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testUradMonitor.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testUradMonitor.getVersionsw()).isEqualTo(DEFAULT_VERSIONSW);
        assertThat(testUradMonitor.getVersionhw()).isEqualTo(DEFAULT_VERSIONHW);
        assertThat(testUradMonitor.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUradMonitor.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUradMonitor.getDetector()).isEqualTo(DEFAULT_DETECTOR);
        assertThat(testUradMonitor.getFactor()).isEqualTo(DEFAULT_FACTOR);
        assertThat(testUradMonitor.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testUradMonitor.getCmp()).isEqualTo(DEFAULT_CMP);
        assertThat(testUradMonitor.getDuty()).isEqualTo(DEFAULT_DUTY);
        assertThat(testUradMonitor.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testUradMonitor.getVoltage()).isEqualTo(DEFAULT_VOLTAGE);
        assertThat(testUradMonitor.getPressure()).isEqualTo(DEFAULT_PRESSURE);
        assertThat(testUradMonitor.getHumidity()).isEqualTo(DEFAULT_HUMIDITY);
        assertThat(testUradMonitor.getLuminosity()).isEqualTo(DEFAULT_LUMINOSITY);
        assertThat(testUradMonitor.getCo2()).isEqualTo(DEFAULT_CO_2);
        assertThat(testUradMonitor.getCh2o()).isEqualTo(DEFAULT_CH_2_O);
        assertThat(testUradMonitor.getPm_25()).isEqualTo(DEFAULT_PM_25);
    }

    @Test
    @Transactional
    public void createUradMonitorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uradMonitorRepository.findAll().size();

        // Create the UradMonitor with an existing ID
        uradMonitor.setId(1L);
        UradMonitorDTO uradMonitorDTO = uradMonitorMapper.toDto(uradMonitor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUradMonitorMockMvc.perform(post("/api/urad-monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uradMonitorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UradMonitor> uradMonitorList = uradMonitorRepository.findAll();
        assertThat(uradMonitorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUradMonitors() throws Exception {
        // Initialize the database
        uradMonitorRepository.saveAndFlush(uradMonitor);

        // Get all the uradMonitorList
        restUradMonitorMockMvc.perform(get("/api/urad-monitors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uradMonitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit_id").value(hasItem(DEFAULT_UNIT_ID.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.toString())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].versionsw").value(hasItem(DEFAULT_VERSIONSW.intValue())))
            .andExpect(jsonPath("$.[*].versionhw").value(hasItem(DEFAULT_VERSIONHW.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].detector").value(hasItem(DEFAULT_DETECTOR.toString())))
            .andExpect(jsonPath("$.[*].factor").value(hasItem(DEFAULT_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].cmp").value(hasItem(DEFAULT_CMP.toString())))
            .andExpect(jsonPath("$.[*].duty").value(hasItem(DEFAULT_DUTY.toString())))
//            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].voltage").value(hasItem(DEFAULT_VOLTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].pressure").value(hasItem(DEFAULT_PRESSURE.intValue())))
            .andExpect(jsonPath("$.[*].humidity").value(hasItem(DEFAULT_HUMIDITY.intValue())))
            .andExpect(jsonPath("$.[*].luminosity").value(hasItem(DEFAULT_LUMINOSITY.intValue())))
            .andExpect(jsonPath("$.[*].co2").value(hasItem(DEFAULT_CO_2.intValue())))
            .andExpect(jsonPath("$.[*].ch2o").value(hasItem(DEFAULT_CH_2_O.intValue())))
            .andExpect(jsonPath("$.[*].pm_25").value(hasItem(DEFAULT_PM_25.intValue())));
    }

    @Test
    @Transactional
    public void getUradMonitor() throws Exception {
        // Initialize the database
        uradMonitorRepository.saveAndFlush(uradMonitor);

        // Get the uradMonitor
        restUradMonitorMockMvc.perform(get("/api/urad-monitors/{id}", uradMonitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uradMonitor.getId().intValue()))
            .andExpect(jsonPath("$.unit_id").value(DEFAULT_UNIT_ID.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.toString()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.versionsw").value(DEFAULT_VERSIONSW.intValue()))
            .andExpect(jsonPath("$.versionhw").value(DEFAULT_VERSIONHW.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.detector").value(DEFAULT_DETECTOR.toString()))
            .andExpect(jsonPath("$.factor").value(DEFAULT_FACTOR.intValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.toString()))
            .andExpect(jsonPath("$.cmp").value(DEFAULT_CMP.toString()))
            .andExpect(jsonPath("$.duty").value(DEFAULT_DUTY.toString()))
//            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.voltage").value(DEFAULT_VOLTAGE.doubleValue()))
            .andExpect(jsonPath("$.pressure").value(DEFAULT_PRESSURE.intValue()))
            .andExpect(jsonPath("$.humidity").value(DEFAULT_HUMIDITY.intValue()))
            .andExpect(jsonPath("$.luminosity").value(DEFAULT_LUMINOSITY.intValue()))
            .andExpect(jsonPath("$.co2").value(DEFAULT_CO_2.intValue()))
            .andExpect(jsonPath("$.ch2o").value(DEFAULT_CH_2_O.intValue()))
            .andExpect(jsonPath("$.pm_25").value(DEFAULT_PM_25.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUradMonitor() throws Exception {
        // Get the uradMonitor
        restUradMonitorMockMvc.perform(get("/api/urad-monitors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUradMonitor() throws Exception {
        // Initialize the database
        uradMonitorRepository.saveAndFlush(uradMonitor);
        int databaseSizeBeforeUpdate = uradMonitorRepository.findAll().size();

        // Update the uradMonitor
        UradMonitor updatedUradMonitor = uradMonitorRepository.findOne(uradMonitor.getId());
        updatedUradMonitor
            .unit_id(UPDATED_UNIT_ID)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .altitude(UPDATED_ALTITUDE)
            .speed(UPDATED_SPEED)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .versionsw(UPDATED_VERSIONSW)
            .versionhw(UPDATED_VERSIONHW)
            .status(UPDATED_STATUS)
            .mobile(UPDATED_MOBILE)
            .detector(UPDATED_DETECTOR)
            .factor(UPDATED_FACTOR)
            .temperature(UPDATED_TEMPERATURE)
            .cmp(UPDATED_CMP)
            .duty(UPDATED_DUTY)
            .time(UPDATED_TIME)
            .voltage(UPDATED_VOLTAGE)
            .pressure(UPDATED_PRESSURE)
            .humidity(UPDATED_HUMIDITY)
            .luminosity(UPDATED_LUMINOSITY)
            .co2(UPDATED_CO_2)
            .ch2o(UPDATED_CH_2_O)
            .pm_25(UPDATED_PM_25);
        UradMonitorDTO uradMonitorDTO = uradMonitorMapper.toDto(updatedUradMonitor);

        restUradMonitorMockMvc.perform(put("/api/urad-monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uradMonitorDTO)))
            .andExpect(status().isOk());

        // Validate the UradMonitor in the database
        List<UradMonitor> uradMonitorList = uradMonitorRepository.findAll();
        assertThat(uradMonitorList).hasSize(databaseSizeBeforeUpdate);
        UradMonitor testUradMonitor = uradMonitorList.get(uradMonitorList.size() - 1);
        assertThat(testUradMonitor.getUnit_id()).isEqualTo(UPDATED_UNIT_ID);
        assertThat(testUradMonitor.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testUradMonitor.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testUradMonitor.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testUradMonitor.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testUradMonitor.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUradMonitor.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testUradMonitor.getVersionsw()).isEqualTo(UPDATED_VERSIONSW);
        assertThat(testUradMonitor.getVersionhw()).isEqualTo(UPDATED_VERSIONHW);
        assertThat(testUradMonitor.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUradMonitor.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUradMonitor.getDetector()).isEqualTo(UPDATED_DETECTOR);
        assertThat(testUradMonitor.getFactor()).isEqualTo(UPDATED_FACTOR);
        assertThat(testUradMonitor.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testUradMonitor.getCmp()).isEqualTo(UPDATED_CMP);
        assertThat(testUradMonitor.getDuty()).isEqualTo(UPDATED_DUTY);
        assertThat(testUradMonitor.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testUradMonitor.getVoltage()).isEqualTo(UPDATED_VOLTAGE);
        assertThat(testUradMonitor.getPressure()).isEqualTo(UPDATED_PRESSURE);
        assertThat(testUradMonitor.getHumidity()).isEqualTo(UPDATED_HUMIDITY);
        assertThat(testUradMonitor.getLuminosity()).isEqualTo(UPDATED_LUMINOSITY);
        assertThat(testUradMonitor.getCo2()).isEqualTo(UPDATED_CO_2);
        assertThat(testUradMonitor.getCh2o()).isEqualTo(UPDATED_CH_2_O);
        assertThat(testUradMonitor.getPm_25()).isEqualTo(UPDATED_PM_25);
    }

    @Test
    @Transactional
    public void updateNonExistingUradMonitor() throws Exception {
        int databaseSizeBeforeUpdate = uradMonitorRepository.findAll().size();

        // Create the UradMonitor
        UradMonitorDTO uradMonitorDTO = uradMonitorMapper.toDto(uradMonitor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUradMonitorMockMvc.perform(put("/api/urad-monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uradMonitorDTO)))
            .andExpect(status().isCreated());

        // Validate the UradMonitor in the database
        List<UradMonitor> uradMonitorList = uradMonitorRepository.findAll();
        assertThat(uradMonitorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUradMonitor() throws Exception {
        // Initialize the database
        uradMonitorRepository.saveAndFlush(uradMonitor);
        int databaseSizeBeforeDelete = uradMonitorRepository.findAll().size();

        // Get the uradMonitor
        restUradMonitorMockMvc.perform(delete("/api/urad-monitors/{id}", uradMonitor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UradMonitor> uradMonitorList = uradMonitorRepository.findAll();
        assertThat(uradMonitorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(UradMonitor.class);
        UradMonitor uradMonitor1 = new UradMonitor();
        uradMonitor1.setId(1L);
        UradMonitor uradMonitor2 = new UradMonitor();
        uradMonitor2.setId(uradMonitor1.getId());
        assertThat(uradMonitor1).isEqualTo(uradMonitor2);
        uradMonitor2.setId(2L);
        assertThat(uradMonitor1).isNotEqualTo(uradMonitor2);
        uradMonitor1.setId(null);
        assertThat(uradMonitor1).isNotEqualTo(uradMonitor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
//        TestUtil.equalsVerifier(UradMonitorDTO.class);
        UradMonitorDTO uradMonitorDTO1 = new UradMonitorDTO();
        uradMonitorDTO1.setId(1L);
        UradMonitorDTO uradMonitorDTO2 = new UradMonitorDTO();
        assertThat(uradMonitorDTO1).isNotEqualTo(uradMonitorDTO2);
        uradMonitorDTO2.setId(uradMonitorDTO1.getId());
        assertThat(uradMonitorDTO1).isEqualTo(uradMonitorDTO2);
        uradMonitorDTO2.setId(2L);
        assertThat(uradMonitorDTO1).isNotEqualTo(uradMonitorDTO2);
        uradMonitorDTO1.setId(null);
        assertThat(uradMonitorDTO1).isNotEqualTo(uradMonitorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uradMonitorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uradMonitorMapper.fromId(null)).isNull();
    }
}
