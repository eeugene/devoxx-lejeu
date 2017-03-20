package fr.aneo.game.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aneo.game.model.HeroCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by raouf on 14/03/17.
 */
public class JWTSigninFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper;

    private JWTService jwtService;

    protected JWTSigninFilter(String defaultFilterProcessesUrl,
                              AuthenticationManager authenticationManager,
                              JWTService jwtService,
                              ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        HeroCredentials credentials = objectMapper.readValue(request.getInputStream(), HeroCredentials.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        jwtService.addToken(response, authResult, objectMapper);
    }
}
