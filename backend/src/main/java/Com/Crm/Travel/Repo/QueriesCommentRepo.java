package Com.Crm.Travel.Repo;

import Com.Crm.Travel.Entities.QueriesComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QueriesCommentRepo extends JpaRepository<QueriesComment, Long> {
    List<QueriesComment> findAllCommentsByQuarie_SerialNumber(Long serialNumber);

    @Transactional
    @EntityGraph(attributePaths = {"appUser"})
    List<QueriesComment> findByQuarie_SerialNumberIn(List<Long> ids);


}
