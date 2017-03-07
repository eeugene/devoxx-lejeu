package fr.aneo.game.resource;

import fr.aneo.game.authentication.AuthenticationService;
import fr.aneo.game.model.Hero;
import fr.aneo.game.service.HeroService;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by raouf on 04/03/17.
 */
@RestController
@RequestMapping("/hero")
public class HeroResource {

    @Autowired
    private HeroService heroService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/all")
    public ResponseEntity<List<Hero>> getAllHeroes() {

        return ok(heroService.findAllHeroes());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Hero> getHeroByEmail(@PathVariable @Email String email) {
        Hero hero = heroService.findHeroByEmail(email);
        if(hero == null) {
            return notFound().build();
        }
        return ok(hero);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createHero(@RequestBody @Valid Hero hero) {
        heroService.createHero(hero);
        authenticationService.login(hero.getEmail(), hero.getPassword());
        return ok().build();
    }
}
