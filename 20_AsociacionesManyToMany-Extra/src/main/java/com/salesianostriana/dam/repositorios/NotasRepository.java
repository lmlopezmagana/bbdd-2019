package com.salesianostriana.dam.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Notas;
import com.salesianostriana.dam.modelo.NotasPK;

/**
 * 
 * @author luismi
 *
 */
public interface NotasRepository extends JpaRepository<Notas, NotasPK>{

}
