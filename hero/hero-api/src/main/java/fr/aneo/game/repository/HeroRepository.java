package fr.aneo.game.repository;

import fr.aneo.game.model.Hero;
import fr.aneo.game.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by raouf on 04/03/17.
 */
public interface HeroRepository extends JpaRepository<Hero, String> {
    List<Hero> findByRole(Role role);
}
