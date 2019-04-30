/**
 * 
 */
package com.salesianostriana.dam.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luismi
 *
 */
@Data @NoArgsConstructor
@Entity
public class Notas {
	
	@EmbeddedId
	private NotasPK id;
	
	@ManyToOne
	@MapsId("alumno_id")
	@JoinColumn(name="alumno_id")
	private Alumno alumno;	
	
	
	@ManyToOne
	@MapsId("asignatura_id")
	@JoinColumn(name="asignatura_id")
	private Asignatura asignatura;
	
	private int primeraEv;
	private int segundaEv;
	private int terceraEv;
	private int notaFinal;
	
	
	/**
	 * @param alumno
	 * @param asignatura
	 * @param primeraEv
	 * @param segundaEv
	 * @param terceraEv
	 * @param notaFinal
	 */
	public Notas(Alumno alumno, Asignatura asignatura, int primeraEv, int segundaEv, int terceraEv, int notaFinal) {
		this.alumno = alumno;
		this.asignatura = asignatura;
		this.primeraEv = primeraEv;
		this.segundaEv = segundaEv;
		this.terceraEv = terceraEv;
		this.notaFinal = notaFinal;
	}
	
	
	

}
