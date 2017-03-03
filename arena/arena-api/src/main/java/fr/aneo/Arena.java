package fr.aneo;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 03/03/2017.
 */
@Service
public class Arena {

    final int minute = 60 * 1000;

    private HeroService heroService;

    @Autowired
    public Arena(HeroService heroService) {
        this.heroService = heroService;
    }

    public void start() {
        while (true) {
            try {
                System.out.println("run battles");
                List<Hero> heros = heroService.loadHeros();
                Optional<BattleResults> battleResults = startBattles(heros);
                if (battleResults.isPresent()) {
                    System.out.println(battleResults.get());
                    heroService.saveResults(battleResults.get());
                }
                Thread.sleep(minute);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }

    public Optional<BattleResults> startBattles(List<Hero> heros) {
        if (heros.size() < 2) {
            return Optional.empty();
        }
        if (heros.size() % 2 > 0) {
            int randomInt = RandomUtils.nextInt(heros.size());
            heros.remove(randomInt);
        }
        List<Hero> heros1 = heros.subList(0, heros.size() / 2);
        List<Hero> heros2 = new ArrayList<>(heros.subList(heros.size() / 2, heros.size()));

        List<BattleResult> battleResults = heros1.stream().map(h -> {
            int randomInt = RandomUtils.nextInt(heros2.size());
            Hero hero2 = heros2.remove(randomInt);
            return fight(h, hero2);
        }).collect(Collectors.toList());

        BattleResults results = new BattleResults();
        results.setResults(battleResults);
        return Optional.of(results);
    }

    private BattleResult fight(Hero hero1, Hero hero2) {
        return new BattleResult(hero1, hero2, false);
    }
}
