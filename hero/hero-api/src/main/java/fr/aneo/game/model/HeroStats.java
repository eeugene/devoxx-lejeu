package fr.aneo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Embeddable
public class HeroStats {

    @Column(name = "ATTACK")
    @NotNull
    private int attackLevel;

    @Enumerated(value = STRING)
    @Column(name = "BONUS")
    private Bonus currentBonus;

    @Column(name = "WIN")
    private Integer wins;

    @Column(name = "LOSS")
    private Integer losses;

    @Column(name = "RANK")
    private Integer currentRanking;

    @Column(name = "BEST_RANK")
    private Integer bestRanking;
}
