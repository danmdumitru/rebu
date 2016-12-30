package rebu.service;

import rebu.domain.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Result.
 */
public interface ResultService {

    /**
     * Save a result.
     *
     * @param result the entity to save
     * @return the persisted entity
     */
    Result save(Result result);

    /**
     *  Get all the results.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Result> findAll(Pageable pageable);

    /**
     *  Get the "id" result.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Result findOne(Long id);

    /**
     *  Delete the "id" result.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
