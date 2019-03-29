/**
 * 
 */
package com.salesianostriana.dam.primerejemplospringcore;

/**
 * Mi primer bean de 2019 con 1º DAM. ¡Cuan feliz me siento!
 * @author lmlopez
 *
 */
public class MiPrimerBean implements ISaludador {

	private String saludo;
	
	@Override
	public void setSaludo(String s) {
		this.saludo = s;
	}
	
	@Override
	public void printSaludo() {
		System.out.println(saludo);
	}
	
	
}
