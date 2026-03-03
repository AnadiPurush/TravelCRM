package com.crm.travel.query.dto;

import com.crm.travel.user.enums.Roles;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QueryAssignmentDTO(
        Long id,
        String name,
        Roles roles,
        LocalDateTime assignedAt
) {
}

