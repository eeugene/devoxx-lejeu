package fr.aneo.game.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

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

    @OneToMany(mappedBy = "quizz", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<QuizzAnswer> answers;

    @NotNull
    private boolean active;

    @NotNull
    @Column(name = "IS_BONUS")
    private boolean bonus;

}
