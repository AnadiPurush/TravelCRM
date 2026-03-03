package com.crm.travel.user.organisationHierarchy.dto.response;

import com.crm.travel.user.organisationHierarchy.enums.OrgUnitTypes;
import lombok.Builder;

/**
 * @file OrgUnitTypeResponse.java
 * Author: Utsav
 * Date: 2/20/26
 *
 */

/**
 * A data transfer object that represents a single {@link OrgUnitTypes} enum constant
 * with additional information.
 * <p>
 * An {@code OrgUnitTypeResponse} object contains the following fields:
 * <ul>
 *     <li>{@code value} - the name of the {@link OrgUnitTypes} enum constant.</li>
 *     <li>{@code displayName} - the human-readable label of the {@link OrgUnitTypes} enum constant.</li>
 *     <li>{@code hasParent} - a flag indicating whether the {@link OrgUnitTypes} enum constant has a parent.</li>
 *     <li>{@code parent} - the parent {@link OrgUnitTypes} enum constant, or {@code null} if the enum constant has no parent.</li>
 * </ul>
 *
 * @see OrgUnitResponse
 */
@Builder
public record OrgUnitTypeResponse(
        String value,          // Enum name (CEO, DEPARTMENT...)
        String displayName,    // Human-readable label
        boolean hasParent,
        OrgUnitTypes parent) {
}
