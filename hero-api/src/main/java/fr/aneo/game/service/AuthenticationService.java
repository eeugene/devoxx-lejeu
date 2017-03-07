package fr.aneo.game.service;

import static java.util.Collections.singleton;

import fr.aneo.game.model.Hero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Created by raouf on 05/03/17.
 */
@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private HeroService heroService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public void login(String email, String password) {

        Hero hero = heroService.findHeroByEmail(email);
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(hero.getRole().name());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(hero, password, singleton(grantedAuthority));

        authenticationManager.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            log.info("Successfully authenticated {} with role {}", hero.getNickname(), hero.getRole().name());
        } else {
            log.error("Failed to authenticate {} ", hero.getNickname());
        }
    }


}
