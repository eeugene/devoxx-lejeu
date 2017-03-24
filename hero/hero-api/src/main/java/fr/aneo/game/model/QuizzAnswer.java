package fr.aneo.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "QUIZZ_ANSWER")
@ToString(exclude = "quizz")
public class QuizzAnswer {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    private String answer;

    @NotNull
    @Column(name = "IS_CORRECT_ANSWER")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    boolean isCorrectAnswer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "QUIZZ_ID")
    private Quizz quizz;

}
