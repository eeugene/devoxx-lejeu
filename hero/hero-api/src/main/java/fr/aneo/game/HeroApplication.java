package fr.aneo.game;

import fr.aneo.game.service.QuizzScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * Created by raouf on 07/03/17.
 */
@Slf4j
@SpringBootApplication
public class HeroApplication {

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext run = SpringApplication.run(HeroApplication.class, args);

        log.info("Start QuizzScheduler");
        QuizzScheduler quizzScheduler = run.getBeanFactory().getBean(QuizzScheduler.class);
        quizzScheduler.start();
    }
}
