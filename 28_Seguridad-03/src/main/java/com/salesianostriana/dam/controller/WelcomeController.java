/**
 * 
 */
package com.salesianostriana.dam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author luismi
 *
 */
@Controller
public class WelcomeController {
	
	@GetMapping("/")
	public String welcome() {
		return "index";
	}

}
