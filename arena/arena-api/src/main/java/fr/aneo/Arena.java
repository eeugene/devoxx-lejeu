package fr.aneo;

import fr.aneo.domain.*;
import fr.aneo.eventstore.EventStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 03/03/2017.
 */
@Service
@Slf4j
public class Arena {

    @Value("${fightIntervalInSeconds}")
    private int fightIntervalInSeconds;

    @Autowired
    private HeroService heroService;
    @Autowired
    private FightExecutor fightExecutor;
    @Autowired
    private PublishService publishService;
    @Autowired
    private EventStore eventStore;

    public void start() {
        while (true) {
            try {
                log.info("run battles");
                List<Hero> heros = heroService.getHeros();
                Optional<BattleResults> battleResults = startBattles(heros);
                if (battleResults.isPresent()) {
                    saveBattleResults(battleResults.get());
                }
                Thread.sleep(TimeUnit.SECONDS.toMillis(fightIntervalInSeconds));
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }

    private void saveBattleResults(BattleResults battleResults) {
        eventStore.saveEvents(battleResults);
        publishService.publish(battleResults);
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

    public BattleResult fight(Hero hero1, Hero hero2) {
        FightDefinition fightDefinition = getFightDefinition(hero1, hero2);
        return new BattleResult(hero1, hero2, fightExecutor.fight(fightDefinition), LocalDateTime.now());
    }

    public FightDefinition getFightDefinition(Hero hero1, Hero hero2) {
        // hero bonus
        Optional<Bonus> hero1Bonus = getBonus(hero1);
        Optional<Bonus> hero2Bonus = getBonus(hero2);
        FightDefinition.FightDefinitionBuilder fightDefinitionBuilder = FightDefinition.builder();

        // random start
        boolean isStartRandom = false;
        boolean hero1StartAttacking;
        if (heroStartAttacking(hero1Bonus, hero2Bonus)) {
            fightDefinitionBuilder.startWithHero1(true);
        } else if (heroStartAttacking(hero2Bonus, hero1Bonus)) {
            fightDefinitionBuilder.startWithHero1(false);
        } else {
            fightDefinitionBuilder.startRandom(true);
            fightDefinitionBuilder.startWithHero1(RandomUtils.nextBoolean());
        }

        fightDefinitionBuilder.cancelHero1FirstAttack(hasBonus(hero2Bonus, Bonus.CANCEL_OPPONENT_FIRST_ATTACK));
        fightDefinitionBuilder.cancelHero2FirstAttack(hasBonus(hero1Bonus, Bonus.CANCEL_OPPONENT_FIRST_ATTACK));

        // other params
        int hero1Hp = hero1.getHp();
        int hero2Hp = hero2.getHp();
        if (hasBonus(hero1Bonus, Bonus.ADD_10_PERCENT_HP)) {
            hero1Hp = (int)(hero1Hp + (hero1Hp*0.1));
        }
        if (hasBonus(hero2Bonus, Bonus.ADD_10_PERCENT_HP)) {
            hero2Hp = (int)(hero2Hp + (hero2Hp*0.1));
        }
        if (hasBonus(hero1Bonus, Bonus.OPPONENT_START_AT_50_PERCENT_HP)) {
            hero2Hp = (int)(hero2Hp*0.5);
        }
        if (hasBonus(hero2Bonus, Bonus.OPPONENT_START_AT_50_PERCENT_HP)) {
            hero1Hp = (int)(hero1Hp*0.5);
        }
        fightDefinitionBuilder.hero1Hp(hero1Hp);
        fightDefinitionBuilder.hero2Hp(hero2Hp);
        fightDefinitionBuilder.hero1Attack(hero1.getAttackForce());
        fightDefinitionBuilder.hero2Attack(hero2.getAttackForce());
        if (hasBonus(hero1Bonus, Bonus.ADD_20_PERCENT_ON_FIRST_ATTACK)) {
            fightDefinitionBuilder.hero1FirstAttackHas20PercentMorePower(true);
        }
        if (hasBonus(hero2Bonus, Bonus.ADD_20_PERCENT_ON_FIRST_ATTACK)) {
            fightDefinitionBuilder.hero2FirstAttackHas20PercentMorePower(true);
        }
        return fightDefinitionBuilder.build();
    }

    private boolean hasBonus(Optional<Bonus> heroBonus, Bonus bonus) {
        return heroBonus.isPresent() && heroBonus.get() == bonus;
    }

    private boolean heroStartAttacking(Optional<Bonus> hero1Bonus, Optional<Bonus> hero2Bonus) {
        if (hasBonus(hero1Bonus, Bonus.FORCE_START_ATTACKING)
                && !hasBonus(hero2Bonus, Bonus.FORCE_START_ATTACKING)) {
            return true;
        }
        return false;
    }

    private Optional<Bonus> getBonus(Hero hero) {
        if (StringUtils.isEmpty(hero.getBonus())) {
            return Optional.empty();
        }
        Bonus bonus = Bonus.valueOf(hero.getBonus());
        return Optional.of(bonus);
    }
}
