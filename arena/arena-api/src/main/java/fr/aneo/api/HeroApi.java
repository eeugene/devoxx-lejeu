package fr.aneo.api;

import feign.RequestLine;
import fr.aneo.domain.BattleResults;
import fr.aneo.domain.Hero;

import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
public interface HeroApi {
    @RequestLine("GET /hero")
    List<Hero> heros();

    @RequestLine("POST /hero/results")
    void saveStats(BattleResults result);
}
