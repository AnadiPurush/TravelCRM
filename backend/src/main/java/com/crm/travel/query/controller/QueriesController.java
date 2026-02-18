package com.crm.travel.query.controller;

import com.crm.travel.query.dto.QueriesDetailDTO;
import com.crm.travel.query.dto.request.QueryRequest;
import com.crm.travel.query.service.QuariesServices;
import com.crm.travel.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quariesCRUD")
public class QueriesController {
    private final QuariesServices quariesServices;


    public QueriesController(QuariesServices quariesServices) {
        this.quariesServices = quariesServices;
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or" + " hasAuthority('QUERY_CREATE') or " + "hasAuthority('USER_MANAGE')")

    @PostMapping("general/quariesSave")
    public ResponseEntity<?> queriesSave(@RequestBody QueryRequest helper) {
        if (quariesServices.saveQuaries(helper))
            return ResponseEntity.ok("Saved successfully");

        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // return quaries with the help of example matcher and force that user must be
    // authenticated to ensure that only user assigned quaries will be fetched
    @PreAuthorize("authenticated")
    @GetMapping("return/Quaries")
    public Page<QueriesDetailDTO> getQueriesByUser(
            @ModelAttribute QueryRequest helper,
            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            @AuthenticationPrincipal User authentication) {

        return quariesServices.findAllWithFilters(helper, authentication, pageable);

    }

    @PreAuthorize("isAuthenticated()")
    public String updateQuery(@RequestBody QueryRequest helper) {


        return quariesServices.updateQuery(helper);
    }


}


