package Com.Crm.Travel.Api_Mapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Com.Crm.Travel.common.enums.AppUserPermissions;

@RestController
@RequestMapping("/api/adminCRUD")

public class CreateUser {
    @PostMapping("/createUser")
    @PreAuthorize("hasRole('SUPERADMIN')")

    public ResponseEntity<?> createUser() {
        return null;
    }

    @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('USER_MANAGE')")

    @GetMapping("/permissions-grouped")
    public Map<String, List<AppUserPermissions>> getGroupedPermissions() {
        Map<String, List<AppUserPermissions>> grouped = new LinkedHashMap<>();

        grouped.put("Trips", List.of(AppUserPermissions.TRIP_READ,
                AppUserPermissions.TRIP_MANAGE,
                AppUserPermissions.TRAVELER_MANAGE));

        grouped.put("Finance", List.of(AppUserPermissions.FINANCE_READ,
                AppUserPermissions.FINANCE_MANAGE));

        grouped.put("Queries", List.of(AppUserPermissions.QUERY_READ,
                AppUserPermissions.QUERY_ASSIGN,
                AppUserPermissions.QUERY_RESPOND));

        grouped.put("User_Manager", List.of(AppUserPermissions.USER_MANAGE));
        return grouped;
    }
}
