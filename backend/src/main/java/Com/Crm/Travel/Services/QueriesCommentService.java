package Com.Crm.Travel.Services;

import Com.Crm.Travel.Entities.DTO.QueriesCommentDTO;
import Com.Crm.Travel.Entities.QueriesComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QueriesCommentService {
    void save(QueriesComment queriesComment);

    @Transactional
    @EntityGraph(attributePaths = {"appUser", "quarie"})
    List<QueriesComment> findAllCommentsByQuarie_SerialNumber(Long serialNumber);

    @Transactional
    Optional<QueriesComment> findById(Long Id);

    @Transactional
    @EntityGraph(attributePaths = {"appUser", "quarie"})
    List<QueriesComment> findByQuarie_SerialNumberIn(List<Long> ids);

    String findQueriesById(Long id, Authentication authentication, QueriesCommentDTO commentDTO);

    String updateComment(QueriesCommentDTO queriesCommentDTO);

}
