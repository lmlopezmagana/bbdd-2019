package com.salesianostriana.dam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Asignatura;
import com.salesianostriana.dam.modelo.Notas;
import com.salesianostriana.dam.modelo.NotasPK;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.AsignaturaServicio;
import com.salesianostriana.dam.servicios.NotasServicio;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner init(AlumnoServicio alumnoServicio, 
			AsignaturaServicio asignaturaServicio, NotasServicio notasServicio) {
		return args -> {
			
			
			Alumno antonio = new Alumno("Antonio", "Pérez", "antonio.perez@gmail.com");
			alumnoServicio.save(antonio);
			
			Asignatura basesDeDatos = new Asignatura("Bases de datos", "Luis Miguel López");
			asignaturaServicio.save(basesDeDatos);
			
			Notas notaAntonio = new Notas();
			notaAntonio.setId(new NotasPK());
			notaAntonio.setAlumno(antonio);
			notaAntonio.setAsignatura(basesDeDatos);
			notaAntonio.setPrimeraEv(10);
			notaAntonio.setSegundaEv(10);
			notaAntonio.setTerceraEv(10);
			notaAntonio.setNotaFinal(10);
			notasServicio.save(notaAntonio);
			
						
		};
	}
	
}
