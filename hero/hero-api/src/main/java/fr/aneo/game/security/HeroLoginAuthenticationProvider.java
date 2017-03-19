package fr.aneo.game.security;

import fr.aneo.game.model.Hero;
import fr.aneo.game.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 19/03/2017.
 */
@Component
public class HeroLoginAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final HeroRepository heroRepository;

    @Autowired
    public HeroLoginAuthenticationProvider(final HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        Hero hero = heroRepository.findOne(email);

        if (hero == null) {
            throw new RuntimeException("Hero not found: " + email);
        }

        if (!encoder.matches(password, hero.getPassword())) {
            throw new RuntimeException("Authentication Failed. Username or Password not valid.");
        }

        if (hero.getRole() == null) throw new RuntimeException("Hero has no roles assigned");

        List<GrantedAuthority> authorities = Arrays.asList(hero.getRole()).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());

        AuthenticatedHero userContext = new AuthenticatedHero(hero.getEmail(), authorities);
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
