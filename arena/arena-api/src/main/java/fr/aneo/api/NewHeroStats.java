package fr.aneo.api;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * Created by eeugene on 24/03/2017.
 */
@Data
@Builder
public class NewHeroStats {
    @Tolerate NewHeroStats() {}
    String email;
    HeroStatsInfo stats;

    @Data
    @Builder
    static class HeroStatsInfo {
        Integer wins;
        Integer losses;
        Integer currentRanking;
        Integer bestRanking;
        String lastFiveBattles;
    }
}
