package fr.aneo;

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

    private List<Hero> getHeros() {
        return Arrays.asList(
                buildHero(1),
                buildHero(2),
                buildHero(3),
                buildHero(4),
                buildHero(5)
        );
    }

    private Hero buildHero(int id) {
        String email = RandomStringUtils.randomAlphabetic(5);
        return Hero.builder().email(email).name("name-" + id).hp(100).attackForce(1).bonus(null).build();
    }
}
