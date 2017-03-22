package fr.aneo.game.service;

import fr.aneo.game.model.Hero;
import fr.aneo.game.repository.HeroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by raouf on 04/03/17.
 */
@Service
@Slf4j
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Hero findHeroByEmail(String email) {
        return heroRepository.findOne(email);
    }

    public List<Hero> findAllHeroes() {
        return heroRepository.findAll();
    }

    public Hero createHero(Hero hero) {
        if (heroRepository.exists(hero.getEmail())) {
            throw new RuntimeException("Cet email existe déjà");
        }
        hero.setPassword(passwordEncoder.encode(hero.getPassword())); // set encoded password
        return heroRepository.save(hero);
    }
}
