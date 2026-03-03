package com.crm.travel.user.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

/**
 * @file UserEntityResponse.java
 * Author: Utsav
 * Date: 2/23/26
 *
 */
@Builder
public record UserEntityResponse(String name,
                                 UUID id,
                                 String email,
                                 String role,
                                 String managerName,
                                 String orgUnitName,
                                 String orgUnitType,
                                 List<String> secondaryManagers,
                                 List<String> permissions

) {
}
