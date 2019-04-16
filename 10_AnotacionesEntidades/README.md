
# Ejemplo 10 - Anotaciones sobre entidades

Partimos del [ejemplo anterior](https://github.com/lmlopezmagana/bbdd-2019/tree/master/09_PrimerEjemploSpringDataJpa).

## Anotaciones mínimas para una entidad

Cómo mínimo, para que una clase sea considerada como una entidad, tenemos que utilizar la anotación `@Entity` a nivel de clase, y declarar un atributo como _clave primaria_ con `@Id`.

```java
@Entity
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String apellidos;
	private String email;

}
```

## Anotaciones a nivel de clase

JPA nos ofrece la anotación [`@Table`](https://docs.oracle.com/javaee/7/api/javax/persistence/Table.html), con las siguientes propiedades:

- `name`: nombre de la tabla. Si no se especifica, se utiliza una estrategia de nombrado por defecto (que suele variar en función del  [dialecto](http://docs.jboss.org/hibernate/stable/annotations/api/org/hibernate/dialect/package-summary.html)).
- `schema`, `catalog`: nombre del esquema o catálogo de la base de datos donde estará la tabla mapeada.
- `indexes`, `uniqueConstraints`: listado de índices o de restricciones de unicidad.


```java
@Entity
@Table(name="STUDENT")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String apellidos;
	private String email;

}
```

## Anotaciones a nivel de atributo

Como hemos comprobado, salvo el atributo anotado con `@Id`, hemos comprobado que no es obligatorio el uso de ningún otra anotación. Con todo, JPA nos ofrece otras dos para poder gestionar algunos aspectos:

- `@Basic`: con dos posibles propiedades
	- `optional`: indica si el atributo debe ser obligatorio o no. Normalmente, implica una restricción `NOT NULL` a nivel del DDL.
	- `fetchType`: si bien ya hablaremos más adelante, indica el _tipo de lectura_ del atributo: si el atributo se obtendrá siempre (por defecto) o solo cuando se pida explícitamente. Por ahora, puedes encontrar más información [aquí](https://www.arquitecturajava.com/jpa-basic-optimizando-los-fechings/).
- `@Column`: con varias propiedades, que suelen afectar al DDL:
	- `columnDefinition`: un `String` con el trozo de código de DDL que se usará para generar la columna en la tabla.
	- `insertable`: Si la columna se incluye en las instrucciones SQL INSERT generadas por el proveedor de persistencia.
	- `name`: nombre de la columna
	- `length`: longitud de la columna
	- `nullable`: si la columna permite valores nulos
	- `preciosion`, `scale`: precisión y escala para valores numéricos
	- `table`: nombre de la tabla que contiene la columna
	- `unique`: si la columna es una clave única
	- `updatable`: Si la columna se incluye en las sentencias de SQL UPDATE generadas por el proveedor de persistencia. 
	
	
Un ejemplo de uso de las anotaciones anteriores podría ser:

```java
@Entity
@Table(name="STUDENT")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Basic(optional=false)
	@Column(name="FIRST_NAME")
	private String nombre;
	@Column(name="LAST_NAME", nullable=false)
	private String apellidos;
	@Column(unique=true)
	private String email;

}
``` 

El DDL generado como resultado sería:

```sql
create sequence hibernate_sequence
start with 1 
increment by 1

create table student (
	id bigint not null, 
	last_name varchar(255) not null, 
	email varchar(255), 
	first_name varchar(255) not null, 
	primary key (id)
)

alter table student 
add constraint UK_fe0i52si7ybu0wjedj6motiim unique (email)
```
