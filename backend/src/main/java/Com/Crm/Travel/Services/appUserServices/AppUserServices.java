package Com.Crm.Travel.Services.appUserServices;

import org.springframework.security.core.Authentication;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.EntitesHelper.ChangePasswordRequest;
import Com.Crm.Travel.Entities.EntitesHelper.UserCreateRequest;

public interface AppUserServices {
    boolean getSuperAdmin(String userEmail);

    void saveSuperAdmin(AppUser user);

    AppUser findUserByEmail(String userEmail);

    boolean isSuperAdmin(Authentication authentication);

    boolean saveUser(UserCreateRequest request);

    void changePassword(String email, ChangePasswordRequest req);

    String forgetPassword(String email, ChangePasswordRequest req);

}
