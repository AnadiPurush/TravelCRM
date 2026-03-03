package com.crm.travel.user.organisationHierarchy.mapper;

import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;
import com.crm.travel.user.organisationHierarchy.dto.request.OrgUnitCreateReq;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitResponse;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitTypeResponse;
import com.crm.travel.user.organisationHierarchy.enums.OrgUnitTypes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @file OrgUnitMapper.java
 * Author: Utsav
 * Date: 2/20/26
 *
 */
@AllArgsConstructor
@Component
public class OrgUnitMapper {

    /**
     * This method maps the request to the domain but validation is due for the developer to do in service layer to setParent to maintain the valid hierarchy
     *
     * @param orgUnitCreateReq the request object contains name, type and parentId
     * @return an instance of OrgUnit domain object
     */
    public OrgUnit OrgUnitCreateMapper(OrgUnitCreateReq orgUnitCreateReq) {
        String rawName = orgUnitCreateReq.name();
        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setName(rawName);
        orgUnit.setOrgUnitTypes(orgUnitCreateReq.type());
        return orgUnit;

    }


    /**
     * This method is used to map OrgUnit entity to OrgUnitResponse DTO.
     * It can be used to fetch all the orgUnits from the repo and map it to response DTO.
     *
     * @param entity OrgUnit entity
     * @return org.crm.travel.user.organisationHierarchy.dto.response.OrgUnitResponse
     */
    public OrgUnitResponse mapOrgUnitToResponse(OrgUnit entity) {
        return OrgUnitResponse.builder()
                .id(entity.getId())
                .value(entity.getOrgUnitTypes().name())
                .displayName(entity.getName())
                .parentName(
                        entity.getParent() != null
                                ? entity.getParent().getName()
                                : null
                )
                .childName(
                        entity.getChildren() != null
                                ? entity.getChildren()
                                .stream()
                                .map(OrgUnit::getName)
                                .toList()
                                : List.of()
                )
                .build();
    }

    /**
     * Maps all available {@link OrgUnitTypes} enum constants
     * to a list of {@link OrgUnitTypeResponse} DTO objects.
     *
     * @return immutable list of {@link OrgUnitTypeResponse}
     * representing all defined {@link OrgUnitTypes}
     */
    public List<OrgUnitTypeResponse> mapOrgUnitsTypes() {
        return Arrays.stream(OrgUnitTypes.values())
                .map(level -> OrgUnitTypeResponse
                        .builder().displayName(level.getDisplayName()).value(level.name()).hasParent(level.isParentLevel()).build())
                .toList();
    }


}
