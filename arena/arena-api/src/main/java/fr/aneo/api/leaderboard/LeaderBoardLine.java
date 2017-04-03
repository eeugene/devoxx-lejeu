package fr.aneo.api.leaderboard;

import lombok.Builder;
import lombok.Data;

/**
 * Created by eeugene on 05/03/2017.
 */
@Data
@Builder
public class LeaderBoardLine {
    private String heroId;
    private long avatarId;
    private String heroName;
    private double winRatio;
}
