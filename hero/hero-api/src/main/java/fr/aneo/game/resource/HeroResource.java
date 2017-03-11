package fr.aneo.game.resource;

import fr.aneo.game.model.Hero;
import fr.aneo.game.service.HeroService;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by raouf on 04/03/17.
 */
@RestController
@RequestMapping("/api/hero")
public class HeroResource {

    @Autowired
    private HeroService heroService;

    @GetMapping("/all")
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
}
