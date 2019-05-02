/**
 * 
 */
package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.repositorios.AlumnoRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

/**
 * @author luismi
 *
 */
@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository>{

	
	public List<Alumno> buscarPorCurso(Curso curso) {
		return this.repositorio.findByCurso(curso);
	}
	
}
