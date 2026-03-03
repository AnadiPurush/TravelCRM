package com.crm.travel.common.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Provides standardized transformation utilities for enum types used in API
 * responses.
 * <p>
 * This package ensures that all enums exposed to the frontend implement
 * {@link DisplayEnum} so that a consistent (value, displayName) structure is
 * returned.
 * <p>
 * Purpose: - Prevent duplication of enum-to-DTO mapping logic - Decouple
 * internal enum constant names from UI display labels - Enforce compile-time
 * safety for display able Enums
 * <p>
 * Used primarily for: - Dropdown population - Metadata endpoints - UI
 * configuration APIs
 */
public class Mapper {
	private Mapper() {
	} // prevent instantiation

	public static <T extends Enum<T> & DisplayEnum> List<EnumResponse> map(Class<T> enumClass) {

		return Arrays.stream(enumClass.getEnumConstants()).map(e -> new EnumResponse(e.name(), e.getDisplayName()))
				.toList();
	}
}
