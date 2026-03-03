package com.crm.travel.user.organisationHierarchy.dto.request;

import com.crm.travel.user.organisationHierarchy.enums.OrgUnitTypes;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

/**
 * @file OrgUnitCreateReq.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */
// Handle request data than map it to entity OrgUnit
public record OrgUnitCreateReq(@Nullable UUID id,
                               @NotBlank String name,
                               @NotBlank OrgUnitTypes type,
                               UUID parentId) {
}
