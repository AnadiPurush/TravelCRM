package com.crm.travel.user.dto.request;

import com.crm.travel.user.enums.Roles;
import com.crm.travel.user.enums.UserPermissions;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

import java.util.Set;
import java.util.UUID;

/**
 * @file UserUpdateRequest.java
 * Author: Utsav
 * Date: 2/28/26
 *
 */
public record UserUpdateRequest(
        @Nullable String name,
        @Nullable @Email String email,
        @Nullable Set<UserPermissions> permissions,
        @Nullable UUID manager,
        @Nullable Set<UUID> secondaryManager,
        @Nullable Roles role,
        @Nullable UUID orgUnit
) {
}
