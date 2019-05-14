/**
 * 
 */
package com.salesianostriana.dam.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;

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
public class Empleado extends Usuario{
	
	private LocalDate fechaAltaSS;

	/**
	 * @param nombre
	 * @param apellidos
	 * @param email
	 * @param password
	 * @param admin
	 * @param fechaAltaSS
	 */
	public Empleado(String nombre, String apellidos, String email, String password, boolean admin,
			LocalDate fechaAltaSS) {
		super(nombre, apellidos, email, password, admin);
		this.fechaAltaSS = fechaAltaSS;
	}

	

}
