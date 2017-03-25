package fr.aneo.game.service;

import fr.aneo.game.model.Quizz;
import fr.aneo.game.repository.QuizzHeroAnswerRepository;
import fr.aneo.game.repository.QuizzRepository;
import fr.aneo.game.model.QuizzHeroAnswer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Quizz getCurrentQuizz() {
        return getNextAvailableQuizz();
    }

    public boolean heroHasAnsweredQuizz(String heroEmail, Long quizzId) {
        return quizzHeroAnswerRepository.findOne(new QuizzHeroAnswer.Id(heroEmail, quizzId)) != null;
    }

    public void saveHeroAnswerToQuizz(String heroEmail, Long quizzId, Long answerId) {
        if (!heroHasAnsweredQuizz(heroEmail, quizzId)) {
            QuizzHeroAnswer answer = new QuizzHeroAnswer();
            answer.setId(new QuizzHeroAnswer.Id(heroEmail, quizzId));
            answer.setQuizzAnswerId(answerId);
            quizzHeroAnswerRepository.save(answer);
        }
    }

    void disableCurrentQuizz() {
        Quizz currentQuizz = getCurrentQuizz();
        if (currentQuizz != null) {
            currentQuizz.setActive(false);
            quizzRepository.save(currentQuizz);
        }
    }

    private Quizz getNextAvailableQuizz() {
        log.info("Loading next available quizz");
        Quizz quizz;
        quizz = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && q.isBonus()).findFirst().orElse(null);
        if (quizz == null) { // no bonus quizz, try normal quizz
            quizz = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && !q.isBonus()).findFirst().orElse(null);
        }
        log.info("Found NextQuizz: " + quizz);
        return quizz;
    }

    public void saveQuizz(Quizz quizz) {
        quizz.getAnswers().stream().forEach(a -> a.setQuizz(quizz));
        quizzRepository.save(quizz);
    }

    @Transactional
    public void activateQuizz(Long quizzId) {
        Quizz quizz = quizzRepository.findOne(quizzId);
        quizz.setActive(true);
    }
}
