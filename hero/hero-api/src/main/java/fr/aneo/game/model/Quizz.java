package fr.aneo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "QUIZZ")
public class Quizz {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "QUESTION")
    private String question;

    @OneToMany(mappedBy = "quizz", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<QuizzAnswer> answers;

    @NotNull
    @Column(name = "ACTIVE")
    private boolean active;

    @NotNull
    @Column(name = "IS_BONUS")
    private boolean bonus;

    public boolean isCorrectAnswer(Long answerId) {
        if (answers != null) {
            return answers.stream()
                    .filter(a -> a.getId().equals(answerId))
                    .map(a -> a.isCorrectAnswer())
                    .findFirst().orElse(false);
        }
        return false;
    }
}
