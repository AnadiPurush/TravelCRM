package com.crm.travel.user.dto.request;

import com.crm.travel.user.enums.Department;
import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.enums.UserPermissions;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserRequest(
        @NotBlank String name,
        @Email String Email,
        @NotBlank String Password,
        boolean manager,
        @Nonnull Department department,
        @Nonnull Roles role,
        Set<UserPermissions> permissions) {
}
