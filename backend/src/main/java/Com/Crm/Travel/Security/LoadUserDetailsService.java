package Com.Crm.Travel.Security;

import Com.Crm.Travel.Repo.AppUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoadUserDetailsService implements UserDetailsService {
    private final AppUserRepo service;
    private final Logger log = LoggerFactory.getLogger(LoadUserDetailsService.class);

    public LoadUserDetailsService(AppUserRepo service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("JWT username Extracted: {}", username);
        return Optional.of(service.findByEmail(username)).orElseThrow(() -> new UsernameNotFoundException("User not found" + username));


    }

}
