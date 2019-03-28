package com.salesianostriana.primerodam.dao;

import java.util.List;
import com.salesianostriana.primerodam.modelo.Alumno;
/**
 * 
 * @author emescacena
 */
public interface ICrudAlumno {
	List <Alumno> findAll();
	Alumno findOne(long id);
	void insert(Alumno alumno);
	void edit (Alumno alumno);
	void delete (Alumno alumno);
}
