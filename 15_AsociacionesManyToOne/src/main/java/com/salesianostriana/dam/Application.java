package com.salesianostriana.dam;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.CursoServicio;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner init(AlumnoServicio alumnoServicio, CursoServicio cursoServicio) {
		return args -> {
			
			Curso nuevoCurso = new Curso("1º DAM", "Miguel Campos");
			
			cursoServicio.add(nuevoCurso);
			
			List<Alumno> nuevos = Arrays.asList(
					new Alumno("Antonio", "Pérez", "antonio.perez@gmail.com", nuevoCurso),
					new Alumno("María", "López", "maria.lopez@gmail.com", nuevoCurso)
					);
			
			nuevos.forEach(alumnoServicio::add);
			
			nuevos.forEach(System.out::println);
		};
	}
	
}
