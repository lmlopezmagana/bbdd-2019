
# Ejemplo 12 - Ejemplo de Crud Completo con Spring Data JPA, Spring Web MVC y Thymeleaf (2)

Continuamos desde el [ejemplo anterior](https://github.com/lmlopezmagana/bbdd-2019/tree/master/11_CrudCompletoUnaEntidad).

## ¿Qué tenemos ya hecho?

Ya tenemos creada la entidad, así como el repositorio, el servicio y el controlador con un método que atiende el listado (también la plantilla que visualiza el mismo).

## ¿Qué vamos a crear?

1. Nos centramos ahora en la creación del formulario para añadir nuevos alumnos

## Algo de teoría sobre formularios

Para crear un formulario con Spring MVC y Thymeleaf, tenemos que seguir 3 pasos:

1. Tener un método en el controlador, anotado con `@GetMapping`, que _prepara_ el formulario.
2. Una plantilla, que visualiza el formulario.
3. Un nuevo método en el controlador, anotado con `@PostMapping`, que procesa la respuesta del formuarlio.
4. Como paso adicional, normalmente en el método del paso 3, **redirigimos** al usuario a una nueva página (por ejemplo, el listado de alumnos):

## Paso 1: Preparación del formulario

Spring + Thymeleaf nos ofrecen el soporte completo para el manejo de formularios. Thymeleaf nos permite "dibujar" el formulario, y la integración con Spring nos permite manejar los datos a través de un objeto especial, llamado **Command Object** (en ocasiones, también llamado _Form Bean_). Este objeto realiza el _respaldo_ de los datos que escribamos en el formulario.

> Es usual que en los primeros ejemplos, podamos utilizar como _Command Object_ nuestras propias clases entidad, aunque en sistemas más complejos tendríamos que crear clases específicas.

En nuestro controlador, añadimos un nuevo método con el siguiente código:

```java
@Controller
public class AlumnoController {
	
	// Resto de código	
	
	/**
	 * Método que atiende la petición de mostrar formulario
	 */
	@GetMapping("/nuevo")
	public String mostrarFormulario(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "formulario";
	}
	
	// Resto de código
	
}

``` 

Como podemos comprobar, cuando invocamos este método del controlador, se añade al modelo el _Command Object_ (en nuestro caso, una instacia vacía de `Alumno`), y redirigimos al usuario a la plantilla del formulario.

## Paso 2: Plantilla del formulario

Creamos una nueva plantilla de Thymeleaf en la misma ruta que la anterior (`/src/main/resources/templates`). En este caso, la llamamos `formulario.html`. 

Las etiquetas o propiedades específicas de formulario son las siguientes:

* `<form>`: es una etiqueta específica de HTML, pero le añadimos algunos elementos de Thymeleaf.
	* `th:action`: tiene el mismo efecto que `action`, pero aplicado a Thymeleaf. Espera el enlace del método del controlador (anotado con `PostMapping`) que procesará el formulario.
	* `th:object`: nombre del _Command Object_ que hemos añadido al modelo en el paso 1, y que almacenará los datos que escribamos en el formulario.
* `<input>`: son campos de formulario de HTML, a los que le añadimos algunas propiedades de Thymeleaf:
	* `th:field`: indica el nombre del atributo del _Command Object_ con el cual enlazamos este campo del formulario.

El código del formulario sería el siguiente:

```html
<form th:action="@{/nuevo/submit}" th:object="${alumno}" method="post">
	<input type="text" name="nombre" th:field="*{nombre}" placeholder="Nombre" /> 
	<input type="text" name="apellidos" th:field="*{apellidos}" placeholder="Apellidos" /> 
	<input type="email" name="email" th:field="*{email}" placeholder="Email" />
	<input type="submit" name="enviar" value="Enviar" />
</form>
```


Una vez que rellenamos con datos el formulario, y pulsamos el botón de enviar.


## Paso 3: Procesamiento de resultados

El formulario del paso 2 envía sus resultados a un método del controlador, anotado con `@PostMapping`. En este método, y usando el servicio correspondiente, realizamos la **lógica de negocio** necesaria. En nuestro caso, almacenar el nuevo alumno en la base de datos.

Por último, el método del controlador utiliza el prefijo `redirect` en la ruta de la plantilla, para redirigir al usuario a un nuevo controlador (el listado de todos los alumnos).

El código en el controlador sería:

```java
@Controller
public class AlumnoController {
	
	// Resto del código
	
	/**
	 * Método que procesa la respuesta del formulario
	 */
	@PostMapping("/nuevo/submit")
	public String procesarFormulario(@ModelAttribute("alumno") Alumno a) {
		alumnoServicio.add(a);
		return "redirect:/";
	}

}

```  
