package fr.aneo.leaderboard;

import feign.*;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.HeroApi;
import fr.aneo.domain.BattleResult;
import fr.aneo.domain.BattleResults;
import fr.aneo.domain.Hero;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 05/03/2017.
 */
@Service
public class LeaderboardService {

    Map<String, Integer> leaderboad = new HashMap<>();
    LeaderboardApi client;
    Logger logger = new Logger.JavaLogger();

    public LeaderboardService() {
        client = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(logger)
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate requestTemplate) {
                        System.out.println(requestTemplate.toString());
                    }
                })
                .target(LeaderboardApi.class, "http://localhost:8081");
    }

    public void updateLeaders(BattleResults battleResults) {
        List<BattleResult> results = battleResults.getResults();
        for (BattleResult battleR : results) {
            Hero hero1 = battleR.getHero1();
            Hero hero2 = battleR.getHero2();
            String winnerHero;
            if (battleR.isPlayer1Won()) {
                winnerHero = hero1.getName();
            } else {
                winnerHero = hero2.getName();
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
                .map( kv -> new LeaderBoardLine(kv.getKey(), kv.getValue()))
                .collect(Collectors.toList());

        LeaderBoard leaderBoard = new LeaderBoard(list);
        client.update(leaderBoard);
        printLeaderboard(leaderBoard);
    }

    public void printLeaderboard(LeaderBoard leaderBoard) {
        System.out.println("-- LEADERBOARD --\n" );
        int i = 0;
        for (LeaderBoardLine entry: leaderBoard.getList()) {
            System.out.println(
                    (++i) + "- " + entry.getHeroName() + " - " + entry.getWinCount()
            );
        }
    }
}
