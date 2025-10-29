package mephi_java_spring.booking.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${booking.security.jwt.secret}")
    private String secret;

    @Value("${booking.security.jwt.expiration-minutes}")
    private long expirationMinutes;

    public String generateToken(Long userId, String username, String role) {
        Instant now = Instant.now();
        Instant exp = now.plus(expirationMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .addClaims(Map.of(
                        "uid", userId,
                        "role", role
                ))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public JwtUserData parseToken(String token) {
        var body = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();

        Long userId = ((Number) body.get("uid")).longValue();
        String username = body.getSubject();
        String role = (String) body.get("role");

        return new JwtUserData(userId, username, role);
    }
}
