package Com.Crm.Travel.Api_Mapping;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.EntitesHelper.LoginRequest;
import Com.Crm.Travel.JWTUtilityClasses.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class RootMapping {
    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public RootMapping(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {


        Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())

            );

            // final UserDetails user = (UserDetails) auth.getPrincipal();
            final AppUser admin = (AppUser) auth.getPrincipal();
            String Jwt = jwtUtil.generateToken(admin.getUsername(), admin);


        return ResponseEntity.ok(Map.of("token", Jwt, "message", "Login Successful"));
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        System.out.println("reached logout");
        return null;
    }
}
