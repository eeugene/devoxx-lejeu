package fr.aneo.game.resource;

import fr.aneo.game.model.Quizz;
import fr.aneo.game.model.QuizzHeroAnswer;
import fr.aneo.game.model.Role;
import fr.aneo.game.service.QuizzService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by rcollard on 09/03/2017.
 */
@RestController
@RequestMapping("/api/quizz")
public class QuizzResource {

    @Autowired
    private QuizzService quizzService;

    @GetMapping
    public ResponseEntity<QuizzDto> currentQuizz() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Not authenticated");
        }
        Quizz currentQuizz = quizzService.getCurrentQuizz();
        if (currentQuizz == null) {
            return ok(null);
        }
        String heroEmail = authentication.getName();
        QuizzHeroAnswer quizzHeroAnswer = quizzService.heroHasAnsweredQuizz(heroEmail, currentQuizz.getId());
        if (quizzHeroAnswer != null) {
            return ok(QuizzDto.builder()
                    .quizzAnswered(true)
                    .quizz(currentQuizz)
                    .selectedAnswer(quizzHeroAnswer.getQuizzAnswerId())
                    .correctAnswer(quizzHeroAnswer.getAnswer().isCorrectAnswer())
                    .build());
        } else {
            return ok(QuizzDto.builder()
                    .quizz(currentQuizz).build());
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> answerCurrentQuizz(@RequestBody QuizzAnswer quizzAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Not authenticated");
        }
        Quizz currentQuizz = quizzService.getCurrentQuizz();
        if (currentQuizz.getId() != quizzAnswer.getQuizzId()) {
            throw new RuntimeException("the quizz is not active");
        }
        String heroEmail = authentication.getName();
        quizzService.saveHeroAnswerToQuizz(heroEmail, quizzAnswer.getQuizzId(), quizzAnswer.getAnswerId());
        return ok(currentQuizz.getAnswers().stream().filter(q -> q.getId().equals(quizzAnswer.getAnswerId())).findFirst().get().isCorrectAnswer());
    }

    @PostMapping("/bonus")
    public ResponseEntity addBonusQuizz(@RequestBody Quizz quizz) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Not authenticated");
        }
        Optional<? extends GrantedAuthority> adminAuthority = authentication.getAuthorities().stream().filter(a -> a.getAuthority().equals(Role.ADMIN.authority())).findFirst();
        if (adminAuthority.isPresent()) {
            quizzService.saveQuizz(quizz);
            return ok().body("quizz created");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Admin role required to access this service");
        }
    }

    @GetMapping(value = "/{quizzId}/activate")
    public ResponseEntity activateQuizz(@PathVariable Long quizzId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Not authenticated");
        }
        Optional<? extends GrantedAuthority> adminAuthority = authentication.getAuthorities().stream().filter(a -> a.getAuthority().equals(Role.ADMIN.authority())).findFirst();
        if (adminAuthority.isPresent()) {
            quizzService.activateQuizz(quizzId);
            return ok("quizz activated");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Admin role required to access this service");
        }
    }

    @Data
    static class QuizzAnswer {
        Long quizzId;
        Long answerId;
    }
    @Data
    @Builder
    static class QuizzDto {
        Quizz quizz;
        Boolean quizzAnswered = false;
        Boolean correctAnswer;
        Long selectedAnswer;
    }
}
