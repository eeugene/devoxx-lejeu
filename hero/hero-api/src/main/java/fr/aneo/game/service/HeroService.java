package fr.aneo.game.service;

import fr.aneo.game.model.*;
import fr.aneo.game.repository.HeroRepository;
import fr.aneo.game.repository.QuizzHeroAnswerRepository;
import fr.aneo.game.resource.HeroResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by raouf on 04/03/17.
 */
@Service
@Slf4j
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private QuizzHeroAnswerRepository quizzHeroAnswerRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        hero.setRegisterTime(new Date());
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
                }
            });
    }

    @Transactional
    public Bonus offerRandomBonus(String heroEmail) {
        Hero hero = heroRepository.findOne(heroEmail);
        Bonus randomBonus = null;
        if (hero != null) {
            randomBonus = getRandomBonus();
            hero.setCurrentBonus(randomBonus);
        }
        return randomBonus;
    }
    
    private Bonus getRandomBonus() {
        Bonus[] values = Bonus.values();
        int i = RandomUtils.nextInt(values.length);
        return values[i];
    }

    public String getCurrentBonusDescription(String heroEmail) {
        Hero hero = heroRepository.findOne(heroEmail);
        Bonus currentBonus = hero.getCurrentBonus();
        return currentBonus != null ? currentBonus.getDescription() : "";
    }

    @Transactional
    public void resetBonusForAll() {
        heroRepository.findAll().stream()
                .forEach(h -> h.setCurrentBonus(null));
    }

    public HeroQuizzStats getHeroQuizzStats(String email) {
        Hero hero = findHeroByEmail(email);
        if(hero == null) {
            return null;
        }
        List<QuizzHeroAnswer> quizzHeroAnswers = quizzHeroAnswerRepository.findByIdHeroEmail(email);
        long totalGoodAnswers = 0;
        List<String> bonusesWined = null;
        if (quizzHeroAnswers != null) {
            totalGoodAnswers = quizzHeroAnswers.stream()
                    .map(heroAnswer -> {
                        Quizz q = heroAnswer.getQuizz();
                        return q.isCorrectAnswer(heroAnswer.getQuizzAnswerId());
                    }).filter(b -> b)
                    .count();
            bonusesWined = quizzHeroAnswers.stream()
                    .map(a -> a.getBonusWined())
                    .filter(Objects::nonNull)
                    .map(bonus -> bonus.getDescription())
                    .collect(Collectors.toList());
        }
        return HeroQuizzStats.builder()
                .totalGoodAnswered(totalGoodAnswers)
                .totalQuizzAnswered(quizzHeroAnswers.size())
                .bonusesWined(bonusesWined)
                .build();
    }

    @Transactional
    public void incrementHp(String heroEmail) {
        Hero hero = heroRepository.findOne(heroEmail);
        if (hero != null) {
            hero.incrementHp(Hero.DEFAULT_HP_INCREMENT);
        }
    }
}
