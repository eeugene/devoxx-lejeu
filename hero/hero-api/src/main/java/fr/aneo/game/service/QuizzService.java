package fr.aneo.game.service;

import akka.actor.ActorRef;
import fr.aneo.game.model.Bonus;
import fr.aneo.game.model.Quizz;
import fr.aneo.game.model.QuizzHeroAnswer;
import fr.aneo.game.repository.QuizzHeroAnswerRepository;
import fr.aneo.game.repository.QuizzRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static akka.pattern.PatternsCS.ask;

/**
 * Created by rcollard on 09/03/2017.
 */
@Service
@Slf4j
public class QuizzService {

    private QuizzRepository quizzRepository;
    @Autowired
    private QuizzHeroAnswerRepository quizzHeroAnswerRepository;
    @Autowired
    private HeroService heroService;

    @Autowired
    private ActorRef quizzScheduler;

    public Quizz getCurrentQuizz() {
        CompletableFuture<Object> ask = ask(quizzScheduler, new QuizzScheduler.GetCurrentQuizz(), 1000).toCompletableFuture();
        Object quizz = null;
        try {
            quizz = ask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (quizz == null || !(quizz instanceof Quizz)) {
            return null;
        }
        return (Quizz) quizz;
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
