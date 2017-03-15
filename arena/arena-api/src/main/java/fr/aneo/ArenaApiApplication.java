package fr.aneo;

import fr.aneo.eventstore.*;
import fr.aneo.leaderboard.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ArenaApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ArenaApiApplication.class, args);

		EventStore eventStore = run.getBeanFactory().getBean(EventStore.class);
		List<BattleFinished> events = eventStore.load();

		// rehydratation of aggregates from events
		HeroStatsView heroStatsView= run.getBeanFactory().getBean(HeroStatsView.class);
		heroStatsView.init(events);
		UserFightTotalView userFightTotalView = run.getBeanFactory().getBean(UserFightTotalView.class);
		userFightTotalView.init(events);

		// start Arena
		Arena arena = run.getBeanFactory().getBean(Arena.class);
		arena.start();
	}

	@Bean public UserFightTotalView userFightTotalView() { return new UserFightTotalView(); }
	@Bean public HeroStatsView heroStatsView() { return new HeroStatsView(); }

	@Bean
	public EventPublisher eventPublisher() {
		EventPublisher eventPublisher = new EventPublisher();
		eventPublisher.addListener(BattleFinished.class, userFightTotalView());
		eventPublisher.addListener(BattleFinished.class, heroStatsView());
		return eventPublisher;
	}
}
