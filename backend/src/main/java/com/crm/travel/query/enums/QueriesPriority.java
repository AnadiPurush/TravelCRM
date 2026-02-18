package com.crm.travel.query.enums;

import com.crm.travel.common.enums.DisplayEnum;

public enum QueriesPriority implements DisplayEnum {
    LOW("Priority Low"),
    MEDIUM("Priority Medium"),
    HIGH("Priority High");

    private final String displayName;

    QueriesPriority(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
