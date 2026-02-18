package com.crm.travel.user.dto.response;

/**
 * @file OrgUnitResponse.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */
public record OrgUnitResponse(String value,
                              String displayName,
                              boolean requireParent,
                              String requiresParentLevel) {
}
