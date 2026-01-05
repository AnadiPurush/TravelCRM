package Com.Crm.Travel.Security;

/**
 * @file SecurityConfig.java
 * Author: Utsav
 * Date: 10-06-2025
 * Time: 00:15:29
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import Com.Crm.Travel.JWTUtilityClasses.jwtAuthFilter;

/**
 * @file SecurityConfig.java
 *       Author: Utsav
 *       Date: 01-06-2025
 *       Time: 23:59:57
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        private final jwtAuthFilter jwtauthfilter;

        public SecurityConfig(jwtAuthFilter jwtauthfilter) {
                this.jwtauthfilter = jwtauthfilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {

                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
                        throws Exception {
                return configuration.getAuthenticationManager();

        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http.cors(cors -> {
                })
                                .csrf(csrfCustomizer -> csrfCustomizer.disable())
                                .sessionManagement(
                                                sessionmanagement -> sessionmanagement
                                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/login").permitAll()
                                                .requestMatchers("/api/check-auth").permitAll()
                                                // Allow OPTIONS for CORS preflight
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                // Require Authentication for EVERYTHING else
                                                .requestMatchers("/api/**").authenticated())
                                .formLogin(form -> form
                                                .disable())
                                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtauthfilter,
                                                UsernamePasswordAuthenticationFilter.class)

                                .build();

        }

}
