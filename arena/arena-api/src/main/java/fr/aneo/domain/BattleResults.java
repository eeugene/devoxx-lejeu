package fr.aneo.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
@Data
@RequiredArgsConstructor
public class BattleResults {
    List<BattleResult> results;
}
