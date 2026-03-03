package com.crm.travel.user.specification;

import com.crm.travel.user.organisationHierarchy.domain.OrgUnit;

import java.util.Set;
import java.util.UUID;

/**
 * @file OrgUnitsSpecification.java
 * Author: Utsav
 * Date: 2/26/26
 *
 */
public class OrgUnitsSpecification {
    //    Build query specification  based on the number of the id's provided by the caller to fetch the child as per the current logged-in user's
    public static void collectDescendantIds(OrgUnit orgUnit, Set<UUID> allSubUnitIds) {
        if (orgUnit == null) return;
        allSubUnitIds.add(orgUnit.getId());
        for (OrgUnit user1 : orgUnit.getChildren()) {
            collectDescendantIds(user1, allSubUnitIds);
        }
    }
}
