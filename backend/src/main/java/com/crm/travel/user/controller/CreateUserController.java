package com.crm.travel.user.controller;

import com.crm.travel.common.globalinterface.ApiController;
import com.crm.travel.user.domain.User;
import com.crm.travel.user.dto.request.ChangePasswordRequest;
import com.crm.travel.user.dto.request.UserRequest;
import com.crm.travel.user.dto.response.UserEntityResponse;
import com.crm.travel.user.mapper.UserMapper;
import com.crm.travel.user.service.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiController

public class CreateUserController {
    private final UserServices services;

    public CreateUserController(UserServices services) {
        this.services = services;

    }

    @PreAuthorize(" hasRole('SUPERADMIN')or hasAuthority('USER_CREATE')")
    @PostMapping("admin/createUser")
    public ResponseEntity<UserEntityResponse> createUserRequest(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserMapper.singleUserResponse(services.saveUser(request)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest req, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        services.changePassword(user.getUsername(), req);
        return ResponseEntity.ok("Password Updated  successfully");
    }

    @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('USER_MANAGE')")
    @PatchMapping("/forgetPassword")
    public String forgetPassword(@RequestBody ChangePasswordRequest req) {
        return services.forgetPassword(req.email(), req);
    }

    @PreAuthorize("hasRole('SUPERADMIN') or hasAuthority('SYSTEM_CONFIG')")
    @PatchMapping("admin/updateUser")
    public ResponseEntity<UserEntityResponse> updateUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(services.updateUser(request));
    }

}
