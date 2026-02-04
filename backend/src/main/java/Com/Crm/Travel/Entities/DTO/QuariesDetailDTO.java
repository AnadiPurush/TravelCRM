package Com.Crm.Travel.Entities.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import Com.Crm.Travel.common.enums.QuariesPriority;
import Com.Crm.Travel.common.enums.QuariesStatus;
import lombok.Builder;

@Builder

public record QuariesDetailDTO(Long serialNumber,
        String requesterName,
        String contactNo,
        String email,
        ArrayList<String> destination,
        String fromLocation,
        Date fromDate,
        Date toDate,
        Long quotedPrice,
        ArrayList<String> requiredServices,
        LocalDateTime createdAt,
        QuariesStatus quariesStatus,
        QuariesPriority quariesPriority) {

}
