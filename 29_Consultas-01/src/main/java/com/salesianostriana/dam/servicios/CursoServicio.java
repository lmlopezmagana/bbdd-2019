/**
 * 
 */
package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.repositorios.AlumnoRepository;
import com.salesianostriana.dam.repositorios.CursoRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

/**
 * @author luismi
 *
 */
@Service
public class CursoServicio extends BaseService<Curso, Long, CursoRepository>{
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * Envoltorio par el método findByNombreContains
	 */
	public List<Curso> buscarPorNombre(String nombre) {
		return repositorio.findByNombreContains(nombre);
	}
	
	/**
	 * Devuelve el número de alumnos en un curso
	 */
	public long alumnoEnUnCurso(Curso curso) {
		return alumnoRepository.countByCurso(curso);
	}

}
