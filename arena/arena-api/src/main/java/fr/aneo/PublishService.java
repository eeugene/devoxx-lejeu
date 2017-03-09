package fr.aneo;

import fr.aneo.domain.BattleResults;
import fr.aneo.leaderboard.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eeugene on 05/03/2017.
 */
@Service
public class PublishService {

    @Autowired
    LeaderboardService leaderboardService;

    public void publishBattleResults(BattleResults battleResults) {
        printBattleResult(battleResults);
        leaderboardService.addBattleResults(battleResults);
    }

    private void printBattleResult(BattleResults battleResults) {
        battleResults.getResults().forEach(
                r -> System.out.println(
                        "BattleResult -> "
                        + r.getHero1().getName() + (r.isHero1Won() ? " [W]" : "    ")
                        + " /VS/ "
                        + r.getHero2().getName() + (r.isHero1Won() ? " " : " [W] ")
                        + (r.getHero1().getBonus()!=null? "(Bonus "+r.getHero1().getName()+": "+r.getHero1().getBonus()+")": "")
                        + (r.getHero2().getBonus()!=null? "(Bonus "+r.getHero2().getName()+": "+r.getHero2().getBonus()+")" : "")
                )
        );
    }

    public void publishFightEvents(List<FightExecutor.FightRound> rounds) {
        rounds.forEach(r -> System.out.println(r));
    }
}
