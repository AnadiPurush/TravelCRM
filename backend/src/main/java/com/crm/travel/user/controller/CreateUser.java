package com.crm.travel.user.controller;

import com.crm.travel.common.globalinterface.ApiController;
import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.request.ChangePasswordRequest;
import com.crm.travel.user.dto.request.UserRequest;
import com.crm.travel.user.service.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiController

public class CreateUser {
    private final UserServices services;

    public CreateUser(UserServices services) {
        this.services = services;

    }

    @PreAuthorize("isAuthenticated() and hasRole('SUPERADMIN')or hasAuthority('USER_CREATE || USER_MANAGE')")
    @PostMapping("admin/createUser")
    public boolean createUserRequest(@RequestBody UserRequest request) {
        try {
            services.saveUser(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest req,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        services.changePassword(user.getUsername(), req);
        return ResponseEntity.ok("Password Updated  successfully");
    }

    @PatchMapping("/forgetPassword")
    @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('USER_MANAGE')")
    public String forgetPassword(@RequestBody ChangePasswordRequest req) {
        return services.forgetPassword(req.email(), req);
    }

    // @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('USER_MANAGE')")

    // @GetMapping("/permissions-grouped")
    // public Map<String, List<UserPermissions>> getGroupedPermissions() {
    // Map<String, List<UserPermissions>> grouped = new LinkedHashMap<>();

    // grouped.put("Trips", List.of(UserPermissions.TRIP_READ,
    // UserPermissions.TRIP_MANAGE,
    // UserPermissions.TRAVELER_MANAGE));

    // grouped.put("Finance", List.of(UserPermissions.FINANCE_READ,
    // UserPermissions.FINANCE_MANAGE));

    // grouped.put("Queries", List.of(UserPermissions.QUERY_READ,
    // UserPermissions.QUERY_ASSIGN,
    // UserPermissions.QUERY_RESPOND));

    // grouped.put("User_Manager", List.of(UserPermissions.USER_MANAGE));

    // // grouped.forEach((grp, per) -> {
    // // System.out.print(grp);
    // // per.forEach(p -> System.out.println(p));
    // // });
    // return grouped;
    // }
}
