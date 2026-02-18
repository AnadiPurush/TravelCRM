package com.crm.travel.query.dto.request;

import com.crm.travel.query.enums.QueriesPriority;
import com.crm.travel.query.enums.QueriesStatus;

import java.util.Date;
import java.util.List;

public record QueryRequest(Long serialNumber,
                           String requesterName,
                           String contactNo,
                           String email,
                           List<String> destination,
                           String fromLocation,
                           Date fromDate,
                           Date toDate,
                           Long quotedPrice,
                           List<String> requiredServices,
                           QueriesStatus queriesStatus,
                           QueriesPriority queriesPriority) {
}
