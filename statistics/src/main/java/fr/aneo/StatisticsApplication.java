package fr.aneo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class StatisticsApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}
}
