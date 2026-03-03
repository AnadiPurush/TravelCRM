package com.crm.travel.user.organisationHierarchy.domain;

import com.crm.travel.user.organisationHierarchy.enums.OrgUnitTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
@Table(
        name = "org_units",
        indexes = {
                @Index(name = "idx_org_unit_parent_id", columnList = "parent_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_orgunit_parent_normalized",
                        columnNames = {"parent_id", "normalized_name"}
                )
        }
)
@Getter
@Setter
public class OrgUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;


    @Column(name = "normalized_name", nullable = false)
    private String normalizedName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrgUnitTypes orgUnitTypes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private OrgUnit parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<OrgUnit> children = new ArrayList<>();


    /**
     * Normalizes the given input string by performing the following operations:
     * <ol>
     * <li>Trims the input string to remove any leading or trailing whitespace.</li>
     * <li>Converts the input string to uppercase.</li>
     * <li>Removes any whitespace characters using the regular expression "\\s+"
     * and replaces them with an empty string.</li>
     * </ol>
     */
    @PrePersist
    @PreUpdate
    private void normalize() {

        if (this.name != null) {
            this.normalizedName =
                    this.name.trim()
                            .toUpperCase()
                            .replaceAll("[\\s_-]+", "");
        }
    }

}
