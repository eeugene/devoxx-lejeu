package fr.aneo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eeugene on 19/03/2017.
 */
@Data
@EqualsAndHashCode(of = "hero")
public class HeroStats {
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

    Map<Hero, Integer> opponentCount = new HashMap<>();

    public void incTotalFightCount(int i) {
        totalFightCount += i;
    }

    public void incTotalVictoryCount(int i) {
        totalVictoryCount += i;
    }

    public void incTotalLossCount(int i) {
        totalLossCount += i;
    }
}
