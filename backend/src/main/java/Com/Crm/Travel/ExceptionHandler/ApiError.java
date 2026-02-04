package Com.Crm.Travel.ExceptionHandler;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;

public record ApiError(
        @Nullable String Code,
        String message,
        int status,
        LocalDateTime localDateTime) {

    public ApiError(String message, String details, int status) {
        this(message, details, status, LocalDateTime.now());
    }
}