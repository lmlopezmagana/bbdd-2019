/**
 * 
 */
package com.salesianostriana.dam.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que modela los datos más básicos de un alumno
 * 
 * @author lmlopez
 *
 */
@Data @NoArgsConstructor
@Entity
@Table(name="STUDENT")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Basic(optional=false)
	@Column(name="FIRST_NAME")
	private String nombre;
	@Column(name="LAST_NAME", nullable=false)
	private String apellidos;
	@Column(unique=true)
	private String email;
	
	
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
