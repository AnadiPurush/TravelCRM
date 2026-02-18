package com.crm.travel.query.domain;

import com.crm.travel.user.domain.User;
import com.crm.travel.user.enums.Department;
import com.crm.travel.user.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class QueryAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "querie_id", nullable = false)
    private Queries querie;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Enumerated(EnumType.STRING)
    private Department department;
    private LocalDateTime assignedAt;

    @PrePersist
    protected void setAssignedAt() {
        this.assignedAt = LocalDateTime.now();
    }

}
