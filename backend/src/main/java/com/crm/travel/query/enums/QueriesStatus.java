package com.crm.travel.query.enums;

import com.crm.travel.common.enums.DisplayEnum;

/**
 * Contains a list of all possible statuses for a query.
 * <p>
 * This enum is immutable and represents the possible statuses for a query
 * in the application. Each status has a corresponding displayName for UI
 * rendering.
 * <p>
 * Design Purpose:
 * - Decouple enum constant naming from UI display labels
 * - Provide a uniform dropdown representation across APIs
 * - Avoid exposing raw enum internals directly to clients
 * <p>
 * Note: Display names are for UI rendering only and should not be used
 * for programmatic purposes.
 * <p>
 * Thread-safety Characteristics:
 * - Fully thread-safe due to enum immutability
 * - Stateless transition validation method
 * - No mutable shared state
 * <p>
 * JSON Example (via DisplayEnum interface):
 * {
 * "name": "IN_PROGRESS",
 * "displayName": "In Progress"
 * }
 *
 * @author Utsav Sharma
 * @implNote This enum implements DisplayEnum for consistent API responses.
 * State transitions are enforced at the service layer,
 * not at the enum level, to maintain separation of concerns.
 * @since 2024-01-01
 */
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