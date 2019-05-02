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

			cursoServicio.save(nuevoCurso);

			List<Alumno> nuevos = Arrays.asList(new Alumno("Antonio", "Pérez", "antonio.perez@gmail.com"),
					new Alumno("María", "López", "maria.lopez@gmail.com"));

			
			nuevos.forEach(alumnoServicio::save);

			
			// Lo hago a la vieja usanza para dejar claro qué se está haciendo
			
			for (Alumno a : nuevos) {
				nuevoCurso.addAlumno(a);
			}

			nuevos.forEach(alumnoServicio::edit);

			// nuevoCurso.getAlumnos().forEach(System.out::println);

			System.out.println(nuevoCurso);
			System.out.println(nuevoCurso.getAlumnos());
			
			
			List<Alumno> alumnosAlmacenados = alumnoServicio.findAll();
			
			Curso cursoAlmacenado = cursoServicio.findById(1L);
			
			System.out.println("\n\nDatos almacenados");
			
			System.out.println(cursoAlmacenado);
			//System.out.println(cursoAlmacenado.getAlumnos());
			alumnoServicio.buscarPorCurso(cursoAlmacenado).forEach(System.out::println);
			
			
			//alumnosAlmacenados.forEach(System.out::println);
			
		};
	}

}
