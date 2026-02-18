package com.crm.travel.query.comment.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QueriesCommentDTO(@Nullable Long commentId,
                                @Nullable String commentText,
                                @Nullable LocalDateTime createdAt,
                                @Nullable LocalDateTime modifiedAt,
                                @Nullable String author) {

}
