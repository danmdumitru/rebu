package rebu.service;

import rebu.domain.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Exercise.
 */
public interface ExerciseService {

    /**
     * Save a exercise.
     *
     * @param exercise the entity to save
     * @return the persisted entity
     */
    Exercise save(Exercise exercise);

    /**
     *  Get all the exercises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Exercise> findAll(Pageable pageable);

    /**
     *  Get the "id" exercise.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Exercise findOne(Long id);

    /**
     *  Delete the "id" exercise.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
