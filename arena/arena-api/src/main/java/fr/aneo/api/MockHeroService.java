package fr.aneo.api;

import fr.aneo.domain.*;
import fr.aneo.eventstore.HeroStatsView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
@RestController
@Slf4j
public class MockHeroService {

    final int nbOfHeros = 1000;
    @Autowired
    HeroStatsView heroStatsView;

    @GetMapping("/heros")
    public List<Hero> heros() {
        return getHeros();
    }
    @PostMapping(value = "/heros/stats")
    public void saveStats(BattleResults battleResults) {
        log.debug("Battle Results SAVED in Hero Api");
    }
    @GetMapping(value = "/heros/{id}/avatar", produces = "image/png")
    public byte[] getAvatar(String heroId) throws Exception {
        return Avatar.DEFAULT_AVATAR;
    }
    @GetMapping("/heros/{email:.*}")
    public HeroStats stats(@PathVariable("email") String id) {
        return heroStatsView.getStats().get(Hero.builder().email(id).build());
    }
    private List<Hero> heros;
    private List<Hero> getHeros() {
        if (heros == null) {
            heros = new LinkedList<>();
            for (int i = 0; i < nbOfHeros; i++) {
                heros.add(buildHero(i+1, null));
            }
        }
        int randomHero = RandomUtils.nextInt(heros.size());
        heros.get(randomHero).setBonus(randomBonus());
        return heros;
    }

    private String randomBonus() {
        Bonus[] values = Bonus.values();
        int i = RandomUtils.nextInt(values.length-1);
        return values[i].name();
    }

    private Hero buildHero(int id, Bonus bonus) {
        return Hero.builder().email("hero-" + id + "@aneo.fr").name("hero-" + id).hp(100).attackForce(10).bonus(bonus != null ? bonus.name() : null).build();
    }
}
