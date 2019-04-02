package com.salesianostriana.dam.ejemplodaoconspring.dao;

import java.util.List;

import com.salesianostriana.dam.ejemplodaoconspring.modelo.Alumno;
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
