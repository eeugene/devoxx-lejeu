package fr.aneo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eeugene on 05/03/2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class LeaderBoard {
    private List<LeaderBoardLine> list;
}
