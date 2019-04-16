
# Ejemplo 11 - Ejemplo de Crud Completo con Spring Data JPA, Spring Web MVC y Thymeleaf

Como en el proyecto anterior, partimos del [ejemplo 9](https://github.com/lmlopezmagana/bbdd-2019/tree/master/09_PrimerEjemploSpringDataJpa). En este caso, vamos a realizar todo lo necesario para crear un Crud completo desde Thymeleaf, Spring Web MVC y Spring Data JPA

## ¿Qué tenemos ya hecho?

1. Ya tenemos todas las **dependencias** necesarias incluidas en el `pom.xml`.
2. Tenemos definida nuestra entidad, `Alumno`.
3. También tenemos creado nuestro repositorio, `AlumnoRepository`.

## ¿Qué vamos a crear?

1. Un servicio, llamado `AlumnoServicio`. En este caso solo va a ser un _envoltorio_ del repositorio, es decir, que los métodos del servicio se llamarán como los del repositorio, y tan solo invocaremos a estos. Más adelante explicamos el motivo por el cual lo hacemos.
2. Un controlador, llamado `AlumnoController`, que nos permitirá gestionar las diferentes peticiones.
3. Varias plantillas de Thymeleaf, para poder mostrar el listado de alumnos, los datos de un solo alumno, el formulario de creación y el de edición.


## Paso 1: Creación de la clase **`AlumnoServicio`**

