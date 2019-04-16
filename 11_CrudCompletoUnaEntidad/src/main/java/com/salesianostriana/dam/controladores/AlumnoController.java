/**
 * 
 */
package com.salesianostriana.dam.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.servicios.AlumnoServicio;

/**
 * @author luismi
 *
 */
@Controller
public class AlumnoController {
	
	private AlumnoServicio alumnoServicio;
	
	public AlumnoController(AlumnoServicio servicio) {
		this.alumnoServicio = servicio;
	}
	
	@GetMapping({"/", "/list"})
	public String listarTodos(Model model) {
		model.addAttribute("lista", alumnoServicio.findAll());
		return "index";
	}

}
