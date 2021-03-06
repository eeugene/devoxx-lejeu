package fr.aneo.eventstore;

import fr.aneo.api.hero.HeroService;
import fr.aneo.domain.Hero;
import fr.aneo.domain.HeroStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by eeugene on 15/03/2017.
 */
@Component
public class HeroStatsView implements Consumer<BattleFinished> {

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
        hero1Stats.incTotalFightCount(1);
        hero1Stats.incTotalVictoryCount(battleFinished.isHero1Won() ? 1 : 0);
        hero1Stats.incTotalLossCount(battleFinished.isHero1Won() ? 0 : 1);
        hero1Stats.setRank(getHeroRank(hero1Stats));
        hero1Stats.getOpponentCount().compute(hero2, (k,v) -> v == null ? 1 : v+1);
        hero1Stats.getLastFiveBattles().add(HeroStats.BattleDetail.builder().opponent(hero2).battleWined(battleFinished.isHero1Won()).build());

        // computing stats hero2
        hero2Stats.incTotalFightCount(1);
        hero2Stats.incTotalVictoryCount(battleFinished.isHero1Won() ? 0 : 1);
        hero2Stats.incTotalLossCount(battleFinished.isHero1Won() ? 1 : 0);
        hero2Stats.setRank(getHeroRank(hero2Stats));
        hero2Stats.getOpponentCount().compute(hero1, (k, v) -> v == null ? 1 : v+1);
        hero2Stats.getLastFiveBattles().add(HeroStats.BattleDetail.builder().opponent(hero1).battleWined(!battleFinished.isHero1Won()).build());
    }

    private int getHeroRank(HeroStats heroStats) {
        return (int)heroStatsMap.entrySet()
                .stream()
                .filter( entry -> entry.getValue().getWinRatio() > heroStats.getWinRatio())
                .count()+1;
    }

    public Collection<HeroStats> getStats() {
        return heroStatsMap.values();
    }
}
