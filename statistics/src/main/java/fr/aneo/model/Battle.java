package fr.aneo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by eeugene on 07/04/2017.
 */
@Data
@Builder
public class Battle {
    String hero1Email;
    String hero2Email;
    Boolean hero1Won;
    Date time;
}
