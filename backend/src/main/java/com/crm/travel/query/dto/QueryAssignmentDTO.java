package com.crm.travel.query.dto;

import com.crm.travel.user.enums.Department;
import com.crm.travel.user.enums.Roles;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QueryAssignmentDTO(
        Long id,
        String name,
        Roles roles,
        Department department,
        LocalDateTime assignedAt
) {
}

