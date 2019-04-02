package com.salesianostriana.dam.ejemplodaoconspring.servicios;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ejemplodaoconspring.dao.ICrudAlumno;
import com.salesianostriana.dam.ejemplodaoconspring.modelo.Alumno;
/**
 * @author emescacena
 */
@Service
public class AlumnoServicio {
	
	@Autowired
	private ICrudAlumno c;
	
	public AlumnoServicio() {
		// this.c=new CrudAlumnoEnMemoria();
	}
	/**
	 * Busca a todos los alumnos
	 * @return lista de alumnos
	 */
	public List<Alumno> findAll(){
		return c.findAll();
	}
	/**
	 * Busca un alumno por su id
	 * @param id
	 * @return un solo alumno.
	 */
	public Alumno findOne(long id) {
		return c.findOne(id);
	}
	/**
	 * A�ade un alumno a la lista
	 * @param alumno
	 */
	public void insert(Alumno alumno) {
		c.insert(alumno);
	}
	/**
	 * Modifica un alumno
	 * @param alumno
	 */
	public void edit(Alumno alumno) {
		c.edit(alumno);
	}
	/**
	 * Elimina un alumno
	 * @param alumno
	 */
	public void delete (Alumno alumno) {
		c.delete(alumno);
	}
	/**
	 * Ordena la lista de alumnos por orden alfab�tico
	 * @return la lista ordenada
	 */
	public List<Alumno> getByOrdenAlfabetico(){
		// @formatter:off
		return c.findAll()
					.stream()
					.sorted(Comparator.comparing(Alumno::getApellidos))
					.collect(Collectors.toList());
 
		// @formatter:on

	}
	/**
	 * Ordena los alumnos por edad
	 * @return la lista ordenada.
	 */
	public List<Alumno> getByEdad(){
		// @formatter:off
		return c.findAll()
				.stream()
				.sorted(Comparator.comparing(Alumno::getEdad))
				.collect(Collectors.toList());
 
		// @formatter:on

	}
	/**
	 * Crea un ID empezando por 100 si la lista est� vac�a. Si no, busca el ID m�s alto 
	 * @return el siguiente ID disponble
	 */
	public long makeID() {
		if(c.findAll().isEmpty()) {
			return 100;
		}else {
			return c.findAll().stream().max(Comparator.comparing(Alumno::getId)).get().getId()+1;
		}
	}
}
