package com.crm.travel.query.domain;

import com.crm.travel.query.comment.domain.QueriesComment;
import com.crm.travel.query.enums.QueriesPriority;
import com.crm.travel.query.enums.QueriesStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JPA entity representing a travel query raised by a user.
 *
 * <p>This entity models the core domain data required to manage
 * the lifecycle of a query, including requester information,
 * priority, status, and associated comments.</p>
 *
 * <p>Domain Responsibilities:
 * - Persist query state and metadata
 * - Maintain associations with related entities (e.g., comments, users)
 * - Support filtering and status-based workflows</p>
 *
 * <p>Persistence Notes:
 * - Managed by the JPA persistence context
 * - Mutable by design
 * - Not thread-safe and should not be shared across threads</p>
 *
 * <p>Design Constraints:
 * - Business logic should reside in service layer
 * - This entity should not be exposed directly via REST APIs
 * - Use DTOs for API projections</p>
 *
 * @author Utsav Sharma
 * @since 2026-02-15T00:00:00
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "quaries")
@Builder
public class Queries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNumber;
    private String requesterName;
    private String contactNo;
    private String email;
    @ToString.Exclude
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destination", joinColumns = @JoinColumn(name = "quaries_serialNumber"))
    @Column(name = "destination")
    private List<String> destination;
    private String fromLocation;
    private Date fromDate;
    private Date toDate;
    private Long quotedPrice;
    @ToString.Exclude
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "required_services", joinColumns = @JoinColumn(name = "quaries_serialNumber"))
    @Column(name = "requiredServices")
    private List<String> requiredServices;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "querie", cascade = CascadeType.ALL)
    private List<QueryAssignment> assignmentHelper;


    @Enumerated(EnumType.STRING)
    private QueriesStatus queriesStatus;

    @Enumerated(EnumType.STRING)
    private QueriesPriority queriesPriority;

    @OneToMany(mappedBy = "quarie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueriesComment> comments = new ArrayList<>();
@PrePersist
    protected void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

}
