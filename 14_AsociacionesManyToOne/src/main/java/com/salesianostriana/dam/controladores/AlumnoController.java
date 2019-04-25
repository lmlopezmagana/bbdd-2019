/**
 * 
 */
package com.salesianostriana.dam.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.modelo.Alumno;
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
	
	/**
	 * Método que gestiona la petición "índice" o de listado
	 * 
	 */
	@GetMapping({"/", "/list"})
	public String listarTodos(Model model) {
		model.addAttribute("lista", alumnoServicio.findAll());
		return "index";
	}
	
	/**
	 * Método que atiende la petición de mostrar formulario
	 */
	@GetMapping("/nuevo")
	public String mostrarFormulario(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "formulario";
	}
	
	/**
	 * Método que procesa la respuesta del formulario
	 */
	@PostMapping("/nuevo/submit")
	public String procesarFormulario(@ModelAttribute("alumno") Alumno a) {
		alumnoServicio.add(a);
		return "redirect:/";
	}

}
