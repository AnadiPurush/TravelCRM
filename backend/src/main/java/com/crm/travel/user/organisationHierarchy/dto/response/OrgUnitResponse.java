package com.crm.travel.user.organisationHierarchy.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

/**
 * @file OrgUnitResponse.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */

/**
 * A data transfer object that represents a unit of organization.
 * <p>
 * An {@code OrgUnitResponse} object contains the following fields:
 * <ul>
 *     <li>{@code id} - the unique identifier of the organization unit</li>
 *     <li>{@code value} - the value of the organization unit</li>
 *     <li>{@code displayName} - the display name of the organization unit</li>
 *     <li>{@code parentName} - the name of the parent organization unit, or {@code null} if the unit has no parent</li>
 *     <li>{@code childName} - a list of names of child organization units, or an empty list if the unit has no children</li>
 * </ul>
 *
 * @see OrgUnitTypeResponse
 */
@Builder
public record OrgUnitResponse(UUID id,
                              String value,
                              String displayName,
                              String parentName,
                              List<String> childName) {
}
