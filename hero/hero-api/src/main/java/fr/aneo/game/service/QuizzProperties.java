package fr.aneo.game.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by eeugene on 22/04/2017.
 */
@Data
@ConfigurationProperties(prefix = "quizz")
public class QuizzProperties {
    int startAt;
    int endAt;
    int interval;
    String intervalTimeUnit;
    boolean simuMode;
    int simuInterval;
    String simuIntervalTimeUnit;
}
