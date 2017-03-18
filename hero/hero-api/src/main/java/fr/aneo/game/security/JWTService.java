package fr.aneo.game.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.lang.String.format;

/**
 * Created by raouf on 14/03/17.
 */
@Slf4j
@Service
public class JWTService {

    private static final String AUTH_HEADER = "Autorization";
    private static final String BEARER_TEMPLATE = "Bearer %s";

    private String issuer;

    private String secretKey;

    private long expirationTime;

    @Autowired
    public JWTService(@Value("aneo.security.jwt.issuer") String issuer,
                      @Value("aneo.security.jwt.secretKey") String secretKey,
                      @Value("${aneo.security.jwt.expirationTime}") long expirationTime) {
        this.issuer = issuer;
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public void addToken(HttpServletResponse response, String email) {

        Date today = new Date();

        String token = Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setIssuedAt(today)
                .setIssuer(issuer)
                .setExpiration(new Date(today.getTime() + expirationTime))
                .compact();
        response.addHeader(AUTH_HEADER, format(BEARER_TEMPLATE, token));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        if(token != null) {
            String email = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if(email != null) {
                return new AuthenticatedHero(email);
            }
        }
        return null;
    }
}