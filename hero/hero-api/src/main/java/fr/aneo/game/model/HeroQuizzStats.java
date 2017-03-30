package fr.aneo.game.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by eeugene on 30/03/2017.
 */
@Data
@Builder
public class HeroQuizzStats {
    private long totalQuizzAnswered;
    private long totalGoodAnswered;
    private List<String> bonusesWined;
}
