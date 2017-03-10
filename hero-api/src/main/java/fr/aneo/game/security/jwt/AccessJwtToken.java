package fr.aneo.game.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public final class AccessJwtToken {
    private static Logger logger = LoggerFactory.getLogger(AccessJwtToken.class);

    private final String token;
    @JsonIgnore
    private Claims claims;

    public AccessJwtToken(final String token, Claims claims) {
        this.token = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.token;
    }

    public Claims getClaims() {
        return claims;
    }
    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }
}