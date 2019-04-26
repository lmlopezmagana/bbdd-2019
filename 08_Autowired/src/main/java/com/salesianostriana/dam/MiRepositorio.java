package com.salesianostriana.dam;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("donBosco")
public class MiRepositorio {

	List<String> findAll() {
		return Arrays.asList("José Manuel", "Daniel", "Juanma", "Alejandro");
	}
	
}
