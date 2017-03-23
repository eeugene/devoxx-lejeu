package fr.aneo.game.repository;

import fr.aneo.game.model.QuizzHeroAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by raouf on 05/03/17.
 */
public interface QuizzHeroAnswerRepository extends JpaRepository<QuizzHeroAnswer, QuizzHeroAnswer.Id> {
}
