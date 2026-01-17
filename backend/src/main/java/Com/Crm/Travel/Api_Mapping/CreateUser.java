package Com.Crm.Travel.Api_Mapping;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.EntitesHelper.ChangePasswordRequest;
import Com.Crm.Travel.Entities.EntitesHelper.UserCreateRequest;
import Com.Crm.Travel.Services.appUserServices.AppUserServices;

@RestController
@RequestMapping("/api/adminCRUD")

public class CreateUser {
    private final AppUserServices services;

    public CreateUser(AppUserServices services) {
        this.services = services;

    }

    @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('USER_CREATE')")
    @PostMapping("/admin/createUser")
    public boolean createUserRequest(@RequestBody UserCreateRequest request) {
        try {
            services.saveUser(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest req,
            Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();
        services.changePassword(user.getUsername(), req);
        return ResponseEntity.ok("Passwors Updated  successfully");
    }

    @PatchMapping("/forgetPassword")
    @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('USER_MANAGE')")
    public String forgetPassword(@RequestBody ChangePasswordRequest req, String email) {
        return services.forgetPassword(email, req);
    }

    // @PreAuthorize("hasRole('SUPERADMIN')or hasAuthority('USER_MANAGE')")

    // @GetMapping("/permissions-grouped")
    // public Map<String, List<AppUserPermissions>> getGroupedPermissions() {
    // Map<String, List<AppUserPermissions>> grouped = new LinkedHashMap<>();

    // grouped.put("Trips", List.of(AppUserPermissions.TRIP_READ,
    // AppUserPermissions.TRIP_MANAGE,
    // AppUserPermissions.TRAVELER_MANAGE));

    // grouped.put("Finance", List.of(AppUserPermissions.FINANCE_READ,
    // AppUserPermissions.FINANCE_MANAGE));

    // grouped.put("Queries", List.of(AppUserPermissions.QUERY_READ,
    // AppUserPermissions.QUERY_ASSIGN,
    // AppUserPermissions.QUERY_RESPOND));

    // grouped.put("User_Manager", List.of(AppUserPermissions.USER_MANAGE));

    // // grouped.forEach((grp, per) -> {
    // // System.out.print(grp);
    // // per.forEach(p -> System.out.println(p));
    // // });
    // return grouped;
    // }
}
