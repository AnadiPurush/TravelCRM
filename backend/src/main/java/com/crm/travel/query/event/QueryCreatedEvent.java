package com.crm.travel.query.event;

/**
 * Domain event representing the creation of a new travel query.
 *
 * <p>Domain purpose: Encapsulates query creation events to enable
 * decoupled processing of business workflows through event-driven
 * architecture patterns.</p>
 *
 * <p>Core responsibilities: Transport query identifier information
 * between event publishers and listeners for asynchronous processing
 * of department-specific assignments and notifications.</p>
 *
 * <p>Layer boundary: Event domain layer that bridges application
 * services with event handling infrastructure while maintaining
 * loose coupling between components.</p>
 *
 * <p>Concurrency model: Immutable record providing thread-safe
 * event data transmission across concurrent processing contexts.</p>
 *
 * @author Utsav Sharma
 * @since 2026-02-15T00:00:00
 */
public record QueryCreatedEvent(Long Id) {
    public record SalesEvent(Long Id) {

    }

    public record OperationsEvent(Long Id) {
    }

    public record AccountsEvent(Long Id) {

    }

}
