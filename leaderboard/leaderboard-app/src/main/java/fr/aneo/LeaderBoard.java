package fr.aneo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by eeugene on 05/03/2017.
 */
@Data
@XmlRootElement
@Builder
public class LeaderBoard {

    @Tolerate
    public LeaderBoard() {}

    private List<LeaderBoardLine> list;
}
