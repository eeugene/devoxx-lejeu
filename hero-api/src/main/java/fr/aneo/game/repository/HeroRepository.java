package fr.aneo.game.repository;

import fr.aneo.game.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by raouf on 04/03/17.
 */
public interface HeroRepository extends JpaRepository<Hero, String> {
}
