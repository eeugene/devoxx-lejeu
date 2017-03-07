package fr.aneo.game.repository;

import fr.aneo.game.model.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by raouf on 05/03/17.
 */
public interface QuizzRepository extends JpaRepository<Quizz, Long> {
}
