package fr.aneo.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by eeugene on 05/03/2017.
 */
@Data
@AllArgsConstructor
public class LeaderBoardLine {
    private String heroId;
    private String heroName;
    private int winCount;
}
