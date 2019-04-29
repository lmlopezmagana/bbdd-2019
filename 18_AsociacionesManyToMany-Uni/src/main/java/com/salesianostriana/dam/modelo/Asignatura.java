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
import lombok.NoArgsConstructor;

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
	
	@ManyToMany
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
	
	
	
}
