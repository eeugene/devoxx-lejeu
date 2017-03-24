package fr.aneo.api;

import fr.aneo.domain.HeroStats;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import javax.persistence.Column;

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
        private Integer wins;
        private Integer losses;
        private Integer currentRanking;
        private Integer bestRanking;
    }
}
