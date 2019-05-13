/**
 * 
 */
package com.salesianostriana.dam.seguridad;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Usuario;
import com.salesianostriana.dam.servicios.UsuarioServicio;

/**
 * @author luismi
 *
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	UsuarioServicio usuarioServicio;
	
	public UserDetailsServiceImpl(UsuarioServicio servicio) {
		this.usuarioServicio = servicio;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioServicio.buscarPorEmail(username);
		
		UserBuilder userBuilder = null;
		
		if (usuario != null) {
			userBuilder = User.withUsername(usuario.getEmail());
			userBuilder.disabled(false);
			userBuilder.password(usuario.getPassword());
			if (usuario.isAdmin()) {
				// Este caso indica que un ADMIN también puede hacer todo lo que hace un USER
				userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));				
			} else {
				userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"));				
			}
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		
		return userBuilder.build();
		
		
	}

}
