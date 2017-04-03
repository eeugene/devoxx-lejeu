package fr.aneo.game.service;

import fr.aneo.game.model.Bonus;
import fr.aneo.game.model.Quizz;
import fr.aneo.game.model.QuizzHeroAnswer;
import fr.aneo.game.repository.QuizzHeroAnswerRepository;
import fr.aneo.game.repository.QuizzRepository;
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
    @Autowired
    private HeroService heroService;

    public Quizz getCurrentQuizz() {
        return getNextAvailableQuizz();
    }

    public QuizzHeroAnswer heroHasAnsweredQuizz(String heroEmail, Long quizzId) {
        return quizzHeroAnswerRepository.findOne(new QuizzHeroAnswer.Id(heroEmail, quizzId));
    }

    @Transactional
    public void saveHeroAnswerToQuizz(String heroEmail, Long quizzId, Long answerId) {
        if (heroHasAnsweredQuizz(heroEmail, quizzId) == null) {
            QuizzHeroAnswer quizzHeroAnswer = new QuizzHeroAnswer();
            quizzHeroAnswer.setId(new QuizzHeroAnswer.Id(heroEmail, quizzId));
            quizzHeroAnswer.setQuizzAnswerId(answerId);
            Quizz quizz = quizzRepository.findOne(quizzId);
            if (quizz != null && quizz.isCorrectAnswer(answerId)) {
                if (quizz.isBonus()) {
                    Bonus bonus = heroService.offerRandomBonus(heroEmail);
                    quizzHeroAnswer.setBonusWined(bonus);
                } else {
                    heroService.incrementHp(heroEmail);
                }
            }
            quizzHeroAnswerRepository.save(quizzHeroAnswer);
        }
    }

    void disableCurrentQuizz() {
        Quizz currentQuizz = getCurrentQuizz();
        if (currentQuizz != null && currentQuizz.isActive()) {
            currentQuizz.setActive(false);
            quizzRepository.save(currentQuizz);
        }
    }

    private Quizz getNextAvailableQuizz() {
        log.debug("Loading next available quizz");
        Quizz quizz;
        quizz = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && q.isBonus()).findFirst().orElse(null);
        if (quizz == null) { // no bonus quizz, try normal quizz
            quizz = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && !q.isBonus()).findFirst().orElse(null);
        }
        log.debug("Found NextQuizz: " + quizz);
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
