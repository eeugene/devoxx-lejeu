package fr.aneo.game.resource;

import fr.aneo.game.model.Hero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by eeugene on 10/03/2017.
 */
@RestController
public class RegisterHeroResource {

    @PostMapping("/register")
    public ResponseEntity<Void> createHero(@RequestBody Hero user) {
        // create user
        // create hero
        return ok().build();
    }
}
