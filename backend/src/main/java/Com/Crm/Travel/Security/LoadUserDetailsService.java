package Com.Crm.Travel.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Com.Crm.Travel.Services.appUserServices.AppUserServices;

@Service
public class LoadUserDetailsService implements UserDetailsService {
    private final AppUserServices service;

    public LoadUserDetailsService(AppUserServices service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return service.findUserByEmail(username);
    }

}
