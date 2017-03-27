package fr.aneo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by eeugene on 23/03/2017.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "QUIZZ_HERO_ANSWER")
public class QuizzHeroAnswer {

    @EmbeddedId
    Id id = new Id();

    @NotNull
    @Column(name = "QUIZZ_ANSWER_ID")
    long quizzAnswerId;

    @ManyToOne
    @JoinColumn(name = "QUIZZ_ANSWER_ID", insertable = false, updatable = false)
    QuizzAnswer answer;

    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1322120000551624359L;
        @Column(name = "HERO_EMAIL")
        protected String heroEmail;
        @Column(name = "QUIZZ_ID")
        protected long quizzId;

        public Id() {
        }

        public Id(String heroEmail, long quizzId) {
            this.heroEmail = heroEmail;
            this.quizzId = quizzId;
        }
    }
}
