package com.crm.travel.security.filter;

import com.crm.travel.security.jwt.JWTUtil;
import com.crm.travel.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService user;
private  final Logger log= LoggerFactory.getLogger(this.getClass());

    public JwtAuthenticationFilter(JWTUtil jwtUtil, CustomUserDetailsService user) {
        this.jwtUtil = jwtUtil;
        this.user = user;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // System.out.println("AUTH HEADER = [" + request.getHeader("Authorization") +
        // "]");

        // String path = request.getRequestURI();
        // if (path.equals("/api/check-auth") || path.equals("/api/logout")) {
        // filterChain.doFilter(request, response);
        // System.out.println("skipped filter");
        // return;
        // }
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
            // System.out.println("here in JwtAuthenticationFilter");

            try {
                String username = jwtUtil.extractUserName(token);
                // System.out.println(username);
                // System.out.println("here in jwtAuthFilter1");
                if (username != null &&
                        (existingAuth == null ||
                                existingAuth instanceof AnonymousAuthenticationToken)) {

                    UserDetails userDetails = user.loadUserByUsername(username);

                    if (jwtUtil.validateToken(token, userDetails)) {

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                        authToken.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request));

                        SecurityContextHolder.getContext()
                                .setAuthentication(authToken);
                    }
                }
            } catch (UsernameNotFoundException e) {
                log.error("Authentication failed", e);   // full stacktrace

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized");
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

}
