package fr.aneo.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

/**
 * Created by eeugene on 25/03/2017.
 */
@Service
public class JwtService {
    private final String token;

    public JwtService(@Value("${aneo.security.jwt.secretKey}") String jwtSecretKey) {
        token = createJwtToken(jwtSecretKey);
    }

    private String createJwtToken(String secretKey) {
        Claims claims = Jwts.claims().setSubject("robot");
        claims.put("scopes", Collections.singletonList("ROLE_ADMIN"));
        Date today = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setIssuedAt(today)
                .compact();
        return token;
    }

    public String getToken() {
        return token;
    }
}
