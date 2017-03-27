package fr.aneo.game.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import fr.aneo.game.model.Hero;
import fr.aneo.game.model.HeroStats;
import fr.aneo.game.service.HeroService;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<HeroRegisterResponse> createHero(@RequestBody Hero hero) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/")
                .buildAndExpand(hero.getEmail()).toUri();
        Hero createdHero;
        try {
            createdHero = heroService.createHero(hero);
        } catch (Exception e) {
            return status(INTERNAL_SERVER_ERROR).body(HeroRegisterResponse.builder().errors(e.getMessage()).build());
        }
        if(createdHero == null) {
            return status(INTERNAL_SERVER_ERROR).body(HeroRegisterResponse.builder().errors("Hero can't be created").build());
        }
        return created(location)
                .body(HeroRegisterResponse.builder().message(
                        format("The hero %s identified by %s was correctly created",
                        hero.getNickname(), hero.getEmail())).build());
    }

    @PostMapping(value = "/stats")
    public void updateStats(@RequestBody UpdateHeroStats stats) {
        if (stats == null) {
            return;
        }

        heroService.updateStats(stats);
    }

    @Data
    @Builder
    public static class UpdateHeroStats {
        @Tolerate UpdateHeroStats() {}
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime tournamentStartTime;
        private Collection<NewHeroStats> list;
    }

    @Data
    @Builder
    public static class NewHeroStats {
        @Tolerate NewHeroStats() {}
        private String email;
        private HeroStats stats;
    }

    @Data
    @Builder
    public static class HeroRegisterResponse {
        private String message;
        private String errors;
    }
}
