package com.crm.travel.security.config;

/**
 * @file SecurityConfig.java
 * Author: Utsav
 * Date: 10-06-2025
 * Time: 00:15:29
 */

import com.crm.travel.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

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

        private final JwtAuthenticationFilter jwtauthfilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
                this.jwtauthfilter = jwtAuthFilter;
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
                                cors.configurationSource(request -> {
                                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                                        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                                        corsConfiguration.setAllowedMethods(
                                                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                                        corsConfiguration.setAllowedHeaders(List.of("*"));
                                        corsConfiguration.setAllowCredentials(true);
                                        corsConfiguration.setMaxAge(3600L);
                                        return corsConfiguration;
                                });
                })
                        .csrf(AbstractHttpConfigurer::disable)
                                .sessionManagement(
                                        sessionManagement -> sessionManagement
                                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/login").permitAll()
                                                .requestMatchers("/api/check-auth").permitAll()
                                                // Allow OPTIONS for CORS preflight
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                // Require Authentication for EVERYTHING else
                                                .requestMatchers("/api/**").authenticated())
                        .formLogin(AbstractHttpConfigurer::disable)
                        .httpBasic(AbstractHttpConfigurer::disable)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtauthfilter,
                                                UsernamePasswordAuthenticationFilter.class)

                                .build();

        }


}
