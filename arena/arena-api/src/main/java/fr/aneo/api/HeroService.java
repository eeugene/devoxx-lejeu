package fr.aneo.api;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.domain.Hero;
import fr.aneo.domain.HeroStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by eeugene on 03/03/2017.
 */
@Service
@Slf4j
public class HeroService {

    private HeroApi heroApi;
    private List<Hero> heros;

    public HeroService(@Value("${heroApiUrl}") String heroApiUrl) {
        heroApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(HeroApi.class, heroApiUrl);
    }

    private List<Hero> loadHeros() {
        heros = heroApi.heros();
        return heros;
    }

    public void saveStats(Map<Hero, HeroStats> stats) {
        log.debug("Hero stats to be saved");
        log.debug(stats.toString());
    }

    public Optional<Hero> getHero(String email) {
        return getHeros().stream().filter(h -> h.getEmail().equals(email)).findFirst();
    }

    public List<Hero> getHeros() {
        if (heros == null) {
            loadHeros();
        }
        return heros;
    }
}
