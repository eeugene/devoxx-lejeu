package fr.aneo.leaderboard;

import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.HeroService;
import fr.aneo.domain.BattleResult;
import fr.aneo.domain.BattleResults;
import fr.aneo.domain.Hero;
import fr.aneo.eventstore.BattleFinished;
import fr.aneo.eventstore.EventStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 05/03/2017.
 */
@Service
@Slf4j
public class LeaderboardService {

    Map<Hero, Integer> leaderboad = new HashMap<>();
    LeaderboardApi leaderboardApi;
    @Autowired
    EventStore eventStore;
    @Autowired
    HeroService heroService;

    public void init() {
        log.info("Rehydrating Leaderboard...");
        List<Hero> heros = heroService.loadHeros();
        List<BattleFinished> events = eventStore.load();
        BattleResults battleResults = new BattleResults();
        List<BattleResult> battleResultList = events.stream().map(evt ->
                BattleResult.builder()
                        .hero1(heros.stream().filter(h -> h.getEmail().equals(evt.getHero1Email())).findFirst().get())
                        .hero2(heros.stream().filter(h -> h.getEmail().equals(evt.getHero2Email())).findFirst().get())
                        .hero1Won(evt.isHero1Won())
                        .time(LocalDateTime.ofInstant(evt.getTime().toInstant(), ZoneId.systemDefault()))
                        .build()
        ).collect(Collectors.toList());
        battleResults.setResults(battleResultList);
        addBattleResults(battleResults);
    }

    public LeaderboardService() {
        leaderboardApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate requestTemplate) {
                        log.info(requestTemplate.toString());
                    }
                })
                .target(LeaderboardApi.class, "http://localhost:8081");
    }

    public void addBattleResults(BattleResults battleResults) {
        List<BattleResult> results = battleResults.getResults();
        for (BattleResult battleR : results) {
            Hero hero1 = battleR.getHero1();
            Hero hero2 = battleR.getHero2();
            Hero winnerHero;
            if (battleR.isHero1Won()) {
                winnerHero = hero1;
            } else {
                winnerHero = hero2;
            }
            leaderboad.compute(winnerHero, (k, v) -> v == null ? 1 : v+1);
        }
        updateLeaderboard();
    }

    private void updateLeaderboard() {
        List<LeaderBoardLine> list = leaderboad.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(20)
                .map( kv -> new LeaderBoardLine(kv.getKey().getEmail(), kv.getKey().getName(), kv.getValue()))
                .collect(Collectors.toList());

        LeaderBoard leaderBoard = new LeaderBoard(list);
        leaderboardApi.update(leaderBoard);
        printLeaderboard(leaderBoard);
    }

    public void printLeaderboard(LeaderBoard leaderBoard) {
        log.info("-- LEADERBOARD --\n" );
        int i = 0;
        for (LeaderBoardLine entry: leaderBoard.getList()) {
            log.info(
                    (++i) + "- " + entry.getHeroName() + " - " + entry.getWinCount()
            );
        }
    }
}
