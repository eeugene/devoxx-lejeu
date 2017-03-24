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
    Quizz currentQuizz = null;
    private QuizzRepository quizzRepository;
    @Autowired
    private QuizzHeroAnswerRepository quizzHeroAnswerRepository;

    @Autowired
    public QuizzService(QuizzRepository quizzRepository) {
        this.quizzRepository = quizzRepository;
        currentQuizz = getNextQuizz();
    }

    public Quizz getCurrentQuizz() {
        return currentQuizz;
    }

    public boolean heroHasAnsweredQuizz(String heroEmail, Long quizzId) {
        return quizzHeroAnswerRepository.findOne(new QuizzHeroAnswer.Id(heroEmail, quizzId)) != null;
    }

    public void saveAnswerToCurrentQuizz(String heroEmail, long quizzId, Long answerId) {
        if (!heroHasAnsweredQuizz(heroEmail, quizzId)) {
            QuizzHeroAnswer answer = new QuizzHeroAnswer();
            answer.setId(new QuizzHeroAnswer.Id(heroEmail, quizzId));
            answer.setQuizzAnswerId(answerId);
            quizzHeroAnswerRepository.save(answer);
        }
    }

    void changeCurrentQuizz() {
        log.info("changeCurrentQuizz called: currentQuizz " + currentQuizz + " is being disabled");
        if (currentQuizz != null) {
            currentQuizz.setActive(false);
            quizzRepository.save(currentQuizz);
        }
        currentQuizz = getNextQuizz();
    }

    private Quizz getNextQuizz() {
        log.info("Loading next available quizz");
        Quizz quizz;
        quizz = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && q.isBonus()).findFirst().orElse(null);
        if (quizz == null) {
            quizz = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && !q.isBonus()).findFirst().orElse(null);
        }
        log.info("getNextQuizz: " + quizz);
        return quizz;
    }
}
