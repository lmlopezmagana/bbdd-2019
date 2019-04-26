package com.salesianostriana.dam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner init(MiServicio servicio, ApplicationContext context) {
		return args -> {			
			servicio.enMayusculas().forEach(System.out::println);
			System.out.println("\n\n");
			for(String s : context.getBeanDefinitionNames()) {
				System.out.println(s);
			}
		};
	}

}
