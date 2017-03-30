package fr.aneo.game.service;

import fr.aneo.game.model.Bonus;
import fr.aneo.game.model.Hero;
import fr.aneo.game.model.HeroStats;
import fr.aneo.game.model.Role;
import fr.aneo.game.repository.HeroRepository;
import fr.aneo.game.resource.HeroResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private Bonus randomBonus;

    public Hero findHeroByEmail(String email) {
        return heroRepository.findOne(email);
    }

    public List<Hero> findAllHeroes() {
        return heroRepository.findByRole(Role.PLAYER);
    }

    public Hero createHero(Hero hero) {
        if (heroRepository.exists(hero.getEmail())) {
            throw new RuntimeException("Cet email existe déjà");
        }
        if (hero.getHeroStats() == null) {
            hero.setHeroStats(new HeroStats());
        }
        hero.setPassword(passwordEncoder.encode(hero.getPassword())); // set encoded password
        return heroRepository.save(hero);
    }

    @Transactional
    public void updateStats(HeroResource.UpdateHeroStats stats) {
        stats.getList().stream()
            .forEach( s ->
            {
                Hero hero = heroRepository.findOne(s.getEmail());
                if (hero != null) {
                    hero.setHeroStats(s.getStats());

                    // reset bonus ?
                    LocalDateTime bonusCreationTime = hero.getBonusCreationLocalDateTime();
                    if (hero.getCurrentBonus() != null && stats.getTournamentStartTime().isAfter(bonusCreationTime)) {
                        hero.setCurrentBonus(null);
                    }
                }
            }
            );
    }

    public Bonus offerRandomBonus(String heroEmail) {
        Hero hero = heroRepository.findOne(heroEmail);
        Bonus randomBonus = null;
        if (hero != null) {
            randomBonus = getRandomBonus();
            hero.setCurrentBonus(randomBonus);
        }
        return randomBonus;
    }

    public Bonus getRandomBonus() {
        Bonus[] values = Bonus.values();
        int i = RandomUtils.nextInt(values.length-1);
        return values[i];
    }

    public String getCurrentBonusDescription(String heroEmail) {
        Hero hero = heroRepository.findOne(heroEmail);
        Bonus currentBonus = hero.getCurrentBonus();
        return currentBonus != null ? currentBonus.getDescription() : "";
    }
}
