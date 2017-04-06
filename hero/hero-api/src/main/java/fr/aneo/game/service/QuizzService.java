package fr.aneo.game.service;

import fr.aneo.game.model.Bonus;
import fr.aneo.game.model.Quizz;
import fr.aneo.game.model.QuizzHeroAnswer;
import fr.aneo.game.repository.QuizzHeroAnswerRepository;
import fr.aneo.game.repository.QuizzRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    private Quizz currentQuizz;

    public Quizz getCurrentQuizz() {
        return currentQuizz;
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

    void changeCurrentQuizz() {
        if (currentQuizz != null && currentQuizz.isActive()) {
            currentQuizz.setActive(false);
            quizzRepository.save(currentQuizz);
        }
        this.currentQuizz = getNextAvailableQuizz();
    }

    private Quizz getNextAvailableQuizz() {
        Quizz quizz;
        List<Quizz> quizzes = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && q.isBonus()).collect(Collectors.toList());

        if (quizzes.isEmpty()) { // no bonus quizz, try normal quizz
            quizzes = quizzRepository.findAll()
                    .stream().filter(q -> q.isActive() && !q.isBonus()).collect(Collectors.toList());
        }
        quizz = getRandomQuizz(quizzes);
        log.debug("Found NextQuizz: " + quizz);
        return quizz;
    }

    private Quizz getRandomQuizz(List<Quizz> quizzes) {
        if (quizzes == null || quizzes.isEmpty()) {
            return null;
        }
        int size = quizzes.size();
        int randomIndex = size > 1 ? RandomUtils.nextInt(size) : 0;
        return quizzes.get(randomIndex);
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
