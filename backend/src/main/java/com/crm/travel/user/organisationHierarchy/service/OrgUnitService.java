package com.crm.travel.user.organisationHierarchy.service;

import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;
import com.crm.travel.user.organisationHierarchy.dto.request.OrgUnitCreateReq;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @file OrgUnitService.java
 * Author: Utsav
 * Date: 2/20/26
 *
 */

public interface OrgUnitService {
    @Transactional
    OrgUnitResponse createOrgUnit(OrgUnitCreateReq orgUnitCreateReq);

    List<OrgUnit> getAllOrgUnits();

    @Transactional
    OrgUnitResponse updateOrgUnit(OrgUnitCreateReq req);
}
