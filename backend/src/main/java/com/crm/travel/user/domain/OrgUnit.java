package com.crm.travel.user.domain;

import com.crm.travel.user.enums.OrgUnitTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @file OrgUnit.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */
@Entity
@Table(name = "org_units", indexes = {
        @Index(name = "idx_org_unit_parent_id", columnList = "parent_id")
})
@Data
public class OrgUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrgUnitTypes orgUnitTypes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private OrgUnit parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<OrgUnit> children = new ArrayList<>();

}
