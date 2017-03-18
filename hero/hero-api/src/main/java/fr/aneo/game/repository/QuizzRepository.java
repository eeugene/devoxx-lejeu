package fr.aneo.game.repository;

import fr.aneo.game.model.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by raouf on 05/03/17.
 */
@Transactional
public interface QuizzRepository extends JpaRepository<Quizz, Long> {
    /*
    public Quizz getNextQuestion();

    public void postAnswer(Quizz q);
    */
}
