package Com.Crm.Travel.Services.servicesImpl;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.DTO.QueriesCommentDTO;
import Com.Crm.Travel.Entities.Quaries;
import Com.Crm.Travel.Entities.QueriesComment;
import Com.Crm.Travel.Repo.QuariesRepo;
import Com.Crm.Travel.Repo.QueriesCommentRepo;
import Com.Crm.Travel.Services.QueriesCommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class QueriesCommentImpl implements QueriesCommentService {
    private final QueriesCommentRepo queriesCommentRepo;

    private final QuariesRepo quariesServices;

    public QueriesCommentImpl(QueriesCommentRepo queriesCommentRepo, QuariesRepo quariesServices) {
        this.queriesCommentRepo = queriesCommentRepo;
        this.quariesServices = quariesServices;
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
                .orElseThrow(() -> new EntityNotFoundException("Querie By the Id Not Found"));
    }

    @Override
    public List<QueriesComment> findByQuarie_SerialNumberIn(List<Long> ids) {
        return queriesCommentRepo.findByQuarie_SerialNumberIn(ids);
    }

    @Transactional
    @Override
    public String findQueriesById(Long id, Authentication authentication, QueriesCommentDTO commentDTO) {
        try {
            AppUser appUser = (AppUser) authentication.getPrincipal();
            Quaries quaries = quariesServices.findById(id).orElseThrow(() -> new EntityNotFoundException("Querie By the Id Not Found"));
            QueriesComment comment = QueriesComment.builder().commentText(commentDTO.commentText()).appUser(appUser)
                    .quarie(quaries).build();
            queriesCommentRepo.save(comment);
            return "Comment saved Successfully ";
        } catch (Exception e) {
            return "Something went wrong please try again later";
        }
    }

    @Transactional
    @Override
    public String updateComment(QueriesCommentDTO queriesCommentDTO) {
        if (queriesCommentDTO.createdAt().isBefore(LocalDateTime.now().minusHours(24))) {
            return "24 Hour edit window is expired please add new comment";
        } else {
            Long l = queriesCommentDTO.commentId();
            QueriesComment comment = queriesCommentRepo.findById(l)
                    .orElseThrow(() -> new EntityNotFoundException("Comment Not Found with id"));
            return "Comment updated Successfully";
        }
    }
}
