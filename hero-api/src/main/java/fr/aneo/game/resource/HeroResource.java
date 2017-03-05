package fr.aneo.game.resource;

import fr.aneo.game.model.Hero;
import fr.aneo.game.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by raouf on 04/03/17.
 */
@RestController
public class HeroResource {

    @Autowired
    private HeroService heroService;

    @GetMapping
    public ResponseEntity<List<Hero>> getAllHeroes() {

        return ok(heroService.findAllHeroes());
    }
}
