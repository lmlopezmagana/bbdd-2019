
# Ejemplo 18 - Ejemplo de dos entidades con una asociación ManyToMany

Realizamos un fork desde alguno de los ejemplos anteriores 

## ¿Cuál es nuestro modelo de datos?

![diagrama uml](./uml.jpg) 

## Algo de teoría sobre asociaciones _ManyToMany_

Una asociación _muchos-a-muchos_ es un tipo de _relación_ entre clases, por la cual, una instancia de uno de los tipos conectados, se puede asociar a muchas instancias del otro tipo, y viceversa. Uno de los ejemplos clásicos suele ser el de las clases `Autor` y `Libro`, mediante la cual un `Autor` puede escribir muchos `Libro`, y un `Libro` puede ser escrito por muchos autores. 

Aunque esta asociación tenga la misma _multiplicidad_ en ambos extremos, también puede tratarse de forma bien **unidireccional**, bien **bidireccional**. Escoger una u otra queda a cargo del programador.

JPA nos ofrece una anotación parecida a las anteriores, `@ManyToMany`, que nos permitirá manejar la relación. En este caso, dicha anotación irá asociada a una colección (normalmente, un `Set` o un `List`).

```java
@ManyToMany
List<Entity> myList = new ArrayList<>();
```

A nivel de DDL, se crea una nueva tabla, con _claves externas_ hacia las tablas de las entdidades asociadas. 

### Anotaciones adicionales

Además de la anotación `@ManyToMany`, podemos completar esta asociación con algo de metainformación, a través de las siguientes anotaciones: 

- `@JoinTable`: nos permite dar algo de configuración a la tabla _join_ que se genera.
	- `name`: podemos cambiar el nombre por defecto de esta tabla
	- `joinColumns`: nos permite proporcionar una colección indicando los `@JoinColumn` (y esto a su vez, nos permite indicar todas las propiedades de cada `@JoinColumn`, como el `name` , `columnDefinition`, ...
	- `inverseJoinColumns`: nos permite proporcionar una colección de claves externas (similar al anterior) pero que apuntan a la entidad que no posee la asociación _muchos-a-muchos_.
	
Por ejemplo: 

```java
@ManyToMany
@JoinTable(
	name="tabla_join",
	joinColumns= @JoinColumn(name="entidadA_id"),
	inverseJoinColumns=@JoinColumn(name="entidadB_id")
)
private List<Entity> myList = new ArrayList<>();
```

## Paso 1: Creamos la segunda entidad

En nuestro ejemplo, ya tenemos creada la entidad `Alumno`. En este caso necesitamos crear la otra entidad a asociar, `Asignatura`.


```java
@Entity
public class Asignatura {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String profesor;
	
	public Asignatura(String nombre, String profesor) {
		this.nombre = nombre;
		this.profesor = profesor;
	}
	
}

```


## Paso 2: Creamos el repositorio y servicio para esta entidad

```java
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long>{

}
```

```java
@Service
public class AsignaturaServicio extends BaseService<Asignatura, Long, AsignaturaRepository>{

}

```

## Paso 3: Modificación de una de las entidades para registrar la asociación entre clases

En el caso del tratamiento unidireccional, necesitamos _ubicar_ la asociación en alguna de las dos entidades. ¿En cuál? No hay ninguna premisa, salvo nuestro sentido común. En este caso, vamos a añadir la asociación en la nueva entidad que hemos creado, `Asignatura`.

```java
@Entity
public class Asignatura {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String profesor;
	
	@ManyToMany
	@JoinTable(
		joinColumns = @JoinColumn(name="asignatura_id"),
		inverseJoinColumns = @JoinColumn(name="alumno_id")
	)
	private List<Alumno> alumnos = new ArrayList<>();
	
	public Asignatura(String nombre, String profesor) {
		this.nombre = nombre;
		this.profesor = profesor;
	}
	
	
	
}

```


Si ejecutamos el proyecto, podemos apreciar el DDL generado

```sql
   create table alumno (
       id bigint not null,
        apellidos varchar(255),
        email varchar(255),
        nombre varchar(255),
        primary key (id)
    ); 
    
    create table asignatura (
       id bigint not null,
        nombre varchar(255),
        profesor varchar(255),
        primary key (id)
    );

    
    create table asignatura_alumnos (
       asignatura_id bigint not null,
        alumno_id bigint not null
    );
    
    alter table asignatura_alumnos 
       add constraint FKscjojadihey4wi6ic169m0va0 
       foreign key (alumno_id) 
       references alumno;
    
    alter table asignatura_alumnos 
       add constraint FK4l3r7vypusyisar8g98g32gm3 
       foreign key (asignatura_id) 
       references asignatura;
```

Podemos apreciar como:

- Se crea una nueva tabla (la tabla _join_) para manejar la asociación
- Además, se añaden sendas claves externas en dicha tabla, y que apuntan a cada una de las entidades.

## Paso 4: ¿Cómo _utilizar_ la asociación?

En este caso, siendo unidireccional, tan solo tendríamos que añadir instancias de `Alumno` a la lista que tiene `Asignatura`, y editar la asignatura.

```java
Asignatura basesDeDatos = new Asignatura("Bases de datos", "Luis Miguel López");
nuevos.forEach(basesDeDatos.getAlumnos()::add);
asignaturaServicio.save(basesDeDatos);
```


