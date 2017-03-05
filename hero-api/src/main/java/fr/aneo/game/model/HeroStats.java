package fr.aneo.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

/**
 * Created by raouf on 04/03/17.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroStats {


    @Column(name = "ATTACK")
    private int attackLevel;

    @Enumerated(value = STRING)
    @Column(name = "BONUS")
    private Bonus currentBonus;

    @Column(name = "WIN")
    private int wins;

    @Column(name = "LOSS")
    private int losses;

    @Column(name = "RANK")
    private int currentRanking;

    @Column(name = "BEST_RANK")
    private int bestRanking;
}
