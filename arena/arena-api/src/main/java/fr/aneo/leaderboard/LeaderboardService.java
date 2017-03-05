package fr.aneo.leaderboard;

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
        printLeaderboard();
    }

    public void printLeaderboard() {
        List<Map.Entry<String, Integer>> list = leaderboad.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(20)
                .collect(Collectors.toList());
        System.out.println("-- LEADERBOARD --\n" );
        int i = 0;
        for (Map.Entry<String, Integer> entry: list) {
            System.out.println(
                    (++i) + "- " + entry.getKey() + " - " + entry.getValue()
            );
        }
    }
}
