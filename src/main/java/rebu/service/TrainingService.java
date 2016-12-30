package rebu.service;

import rebu.domain.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Training.
 */
public interface TrainingService {

    /**
     * Save a training.
     *
     * @param training the entity to save
     * @return the persisted entity
     */
    Training save(Training training);

    /**
     *  Get all the trainings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Training> findAll(Pageable pageable);

    /**
     *  Get the "id" training.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Training findOne(Long id);

    /**
     *  Delete the "id" training.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
