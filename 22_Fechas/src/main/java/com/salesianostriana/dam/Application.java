package com.salesianostriana.dam;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
			
			Alumno alumno = new Alumno();
			alumno.setNombre("Pepe");
			alumno.setApellidos("Pérez Pérez");
			alumno.setEmail("pepe.perez@perezyperez.com");
			alumno.setFechaNacimiento(LocalDate.of(1990, 1, 1));
			alumno.setFechaUltimoAcceso(LocalDateTime.now());
			
			servicio.save(alumno);
			
			
			servicio.findAll().forEach(System.out::println);
			
		};
	}
	
	

}
