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
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase que modela los datos más básicos de un alumno
 * 
 * @author lmlopez
 *
 */
@Data @NoArgsConstructor
@Entity
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String apellidos;
	private String email;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy="alumnos")
	//@ManyToMany(mappedBy="alumnos", fetch = FetchType.EAGER)
	private List<Asignatura> asignaturas = new ArrayList<>();
	
	
	/**
	 * @param nombre
	 * @param apellidos
	 * @param email
	 */
	public Alumno(String nombre, String apellidos, String email) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
	}


	
	
	
	

}
