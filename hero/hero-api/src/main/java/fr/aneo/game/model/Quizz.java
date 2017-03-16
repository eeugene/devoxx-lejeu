package fr.aneo.game.model;

import lombok.*;

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
public class Quizz {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "ANSWERS")
    private List<String> answersAsString;

    @NotNull
    @Column(name = "CHOICE")
    private int userChoice;
}
