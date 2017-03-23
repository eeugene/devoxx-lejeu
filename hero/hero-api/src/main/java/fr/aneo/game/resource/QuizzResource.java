package fr.aneo.game.resource;

import fr.aneo.game.model.Quizz;
import fr.aneo.game.service.HeroService;
import fr.aneo.game.service.QuizzService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by rcollard on 09/03/2017.
 */
@RestController
@RequestMapping("/api/quizz")
public class QuizzResource {

    @Autowired
    private QuizzService quizzService;

    @Autowired
    private HeroService heroService;

    @GetMapping
    public ResponseEntity<Quizz> current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("no authentication found");
        }
        Quizz currentQuestion = quizzService.getCurrentQuestion();
        String heroEmail = authentication.getName();
        if (quizzService.heroHasAnsweredQuestion(heroEmail, currentQuestion.getId())) {
            return ok(null);
        } else {
            return ok(currentQuestion);
        }
    }

    @PostMapping
    public void answerCurrentQuestion(@RequestBody QuizzAnswer quizzAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("no authentication found");
        }
        String heroEmail = authentication.getName();
        quizzService.saveAnswerToCurrentQuestion(heroEmail, quizzAnswer.getQuizzId(), quizzAnswer.getAnswerId());
        return;
    }

    @Data
    static class QuizzAnswer {
        long quizzId;
        long answerId;
    }
}
