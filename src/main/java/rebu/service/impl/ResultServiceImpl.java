package rebu.service.impl;

import rebu.service.ResultService;
import rebu.domain.Result;
import rebu.repository.ResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Result.
 */
@Service
@Transactional
public class ResultServiceImpl implements ResultService{

    private final Logger log = LoggerFactory.getLogger(ResultServiceImpl.class);
    
    @Inject
    private ResultRepository resultRepository;

    /**
     * Save a result.
     *
     * @param result the entity to save
     * @return the persisted entity
     */
    public Result save(Result result) {
        log.debug("Request to save Result : {}", result);
        Result newResut = resultRepository.save(result);
        return newResut;
    }

    /**
     *  Get all the results.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Result> findAll(Pageable pageable) {
        log.debug("Request to get all Results");
        Page<Result> result = resultRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one result by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Result findOne(Long id) {
        log.debug("Request to get Result : {}", id);
        Result result = resultRepository.findOne(id);
        return result;
    }

    /**
     *  Delete the  result by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Result : {}", id);
        resultRepository.delete(id);
    }
}
