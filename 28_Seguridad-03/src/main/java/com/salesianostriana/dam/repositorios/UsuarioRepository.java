package com.salesianostriana.dam.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Usuario;

/**
 * 
 * @author luismi
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findFirstByEmail(String email);

}
