package com.crm.travel.user.dto.request;

import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.enums.UserPermissions;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record UserRequest(
        @NotBlank String name,
        @Email String Email,
        @NotBlank String Password,
        @NotBlank Roles role,
        Set<UserPermissions> permissions,
        UUID orgUnitId,
        UUID reportingManagerId,
        Set<UUID> secondaryManagerIds) {
}
