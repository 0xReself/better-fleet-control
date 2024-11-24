package com.fleet.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FleetApplication {

	public static void main(String[] args) throws InterruptedException {
		Scenario s = new Scenario(5, 10);
		ScenarioRunner.scenario = s;

		SpringApplication.run(FleetApplication.class, args);

		while(true){
			ScenarioRunner.scenario.update();
			Thread.sleep((long)(1000 * 1));
		}
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:8080").allowedMethods("GET", "POST","PUT", "DELETE")
						.allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}

}
