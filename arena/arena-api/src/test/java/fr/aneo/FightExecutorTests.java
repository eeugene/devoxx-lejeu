package fr.aneo;

import fr.aneo.domain.Bonus;
import fr.aneo.domain.FightDefinition;
import fr.aneo.domain.Hero;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by eeugene on 04/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FightExecutorTests {
    @Autowired
    Arena arena;
    @Autowired
    FightExecutor fightExecutor;

    @Test
    public void fightExecutorTests_Classic() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, null), buildHero(2, null));
        System.out.println(fightDefinition);
        boolean hero1Won = fightExecutor.fight(fightDefinition);
        if (fightDefinition.isStartWithHero1())
        {
            assertThat(hero1Won).isTrue();
        } else {
            assertThat(hero1Won).isFalse();
        }
    }

    @Test
    public void fightExecutorTests_Combi1() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.ADD_10_PERCENT_HP), buildHero(2, Bonus.FORCE_START_ATTACKING));
        System.out.println(fightDefinition);
        boolean hero1Won = fightExecutor.fight(fightDefinition);
        assertThat(hero1Won).isTrue();
    }

    @Test
    public void fightExecutorTests_CancelFirstAttack() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, null), buildHero(2, Bonus.CANCEL_OPPONENT_FIRST_ATTACK));
        System.out.println(fightDefinition);
        boolean hero1Won = fightExecutor.fight(fightDefinition);
        assertThat(hero1Won).isFalse();
    }

    private Hero buildHero(int id, Bonus bonus) {
        String email = RandomStringUtils.randomAlphabetic(5);
        return Hero.builder().email(email).name("name-" + id).hp(100).attackForce(50).bonus(bonus != null ? bonus.name() : null).build();
    }
}