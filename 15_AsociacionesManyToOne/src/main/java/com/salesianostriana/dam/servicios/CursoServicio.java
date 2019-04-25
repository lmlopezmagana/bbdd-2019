/**
 * 
 */
package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.repositorios.CursoRepository;

/**
 * @author luismi
 *
 */
@Service
public class CursoServicio {

	// Inyectamos la dependencia al nuevo estilo, sin @Autowired
	private CursoRepository cursoRepository;
	
	public CursoServicio(CursoRepository repo) {
		this.cursoRepository = repo;
	}
	
	/**
	 * Inserta un nuevo curso
	 * 
	 * @param a el Curso a insertar
	 * @return El curso ya insertado (con el Id no vacío).
	 */
	public Curso add(Curso c) { return cursoRepository.save(c); }
	
	
	/**
	 * Edita un curso, si existe; si no, lo inserta como uno nuevo.
	 * @param c
	 * @return
	 */
	public Curso edit(Curso c) { return cursoRepository.save(c); }

	/**
	 * Elimina el curso
	 * 
	 * @param c
	 */
	public void delete(Curso c) { cursoRepository.delete(c); }
	
	/**
	 * Elimina el un Curso por su Id
	 * @param id
	 */
	public void delete(long id) { cursoRepository.deleteById(id); }
	
	/**
	 * Devuelve todos los cursos
	 * @return
	 */
	public List<Curso> findAll() { return cursoRepository.findAll(); }
	
	
	/**
	 * Devuelve un curso en base a su Id
	 * @param id
	 * @return el curso encontrado o <code>null</code>
	 */
	public Curso findById(long id) {
		return cursoRepository.findById(id).orElse(null);
	}
	
	
	
}
