package rebu.service.mapper;

import rebu.domain.*;
import rebu.service.dto.UradMonitorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UradMonitor and its DTO UradMonitorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UradMonitorMapper extends EntityMapper <UradMonitorDTO, UradMonitor> {
    
    
    default UradMonitor fromId(Long id) {
        if (id == null) {
            return null;
        }
        UradMonitor uradMonitor = new UradMonitor();
        uradMonitor.setId(id);
        return uradMonitor;
    }
}
