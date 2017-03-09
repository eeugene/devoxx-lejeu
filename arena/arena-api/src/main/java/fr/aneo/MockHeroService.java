package fr.aneo;

import fr.aneo.domain.BattleResults;
import fr.aneo.domain.Bonus;
import fr.aneo.domain.Hero;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
@RestController
public class MockHeroService {

    @RequestMapping("/heros")
    public List<Hero> heros() {
        return getHeros();
    }
    @RequestMapping(value = "/heros/results", method = RequestMethod.POST)
    public void save(BattleResults battleResults) {
        System.out.println("SAVED");
    }

    private List<Hero> heros;
    private List<Hero> getHeros() {
        int randomHero = RandomUtils.nextInt(5);
        if (heros == null) {
            heros = Arrays.asList(
                    buildHero(1, null),
                    buildHero(2, null),
                    buildHero(3, null),
                    buildHero(4, null),
                    buildHero(5, null)
            );
        }
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
