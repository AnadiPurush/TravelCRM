package com.crm.travel.user.organisationHierarchy.repository;

import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @file OrgUnitRepo.java
 * Author: Utsav
 * Date: 2/20/26
 *
 */
@Repository
public interface OrgUnitRepo extends JpaRepository<OrgUnit, UUID> {
}
