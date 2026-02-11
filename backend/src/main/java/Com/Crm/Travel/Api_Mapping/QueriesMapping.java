package Com.Crm.Travel.Api_Mapping;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.DTO.QuariesDetailDTO;
import Com.Crm.Travel.Entities.DTO.QueriesCommentDTO;
import Com.Crm.Travel.Entities.EntitesHelper.QuariesHelper;
import Com.Crm.Travel.Services.QuariesServices;
import Com.Crm.Travel.Services.QueriesCommentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quariesCRUD")
public class QueriesMapping {
    private final QuariesServices quariesServices;


    public QueriesMapping(QuariesServices quariesServices) {
        this.quariesServices = quariesServices;
    }

    @PostMapping("general/quariesSave")
    public ResponseEntity<?> queriesSave(@RequestBody QuariesHelper helper, Authentication principal) {
        if (quariesServices.saveQuaries(helper, principal))
            return ResponseEntity.ok("Saved successfully");

        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // return quaries with the help of example matcher and force that user must be
    // authenticated to ensure that only user assigned quaries will be fetched
    @PreAuthorize("authenticated")
    @GetMapping("return/Quaries")
    public ResponseEntity<Page<QuariesDetailDTO>> getQueriesByUser(
            @ModelAttribute QuariesHelper helper,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy) {

        AppUser authentication = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<QuariesDetailDTO> queries = quariesServices.findAllWithFilters(helper, authentication, page, size, sortBy);
        return ResponseEntity.ok(queries);
    }


}

@RestController
@RequestMapping("/api/quariescommentCRUD")
class QueriesCommentMapping {
    @Lazy
    private final QueriesCommentService queriesCommentService;

    QueriesCommentMapping(QueriesCommentService quariesCommentService) {

        this.queriesCommentService = quariesCommentService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("general/{queryId}/commentsave")
    public ResponseEntity<String> saveComment(
            @PathVariable Long queryId,
            @RequestBody QueriesCommentDTO queriesCommentDTO,
            Authentication authentication
    ) {
        String commentAddedSuccessfully = queriesCommentService.findQueriesById(queryId, authentication, queriesCommentDTO);
        return ResponseEntity.ok(commentAddedSuccessfully);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("update/comment")
    public ResponseEntity<String> updateComment(
            @ModelAttribute QueriesCommentDTO commentDTO) {
        String commentUpdate = queriesCommentService.updateComment(commentDTO);
        return ResponseEntity.ok(commentUpdate);
    }
}


