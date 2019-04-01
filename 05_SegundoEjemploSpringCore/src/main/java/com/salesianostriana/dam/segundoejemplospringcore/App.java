package com.salesianostriana.dam.segundoejemplospringcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.salesianostriana.dam.segundoejemplospringcore.saludadores.ISaludador;
import com.salesianostriana.dam.segundoejemplospringcore.traductores.ITranslator;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		ApplicationContext appContext = new AnnotationConfigApplicationContext(
				"com.salesianostriana.dam.segundoejemplospringcore");

		ISaludador comoMeDeLaGana = appContext.getBean(ISaludador.class);

		comoMeDeLaGana.setSaludo("Hola a todos los bicharracos!");

		comoMeDeLaGana.printSaludo();
		
		comoMeDeLaGana.printSaludoTraducido();

//		ITranslator traductor = appContext.getBean(ITranslator.class);
//
//		comoMeDeLaGana.setSaludo(traductor.translate("Hola a todos los bicharracos!"));
//		
//		comoMeDeLaGana.printSaludo();
//		
//		
//		for(String s: appContext.getBeanDefinitionNames())
//				System.out.println(s);
		

		((AnnotationConfigApplicationContext) appContext).close();
	}
}
