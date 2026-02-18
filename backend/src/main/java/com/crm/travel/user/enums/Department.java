package com.crm.travel.user.enums;

import com.crm.travel.common.enums.DisplayEnum;

public enum Department implements DisplayEnum {

    SALES("Sales"),
    OPERATIONS("Operations"),
    ACCOUNTS("Accounts");

    private final String displayName;

    Department(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
