package com.crm.travel.user.organisationHierarchy.controller;

import com.crm.travel.common.globalinterface.ApiController;
import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;
import com.crm.travel.user.organisationHierarchy.dto.request.OrgUnitCreateReq;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitResponse;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitTypeResponse;
import com.crm.travel.user.organisationHierarchy.mapper.OrgUnitMapper;
import com.crm.travel.user.organisationHierarchy.service.OrgUnitService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @file OrgUnitController.java
 * Author: Utsav
 * Date: 2/20/26
 *
 */
@RestController
@ApiController
@AllArgsConstructor
public class OrgUnitController {
    private final OrgUnitMapper orgUnitMapper;
    private final OrgUnitService orgUnitService;


    // endpoint to get all org units and send to frontend when admin is creating new user
    @GetMapping("response/v1/org-units")
    public List<OrgUnitResponse> responseOrgUnit() {
        List<OrgUnit> allOrgUnits = orgUnitService.getAllOrgUnits();
        return allOrgUnits.stream().map(orgUnitMapper::mapOrgUnitToResponse).toList();
    }

    // endpoint to create new org unit
//    Generic Exception Handling need to be replaced with custom exception handling more distinct DuplicateOrgUnitException → violates uniqueness invariant
//
//InvalidHierarchyException → violates structural invariant
//
//ParentRequiredException → violates mandatory relationship invariant
    @PreAuthorize("hasRole('SUPERADMIN') or hasAuthority('SYSTEM_CONFIG')")
    @PostMapping("request/v1/org-units")
    public ResponseEntity<?> createOrgUnit(@RequestBody OrgUnitCreateReq orgUnitCreateReq) {
        try {
            var orgUnitResponse = orgUnitService.createOrgUnit(orgUnitCreateReq);
            return ResponseEntity.ok(orgUnitResponse);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "OrgUnit already exists under same parent"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid parent"));
        } catch (RequestRejectedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Parent Required"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Parent not found"));
        }
    }

    // endpoint to get all org unit types and send to frontend when admin is creating new org unit

    @GetMapping("response/v1/org-units-types")
    public List<OrgUnitTypeResponse> responseOrgUnitTypes() {
        return orgUnitMapper.mapOrgUnitsTypes();
    }


    /**
     * Endpoint to update an existing OrgUnit. The request body should contain the updated OrgUnit data or Default data.
     *
     * @param orgUnitCreateReq the updated OrgUnit data
     * @return the updated OrgUnit data as a ResponseEntity
     */
    @PreAuthorize("hasRole('SUPERADMIN') or hasAuthority('SYSTEM_CONFIG')")
    @PatchMapping("request/v1/update/org-unit")
    public ResponseEntity<?> updateOrgUnit(@RequestBody OrgUnitCreateReq orgUnitCreateReq) {
        try {
            return ResponseEntity.ok(orgUnitService.updateOrgUnit(orgUnitCreateReq));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "OrgUnit already exists under same parent"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid parent"));
        } catch (RequestRejectedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Parent Required"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Parent not found"));
        }
    }
}
