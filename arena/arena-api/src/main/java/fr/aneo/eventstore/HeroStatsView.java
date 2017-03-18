package fr.aneo.eventstore;

import fr.aneo.api.HeroService;
import fr.aneo.domain.Hero;
import fr.aneo.leaderboard.LeaderboardService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by eeugene on 15/03/2017.
 */
@Component
public class HeroStatsView implements Consumer<BattleFinished> {

    @Data
    @EqualsAndHashCode(of = "email")
    public static class HeroStats {
        Hero hero;
        int rank;
        int totalFightCount;
        int totalVictoryCount;
        int totalLossCount;

        public HeroStats(Hero hero) {
            this.hero = hero;
        }

        public Double getWinRatio() {
            double ratio = (double) totalVictoryCount / totalFightCount;
            double value = (ratio * 100);
            double rounded = (double) Math.round(value * 100) / 100;
            return rounded;
        }
        Map<Hero,Integer> opponentCount = new HashMap<>();
    }

    @Autowired
    HeroService heroService;

    Map<Hero, HeroStats> heroStatsMap = new HashMap<>();

    public void init(List<BattleFinished> events ) {
        events.forEach(event -> this.accept(event));
    }

    @Override
    public void accept(BattleFinished battleFinished) {
        Optional<Hero> optHero1 = heroService.getHero(battleFinished.getHero1Email());
        if (!optHero1.isPresent()) return;
        Hero hero1 = optHero1.get();
        HeroStats hero1Stats = heroStatsMap.get(hero1);
        if (hero1Stats == null) {
            hero1Stats = new HeroStats(hero1);
            heroStatsMap.put(hero1, hero1Stats);
        }
        Optional<Hero> optHero2 = heroService.getHero(battleFinished.getHero2Email());
        if (!optHero2.isPresent()) return;
        Hero hero2 = optHero2.get();
        HeroStats hero2Stats = heroStatsMap.get(hero2);
        if (hero2Stats == null) {
            hero2Stats = new HeroStats(hero2);
            heroStatsMap.put(hero2, hero2Stats);
        }
        // computing stats hero1
        hero1Stats.totalFightCount++;
        hero1Stats.totalVictoryCount += battleFinished.isHero1Won() ? 1 : 0;
        hero1Stats.totalLossCount += battleFinished.isHero1Won() ? 0 : 1;
        hero1Stats.rank = getHeroRank(hero1Stats);
        hero1Stats.opponentCount.compute(hero2, (k,v) -> v == null ? 1 : v+1);
        // computing stats hero2
        hero2Stats.totalFightCount++;
        hero2Stats.totalVictoryCount += battleFinished.isHero1Won() ? 0 : 1;
        hero2Stats.totalLossCount += battleFinished.isHero1Won() ? 1 : 0;
        hero2Stats.rank = getHeroRank(hero2Stats);
        hero2Stats.opponentCount.compute(hero1, (k, v) -> v == null ? 1 : v+1);
    }

    private int getHeroRank(HeroStats heroStats) {
        return (int)heroStatsMap.entrySet()
                .stream()
                .filter( entry -> entry.getValue().getWinRatio() > heroStats.getWinRatio())
                .count()+1;
    }

    public Map<Hero, HeroStats> getStats() {
        return heroStatsMap;
    }
}
