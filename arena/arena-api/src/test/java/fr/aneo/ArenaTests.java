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
public class ArenaTests {
    @Autowired
    Arena arena;
    @Test
    public void fightDefinitionTests() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, null), buildHero(2, null));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(true);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }
    @Test
    public void fightDefinitionTests_Hero1StartAttacking() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.BOOST_FIRST_START), buildHero(2, null));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isStartWithHero1()).isEqualTo(true);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(false);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }
    @Test
    public void fightDefinitionTests_Hero2StartAttacking() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, null), buildHero(2, Bonus.BOOST_FIRST_START));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isStartWithHero1()).isEqualTo(false);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(false);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }
    @Test
    public void fightDefinitionTests_BothStartAttacking() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.BOOST_FIRST_START), buildHero(2, Bonus.BOOST_FIRST_START));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(true);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }

    @Test
    public void fightDefinitionTests_CancelHero2FirstAttack() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.HANDICAP_ATTACK_CANCEL), buildHero(2, Bonus.BOOST_FIRST_START));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(true);
        assertThat(fightDefinition.isStartWithHero1()).isEqualTo(false);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(false);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }

    @Test
    public void fightDefinitionTests_CancelBothHeroFirstAttack() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.HANDICAP_ATTACK_CANCEL), buildHero(2, Bonus.HANDICAP_ATTACK_CANCEL));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(true);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(true);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(true);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }

    @Test
    public void fightDefinitionTests_Add10PercentHpToHero1() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.BOOST_HP_10), buildHero(2, null));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(110);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(true);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }

    @Test
    public void fightDefinitionTests_Hero2StartAt50PercentHp() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.HANDICAP_HP_50), buildHero(2, null));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(50);
        assertThat(fightDefinition.getHero1Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(true);
        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(false);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }

    @Test
    public void fightDefinitionTests_Add20PercentPowerOnFirstAttack() {
        FightDefinition fightDefinition = arena.getFightDefinition(buildHero(1, Bonus.BOOST_ATTACK_20), buildHero(2, null));
        assertThat(fightDefinition.getHero1Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Hp()).isEqualTo(100);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.getHero2Attack()).isEqualTo(1);
        assertThat(fightDefinition.isCancelHero1FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isCancelHero2FirstAttack()).isEqualTo(false);
        assertThat(fightDefinition.isStartRandom()).isEqualTo(true);

        assertThat(fightDefinition.isHero1FirstAttackHas20PercentMorePower()).isEqualTo(true);
        assertThat(fightDefinition.isHero2FirstAttackHas20PercentMorePower()).isEqualTo(false);
    }

    private Hero buildHero(int id, Bonus bonus) {
        String email = RandomStringUtils.randomAlphabetic(5);
        return Hero.builder().email(email).nickname("name-" + id).hpLevel(100).attackLevel(1).currentBonus(bonus != null ? bonus.name() : null).build();
    }
}
