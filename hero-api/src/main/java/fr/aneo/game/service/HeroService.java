package fr.aneo.game.service;

import fr.aneo.game.model.Hero;
import fr.aneo.game.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by raouf on 04/03/17.
 */
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    public List<Hero> findAllHeroes() {
        return heroRepository.findAll();
    }
}
