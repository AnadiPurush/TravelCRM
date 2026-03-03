package com.crm.travel.user.controller;

import com.crm.travel.common.globalinterface.ApiController;
import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.response.PermissionsResponse;
import com.crm.travel.user.dto.response.RolesResponse;
import com.crm.travel.user.dto.response.UserEntityResponse;
import com.crm.travel.user.organisationHierarchy.dto.response.OrgUnitTypeResponse;
import com.crm.travel.user.service.UserServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @file UserResponseController.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */
@RestController
@ApiController
public class UserResponseController {


    private final UserServices userServices;

    public UserResponseController(UserServices userServices) {
        super();
        this.userServices = userServices;

    }

    @GetMapping("hierarchy/organization/levels")
    public List<OrgUnitTypeResponse> getLevels() {
        return userServices.getLevels();
    }

    @GetMapping("hierarchy/organization/roles")
    public List<RolesResponse> getRoles() {
        return userServices.getRoles();
    }

    @GetMapping("hierarchy/organization/permissions")
    public List<PermissionsResponse> getPermissions() {
        return userServices.getPermissions();
    }

    //fetch all user's at once only specific to super admin
    @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('SYSTEM_CONFIG')")
    @GetMapping("heirarchy/organization/fetch-user")
    public ResponseEntity<Page<UserEntityResponse>> fetchUsers(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userServices.findAll(pageable));
    }

    //     Build Specification to retrieve child user as per the current logged-in user's
    @GetMapping("heirarchy/organization/under-me/{keyword}")
    public ResponseEntity<Page<UserEntityResponse>> getDescendent(@AuthenticationPrincipal User currentUser,
                                                                  @PageableDefault(page = 0,
                                                                          size = 10) Pageable pageable,
                                                                  @PathVariable String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(userServices.getDescendantUsers(currentUser, pageable, keyword));
    }


}
