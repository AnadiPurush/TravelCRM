package com.crm.travel.query.comment.controller;

import com.crm.travel.common.globalinterface.ApiController;
import com.crm.travel.query.comment.dto.QueriesCommentDTO;
import com.crm.travel.query.comment.service.QueriesCommentService;
import com.crm.travel.user.domain.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiController
class QueriesCommentController {
    @Lazy
    private final QueriesCommentService queriesCommentService;

    QueriesCommentController(QueriesCommentService queriesCommentService) {

        this.queriesCommentService = queriesCommentService;
    }


    @PostMapping("general/{queryId}/commentsave")
    public String saveComment(
            @PathVariable Long queryId,
            @RequestBody QueriesCommentDTO queriesCommentDTO,
            @AuthenticationPrincipal User authentication
    ) {
        return queriesCommentService.findQueriesById(queryId, authentication, queriesCommentDTO);

    }


    @PatchMapping("update/comment")
    public String updateComment(
            @RequestBody QueriesCommentDTO commentDTO) {
        return queriesCommentService.updateComment(commentDTO);

    }
}
