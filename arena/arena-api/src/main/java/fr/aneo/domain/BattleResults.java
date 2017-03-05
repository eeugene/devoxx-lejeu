package fr.aneo.domain;

import fr.aneo.domain.BattleResult;
import lombok.*;

import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
@Data
@RequiredArgsConstructor
public class BattleResults {
    List<BattleResult> results;
}
