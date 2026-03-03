package com.crm.travel.user.dto.response;

import lombok.Builder;

/**
 * @file PermissionsResponse.java
 * Author: Utsav
 * Date: 2/23/26
 *
 */
@Builder
public record PermissionsResponse(String value,
                                  String displayName) {
}
