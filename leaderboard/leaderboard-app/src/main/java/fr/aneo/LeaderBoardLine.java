package fr.aneo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

/**
 * Created by eeugene on 05/03/2017.
 */
@Data
@Builder
@EqualsAndHashCode(of = "heroId")
public class LeaderBoardLine {

    @Tolerate
    public LeaderBoardLine() {}
    private String heroId;
    private long avatarId;
    private String heroName;
    private double winRatio;
}
