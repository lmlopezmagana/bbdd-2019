package com.salesianostriana.dam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author luismi
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/")
	public String index() {
		return "admin/index";
	}

}
