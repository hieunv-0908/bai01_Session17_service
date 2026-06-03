package re.bai01_session17;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieTicketJwtExample {

    public static void main(String[] args) {
        SecretKey key = Keys.hmacShaKeyFor("this-is-a-secret-key-of-at-least-32-characters-long-for-hs256-algorithmthis".getBytes());

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 123L);
        claims.put("roles", "USER");

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600 * 1000);

        String jwtToken = Jwts.builder()
                .claims(claims)
                .subject("user@movieticket.com")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();

        System.out.println("Generated JWT: " + jwtToken);

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwtToken);
            System.out.println("JWT is valid and verified!");

            String subject = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload().getSubject();
            Long userId = (Long) Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload().get("userId");
            String roles = (String) Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload().get("roles");

            System.out.println("Subject: " + subject);
            System.out.println("User ID: " + userId);
            System.out.println("Roles: " + roles);
        } catch (Exception e) {
            System.err.println("Invalid JWT: " + e.getMessage());
        }
    }
}

