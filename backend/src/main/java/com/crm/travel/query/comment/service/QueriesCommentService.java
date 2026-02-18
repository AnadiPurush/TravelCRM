package com.crm.travel.query.comment.service;

import com.crm.travel.query.comment.domain.QueriesComment;
import com.crm.travel.query.comment.dto.QueriesCommentDTO;
import com.crm.travel.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QueriesCommentService {
    void save(QueriesComment queriesComment);

    @Transactional
    @EntityGraph(attributePaths = {"user", "quarie"})
    List<QueriesComment> findAllCommentsByQuarie_SerialNumber(Long serialNumber);

    @Transactional
    Optional<QueriesComment> findById(Long Id);

    @Transactional
    @EntityGraph(attributePaths = {"user", "quarie"})
    List<QueriesComment> findByQuarie_SerialNumberIn(List<Long> ids);

    String findQueriesById(Long id, User authentication, QueriesCommentDTO commentDTO);

    String updateComment(QueriesCommentDTO queriesCommentDTO);

}
