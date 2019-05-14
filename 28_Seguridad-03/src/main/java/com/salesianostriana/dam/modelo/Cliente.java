/**
 * 
 */
package com.salesianostriana.dam.modelo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author luismi
 *
 */
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Cliente extends Usuario {
	
	private LocalDate fechaNacimiento;

	/**
	 * @param nombre
	 * @param apellidos
	 * @param email
	 * @param password
	 * @param admin
	 * @param fechaNacimiento
	 */
	public Cliente(String nombre, String apellidos, String email, String password, 
			LocalDate fechaNacimiento) {
		super(nombre, apellidos, email, password);
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	

}
