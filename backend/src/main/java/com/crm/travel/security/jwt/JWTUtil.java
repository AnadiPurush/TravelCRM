package com.crm.travel.security.jwt;

import com.crm.travel.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Getter
@Setter
public class JWTUtil {
    @Value("${jwt.secret}")
    private String SecretKey;

    public String generateToken(String username, User user) {

        List<String> permissions = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        System.out.print(username);
        return Jwts.builder()
                .setSubject(username) // 👈 identity (email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(SecretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractUserName(String token) {
        return Jwts.parserBuilder().setSigningKey(SecretKey.getBytes()).build().parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token, UserDetails appUser) {
        return extractUserName(token).equals(appUser.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SecretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());

    }

}
