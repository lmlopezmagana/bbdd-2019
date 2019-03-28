package com.salesianostriana.primerodam.dao;

import java.util.ArrayList;
import java.util.List;

import com.salesianostriana.primerodam.modelo.Alumno;

/**
 * @author emescacena
 */
public class CrudAlumnoEnMemoria implements ICrudAlumno {

	private List<Alumno> listaAlumnos;

	public CrudAlumnoEnMemoria() {
		this.listaAlumnos = new ArrayList<Alumno>();
	}

	@Override
	public List<Alumno> findAll() {
		return listaAlumnos;
	}

	/**
	 * {@link Stream}
	 */
	@Override
	public Alumno findOne(long id) {
		return listaAlumnos.stream().filter(x -> x.getId() == id).findFirst().get();

	}

	@Override
	public void insert(Alumno alumno) {
		listaAlumnos.add(alumno);

	}

	@Override
	public void edit(Alumno alumno) {
		// @formatter:off
		listaAlumnos.set(
				listaAlumnos.indexOf(
						listaAlumnos
							.stream()
							.filter(x->alumno.getId()==x.getId())
							.findFirst()
							.get()), 
				alumno);
		 
//		listaAlumnos
//			.stream()
//			.filter(x -> x.getId() == alumno.getId())
//			.map((Alumno x) -> {
//				x.setApellidos(alumno.getApellidos());
//				x.setNombre(alumno.getNombre());
//				x.setEmail(alumno.getEmail());
//				x.setFechaNacimiento(alumno.getFechaNacimiento());
//				
//				return x;
//			});
		
		// @formatter:on

	}

	@Override
	public void delete(Alumno alumno) {
		listaAlumnos.remove(alumno);

	}

}
