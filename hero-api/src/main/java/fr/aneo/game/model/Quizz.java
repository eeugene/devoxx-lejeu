package fr.aneo.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by raouf on 04/03/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quizz {

    @Id
    private Long id;

    private String question;

    private String [] possibleAnswers;

    private int validAnswerIndex;
}
