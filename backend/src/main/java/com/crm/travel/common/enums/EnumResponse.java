package com.crm.travel.common.enums;

/**
 * Contains immutable response models related to enum and lightweight
 * API projections.
 * <p>
 * These records are designed for:
 * - Frontend dropdown population
 * - Metadata exposure
 * - Lightweight user projections
 * <p>
 * Records in this package are immutable and contain no business logic.
 * They exist strictly for transport across application boundaries.
 */

/**
 * Immutable DTO representing a displayable enum value.
 *
 * <p>This structure separates:
 * - {@code name}        : Stable programmatic identifier (Enum constant name)
 * - {@code displayName} : Human-readable label for UI rendering</p>
 *
 * <p>Design Purpose:
 * - Decouple enum constant naming from UI display labels
 * - Provide a uniform dropdown representation across APIs
 * - Avoid exposing raw enum internals directly to clients</p>
 * <p>
 * Example JSON:
 * {
 * "name": "IN_PROGRESS",
 * "displayName": "In Progress"
 * }
 *
 * @param name        internal enum constant name (Enum.name())
 * @param displayName human-readable display label
 */
public record EnumResponse(String name,
                           String displayName) {

}


