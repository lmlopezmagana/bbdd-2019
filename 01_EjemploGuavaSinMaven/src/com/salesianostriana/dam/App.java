package com.salesianostriana.dam;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class App {

	public static void main(String[] args) {

		BiMap<Integer, String> empleados = HashBiMap.create();

		empleados.put(new Integer(101), "Angel");
		empleados.put(new Integer(102), "Luismi");
		empleados.put(new Integer(103), "Miguel");

		System.out.println(empleados.inverse().get("Angel"));

	}

}
