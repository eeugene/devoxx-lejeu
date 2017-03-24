package fr.aneo.api;

import com.google.common.collect.Maps;
import com.sun.org.apache.bcel.internal.generic.NEW;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.domain.Hero;
import fr.aneo.domain.HeroStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 03/03/2017.
 */
@Service
@Slf4j
public class HeroService {

    private HeroApi heroApi;
    private List<Hero> heros;
    private Map<String, Object> header;

    public HeroService(@Value("${heroApiUrl}") String heroApiUrl) {
        heroApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(HeroApi.class, heroApiUrl);
        header = getHeader();
    }

    private List<Hero> loadHeros() {
        heros = heroApi.heros(header);
        return heros;
    }

    public void saveStats(Collection<HeroStats> stats) {
        log.debug("Hero stats to be saved");
        log.debug(stats.toString());
        Collection<NewHeroStats> list = stats.stream().map(
                s -> NewHeroStats
                        .builder()
                        .email(s.getHero().getEmail())
                        .stats(NewHeroStats.HeroStatsInfo
                                .builder()
                                .wins(s.getTotalVictoryCount())
                                .losses(s.getTotalLossCount())
                                .currentRanking(s.getRank())
                                .build()
                        )
                        .build()
        ).collect(Collectors.toList());
        UpdateHeroStats updateHeroStats = UpdateHeroStats.builder().list(list).build();
        heroApi.saveStats(header, updateHeroStats);
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

    public Map<String,Object> getHeader() {
        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2JvdCIsInNjb3BlcyI6WyJST0xFX0FETUlOIl0sImlhdCI6MTQ5MDM0OTM3MCwiaXNzIjoiYW5lbyIsImV4cCI6MTQ5MDQzNTc3MH0.u-PP9tS0744E_wLUxn3IK47_vA_ouDl2Asg8TPBvAAZzqhnBAq99yA_SIun6ufClZ9nNlRm0QOtxGS-IssJ93w");
        return headerMap;
    }
}
