package fr.aneo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;

/**
 * Created by eeugene on 23/03/2017.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "QUIZZ_HERO_ANSWER")
public class QuizzHeroAnswer {

    @EmbeddedId
    private Id id = new Id();

    @NotNull
    @Column(name = "QUIZZ_ANSWER_ID")
    private long quizzAnswerId;

    @Column(name = "BONUS_WINED")
    @Enumerated(value = STRING)
    private Bonus bonusWined;

    @ManyToOne
    @JoinColumn(name = "QUIZZ_ANSWER_ID", insertable = false, updatable = false)
    QuizzAnswer answer;

    @Data
    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1322120000551624359L;
        @Column(name = "HERO_EMAIL")
        private String heroEmail;
        @Column(name = "QUIZZ_ID")
        private long quizzId;

        public Id() {
        }

        public Id(String heroEmail, long quizzId) {
            this.heroEmail = heroEmail;
            this.quizzId = quizzId;
        }
    }
}
