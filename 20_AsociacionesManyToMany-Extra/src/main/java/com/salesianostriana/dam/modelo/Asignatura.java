/**
 * 
 */
package com.salesianostriana.dam.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase que modela los datos de una Asignatura
 * 
 * 
 * @author luismi
 *
 */
@Data @NoArgsConstructor
@Entity
public class Asignatura {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String profesor;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany
	//@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		joinColumns = @JoinColumn(name="asignatura_id"),
		inverseJoinColumns = @JoinColumn(name="alumno_id")
	)
	private List<Alumno> alumnos = new ArrayList<>();
	
	/**
	 * @param nombre
	 * @param profesor
	 */
	public Asignatura(String nombre, String profesor) {
		this.nombre = nombre;
		this.profesor = profesor;
	}
	
	
	/**
	 * Método auxiliar para manejar la asociación bidireccional con Alumno
	 * @param a
	 */
	public void addAlumno(Alumno a) {
		alumnos.add(a);
		a.getAsignaturas().add(this);
	}
	
	/**
	 * Método auxiliar para manejar la asociación bidireccional con Alumno
	 * @param a
	 */
	public void deleteAlumno(Alumno a) {
		alumnos.remove(a);
		a.getAsignaturas().remove(this);
	}
	
}
