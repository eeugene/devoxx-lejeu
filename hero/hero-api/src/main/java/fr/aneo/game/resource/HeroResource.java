package fr.aneo.game.resource;

import fr.aneo.game.model.Hero;
import fr.aneo.game.service.HeroService;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/hero")
public class HeroResource {

    @Autowired
    private HeroService heroService;

    @GetMapping
    public List<Hero> getAllHeroes() {
        return heroService.findAllHeroes();
    }

    @GetMapping("/{email:.*}")
    public ResponseEntity<Hero> getHeroByEmail(@PathVariable @Email String email) {
        Hero hero = heroService.findHeroByEmail(email);
        if(hero == null) {
            return notFound().build();
        }
        return ok(hero);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<HeroResponse> createHero(@RequestBody Hero hero) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/")
                .buildAndExpand(hero.getEmail()).toUri();
        Hero createdHero;
        try {
            createdHero = heroService.createHero(hero);
        } catch (Exception e) {
            return status(INTERNAL_SERVER_ERROR).body(HeroResponse.builder().errors(e.getMessage()).build());
        }
        if(createdHero == null) {
            return status(INTERNAL_SERVER_ERROR).body(HeroResponse.builder().errors("Hero can't be created").build());
        }
        return created(location)
                .body(HeroResponse.builder().message(
                        format("The hero %s identified by %s was correctly created",
                        hero.getNickname(), hero.getEmail())).build());
    }

    @Data
    @Builder
    public static class HeroResponse {
        private String message;
        private String errors;
    }
}
