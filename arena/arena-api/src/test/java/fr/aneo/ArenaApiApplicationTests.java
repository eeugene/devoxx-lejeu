package fr.aneo;

import fr.aneo.api.hero.HeroService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArenaApiApplicationTests {

	@Mock
	HeroService heroService;

	@Test
	public void contextLoads() {
	}

}
