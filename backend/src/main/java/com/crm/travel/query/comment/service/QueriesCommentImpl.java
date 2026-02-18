package com.crm.travel.query.comment.service;

import com.crm.travel.query.comment.domain.QueriesComment;
import com.crm.travel.query.comment.dto.QueriesCommentDTO;
import com.crm.travel.query.comment.repository.QueriesCommentRepo;
import com.crm.travel.query.domain.Queries;
import com.crm.travel.query.repository.QuariesRepo;
import com.crm.travel.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class QueriesCommentImpl implements QueriesCommentService {
    private final QueriesCommentRepo queriesCommentRepo;

    private final QuariesRepo queriesServices;

    public QueriesCommentImpl(QueriesCommentRepo queriesCommentRepo, QuariesRepo queriesServices) {
        this.queriesCommentRepo = queriesCommentRepo;
        this.queriesServices = queriesServices;
    }

    @Override
    public void save(QueriesComment queriesComment) {
        queriesCommentRepo.save(queriesComment);
    }

    @Override
    public List<QueriesComment> findAllCommentsByQuarie_SerialNumber(Long serialNumber) {
        return queriesCommentRepo.findAllCommentsByQuarie_SerialNumber(serialNumber);
    }

    @Override
    public Optional<QueriesComment> findById(Long Id) {
        return Optional.of(queriesCommentRepo.findById(Id))
                .orElseThrow(() -> new EntityNotFoundException("Query By the Id Not Found"));
    }

    @Override
    public List<QueriesComment> findByQuarie_SerialNumberIn(List<Long> ids) {
        return queriesCommentRepo.findByQuarie_SerialNumberIn(ids);
    }

    @Transactional
    @Override
    public String findQueriesById(Long id, User authentication, QueriesCommentDTO commentDTO) {
        try {
            Queries queries = queriesServices.findById(id).orElseThrow(() -> new EntityNotFoundException("Query By the Id Not Found"));
            QueriesComment comment = QueriesComment.builder().commentText(commentDTO.commentText()).user(authentication)
                    .quarie(queries).build();
            queriesCommentRepo.save(comment);
            return "Comment saved Successfully ";
        } catch (Exception e) {
            return "Something went wrong please try again later";
        }
    }

    @Transactional
    @Override
    public String updateComment(QueriesCommentDTO queriesCommentDTO) {
        Long l = queriesCommentDTO.commentId();
        QueriesComment comment = queriesCommentRepo.findById(l)
                .orElseThrow(() -> new EntityNotFoundException("Comment Not Found with id"));

        if (comment.getCreatedAt().isBefore(LocalDateTime.now().minusHours(24))) {
            return "24 Hour edit window is expired please add new comment";
        } else {
            comment.setCommentText(queriesCommentDTO.commentText());
            comment.setModifiedAt(LocalDateTime.now());
            return "Comment updated Successfully";
        }
    }
}
