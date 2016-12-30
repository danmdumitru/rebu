package rebu.repository;

import rebu.domain.Training;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Training entity.
 */
@SuppressWarnings("unused")
public interface TrainingRepository extends JpaRepository<Training,Long> {

    @Query("select training from Training training where training.user.login = ?#{principal.username}")
    List<Training> findByUserIsCurrentUser();

}
