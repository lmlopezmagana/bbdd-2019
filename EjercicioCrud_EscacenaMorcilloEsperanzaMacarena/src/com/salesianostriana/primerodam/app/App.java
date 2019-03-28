package com.salesianostriana.primerodam.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.salesianostriana.primerodam.modelo.Alumno;
import com.salesianostriana.primerodam.servicios.AlumnoServicio;

/**
 * @author emescacena
 * 
 */

public class App {

	public static void main(String[] args) {
		AlumnoServicio a=new AlumnoServicio();
		String menu,nombre, apellidos, email,fecha;
		long id;
		boolean salir=false;
		LocalDate fechaNac;
		Alumno aux;
		Scanner sc=new Scanner(System.in);
		//PRUEBA
		a.insert(new Alumno(a.makeID(),"Antonio","Durán","aduran@gmail.com",LocalDate.of(1996, 10, 15)));
		a.insert(new Alumno(a.makeID(), "Jesús", "Ceacero","jceacero@gmail.com", LocalDate.of(1991, 8, 15)));
		System.out.println("AULA VIRTUAL");
		do {
			System.out.println("[1] Mostrar Alumnos.\n"
					+ "[2] Añadir Alumno.\n"
					+ "[3] Editar Alumno.\n"
					+ "[4] Eliminar Alumno.\n"
					+ "[5] Mostrar por edad.\n"
					);
			menu=sc.nextLine();
			switch(menu) {
				case "1":
					a.findAll().stream().forEach(System.out::println);
					break;
				case "2":
					System.out.println("AÑADIR NUEVO ALUMNO:"
							+ "\nNombre:");
					nombre=sc.nextLine();
					
					System.out.println("\nApellidos:");
					apellidos=sc.nextLine();
					
					System.out.println("\nFecha de nacimiento (dd/MM/YYYY");
					fechaNac=LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					
					System.out.println("\nEmail:");
					email=sc.nextLine();
					aux=new Alumno(a.makeID(),nombre,apellidos,email, fechaNac);
					a.insert(aux);
					break;
				case "3":
					System.out.println("EDITAR UN ALUMNO:"
							+ "\nIntroduzca el ID del alumno que desea editar:");
					id=Long.parseLong(sc.nextLine());
					System.out.println(a.findOne(id));
					System.out.println("Reescriba la información del alumno:"
							+ "\nNombre:");
					nombre=sc.nextLine();
					
					System.out.println("\nApellidos:");
					apellidos=sc.nextLine();
					
					System.out.println("\nFecha de nacimiento (dd/MM/YYYY");
					fechaNac=LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					
					System.out.println("\nEmail:");
					email=sc.nextLine();
					aux=new Alumno(a.findOne(id).getId(),nombre,apellidos,email, fechaNac);
					a.edit(aux);
					break;
				case "4":
					System.out.println("BORRAR UN ALUMNO:"
							+ "\nIntroduzca el ID del alumno que desea eliminar:");
					a.delete(a.findOne(sc.nextLong()));
					break;
				case "5":
					a.getByEdad().stream().forEach(System.out::println);
					break;
			}
		}while(!salir);

	}

}
