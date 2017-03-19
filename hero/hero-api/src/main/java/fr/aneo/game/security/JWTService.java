package fr.aneo.game.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Created by raouf on 14/03/17.
 */
@Slf4j
@Service
class JWTService {

    private static final String AUTH_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";
    private static final String BEARER_TEMPLATE = HEADER_PREFIX + "%s";

    private String issuer;
    private String secretKey;
    private long expirationTime;

    @Autowired
    public JWTService(@Value("${aneo.security.jwt.issuer}") String issuer,
                      @Value("${aneo.security.jwt.secretKey}") String secretKey,
                      @Value("${aneo.security.jwt.expirationTime}") long expirationTime) {
        this.issuer = issuer;
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    void addToken(HttpServletResponse response, Authentication authentication) {
        AuthenticatedHero authenticatedHero = (AuthenticatedHero) authentication.getPrincipal();
        if (authenticatedHero == null) {
            throw new IllegalArgumentException("Cannot create JWT Token without authenticatedHero");
        }

        if (authenticatedHero.getAuthorities() == null || authenticatedHero.getAuthorities().isEmpty()) {
            throw new IllegalArgumentException("Hero doesn't have any privileges");
        }
        Claims claims = Jwts.claims().setSubject(authenticatedHero.getName());
        claims.put("scopes", authenticatedHero.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
        Date today = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setIssuedAt(today)
                .setIssuer(issuer)
                .setExpiration(new Date(today.getTime() + expirationTime))
                .compact();
        response.addHeader(AUTH_HEADER, format(BEARER_TEMPLATE, token));
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        if (token != null) {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(extract(token));
            String email = jwsClaims.getBody().getSubject();
            List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
            List<GrantedAuthority> authorities = scopes.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            if (email != null) {
                return new AuthenticatedHero(email, authorities);
            }
        }
        return null;
    }

    private String extract(String header) {
        if (StringUtils.isEmpty(header)) {
            throw new RuntimeException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new RuntimeException("Invalid authorization header size.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}