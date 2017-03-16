package fr.aneo.game.repository;

import fr.aneo.game.model.Hero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by raouf on 04/03/17.
 */
@Transactional
public interface HeroRepository extends JpaRepository<Hero, String> {
    Page<Hero> findAll(Pageable pageable);
}
