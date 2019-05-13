package com.salesianostriana.dam.servicios;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Usuario;
import com.salesianostriana.dam.repositorios.UsuarioRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

/**
 * 
 */

/**
 * @author luismi
 *
 */
@Service
public class UsuarioServicio extends BaseService<Usuario, Long, UsuarioRepository>{
	
	public Usuario buscarPorEmail(String email) {
		return repositorio.findFirstByEmail(email);
	}

}
