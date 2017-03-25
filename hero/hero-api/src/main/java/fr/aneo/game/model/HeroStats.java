package fr.aneo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Embeddable
public class HeroStats {
    @Column(name = "WIN")
    private int wins;

    @Column(name = "LOSS")
    private int losses;

    @Column(name = "RANK")
    private int currentRanking;

    @Column(name = "BEST_RANK")
    private int bestRanking;
}
