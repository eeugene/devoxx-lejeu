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

    @GetMapping
    public ResponseEntity<Quizz> currentQuizz() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Not authenticated");
        }
        Quizz currentQuizz = quizzService.getCurrentQuizz();
        if (currentQuizz == null) {
            return ok(null);
        }
        String heroEmail = authentication.getName();
        if (quizzService.heroHasAnsweredQuizz(heroEmail, currentQuizz.getId())) {
            return ok(null);
        } else {
            return ok(currentQuizz);
        }
    }

    @PostMapping
    public void answerCurrentQuizz(@RequestBody QuizzAnswer quizzAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Not authenticated");
        }
        Quizz currentQuizz = quizzService.getCurrentQuizz();
        if (currentQuizz.getId() != quizzAnswer.getQuizzId()) {
            throw new RuntimeException("the quizz is not active");
        }
        String heroEmail = authentication.getName();
        quizzService.saveAnswerToCurrentQuizz(heroEmail, quizzAnswer.getQuizzId(), quizzAnswer.getAnswerId());
        return;
    }

    @Data
    static class QuizzAnswer {
        Long quizzId;
        Long answerId;
    }
}
