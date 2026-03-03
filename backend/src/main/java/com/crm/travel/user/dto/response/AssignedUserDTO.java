package com.crm.travel.user.dto.response;

import com.crm.travel.user.enums.Roles;
import lombok.Builder;

@Builder
public record AssignedUserDTO(
        String displayName,
        String email,
        Roles role) {

}
