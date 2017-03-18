package fr.aneo;

import fr.aneo.api.HeroService;
import fr.aneo.domain.BattleResults;
import fr.aneo.eventstore.HeroStatsView;
import fr.aneo.leaderboard.LeaderboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by eeugene on 05/03/2017.
 */
@Service
@Slf4j
public class PublishService {

    @Autowired
    LeaderboardService leaderboardService;
    @Autowired
    HeroService heroService;
    @Autowired
    HeroStatsView heroStatsView;

    public void publish(BattleResults battleResults) {
        if (log.isDebugEnabled()) {
            printBattleResult(battleResults);
        }
        heroService.saveStats(heroStatsView.getStats());
        leaderboardService.updateLeaderboard();
    }

    private void printBattleResult(BattleResults battleResults) {
        battleResults.getResults().forEach(
                r -> log.debug(
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
        rounds.forEach(r -> log.debug(valueOf(r)));
    }
}
