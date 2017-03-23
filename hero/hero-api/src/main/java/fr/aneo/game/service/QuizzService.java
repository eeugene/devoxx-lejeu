package fr.aneo.game.service;

import fr.aneo.game.model.Quizz;
import fr.aneo.game.repository.QuizzHeroAnswerRepository;
import fr.aneo.game.repository.QuizzRepository;
import fr.aneo.game.model.QuizzHeroAnswer;
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
    @Autowired
    private QuizzHeroAnswerRepository quizzHeroAnswerRepository;

    public Quizz getCurrentQuestion() {
        return quizzRepository.findAll().get(0);
    }

    public boolean heroHasAnsweredQuestion(String heroEmail, Long quizzId) {
        return quizzHeroAnswerRepository.findOne(new QuizzHeroAnswer.Id(heroEmail, quizzId)) != null;
    }

    public void saveAnswerToCurrentQuestion(String heroEmail, long quizzId, Long answerId) {
        QuizzHeroAnswer answer = new QuizzHeroAnswer();
        answer.setId(new QuizzHeroAnswer.Id(heroEmail, quizzId));
        answer.setQuizzAnswerId(answerId);
        quizzHeroAnswerRepository.save(answer);
    }

}
