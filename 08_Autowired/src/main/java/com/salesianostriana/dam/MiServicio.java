package com.salesianostriana.dam;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MiServicio {
	
	@Autowired
	@Qualifier("donBosco")
	private MiRepositorio repositorio;
	
//	public MiServicio(MiRepositorio repositorio) {
//		this.repositorio = repositorio;
//	}
		
	
	public List<String> enMayusculas() {
		return repositorio
			.findAll()
			.stream()
			.map(String::toUpperCase)
			.collect(Collectors.toList());
		
	}

}
