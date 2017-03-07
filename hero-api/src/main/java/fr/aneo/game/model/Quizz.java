package fr.aneo.game.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    private String question;

    @Column(name = "ANSWERS")
    private String answersAsString;

    @NotNull
    @Column(name = "CHOICE")
    private int userChoice;
}
