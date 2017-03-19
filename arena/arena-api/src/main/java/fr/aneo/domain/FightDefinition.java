package fr.aneo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

/**
 * Created by eeugene on 04/03/2017.
 */
@Data
@Value
@Builder
@AllArgsConstructor
public class FightDefinition {
    int hero1Hp;
    int hero1Attack;
    boolean hero1FirstAttackHas20PercentMorePower;
    boolean startWithHero1;
    boolean startRandom;
    boolean cancelHero1FirstAttack;
    int hero2Hp;
    int hero2Attack;
    boolean hero2FirstAttackHas20PercentMorePower;
    boolean cancelHero2FirstAttack;
}
