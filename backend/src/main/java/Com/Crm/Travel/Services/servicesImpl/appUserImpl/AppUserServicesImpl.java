package Com.Crm.Travel.Services.servicesImpl.appUserImpl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Repo.AppUserRepo;
import Com.Crm.Travel.Services.appUserServices.AppUserServices;

@Service
public class AppUserServicesImpl implements AppUserServices {
    private final AppUserRepo userRepo;

    public AppUserServicesImpl(AppUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean getSuperAdmin(String userEmail) {
        return userRepo.existsByEmail(userEmail);
    }

    @Override
    @Transactional
    public AppUser saveSuperAdmin(AppUser user) {
        return userRepo.save(user);
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

}
