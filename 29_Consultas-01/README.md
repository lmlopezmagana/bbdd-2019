
# Ejemplo 29 - Consultas con Spring Data JPA: _Query methods_

## Modelo de datos

Partimos de un modelo de datos como este

![Diagrama](./uml.jpg)

## Consulta a través de métodos 

Una de los grandes aportes que nos proporciona Spring Data JPA frente al uso _simple_ de JPA es la posibilidad de crear consultas de una forma sencilla y rápida desde los propios repositorios.

La generación de consultas a partir del nombre del método es una estrategia de generación de consultas donde la consulta invocada se deriva del nombre del método de consulta.

Podemos crear métodos de consulta que utilizan esta estrategia siguiendo estas reglas:

- El nombre de nuestro método de consulta debe comenzar con uno de los siguientes prefijos: `find…By`, `read…By`, `query…By`, `count…By`, and `get…By`.
- Si queremos limitar el número de resultados devueltos, podemos agregar la palabra clave `First` o `Top` antes de `By`. Si queremos obtener más de un resultado, debemos agregar un valor numérico. Por ejemplo, `findTopBy` , `findTop1By`, `findFirstBy` y `findFirst1By` devuelven la primera entidad que coincida con los criterios de búsqueda especificados.
- Si queremos seleccionar resultados únicos, debemos agregar la palabra clave `Distinct` antes de `By`. Por ejemplo, `findTitleDistinctBy` o `findDistinctTitleBy` significa que queremos seleccionar todos los títulos únicos que se encuentran en la base de datos.
- Debemos agregar los criterios de búsqueda de nuestro método de consulta después de `By` . Podemos especificar los criterios de búsqueda mediante la combinación de [expresiones de propiedades](http://docs.spring.io/spring-data/jpa/docs/2.1.8.RELEASE/reference/html/#repositories.query-methods.query-property-expressions) con las [palabras clave](http://docs.spring.io/spring-data/jpa/docs/2.1.8.RELEASE/reference/html/#repository-query-keywords) admitidas.
- Si nuestro método de consulta especifica x condiciones de búsqueda, debemos agregar X  parámetros al método . En otras palabras, el número de parámetros del método debe ser igual al número de condiciones de búsqueda. Además, los parámetros del método deben darse en el mismo orden que las condiciones de búsqueda.
- El tipo de retorno podrá ser:
	- Para consultas que devuelven un resultado:
		- Tipo básico . Nuestro método de consulta devolverá el tipo básico encontrado o nulo .
		- Entidad . Nuestro método de consulta devolverá un objeto entidad o nulo .
		- Guava / Java 8 `Optional<T>` . Nuestro método de consulta devolverá un opcional que contiene el objeto encontrado o un opcional vacío.
	- Para consultas que devuelven más de un resultado:
		- `List<T>` : Nuestro método de consulta devolverá una lista que contiene los resultados de la consulta o una lista vacía.
		- `Stream<T>` : Nuestro método de consulta devolverá un _stream_ que se puede utilizar para acceder a los resultados de la consulta o un _stream_ vacío.
		
## Ejemplos

### Ejemplo 1: Consultar todos los cursos cuyo nombre contiene una cadena de caracteres

```java
public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	/**
	 * Buscar todos los cursos cuyo campo nombre contiene el valor proporcionado
	 * @param nombre
	 * @return Cursos que contienen dicha cadena de caracteres o una lista vacía
	 */
	List<Curso> findByNombreContains(String nombre);
	
}

```

```java
@Service
public class CursoServicio extends BaseService<Curso, Long, CursoRepository>{
	
	
	/**
	 * Envoltorio par el método findByNombreContains
	 */
	public List<Curso> buscarPorNombre(String nombre) {
		return repositorio.findByNombreContains(nombre);
	}
	

}

```

### Ejemplo 2: Devolver el número de alumnos que hay en un curso dado

Este es un ejemplo en el que situamos la consulta en el repositorio correspondiente (el de `Alumno`), pero tiene sentido invocarla desde un servicio diferente (el de `Curso`).

```java
public interface AlumnoRepository 
	extends JpaRepository<Alumno, Long> {

	/**
	 * Devuelve el número de alumnos que hay en un curso dado 
	 */
	public long countByCurso(Curso curso);
		
}
```

```java
@Service
public class CursoServicio extends BaseService<Curso, Long, CursoRepository>{
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	// Resto del código
		
	/**
	 * Devuelve el número de alumnos en un curso
	 */
	public long alumnoEnUnCurso(Curso curso) {
		return alumnoRepository.countByCurso(curso);
	}

}
```

### Ejemplo 3: Obtener, como mucho, 5 alumnos, ordenados por fecha de nacimiento descendente cuyo apellido sea igual al proporcionado

```java
public interface AlumnoRepository 
	extends JpaRepository<Alumno, Long> {

	// Resto del código
	
	/**
	 * Devuelve 5 alumnos, ordenados por fecha de nacimiento descendente
	 * cuyo apellido sea igual al proporcionado
	 */
	public List<Alumno> findTop5ByApellido1OrderByFechaNacimientoDesc(String apellido1);
		
}
```

```java
@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository>{

	/**
	 * Devuelve 5 alumnos, ordenados por fecha de nacimiento descendente
	 * cuyo apellido sea igual al proporcionado
	 */
	public List<Alumno> cincoPorApellido(String apellido) {
		return repositorio.findTop5ByApellido1OrderByFechaNacimientoDesc(apellido);
	}
		
}

```


### Ejemplo 4: Devuelve 3 alumnos cuyo primer o segundo apellido es alguno de los proporcionados, obteniendo el resultado en un `Stream<Alumno>`

```java
public interface AlumnoRepository 
	extends JpaRepository<Alumno, Long> {

	// Resto del código 	
	/**
	 * Devuelve 3 alumnos cuyo primer o segundo apellido es alguno de los proporcionados
	 * @return Un Stream de alumnos
	 */
	public Stream<Alumno> findTop3ByApellido1ContainsOrApellido2Contains(String apellido1, String apellido2);
	
}
```

```java
@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository>{

	// Resto del código
	
	/**
	 * Obtiene desde el repositorio como mucho 3 alumnos cuyo primer o segundo 
	 * apellido sea el proporcionado.
	 * @return Una lista con el nombre completo de los alumnos como String.
	 */
	@Transactional
	public List<String> nombreAlumnosContieneApellido(String apellido) {
		return repositorio
				.findTop3ByApellido1ContainsOrApellido2Contains(apellido, apellido)
				.map(a -> a.getNombre() + " " + a.getApellido1() + " " + a.getApellido2())
				.collect(Collectors.toList());
	}
	
}

```

> Es habitual necesitar la anotación `@Transactional` cuando utilicemos `Stream<T>` como tipo de retorno de una consulta.


## Bibliografía

- [https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-from-method-names/](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-from-method-names/) 