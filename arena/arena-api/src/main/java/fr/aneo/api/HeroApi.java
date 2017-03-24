package fr.aneo.api;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import fr.aneo.domain.BattleResults;
import fr.aneo.domain.Hero;
import fr.aneo.domain.HeroStats;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by eeugene on 03/03/2017.
 */
public interface HeroApi {

    @RequestLine("GET /api/hero")
    List<Hero> heros(@HeaderMap Map<String, Object> headerMap);

    @RequestLine("POST /api/hero/stats")
    @Headers("Content-Type: application/json")
    void saveStats(@HeaderMap Map<String, Object> headerMap, UpdateHeroStats stats);
}
