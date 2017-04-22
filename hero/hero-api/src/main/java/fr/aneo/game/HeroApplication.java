package fr.aneo.game;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import fr.aneo.game.repository.QuizzRepository;
import fr.aneo.game.service.HeroService;
import fr.aneo.game.service.QuizzScheduler;
import fr.aneo.game.service.QuizzProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by raouf on 07/03/17.
 */
@Slf4j
@SpringBootApplication
public class HeroApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(HeroApplication.class, args);
    }

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("MySystem");
    }

    @Bean
    @Autowired
    public ActorRef quizzScheduler(QuizzProperties quizzProperties, QuizzRepository quizzRepository, HeroService heroService) {
        return actorSystem().actorOf(QuizzScheduler.props(quizzProperties, quizzRepository, heroService));
    }

    @Bean
    public QuizzProperties quizzProps() {
        return new QuizzProperties();
    }
}
