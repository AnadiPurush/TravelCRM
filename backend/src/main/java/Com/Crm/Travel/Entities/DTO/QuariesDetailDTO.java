package Com.Crm.Travel.Entities.DTO;

import Com.Crm.Travel.common.enums.QueriesPriority;
import Com.Crm.Travel.common.enums.QueriesStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder

public record QuariesDetailDTO(Long serialNumber,
                               String requesterName,
                               List<QueriesCommentDTO> queriesCommentDTOS,
                               String contactNo,
                               String email,
                               List<String> destination,
                               String fromLocation,
                               Date fromDate,
                               Date toDate,
                               Long quotedPrice,
                               List<String> requiredServices,
                               LocalDateTime createdAt,
                               QueriesStatus queriesStatus,
                               QueriesPriority queriesPriority) {

}
