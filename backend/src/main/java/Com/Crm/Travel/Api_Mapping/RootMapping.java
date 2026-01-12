package Com.Crm.Travel.Api_Mapping;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.EntitesHelper.LoginRequest;
import Com.Crm.Travel.Entities.Quaries;
import Com.Crm.Travel.JWTUtilityClasses.JWTUtil;
import Com.Crm.Travel.Security.LoadUserDetailsService;
import Com.Crm.Travel.Services.quarieServices.QuariesServices;

@RestController
@RequestMapping("/api")
public class RootMapping {
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(RootMapping.class);
    private final QuariesServices quariesServices;
    private final JWTUtil jwtUtil;

    public RootMapping(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
            LoadUserDetailsService loadUserDetailsService, QuariesServices quariesServices) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.quariesServices = quariesServices;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())

            );

            // final UserDetails user = (UserDetails) auth.getPrincipal();
            final AppUser admin = (AppUser) auth.getPrincipal();
            String Jwt = jwtUtil.generateToken(admin.getUsername(), admin);

            Map<String, String> response = new HashMap<>();
            response.put("token", Jwt);
            response.put("message", "Login successful!");
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            logger.error("Authentication failed: {}", e.getMessage());

            return ResponseEntity.ok(Map.of("message", "UsernameNotFoundException"));
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed: {}", e.getMessage());

            return ResponseEntity.ok(Map.of("message", "BadCredentialsException"));

        } catch (AuthenticationException e) {
            logger.error("Authentication failed: {}", e.getMessage());

            return ResponseEntity.ok(Map.of("message", "general Exception"));
        }

    }

    // do not need this beacuse the filter is already their to perform this action
    @PostMapping("/check-auth")
    public ResponseEntity<Map<String, String>> checkAuthStatus() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // logger.debug("this method is run as check auth submitted request");
        Map<String, String> response = new HashMap<>();

        if (authentication != null
                && authentication.isAuthenticated()) {
            response.put("status", "authenticated");
            response.put("message", "User is authenticated.");
            // System.out.println("token is not expired");
            // authentication.getAuthorities().forEach(a ->
            // System.out.print(a.getAuthority()));

        } else {
            response.put("status", "unauthenticated");
            response.put("message", "User is not authenticated or session is invalid.");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("general/Quaries")
    public ResponseEntity<?> quariesSave(@RequestBody Quaries quaries) {
        quariesServices.saveQuaries(quaries);
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        System.out.println("reached logout");
        return null;
    }
}
