package fr.aneo.api;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.util.Collection;

/**
 * Created by eeugene on 24/03/2017.
 */
@Data
@Builder
public class UpdateHeroStats {
    @Tolerate UpdateHeroStats() {}
    Collection<NewHeroStats> list;
}
