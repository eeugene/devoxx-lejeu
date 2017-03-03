package fr.aneo;

import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
public interface HeroApi {
    @RequestLine("GET /heros")
    List<Hero> heros();

    @RequestLine("POST /heros/results")
    void saveResults(BattleResults result);
}
