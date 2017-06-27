package rebu.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiParam;
import rebu.repository.UradMonitorRepository;
import rebu.service.UradMonitorService;
import rebu.service.dto.UradMonitorDTO;
import rebu.web.rest.util.HeaderUtil;
import rebu.web.rest.util.PaginationUtil;

/**
 * REST controller for managing UradMonitor.
 */
@RestController
@RequestMapping("/api")
public class UradMonitorResource {

    private final Logger log = LoggerFactory.getLogger(UradMonitorResource.class);

    private static final String ENTITY_NAME = "uradMonitor";

    private final UradMonitorService uradMonitorService;
    
    private final UradMonitorRepository uradMonitorRepository;

    public UradMonitorResource(UradMonitorService uradMonitorService, UradMonitorRepository uradMonitorRepository) {
        this.uradMonitorService = uradMonitorService;
        this.uradMonitorRepository = uradMonitorRepository;
    }

    /**
     * POST  /urad-monitors : Create a new uradMonitor.
     *
     * @param uradMonitorDTO the uradMonitorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uradMonitorDTO, or with status 400 (Bad Request) if the uradMonitor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/urad-monitors")
    @Timed
    public ResponseEntity<UradMonitorDTO> createUradMonitor(@RequestBody ArrayList<UradMonitorDTO> uradMonitorDTOs) throws URISyntaxException {
        log.debug("REST request to save UradMonitor : {}", uradMonitorDTOs);
        UradMonitorDTO result = new UradMonitorDTO();
        for (UradMonitorDTO uradMonitorDTO : uradMonitorDTOs) {
        	if (uradMonitorDTO.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new uradMonitor cannot already have an ID")).body(null);
            }
//        	result = uradMonitorService.findOne(569L);
            
            result = uradMonitorService.save(uradMonitorDTO);
            
		}
        return ResponseEntity.created(new URI("/api/urad-monitors/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    
    /**
     * POST  /urad-monitors/:id : get the "id" uradMonitor.
     *
     * @param id the id of the uradMonitorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uradMonitorDTO, or with status 404 (Not Found)
     */
    @PostMapping("/urad-monitors/forecast")
    @Timed
    public ResponseEntity<UradMonitorDTO> forecastUradMonitor(@RequestBody UradMonitorDTO forecastUrad) {
    	
        log.debug("REST request to get FORECAST UradMonitor : {}", forecastUrad.getUnitId());
        UradMonitorDTO uradMonitorDTO = new UradMonitorDTO(uradMonitorRepository.findTop1ByUnitIdOrderByIdDesc(forecastUrad.getUnitId()));  
        
        long firstNewRecordFromDb = uradMonitorDTO.getId() - forecastUrad.getId() + 1;
        RConnection connection = null;
        UradMonitorDTO result = new UradMonitorDTO();
        try {
             /*Create a connection to Rserve instance running on default port
             * 6311*/
             
            connection = new RConnection();
            connection.eval("source('C:\\\\Users\\\\DumitruD\\\\Desktop\\\\liniarRegressionWithDB.R')");
            
            if ("co2".equals(forecastUrad.getDetector())) {
            	forecastUrad.setDetector("co_2");
			}else if ("ch2o".equals(forecastUrad.getDetector())) {
				forecastUrad.setDetector("ch_2_o");
			}else if ("pm25".equals(forecastUrad.getDetector())) {
				forecastUrad.setDetector("pm_25");
			}
            System.out.println("forecastUrad.getUnitId()= " + forecastUrad.getUnitId());
            System.out.println("forecastUrad.getDetector()= " + forecastUrad.getDetector());
            System.out.println("firstNewRecordFromDb= "+ firstNewRecordFromDb);
            try {
            	result = new UradMonitorDTO(connection.eval("predictionFromDB("+forecastUrad.getUnitId()+","+forecastUrad.getDetector()+","+firstNewRecordFromDb+")").asDoubles());
			} catch (REXPMismatchException e) {
				e.printStackTrace();
			}finally{
	            connection.close();
		        }
        } catch (RserveException e) {
            e.printStackTrace();
        } 
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
    
    /**
     * PUT  /urad-monitors : Updates an existing uradMonitor.
     *
     * @param uradMonitorDTO the uradMonitorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uradMonitorDTO,
     * or with status 400 (Bad Request) if the uradMonitorDTO is not valid,
     * or with status 500 (Internal Server Error) if the uradMonitorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/urad-monitors")
    @Timed
    public ResponseEntity<UradMonitorDTO> updateUradMonitor(@RequestBody UradMonitorDTO uradMonitorDTO) throws URISyntaxException {
        log.debug("REST request to update UradMonitor : {}", uradMonitorDTO);
//        if (uradMonitorDTO.getId() == null) {
//            return createUradMonitor(uradMonitorDTO);
//        }
        UradMonitorDTO result = uradMonitorService.save(uradMonitorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uradMonitorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urad-monitors : get all the uradMonitors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uradMonitors in body
     * @throws URISyntaxException 
     */
    @GetMapping("/urad-monitors")
    @Timed
    public ResponseEntity<List<UradMonitorDTO>> getAllUradMonitors(@ApiParam Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of UradMonitors");
        Page<UradMonitorDTO> page = uradMonitorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/urad-monitors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /urad-monitors/:id : get the "id" uradMonitor.
     *
     * @param id the id of the uradMonitorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uradMonitorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/urad-monitors/{id}")
    @Timed
    public ResponseEntity<UradMonitorDTO> getUradMonitor(@PathVariable Long id) {
        log.debug("REST request to get UradMonitor : {}", id);
        UradMonitorDTO uradMonitorDTO = uradMonitorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uradMonitorDTO));
    }

    /**
     * DELETE  /urad-monitors/:id : delete the "id" uradMonitor.
     *
     * @param id the id of the uradMonitorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/urad-monitors/{id}")
    @Timed
    public ResponseEntity<Void> deleteUradMonitor(@PathVariable Long id) {
        log.debug("REST request to delete UradMonitor : {}", id);
        uradMonitorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
