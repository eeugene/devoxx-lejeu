package fr.aneo.game.resource;

import fr.aneo.game.model.Hero;
import fr.aneo.game.service.HeroService;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.*;

/**
 * Created by raouf on 04/03/17.
 */
@RestController
@RequestMapping("/api/heroes")
public class HeroResource {

    @Autowired
    private HeroService heroService;

    @GetMapping
    public List<Hero> getAllHeroes() {
        return heroService.findAllHeroes();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Hero> getHeroByEmail(@PathVariable @Email String email) {
        Hero hero = heroService.findHeroByEmail(email);
        if(hero == null) {
            return notFound().build();
        }
        return ok(hero);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createHero(@RequestBody Hero hero) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/")
                .buildAndExpand(hero.getEmail()).toUri();

        Hero createdHero = heroService.createHero(hero);
        if(createdHero == null) {
            return status(INTERNAL_SERVER_ERROR).build();
        }
        return created(location)
                .body(format("The hero %s identified by %s was correctly created",
                        hero.getNickname(), hero.getEmail()));
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> authenticateHero(@RequestBody UserCredentials credentials) {
        Hero hero = heroService.findHeroByEmail(credentials.getUsername());
        if(hero == null) {
            return notFound().build();
        }
        return ok().build();
    }
}
