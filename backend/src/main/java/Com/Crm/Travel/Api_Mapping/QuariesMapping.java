package Com.Crm.Travel.Api_Mapping;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.DTO.QuariesDetailDTO;
import Com.Crm.Travel.Entities.EntitesHelper.QuariesHelper;
import Com.Crm.Travel.Entities.Quaries;
import Com.Crm.Travel.Services.appUserServices.AppUserServices;
import Com.Crm.Travel.Services.quarieServices.QuariesServices;

@RestController
@RequestMapping("/api/quariesCRUD")
public class QuariesMapping {
    private final QuariesServices quariesServices;
    private final AppUserServices appUserServices;

    public QuariesMapping(QuariesServices quariesServices, AppUserServices appUserServices) {
        this.quariesServices = quariesServices;
        this.appUserServices = appUserServices;
    }

    @PostMapping("general/quariesSave")
    public ResponseEntity<?> queriesSave(@RequestBody QuariesHelper helper, Authentication principal) {

        AppUser assignedUser = Optional.ofNullable(helper.assignedUseremail())
                .filter(StringUtils::hasText)
                .map(appUserServices::findUserByEmail)
                .orElseGet(() -> (AppUser) principal.getPrincipal());
        Quaries quaries = Quaries.builder()
                .requesterName(helper.requesterName())
                .contactNo(helper.contactNo())
                .email(helper.email())
                .Destination(helper.Destination())
                .fromLocation(helper.fromLocation())
                .fromDate(helper.fromDate())
                .toDate(helper.toDate())
                .quotedPrice(helper.quotedPrice())
                .requiredServices(helper.requiredServices())
                .quariesStatus(helper.quariesStatus())
                .quariesPriority(helper.quariesPriority())
                .appUser(assignedUser)
                .build();

        quariesServices.saveQuaries(quaries);

        return ResponseEntity.ok("Saved successfully");
    }

    // return quaries with the help of example matcher and force that user must be
    // authenticated to ensure that only user assigned quaries will be fetched
    @PreAuthorize("authenticated")
    @GetMapping("return/Quaries")
    public ResponseEntity<Page<QuariesDetailDTO>> getQueriesByUser(
            Quaries quaries,
            @ModelAttribute QuariesHelper helper,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assignedAt") String sortBy) {

        AppUser authentication = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Quaries filterCriteria = Quaries.builder()
                .Destination(helper.Destination())
                .appUser(authentication)
                .quariesStatus(helper.quariesStatus())
                .quariesPriority(helper.quariesPriority())
                .requesterName(helper.requesterName())
                .build();
        if (authentication != null) {

            Page<Quaries> queries = quariesServices.findAllWithFilters(filterCriteria, page, size, sortBy);
            Page<QuariesDetailDTO> queriesDTOPage = queries.map(query -> QuariesDetailDTO.builder()
                    .requesterName(query.getRequesterName())
                    .destination(query.getDestination())
                    .fromLocation(query.getFromLocation())
                    .fromDate(query.getFromDate())
                    .toDate(query.getToDate())
                    .quotedPrice(query.getQuotedPrice())
                    .requiredServices(query.getRequiredServices())
                    .createdAt(query.getCreatedAt())
                    .quariesStatus(query.getQuariesStatus())
                    .quariesPriority(query.getQuariesPriority())
                    .serialNumber(query.getSerialNumber())
                    .contactNo(query.getContactNo())
                    .email(query.getEmail())
                    .build());

            return ResponseEntity.ok(queriesDTOPage);
        }

        else
            return ResponseEntity.ok(Page.empty());

    }
}



