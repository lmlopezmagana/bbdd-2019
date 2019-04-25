
# Ejemplo 13 - Ejemplo de Crud Completo con Spring Data JPA, Spring Web MVC y Thymeleaf (3)

Continuamos desde el [ejemplo anterior](https://github.com/lmlopezmagana/bbdd-2019/tree/master/12_CrudCompletoUnaEntidad2).

## ¿Qué tenemos ya hecho?

Ya tenemos creada la entidad, así como el repositorio, el servicio y el controlador con un método que atiende el listado (también la plantilla que visualiza el mismo). Además, tenemos el formulario que permite insertar nuevos alumnos

## ¿Qué vamos a crear?

1. Añadiremos los elementos necesarios para crear dos botones que nos permitan acceder el formulario de edición y borrar un alumno.
2. Nos centramos ahora en modificar el formulario de manera que nos permita editar alumnos existentes.

## Algo de teoría

### Controladores que reciben parámetros

Hasta ahora hemos aprendido a crear métodos en el controlador que _atendían_ a una ruta concreta, pero esta ruta siempre era _estática_. Por ejemplo:

```java
@Controller
public class AlumnoController {
	
	// Resto del código
	
	@GetMapping("/nuevo")
	public String mostrarFormulario(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "formulario";
	}
	
	// Resto del código
}

```

Este método siempre atenderá a la ruta `/nuevo`. 

Sin embargo, en alguna ocasión nos podría interesar poder pasar un parámetro (o varios) a través de la ruta, de forma que podamos recoger ese parámetro en el controlador, y así poder usarlo.

Por ejemplo, la ruta de un formulario de edición para el alumno con Id 7 podría ser `/editar/7`; y si el alumno que queremos editar es el que tiene como Id 1234, sería `/editar/1234`. De esta forma, podríamos plantear una ruta _genérica_, `/editar/{id}`, y recoger el valor de `id` en el controlador. Para poder hacer esto, tenemos que utilizar la anotación `@PathVariable`. Este nos va a permitir _inyectar_ el valor de un segmento de la ruta en un parámetro del controlador. Veamos el código de ejemplo:

```java
@GetMapping("/editar/{id}")
public String mostrarFormularioEdicion(@PathVariable("id") long id, Model model)
```

Podemos apreciar algunos elementos:

- En la ruta aparece un `{id}`. Las llaves sirven para indicar que es un parámetro, y no una ruta fija.
- La anotación `@PathVariable` nos sirve para recoger ese valor.
- El nombre que está contenido entre llaves es el que aparece entre comillas como parámetro de la anotación `@PathVariable`.
- El valor de `{id}` se inyecta en la variable `long id`.


### Generación de enlaces

Como sabemos, para crear enlaces con Thymeleaf, normalmente utilizamos expresiones del tipo `@{...}`. Thymeleaf nos ofrece la posibilidad de utilizar un valor variable que tenga en su contexto para generar un enlace. Para ello, utilizamos expresiones como `/editar/{id}(id=${valor})`. De esta forma, sucede lo siguiente:

- Thymeleaf detecta que en la ruta del enlace hay un parámetro variable, llamado `id` (lo detecta porque va entre llaves).
- Al final de la ruta, y entre paréntesis, realiza la asignación de los valores de estos parámetros.
- El valor de un parámetro puede venir dado por otra expresión, en este caso, el contenido de la variable `${valor}`.

El código de ejemplo sería el siguiente:

```html
<a href="#" th:href="@{/editar/{id}(id=${alumno.id})}">Editar</a>
```

## Paso 1: Modificaciones en el listado para añadir _botones de operaciones_

La forma más sencilla que tenemos de manejar la edición y borrado de un alumno es añadiendo dos enlaces o botones para cada alumno en el listado de todos ellos. Cada uno de estos botones tendrá un enlace especial, que nos permitirá realizar la operación correspondiente.

```html
	<tbody>
		<tr th:each="alumno : ${lista}">
			<td th:text="${alumno.id}">1</td>
			<td th:text="${alumno.nombre}">Nombre</td>
			<td th:text="${alumno.apellidos}">Apellidos</td>
			<td th:text="${alumno.email}">Email</td>
			<td>
				<a href="#" th:href="@{/editar/{id}(id=${alumno.id})}">Editar</a> | 
				<a href="#" th:href="@{/borrar/{id}(id=${alumno.id})}">Borrar</a>
			</td>
		</tr>
	</tbody>
```

## Paso 2: Cambios en `AlumnoController` para poder manejar la operación de edición

El proceso de edición de una entidad (en nuestro caso, un `Alumno`) se realiza a través de un formulario. Por tanto, necesitamos tenemos

1. Un método en `AlumnoController` que permita mostrar el formulario
2. Otro método (anotado con `@PostMapping`) en el mismo controlador, que nos permita procesar la información.

De hecho, el formulario de **insertar** y el formulario de **editar** serán casi idénticos. La única salvedad es que el formulario de inserción recibe un alumno _vacío_, y el de edición recibirá un alumno con datos. Por tanto tenemos dos opciones:

- Crear un nuevo formulario (casi idéntico) y solamente para editar
- **Reutilizar el mismo formulario, con alguna pequeña modificación**. Tomamos esta segunda opción

### Paso 2.1 Método del controlador para mostrar el formulario

A diferencia que el formulario para crear un nuevo registro, este formulario debe buscar antes al alumno si existe; y de existir, _pasarlo_ a través del modelo al formulario. Utilizamos el Id del alumno a modificar para localizarlo y pasarlo al modelo. Si no lo encontramos (o no existe), redirigimos al listado.

```java
@Controller
public class AlumnoController {

	// Resto del código	

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEdicion(@PathVariable("id") long id, Model model) {
		
		Alumno aEditar = alumnoServicio.findById(id);
		
		if (aEditar != null) {
			model.addAttribute("alumno", aEditar);
			return "formulario";
		} else {
			// No existe ningún alumno con el Id proporcionado.
			// Redirigimos hacia el listado.
			return "redirect:/";
		}
	}
	
	// Resto del código	
	
}
```

### Paso 2.2 Modificar el formulario para que sepa cuándo queremos editar 

En el formulario necesitamos modificar, al menos, el atributo `th:action`, para que cuando queramos crear un nuevo `Alumno`, nos envíe a `/nuevo/submit`; y en caso de querer editar, nos envíe a `/editar/submit`. Lo podemos implementar utilizando el operador `?:`.

```html
		<form th:action="${alumno.id != 0} ? @{/editar/submit} : @{/nuevo/submit}" 
			  th:object="${alumno}" 
			  method="post">
```

Además, caemos en la cuenta de que cuando creamos un nuevo `Alumno`, es la base de datos quien genera el Id al almacenarlo; pero al editar, ya tenemos ese valor, y tiene que ser inmutable (es decir, no puede cambiar). Para poder _recibirlo_, y que después se asocie al _Command Object_ para enviarlo, necesitamos añadir un campo al formulario. **Normalmente, los Id que se autogeneran no se suelen visualizar en el formulario**. Para poder recibirlo sin visualizarlo añadimos un campo oculto (_hidden_), que también será condicional.

```html
<input th:if="${alumno.id != 0}" type="hidden" name="id" th:field="*{id}" />
```

De esta forma, el campo solo aparecerá cuando el `Alumno` recibido a través del modelo no tenga un Id asignado.

### Paso 2.3 Procesamiento de los datos para finalizar la edición

El contenido de este método del controlador es casi idéntico al de inserción.

```java
@Controller
public class AlumnoController {

	// Resto del código
	
	@PostMapping("/editar/submit")
	public String procesarFormularioEdicion(@ModelAttribute("alumno") Alumno a) {
		alumnoServicio.edit(a);
		return "redirect:/";
	}

}

```

## Paso 3: Implementación del borrado

Una primera implementación del borrado es sencilla de implementar. Utilizamos la misma idea de generar una ruta con el Id del `Alumno` a borrar, y un método del controlador que implemente dicho borrado.

```html
<a href="#" th:href="@{/borrar/{id}(id=${alumno.id})}">Borrar</a>
```

Para facilitar el borrado, modificamos el servicio, aprovechando un método del repositorio, que es capaz de borrar un `Alumno` por su Id.

```java
@Service
public class AlumnoServicio {

	// Resto del código	

	public void delete(Alumno a) { alumnoRepository.delete(a); }
	
	public void delete(long id) { alumnoRepository.deleteById(id); }
	
	// Resto del código	

}
```

De esa forma, el controlador sería así:

```java
@Controller
public class AlumnoController {

	// Resto del código
	
	public String borrar(@PathVariable("id") long id) {
		alumnoServicio.delete(id);
		return "redirect:/";
	}

}

```

**En un futuro, sería bueno añadir algún tipo de confirmación para el borrado, para evitar accidentes inesperados.**