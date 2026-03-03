package com.crm.travel.user.organisationHierarchy.service;

import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;
import com.crm.travel.user.organisationHierarchy.dto.request.OrgUnitCreateReq;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitResponse;
import com.crm.travel.user.organisationHierarchy.enums.OrgUnitTypes;
import com.crm.travel.user.organisationHierarchy.mapper.OrgUnitMapper;
import com.crm.travel.user.organisationHierarchy.repository.OrgUnitRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @file OrgUnitServiceImpl.java
 * Author: Utsav
 * Date: 2/20/26
 *
 */
@Service
public class OrgUnitServiceImpl implements OrgUnitService {
    private final OrgUnitRepo orgUnitRepo;
    private final OrgUnitMapper orgUnitMapper;

    public OrgUnitServiceImpl(OrgUnitRepo orgUnitRepo, OrgUnitMapper orgUnitMapper) {
        this.orgUnitRepo = orgUnitRepo;
        this.orgUnitMapper = orgUnitMapper;
    }

    @Override
    @Transactional
    public OrgUnitResponse createOrgUnit(OrgUnitCreateReq request) {
        OrgUnit orgUnit = orgUnitMapper.OrgUnitCreateMapper(request);
        validateAndAssignParent(request, orgUnit);
        try {
            return orgUnitMapper.mapOrgUnitToResponse(orgUnitRepo.save(orgUnit));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("OrgUnit already exists under same parent");
        }

    }


    @Override
    public List<OrgUnit> getAllOrgUnits() {
        return orgUnitRepo.findAll();
    }

    @Override
    @Transactional
    public OrgUnitResponse updateOrgUnit(OrgUnitCreateReq req) {
        OrgUnit orgUnit1 = orgUnitRepo.findById(req.id()).orElseThrow(() -> new EntityNotFoundException("OrgUnit not found"));

        if (!orgUnit1.getNormalizedName().equals(req.name().trim().toUpperCase().replaceAll("[\\s_-]+", ""))) {
            orgUnit1.setName(req.name());
        }
        orgUnit1.setOrgUnitTypes(req.type());
        validateAndAssignParent(req, orgUnit1);
        return orgUnitMapper.mapOrgUnitToResponse(orgUnit1);
    }

    private void validateAndAssignParent(OrgUnitCreateReq req, OrgUnit orgUnit1) {
        if (req.parentId() == null) {
            if (req.type().requiresParent()) {
                throw new RequestRejectedException(req.type() + " requires a parent");
            }

        } else {
            OrgUnit parent = orgUnitRepo.findById(req.parentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
            if (!OrgUnitTypes.isValidParent(req.type(), parent.getOrgUnitTypes())) {
                throw new IllegalArgumentException("Invalid parent");
            }
            orgUnit1.setParent(parent);
        }
    }
}
