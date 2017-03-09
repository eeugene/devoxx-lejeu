package fr.aneo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Created by eeugene on 03/03/2017.
 */
@Data
@Value
@Builder
@AllArgsConstructor
public class BattleResult {
    Hero hero1;
    Hero hero2;
    boolean hero1Won;
    LocalDateTime time;
}
