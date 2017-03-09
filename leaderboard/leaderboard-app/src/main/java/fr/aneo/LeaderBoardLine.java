package fr.aneo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by eeugene on 05/03/2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardLine {
    private String heroId;
    private String heroName;
    private int winCount;
}
