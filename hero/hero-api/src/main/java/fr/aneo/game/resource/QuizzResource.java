package fr.aneo.game.resource;

import fr.aneo.game.model.Quizz;
import fr.aneo.game.service.QuizzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rcollard on 09/03/2017.
 */
@RestController
@RequestMapping("/api/quizz")
public class QuizzResource {

    @Autowired
    private QuizzService quizzService;


    @GetMapping
    public Quizz current() {
        return quizzService.getCurrentQuestion();
    }
}
