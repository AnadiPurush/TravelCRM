package Com.Crm.Travel.Services;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.EntitesHelper.ChangePasswordRequest;
import Com.Crm.Travel.Entities.EntitesHelper.UserCreateRequest;
import Com.Crm.Travel.common.enums.Department;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppUserServices {
    boolean getSuperAdmin(String userEmail);

    @Transactional
    void saveSuperAdmin(AppUser user);

    AppUser findUserByEmail(String userEmail);

    boolean isSuperAdmin(Authentication authentication);

    @Transactional
    boolean saveUser(UserCreateRequest request);

    @Transactional
    void changePassword(String email, ChangePasswordRequest req);

    @Transactional
    String forgetPassword(String email, ChangePasswordRequest req);

    List<AppUser> findByDepartment(Department department);

}
