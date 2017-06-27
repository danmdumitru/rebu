package rebu.service;

import rebu.service.dto.UradMonitorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UradMonitor.
 */
public interface UradMonitorService {

    /**
     * Save a uradMonitor.
     *
     * @param uradMonitorDTO the entity to save
     * @return the persisted entity
     */
    UradMonitorDTO save(UradMonitorDTO uradMonitorDTO);

    /**
     *  Get all the uradMonitors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UradMonitorDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" uradMonitor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UradMonitorDTO findOne(Long id);

    /**
     *  Delete the "id" uradMonitor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
