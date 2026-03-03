package com.crm.travel.user.dto.response;

/**
 * @file RolesResponse.java
 * Author: Utsav
 * Date: 2/20/26
 *
 */
public record RolesResponse(
        String value,
        String displayName,
        int level
) {
}
