package com.salesianostriana.dam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.servicios.AlumnoServicio;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner init(AlumnoServicio servicio) {
		return args -> {
			
			servicio.save(new Alumno("Luis Miguel","López Magaña","luismi.lopez@salesianos.edu"));
			servicio.save(new Alumno("Miguel","Campos Rivera","miguel.campos@salesianos.edu"));
			
			
			servicio.findAll().forEach(System.out::println);
			
		};
	}
	
	

}
