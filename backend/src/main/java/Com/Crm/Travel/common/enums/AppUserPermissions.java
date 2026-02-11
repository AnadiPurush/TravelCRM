package Com.Crm.Travel.common.enums;

import Com.Crm.Travel.common.enums.Response.DisplayEnum;

public enum AppUserPermissions implements DisplayEnum {
    // SALES
    QUERY_CREATE("Create Query"),
    QUERY_ASSIGN("Assign Query"),
    QUERY_VIEW("View Query"),
    QUOTE_CREATE("Create Quote"),
    QUOTE_UPDATE("Update Quote"),

    // OPERATIONS
    TRIP_CREATE("Create Trip"),
    TRIP_ASSIGN("Assign Trip"),
    TICKET_UPLOAD("Upload Ticket"),
    VISA_PROCESS("Process Visa"),

    // ACCOUNTS
    EXPENSE_ADD("Add Expense"),
    EXPENSE_VIEW("View Expense"),
    INVOICE_CREATE("Create Invoice"),
    LEDGER_VIEW("View Ledger"),

    // ADMIN
    USER_CREATE("Create User"),
    USER_MANAGE("Manage Users"),
    PERMISSION_ASSIGN("Assign Permission"),
    SYSTEM_CONFIG("System Configuration");

    private final String displayName;

    AppUserPermissions(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
