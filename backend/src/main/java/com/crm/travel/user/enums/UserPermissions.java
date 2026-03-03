package com.crm.travel.user.enums;

import com.crm.travel.common.enums.DisplayEnum;

public enum UserPermissions implements DisplayEnum {

    SUPER_ADMIN("Super Admin Global Access"),
    DIRECTOR("Director->Manager->Team Access grants insight into team activity."),
    MANAGER("Grant Manager->Team Access for oversight of team activity."),
    // SALES
    QUERY_CREATE("Create Query"),
    QUOTE_UPDATE("Update Quote"),

    // OPERATIONS
    TICKET_UPLOAD("Upload Ticket"),
    VISA_PROCESS("Process Visa"),

    // ACCOUNTS
    EXPENSE_ADD("Add Expense"),
    EXPENSE_VIEW("View Expense"),
    INVOICE_CREATE("Create Invoice"),
    LEDGER_VIEW("View Ledger"),

    //     ADMIN
    USER_CREATE("Create User"),
    USER_MANAGE("Manage Users"),
    PERMISSION_ASSIGN("Assign Permission"),
    SYSTEM_CONFIG("System Configuration"),
    TRIP_ASSIGN("Can reassign team member");

    private final String displayName;

    UserPermissions(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
