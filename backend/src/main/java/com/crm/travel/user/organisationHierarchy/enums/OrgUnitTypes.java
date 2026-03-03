package com.crm.travel.user.organisationHierarchy.enums;
/**
 * @file OrgUnitTypes.java
 * Author: Utsav
 * Date: 2/19/26
 *
 */

import com.crm.travel.common.enums.DisplayEnum;
import lombok.Getter;


/**
 * The enum represents the various types of organizational units.
 * The constants in this enum are defined as follows:
 * - CEO: Represents the Chief Executive Officer.
 * - DEPARTMENT: Represents a department within an organization.
 * - DIVISION: Represents a division within an organization.
 * - TEAM: Represents a team within an organization.
 * Each constant is associated with a display name and a parent level.
 * The display name is used to represent the enum value in a user-friendly format.
 * The parent level represents the organizational unit that the current unit is a part of.
 * For example, a department is typically part of a CEO, a division is typically part of a department,
 * and a team is typically part of a division.
 * The enum implements the {@link DisplayEnum} interface, which provides a method to retrieve the display name.
 * The enum is marked as {@code final} to prevent any modifications to the constants.
 * The enum fields are marked as {@code private final} to prevent any modifications to the fields.
 * The enum is marked as {@code @Getter} to enable the retrieval of the display name and parent level.
 */
public enum OrgUnitTypes implements DisplayEnum {
    CEO("CEO"),
    DEPARTMENT("Department", CEO),
    DIVISION("Division", DEPARTMENT),
    TEAM("Team", DIVISION);


    private final String displayName;

    @Getter
    private final OrgUnitTypes parentLevel;

    OrgUnitTypes(String displayName) {
        this(displayName, null);
    }

    OrgUnitTypes(String displayName, OrgUnitTypes parentLevel) {
        this.displayName = displayName;
        this.parentLevel = parentLevel;
    }


    public static boolean isValidParent(OrgUnitTypes child,
                                        OrgUnitTypes parent) {
        if (child.parentLevel == null) {
            return false;
        }

        return child.parentLevel.equals(parent);
    }
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    // Check if this level requires a parent
    public boolean requiresParent() {
        return parentLevel != null;
    }
    public boolean isParentLevel() {
        return parentLevel != null;
    }


}
