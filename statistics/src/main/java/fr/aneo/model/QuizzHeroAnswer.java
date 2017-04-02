package fr.aneo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created by eeugene on 02/04/2017.
 */
@Data
@Builder
public class QuizzHeroAnswer {
    private int quizzId;
    private int answerId;
    private String bonusWined;
    public boolean isGoodAnswer() {
        return !StringUtils.isEmpty(bonusWined);
    }
}
