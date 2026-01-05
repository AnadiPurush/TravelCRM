package Com.Crm.Travel.Services.appUserServices;

import org.springframework.security.core.Authentication;

import Com.Crm.Travel.Entities.AppUser;

public interface AppUserServices {
    boolean getSuperAdmin(String userEmail);

    AppUser saveSuperAdmin(AppUser user);

    AppUser findUserByEmail(String userEmail);

    boolean isSuperAdmin(Authentication authentication);

}
