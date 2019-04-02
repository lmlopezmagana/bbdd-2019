package com.salesianostriana.dam.ejemplodaoconspring.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
/**
 * Esta clase sirve para crear un objeto alumno
 * @author emescacena
 * 
 * 
 */
public class Alumno {
	private long id;
	private String nombre, apellidos, email;
	private LocalDate fechaNacimiento;
	
	/**
	 * Constructor vac�o. 
	 */
	public Alumno() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param nombre
	 * @param apellidos
	 * @param email
	 * @param fechaNacimiento
	 */
	public Alumno(long id, String nombre, String apellidos, String email, LocalDate fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, email, fechaNacimiento, id, nombre);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(email, other.email)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && id == other.id
				&& Objects.equals(nombre, other.nombre);
	}

	
	
	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", fechaNacimiento=" + fechaNacimiento + "]";
	}
	
	/**
	 * Sirve para calcular la edad del alumno en la fecha 31/12 del a�o actual.
	 * @return la edad del alumno.
	 */
	public int getEdad() {
		// Una versi�n
		//return Period.between(fechaNacimiento, LocalDate.of(LocalDate.now().getYear(), 12, 31)).getYears();
		// Otra versi�n
		return Math.toIntExact(ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.of(LocalDate.now().getYear(), 12, 31)));
	}
	
}
