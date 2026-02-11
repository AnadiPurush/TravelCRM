package Com.Crm.Travel.common.enums;

import Com.Crm.Travel.common.enums.Response.DisplayEnum;

public enum QueriesStatus implements DisplayEnum {
    NEW("New"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed"),
    CANCELLED("Cancelled"),
    CONFIRMED("Confirmed");

    private final String displayName;

    QueriesStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public boolean canTransitionTo(QueriesStatus next) {
        return switch (this) {
            case NEW -> next == IN_PROGRESS;
            case IN_PROGRESS -> next == CLOSED || next == CANCELLED || next == CONFIRMED;
            case CLOSED, CANCELLED -> next == NEW;
            case CONFIRMED -> false;
        };
    }
}