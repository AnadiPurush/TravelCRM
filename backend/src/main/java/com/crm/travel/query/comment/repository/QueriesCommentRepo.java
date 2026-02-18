package com.crm.travel.query.comment.repository;

import com.crm.travel.query.comment.domain.QueriesComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QueriesCommentRepo extends JpaRepository<QueriesComment, Long> {
    List<QueriesComment> findAllCommentsByQuarie_SerialNumber(Long serialNumber);

    @Transactional
    @EntityGraph(attributePaths = {"user"})
    List<QueriesComment> findByQuarie_SerialNumberIn(List<Long> ids);


}
