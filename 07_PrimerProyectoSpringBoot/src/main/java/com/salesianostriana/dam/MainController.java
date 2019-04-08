/**
 * 
 */
package com.salesianostriana.dam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lmlopez
 *
 */
@Controller
public class MainController {
	
	@GetMapping("/")
	public String index() {		
		return "index";
	}

}
