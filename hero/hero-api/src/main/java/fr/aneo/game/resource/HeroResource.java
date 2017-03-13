package fr.aneo.game.resource;

import fr.aneo.game.model.Hero;
import fr.aneo.game.service.HeroService;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Formatter;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

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

    @PostMapping
    public ResponseEntity<String> createHero(@RequestBody Hero hero) {
        // create user
        // create hero
        heroService.createHero(hero);
        return ok(String.format("The hero %s was correctly created", hero.getNickname()));
    }
}
