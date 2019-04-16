
# Ejemplo 11 - Ejemplo de Crud Completo con Spring Data JPA, Spring Web MVC y Thymeleaf

Como en el proyecto anterior, partimos del [ejemplo 9](https://github.com/lmlopezmagana/bbdd-2019/tree/master/09_PrimerEjemploSpringDataJpa), aunque eliminaremos algo de código. En este caso, vamos a realizar todo lo necesario para crear un Crud completo desde Thymeleaf, Spring Web MVC y Spring Data JPA

## ¿Qué tenemos ya hecho?

1. Ya tenemos todas las **dependencias** necesarias incluidas en el `pom.xml`.
2. Tenemos definida nuestra entidad, `Alumno`.
3. También tenemos creado nuestro repositorio, `AlumnoRepository`.

## ¿Qué vamos a crear?

1. Un servicio, llamado `AlumnoServicio`. En este caso solo va a ser un _envoltorio_ del repositorio, es decir, que los métodos del servicio se llamarán como los del repositorio, y tan solo invocaremos a estos. Más adelante explicamos el motivo por el cual lo hacemos.
2. Un controlador, llamado `AlumnoController`, que nos permitirá gestionar las diferentes peticiones.
3. Varias plantillas de Thymeleaf, para poder mostrar el listado de alumnos, los datos de un solo alumno, el formulario de creación y el de edición.


## Paso 1: Creación de la clase **`AlumnoServicio`**

Lo creamos en un nuevo paquete, llamado `servicios`. Creamos dentro una nueva clase llamada `AlumnoServicio`, que encapsula un servicio CRUD de alumnos. Se trata de un _envoltorio_
 del repositorio de alumnos, ya que tendrá los mismos métodos, y ninguno más. En tutoriales posteriores ofreceremos la posibilidad de crear un servicio abstracto, del cual podrán extender los demás, para preocuparse solo de la lógica propia del servicio.

```java
@Service
public class AlumnoServicio {
	
	// Inyectamos la dependencia al nuevo estilo, sin @Autowired
	private AlumnoRepository alumnoRepository;
	
	public AlumnoServicio(AlumnoRepository repo) {
		this.alumnoRepository = repo;
	}
	
	public Alumno add(Alumno a) { return alumnoRepository.save(a); }
	
	public Alumno edit(Alumno a) { return alumnoRepository.save(a); }

	public void delete(Alumno a) { alumnoRepository.delete(a); }
	
	public List<Alumno> findAll() { return alumnoRepository.findAll(); }
	
	public Alumno findById(long id) {
		return alumnoRepository.findById(id).orElse(null);
	}

}

```

## Paso 2: Creación del esqueleto del controlador, **`AlumnoController`**

Creamos un nuevo paquete llamado `controladores`, y dentro la nueva clase `AlumnoController`. El esqueleto de la clase incluirá el método de entrada, que mostrará un listado de todos los alumnos:

```java
@Controller
public class AlumnoController {
	
	private AlumnoServicio alumnoServicio;
	
	public AlumnoController(AlumnoServicio servicio) {
		this.alumnoServicio = servicio;
	}
	
	@GetMapping({"/", "/list"})
	public String listarTodos(Model model) {
		model.addAttribute("lista", alumnoServicio.findAll());
		return "index";
	}

}
```

Para finalizar este paso, creamos la plantilla, en la ruta `/src/main/resources/templates/`, llamada `index.html`. Lo describiremos por pasos:

- Para indicar que es una plantilla de Thymeleaf, tenemos que añadir el espacio de nombres en la etiqueta `<html>`:

```html
<html xmlns:th="http://www.thymeleaf.org">
```

- En este ejemplo, no nos centramos en el diseño, así que no usaremos ni plantillas, ni siquiera CSS. El menú lo definimos como una sencilla capa con enlaces (las opciones editar y borrar aparecerán más adelante).

```html
<div id="menu">
	<a th:href="@{/list}">Inicio</a> | <a href="@{/nuevo}">Nuevo alumno</a>
</div>
```

- El listado de alumnos lo _pintaremos_ mediante una tabla. En la misma, utilizamos los elementos de thymeleaf `th:each` para indicar que haga un bucle sobre la colección `${lista}`:

```html
	<tbody>
		<tr th:each="alumno : ${lista}">
			<td th:text="${alumno.id}">1</td>
			<td th:text="${alumno.nombre}">Nombre</td>
			<td th:text="${alumno.apellidos}">Apellidos</td>
			<td th:text="${alumno.email}">Email</td>
		</tr>
	</tbody>
```

El código completo de la plantilla sería:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Ejemplo de un CRUD completo</title>
</head>
<body>
<div id="menu">
	<a th:href="@{/list}">Inicio</a> | <a href="@{/nuevo}">Nuevo alumno</a>
</div>
<br /><br />
<table border=1>
	<thead>
		<tr>
			<th>Id</th>
			<th>Nombre</th>
			<th>Apellidos</th>
			<th>email</th>
		</tr>
	</thead>
	<tbody>
		<tr th:each="alumno : ${lista}">
			<td th:text="${alumno.id}">1</td>
			<td th:text="${alumno.nombre}">Nombre</td>
			<td th:text="${alumno.apellidos}">Apellidos</td>
			<td th:text="${alumno.email}">Email</td>
		</tr>
	</tbody>
</table>
</body>
</html>
```

## Paso adicional: fichero `data.sql`

Para que nuestra base de datos tenga datos de ejemplo, podemos hacerlo de dos formas:

- Crear un bean que utilice repositorios o servicios para insertarlos (programáticamente).
- *Hacerlo a través de sql*. Tomamos esa segunda opción.

Para hacerlo, seguimos los siguientes pasos:

1. Necesitamos crear un fichero llamado `data.sql` en algún lugar del _classpath_. La carpeta `/src/main/resources/` es un buen lugar.
2. Una verz creado, podemos usar la sintaxis de sql `INSERT INTO ... VALUES `.
3. Para generar los valores de la columna que es clave primaria, utilizamos la secuencia de JPA ha generado.

El contenido del fichero podría ser algo así:

```sql
insert into alumno values (NEXTVAL('hibernate_sequence'),'Luis Miguel','López','luismi.lopez@triana.com');
insert into alumno values (NEXTVAL('hibernate_sequence'),'Ángel','Naranjo','angel.narajo@triana.com');
``` 

**En los próximos ejemplos, añadiremos los formularios de creación y edición, así como la opción de visualizar solo un registro o de borrarlos**.