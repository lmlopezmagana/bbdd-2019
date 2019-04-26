package com.salesianostriana.dam._EjemploGuavaConMaven;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Esta clase sirve para....
 * @author Luismi
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	BiMap<Integer, String> empleados = HashBiMap.create();

		empleados.put(new Integer(101), "Angel");
		empleados.put(new Integer(102), "Luismi");
		empleados.put(new Integer(103), "Miguel");

		System.out.println(empleados.inverse().get("Angel"));
    	
    }

    /**
     * Este es simplemente un método de ejemplo
     * @param argument1 En realidad no vale para nada, pero ahí está
     * @param argumento2 Este tampoco vale, pero siempre van en pareja
     * @return Devuelve una cadena vacía, qué se le va a hacer
     * @throws NullPointerException En algún caso, puede devolver dicha excepción
     */
    public String unMetodo(String argument1, int argumento2) throws NullPointerException {
    	return "";
    }
    
}
