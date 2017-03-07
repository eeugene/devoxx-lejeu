package fr.aneo.game.service;

import fr.aneo.game.model.Hero;
import fr.aneo.game.repository.HeroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Hero findHeroByEmail(String email) {
        return heroRepository.findOne(email);
    }

    public List<Hero> findAllHeroes() {
        return heroRepository.findAll();
    }
}
