package com.crm.travel.user.enums;

import com.crm.travel.common.enums.DisplayEnum;

/**
 * @file OrgUnitTypes.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */

public enum OrgUnitTypes implements DisplayEnum {
    DEPARTMENT("Department"),
    DIVISION("Division", DEPARTMENT),
    TEAM("Team", DIVISION);


    private final String displayName;

    private final OrgUnitTypes parentLevel;

    OrgUnitTypes(String displayName) {
        this(displayName, null);
    }

    OrgUnitTypes(String displayName, OrgUnitTypes parentLevel) {
        this.displayName = displayName;
        this.parentLevel = parentLevel;
    }

    public String getParentLevel() {
        return parentLevel != null ? parentLevel.name() : null;

    }

    @Override
    public String getDisplayName() {
        return this.name();
    }

    public boolean isParentLevel() {
        return parentLevel != null;
    }
}
