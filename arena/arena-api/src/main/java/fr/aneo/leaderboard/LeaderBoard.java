package fr.aneo.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by eeugene on 05/03/2017.
 */
@Data
@AllArgsConstructor
@XmlRootElement
public class LeaderBoard {
    private List<LeaderBoardLine> list;
}
