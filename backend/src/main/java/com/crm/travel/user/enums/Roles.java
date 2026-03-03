package com.crm.travel.user.enums;

import com.crm.travel.common.enums.DisplayEnum;
import lombok.Getter;

public enum Roles implements DisplayEnum {

    //Ceo
    CEO("Ceo", 1),
    //Directors
    DIRECTOR_FINANCE("Director of Finance", 2),
    DIRECTOR_HR("Director of HR", 2),
    DIRECTOR_MARKETING("Director of Marketing", 2),
    DIRECTOR_OPERATIONS("Director of Operations", 2),
    DIRECTOR_SALES("Director of Sales", 2),
    //Hr's
    HR_MANAGER("Hr Manager", 3),
    HR("Hr", 3),
    //Manager's
    SALES_MANAGER("Sales Manager", 4),
    ACCOUNTS_MANAGER("Accounts Manager", 4),
    OPERATIONS_MANAGER("Operations Manager", 4),
    MARKETING_MANAGER("Marketing Manager", 4),
    FINANCE_MANAGER("Finance Manager", 4),
    //Executives
    HR_EXECUTIVE("Hr Executive", 5),
    OPERATIONS_EXECUTIVE("Operations Executive", 5),
    MARKETING_EXECUTIVE("Marketing Executive", 5),
    FINANCE_EXECUTIVE("Finance Executive", 5),
    ACCOUNTS_EXECUTIVE("Accounts Executive", 5),
    SALES_EXECUTIVE("Sales Executive", 5);


    private final String display;
    @Getter
    private final int level;

    Roles(String display, int level) {
        this.display = display;
        this.level = level;
    }

    public boolean canManage(Roles other) {
        return this.level < other.level;
    }


    @Override
    public String getDisplayName() {
        return display;
    }
}
