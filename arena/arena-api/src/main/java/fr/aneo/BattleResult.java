package fr.aneo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
@Data
@Value
@AllArgsConstructor
public class BattleResult {
    Hero hero1;
    Hero hero2;
    boolean player1Won;
}
