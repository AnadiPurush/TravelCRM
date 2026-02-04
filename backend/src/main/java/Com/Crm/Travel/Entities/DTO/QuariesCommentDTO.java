package Com.Crm.Travel.Entities.DTO;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;

public record QuariesCommentDTO(@Nullable Long commentId,
        @Nullable String commentText,
        @Nullable LocalDateTime createdAt,
        @Nullable String author) {

}
