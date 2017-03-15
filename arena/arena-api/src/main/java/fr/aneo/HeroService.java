package fr.aneo;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.domain.BattleResults;
import fr.aneo.domain.Hero;
import fr.aneo.eventstore.HeroStatsView;
import lombok.extern.slf4j.Slf4j;
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

    HeroApi client;
    List<Hero> heros;

    public HeroService() {
        client = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(HeroApi.class, "http://localhost:8080");
    }

    public void saveResults(BattleResults results) {
        client.saveResults(results);
    }

    private List<Hero> loadHeros() {
        heros = client.heros();
        return heros;
    }

    public void saveStats(Map<Hero, HeroStatsView.HeroStats> stats) {
        log.info("Hero stats to be saved");
        log.info(stats.toString());
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
