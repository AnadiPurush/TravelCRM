package Com.Crm.Travel.Entities.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Com.Crm.Travel.common.enums.QuariesPriority;
import Com.Crm.Travel.common.enums.QuariesStatus;
import lombok.Builder;

@Builder

public record QuariesDetailDTO(Long serialNumber,
        String requesterName,
        List<QueriesCommentDTO> quariesCommentDTOS,
        String contactNo,
        String email,
        List<String> destination,
        String fromLocation,
        Date fromDate,
        Date toDate,
        Long quotedPrice,
        List<String> requiredServices,
        LocalDateTime createdAt,
        QuariesStatus quariesStatus,
        QuariesPriority quariesPriority) {

}
