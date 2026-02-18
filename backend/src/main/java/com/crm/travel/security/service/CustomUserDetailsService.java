package com.crm.travel.security.service;

import com.crm.travel.user.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo service;
    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepo service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("JWT username Extracted: {}", username);
        return Optional.of(service.findByEmail(username)).orElseThrow(() -> new UsernameNotFoundException("User not found" + username));


    }

}
