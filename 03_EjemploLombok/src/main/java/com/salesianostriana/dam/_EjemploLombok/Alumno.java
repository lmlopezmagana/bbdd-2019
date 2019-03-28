/**
 * 
 */
package com.salesianostriana.dam._EjemploLombok;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author lmlopez
 *
 */

@Data @AllArgsConstructor @RequiredArgsConstructor
public class Alumno {
	
	@NonNull
	private String nombre;
	@NonNull
	private String apellidos;
	private String email;
	private LocalDate fechaNacimiento;
	
	public int getEdad() {
		return Math.toIntExact(ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.of(LocalDate.now().getYear(), 12, 31)));
	}

}
