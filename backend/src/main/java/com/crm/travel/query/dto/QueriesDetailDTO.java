package com.crm.travel.query.dto;

import com.crm.travel.query.comment.dto.QueriesCommentDTO;
import com.crm.travel.query.enums.QueriesPriority;
import com.crm.travel.query.enums.QueriesStatus;
import com.crm.travel.user.dto.response.AssignedUserDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder

public record QueriesDetailDTO(Long serialNumber,
                               String requesterName,
                               String contactNo,
                               String email,
                               List<QueriesCommentDTO> queriesCommentDTOS,
                               List<String> destination,
                               String fromLocation,
                               Date fromDate,
                               Date toDate,
                               Long quotedPrice,
                               List<String> requiredServices,
                               LocalDateTime createdAt,
                               QueriesStatus queriesStatus,
                               QueriesPriority queriesPriority,
                               List<AssignedUserDTO> queryAssignmentHelper) {

}
