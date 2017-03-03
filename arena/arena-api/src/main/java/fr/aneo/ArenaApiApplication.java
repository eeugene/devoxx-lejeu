package fr.aneo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ArenaApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ArenaApiApplication.class, args);
		Arena arena = run.getBeanFactory().getBean(Arena.class);
		arena.start();
	}
}
