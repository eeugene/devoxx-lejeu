package fr.aneo.game.service;

import fr.aneo.game.model.Quizz;
import fr.aneo.game.repository.QuizzRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rcollard on 09/03/2017.
 */
@Service
@Slf4j
public class QuizzService {

    @Autowired
    private QuizzRepository quizzRepository;

    public Quizz getQuestion() {
        return quizzRepository.getNextQuestion();
    }

    public void postAnswer(Quizz q) {
        quizzRepository.postAnswer(q);
    }

}
