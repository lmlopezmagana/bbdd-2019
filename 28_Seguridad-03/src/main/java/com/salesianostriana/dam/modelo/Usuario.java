/**
 * 
 */
package com.salesianostriana.dam.modelo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luismi
 *
 */
@Data @NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements UserDetails {
	
	@Id @GeneratedValue
	private long id;
	
	private String nombre;
	
	private String apellidos;
	
	@Column(unique = true)
	private String email;

	private String password;
	
	private boolean cuentaCaducada;
	private boolean cuentaBloqueada;
	private boolean credencialesCaducadas;

	/**
	 * @param nombre
	 * @param apellidos
	 * @param email
	 * @param password
	 */
	public Usuario(String nombre, String apellidos, String email, String password) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.cuentaCaducada = false;
		this.cuentaBloqueada = false;
		this.credencialesCaducadas = false;
	}


	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();


	@Override
	public String getUsername() {
		return email;
	}


	@Override
	public boolean isAccountNonExpired() {
		return !cuentaCaducada;
	}


	@Override
	public boolean isAccountNonLocked() {
		return !cuentaBloqueada;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return !credencialesCaducadas;
	}


	@Override
	public boolean isEnabled() {
		return !cuentaBloqueada;
	}
	
	
	
	
}