package com.crm.travel.user.enums;

import com.crm.travel.common.enums.DisplayEnum;

public enum Roles implements DisplayEnum {
    SALES_MANAGER("Sales Manager"),
    SALES_EXECUTIVE("Sales Executive"),
    OPERATIONS_MANAGER("Operations Manager"),
    OPERATIONS_EXECUTIVE("Operations Executive"),
    SUPER_ADMIN("Super Admin"),
    ACCOUNTS_EXECUTIVE("Accounts Executive"),
    ACCOUNTS_MANAGER("Accounts Manager");


    private final String display;

    Roles(String display) {
        this.display = display;
    }

    @Override
    public String getDisplayName() {
        return display;
    }
}
