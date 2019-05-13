/**
 * 
 */
package com.salesianostriana.dam.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luismi
 *
 */
@Data @NoArgsConstructor
@Entity
public class Usuario {
	
	@Id @GeneratedValue
	private long id;
	
	private String nombre;
	
	private String apellidos;
	
	@Column(unique = true)
	private String email;

	private String password;

	private boolean admin;

	/**
	 * @param nombre
	 * @param apellidos
	 * @param email
	 * @param password
	 * @param esAdmin
	 */
	public Usuario(String nombre, String apellidos, String email, String password, boolean admin) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
	
	
	
	
}