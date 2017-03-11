package fr.aneo.game.repository;

import fr.aneo.game.model.Hero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by raouf on 04/03/17.
 */
@Transactional
public interface HeroRepository extends JpaRepository<Hero, String> {
    Page<Hero> findAll(Pageable pageable);

    @Query("select u from Hero u left join fetch u.roles r where u.email=:email")
    public Optional<Hero> findByEmail(@Param("email") String email);
}
