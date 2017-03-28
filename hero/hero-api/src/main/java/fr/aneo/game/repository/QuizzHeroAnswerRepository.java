package fr.aneo.game.repository;

import fr.aneo.game.model.QuizzHeroAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by raouf on 05/03/17.
 */
public interface QuizzHeroAnswerRepository extends JpaRepository<QuizzHeroAnswer, QuizzHeroAnswer.Id> {
    List<QuizzHeroAnswer> findByIdHeroEmail(String email);
}
