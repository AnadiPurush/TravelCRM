package com.crm.travel.user.mapper;

import com.crm.travel.user.dto.response.OrgUnitResponse;
import com.crm.travel.user.enums.OrgUnitTypes;

import java.util.Arrays;
import java.util.List;

/**
 * @file mapOrgUnits.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */
public class UserResponseMapper {
    public static List<OrgUnitResponse> mapOrgUnitsTypes() {

        return Arrays.stream(OrgUnitTypes.values())
                .map(level -> new OrgUnitResponse(
                        level.name(),
                        level.getDisplayName(),
                        level.isParentLevel(),
                        level.getParentLevel()
                ))
                .toList();
    }

}
