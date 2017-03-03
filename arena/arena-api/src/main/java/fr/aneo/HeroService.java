package fr.aneo;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
@Service
public class HeroService {

    HeroApi client;

    public HeroService() {
        client = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(HeroApi.class, "http://localhost:8080");
    }

    public void saveResults(BattleResults results) {
        client.saveResults(results);
    }

    public List<Hero> loadHeros() {
        return client.heros();
    }
}
