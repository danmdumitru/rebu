package rebu.service.impl;

import rebu.service.TrainingService;
import rebu.domain.Training;
import rebu.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Training.
 */
@Service
@Transactional
public class TrainingServiceImpl implements TrainingService{

    private final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);
    
    @Inject
    private TrainingRepository trainingRepository;

    /**
     * Save a training.
     *
     * @param training the entity to save
     * @return the persisted entity
     */
    public Training save(Training training) {
        log.debug("Request to save Training : {}", training);
        Training result = trainingRepository.save(training);
        return result;
    }

    /**
     *  Get all the trainings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Training> findAll(Pageable pageable) {
        log.debug("Request to get all Trainings");
        Page<Training> result = trainingRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one training by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Training findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        Training training = trainingRepository.findOne(id);
        return training;
    }

    /**
     *  Delete the  training by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.delete(id);
    }
}
