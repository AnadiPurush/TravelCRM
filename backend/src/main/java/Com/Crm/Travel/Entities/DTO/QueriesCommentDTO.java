package Com.Crm.Travel.Entities.DTO;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record QueriesCommentDTO(@Nullable Long commentId,
                                @Nullable String commentText,
                                @Nullable LocalDateTime createdAt,
                                @Nullable LocalDateTime modifiedAt,
                                @Nullable String author) {

}
