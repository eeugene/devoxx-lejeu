package fr.aneo.api;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.domain.Hero;
import fr.aneo.domain.HeroStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    public HeroService(@Value("${heroApiUrl}") String heroApiUrl,
                       JwtService jwtService) {
        heroApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(HeroApi.class, heroApiUrl);
        header = createAuthenticationHeader(jwtService);
    }

    public List<Hero> loadHeros() {
        heros = heroApi.heros(header);
        return heros;
    }

    public void saveStats(Collection<HeroStats> stats, LocalDateTime tournamentStartTime) {
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
                                .bestRanking(s.getBestRank())
                                .lastFiveBattles(
                                        s.getLastFiveBattles().getWindow()
                                                .stream()
                                                .map(b -> (b.isBattleWined() ? "[W]" : "[L]")
                                                        + " " +
                                                        b.getOpponent().getNickname()
                                                ).collect(Collectors.joining(";"))
                                )
                                .build()
                        )
                        .build()
        ).collect(Collectors.toList());
        UpdateHeroStats updateHeroStats = UpdateHeroStats.builder().list(list).tournamentStartTime(tournamentStartTime).build();
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

    public Map<String,Object> createAuthenticationHeader(JwtService jwtService) {
        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + jwtService.getToken());
        return headerMap;
    }
}
