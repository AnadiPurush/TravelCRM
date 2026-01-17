package Com.Crm.Travel.Services.servicesImpl.appUserImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.EntitesHelper.ChangePasswordRequest;
import Com.Crm.Travel.Entities.EntitesHelper.UserCreateRequest;
import Com.Crm.Travel.Repo.AppUserRepo;
import Com.Crm.Travel.Services.appUserServices.AppUserServices;

@Service
public class AppUserServicesImpl implements AppUserServices {
    private final AppUserRepo userRepo;
    private final PasswordEncoder encoder;

    public AppUserServicesImpl(AppUserRepo userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;

    }

    @Override
    public boolean getSuperAdmin(String userEmail) {
        return userRepo.existsByEmail(userEmail);
    }

    @Override
    @Transactional
    public void saveSuperAdmin(AppUser user) {

        userRepo.save(user);
    }

    @Override
    public AppUser findUserByEmail(String userEmail) {
        return userRepo.findByEmail(userEmail);

    }

    @Override
    public boolean isSuperAdmin(Authentication authentication) {
        String name = authentication.getName();
        return userRepo.findByName(name).map(AppUser::isSuperAdmin).orElse(false);

    }

    @Override
    @Transactional
    public boolean saveUser(UserCreateRequest request) {
        try {
            AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AppUser user = new AppUser();
            boolean superAdmin = currentUser.isSuperAdmin();
            user.setName(request.name());
            user.setPassword(encoder.encode(request.Password()));
            user.setEmail(request.Email());
            user.setPermissions(request.permissions());

            if (superAdmin) {

                user.setManager(request.manager());
                user.setDepartment(request.department());
                userRepo.save(user);
                return true;
            } else {

                user.setDepartment(currentUser.getDepartment());
                userRepo.save(user);
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordRequest req) {
        try {
            AppUser user = userRepo.findByEmail(email);
            if (encoder.matches(req.oldPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(req.newPassword()));

            } else {
                throw new InternalError("Something went wrong while saving the password");
            }
        } catch (UsernameNotFoundException e) {

            throw new UsernameNotFoundException("user not found");
        }

    }

    @Override
    @Transactional
    public String forgetPassword(String email, ChangePasswordRequest req) {
        try {
            AppUser user = userRepo.findByEmail(email);
            // dirty checking password
            user.setPassword(encoder.encode(req.newPassword()));
            userRepo.saveAndFlush(user);

            return "Password changed successfully";
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("user not found");
        } catch (NullPointerException e) {
            throw new InternalError("Something went wrong while saving the password");
        }
    }

}
