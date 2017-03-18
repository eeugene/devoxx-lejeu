package fr.aneo;

import fr.aneo.eventstore.*;
import fr.aneo.leaderboard.LeaderboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Slf4j
@SpringBootApplication
public class ArenaApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ArenaApiApplication.class, args);

		log.info("Loading events");
		EventStore eventStore = run.getBeanFactory().getBean(EventStore.class);
		List<BattleFinished> events = eventStore.load();

		log.info("Rehydratation of aggregates from events");
		HeroStatsView heroStatsView= run.getBeanFactory().getBean(HeroStatsView.class);
		heroStatsView.init(events);

		log.info("Start ArenaScheduler");
		ArenaScheduler arenaScheduler = run.getBeanFactory().getBean(ArenaScheduler.class);
		arenaScheduler.start();
	}

	@Bean public HeroStatsView heroStatsView() { return new HeroStatsView(); }

	@Bean
	public EventPublisher eventPublisher() {
		EventPublisher eventPublisher = new EventPublisher();
		eventPublisher.addListener(BattleFinished.class, heroStatsView());
		return eventPublisher;
	}
}
