package rebu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rebu.domain.UradMonitor;


/**
 * Spring Data JPA repository for the UradMonitor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UradMonitorRepository extends JpaRepository<UradMonitor,Long> {
	UradMonitor findTop1ByUnitIdOrderByIdDesc( String unitId);
}
