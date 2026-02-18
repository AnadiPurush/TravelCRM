package com.crm.travel.user.dto.request;

import jakarta.annotation.Nullable;

public record ChangePasswordRequest(
        @Nullable String oldPassword,
        String newPassword,
        @Nullable String email

) {
}
