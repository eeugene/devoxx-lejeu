package fr.aneo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Entity
public class Quizz {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    private String question;

    @OneToMany(mappedBy = "quizzId", fetch = FetchType.EAGER)
    private Collection<QuizzAnswer> answers;

    @NotNull
    private boolean active;

    @NotNull
    @Column(name = "IS_BONUS")
    private boolean bonus;
}
