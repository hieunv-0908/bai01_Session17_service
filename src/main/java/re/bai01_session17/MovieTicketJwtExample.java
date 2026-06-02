package re.bai01_session17;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieTicketJwtExample {

    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 123L);
        claims.put("roles", "USER");

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600 * 1000);

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject("user@movieticket.com")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Generated JWT: " + jwtToken);

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            System.out.println("JWT is valid and verified!");

            String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody().getSubject();
            Long userId = (Long) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody().get("userId");
            String roles = (String) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody().get("roles");

            System.out.println("Subject: " + subject);
            System.out.println("User ID: " + userId);
            System.out.println("Roles: " + roles);
        } catch (Exception e) {
            System.err.println("Invalid JWT: " + e.getMessage());
        }
    }
}

