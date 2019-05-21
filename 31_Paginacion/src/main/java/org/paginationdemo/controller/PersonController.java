package org.paginationdemo.controller;

import java.util.Optional;

import org.paginationdemo.domain.Pager;
import org.paginationdemo.domain.Person;
import org.paginationdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Branislav Lazic
 * Modificaciones realizadas por Luis Miguel López 
 */
@Controller
public class PersonController {

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10, 20, 50};

    @Autowired
    private PersonService personService;

    /**
     * Manejo de las peticiones
     *
     * @param pageSize
     * @param page
     * @return view
     */
    @GetMapping("/")
    public String showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("page") Optional<Integer> page, Model model) {

    	// Evalúa el tamaño de página. Si el parámetro es "nulo", devuelve
    	// el tamaño de página inicial.
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        
        // Calcula qué página se va a mostrar. Si el parámetro es "nulo" o menor
        // que 0, se devuelve el valor inicial. De otro modo, se devuelve el valor
        // del parámetro decrementado en 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        // Obtenemos la página definida por evalPage y evalPageSize de objetos de nuestro modelo
        Page<Person> persons = personService.findAllPageable(PageRequest.of(evalPage, evalPageSize));
        // Creamos el objeto Pager (paginador) indicando los valores correspondientes.
        // Este sirve para que la plantilla sepa cuantas páginas hay en total, cuantos botones
        // debe mostrar y cuál es el número de objetos a dibujar.
        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);
        
        model.addAttribute("persons", persons);
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("pager", pager);
    	
    	return "persons";
    }
    
    

}
