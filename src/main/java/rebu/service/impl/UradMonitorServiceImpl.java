package rebu.service.impl;

import rebu.service.UradMonitorService;
import rebu.domain.UradMonitor;
import rebu.repository.UradMonitorRepository;
import rebu.service.dto.UradMonitorDTO;
import rebu.service.mapper.UradMonitorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UradMonitor.
 */
@Service
@Transactional
public class UradMonitorServiceImpl implements UradMonitorService{

    private final Logger log = LoggerFactory.getLogger(UradMonitorServiceImpl.class);

    private final UradMonitorRepository uradMonitorRepository;

    private final UradMonitorMapper uradMonitorMapper;

    public UradMonitorServiceImpl(UradMonitorRepository uradMonitorRepository, UradMonitorMapper uradMonitorMapper) {
        this.uradMonitorRepository = uradMonitorRepository;
        this.uradMonitorMapper = uradMonitorMapper;
    }

    /**
     * Save a uradMonitor.
     *
     * @param uradMonitorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UradMonitorDTO save(UradMonitorDTO uradMonitorDTO) {
        log.debug("Request to save UradMonitor : {}", uradMonitorDTO);
        UradMonitor uradMonitor = uradMonitorMapper.toEntity(uradMonitorDTO);
        uradMonitor = uradMonitorRepository.save(uradMonitor);
        return uradMonitorMapper.toDto(uradMonitor);
    }

    /**
     *  Get all the uradMonitors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UradMonitorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UradMonitors");
        return uradMonitorRepository.findAll(pageable)
            .map(uradMonitorMapper::toDto);
    }

    /**
     *  Get one uradMonitor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UradMonitorDTO findOne(Long id) {
        log.debug("Request to get UradMonitor : {}", id);
        UradMonitor uradMonitor = uradMonitorRepository.findOne(id);
        return uradMonitorMapper.toDto(uradMonitor);
    }

    /**
     *  Delete the  uradMonitor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UradMonitor : {}", id);
        uradMonitorRepository.delete(id);
    }
}
