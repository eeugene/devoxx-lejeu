package fr.aneo;

import fr.aneo.leaderboard.LeaderboardService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ArenaApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ArenaApiApplication.class, args);
		LeaderboardService leaderboardService = run.getBeanFactory().getBean(LeaderboardService.class);
		leaderboardService.init();
		Arena arena = run.getBeanFactory().getBean(Arena.class);
		arena.start();
	}
}
