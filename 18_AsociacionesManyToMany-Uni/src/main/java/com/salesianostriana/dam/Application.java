package com.salesianostriana.dam;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Asignatura;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.AsignaturaServicio;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner init(AlumnoServicio alumnoServicio, AsignaturaServicio asignaturaServicio) {
		return args -> {
			
			
			List<Alumno> nuevos = Arrays.asList(
					new Alumno("Antonio", "Pérez", "antonio.perez@gmail.com"),
					new Alumno("María", "López", "maria.lopez@gmail.com")
					);
			
			nuevos.forEach(alumnoServicio::save);
			
			
			Asignatura basesDeDatos = new Asignatura("Bases de datos", "Luis Miguel López");
			nuevos.forEach(basesDeDatos.getAlumnos()::add);
			asignaturaServicio.save(basesDeDatos);
					
			nuevos.forEach(System.out::println);
			
			asignaturaServicio.findAll().forEach(System.out::println);
			
		};
	}
	
}
