
# Ejemplo 22 - Cómo manejar fechas con JPA 2.2 y Java 8

Java 8 supuso un gran cambio con respecto a las versiones anteriores en muchos aspectos. Uno de ellos fue la introducción de un nuevo [api de fechas y horas](https://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html).

Alguno de los tipos de datos más usados son:

- [`java.time.LocalDate`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
- [`java.time.LocalDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html)

El api de persistencia JPA, sin embargo, no ha introducido hasta la versión 2.2 (que es la actual) la posibilidad de manejar de _forma nativa_ estos tipos de datos. Hasta ahora, tenían que usarse conversores o soluciones propietarias para ello. Podemos utilizar Hibernate, desde su versión 5.3, como _vendor_ de JPA 2.2, para así aprovechar esta funcionalidad.


Por tanto, para manejar fechas de _java.time_ con una entidad, tan solo tenemos que utilizarlas como otro tipo de dato básico:

```java
@Entity
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String apellidos;
	private String email;
	
	private LocalDate fechaNacimiento;
	
	private LocalDateTime fechaUltimoAcceso;
	
}
```

Para crear una nueva instancia y almacenarla en la base de datos, también sería igual que otra entidad como las que hemos utilizado hasta ahora:

```java
Alumno alumno = new Alumno();
alumno.setNombre("Pepe");
alumno.setApellidos("Pérez Pérez");
alumno.setEmail("pepe.perez@perezyperez.com");
alumno.setFechaNacimiento(LocalDate.of(1990, 1, 1));
alumno.setFechaUltimoAcceso(LocalDateTime.now());
			
servicio.save(alumno);

```

## Manejo de fechas _java.time_ desde Thymeleaf

Para manejar las fechas de Java 8 desde Thymeleaf sí que necesitamos hacer algo más.

### Nueva dependencia en el `pom.xml'

En primer lugar, tenemos que añadir una dependecia de elementos _extra_ para fechas de Java 8.

```xml
<dependency>
	<groupId>org.thymeleaf.extras</groupId>
	<artifactId>thymeleaf-extras-java8time</artifactId>
</dependency>
```

Al trabajar con Spring Boot, nos _ahorramos_ tener que hacer algunas cosas:

- No tenemos que añadir la versión de la dependencia
- No tenemos que configurar (ya lo hace Spring Boot) esta librería.

### ¿Qué ofrece Thymeleaf para las fechas de Java 8?

Si consultamos la [documentación de la librería](https://github.com/thymeleaf/thymeleaf-extras-java8time) podemos ver como tenemos a nuestra disposición otro nuevo interfaz, `Temporal`, con varios subinterfaces (asociados a los diferentes tipos de datos de _java.time_) y con muchos métodos de utilidad. La lista la tienes a continuación:

```java
/*
 * =============================================================================
 * See javadoc API for class org.thymeleaf.extras.java8time.expression.Temporals
 * =============================================================================
 */

/*
 * Format date with the standard locale format
 * Also works with arrays, lists or sets
 */
${#temporals.format(temporal)}
${#temporals.arrayFormat(temporalsArray)}
${#temporals.listFormat(temporalsList)}
${#temporals.setFormat(temporalsSet)}

/*
 * Format date with the standard format for the provided locale
 * Also works with arrays, lists or sets
 */
${#temporals.format(temporal, locale)}
${#temporals.arrayFormat(temporalsArray, locale)}
${#temporals.listFormat(temporalsList, locale)}
${#temporals.setFormat(temporalsSet, locale)}

/*
 * Format date with the specified pattern
 * SHORT, MEDIUM, LONG and FULL can also be specified to used the default java.time.format.FormatStyle patterns
 * Also works with arrays, lists or sets
 */
${#temporals.format(temporal, 'dd/MMM/yyyy HH:mm')}
${#temporals.arrayFormat(temporalsArray, 'dd/MMM/yyyy HH:mm')}
${#temporals.listFormat(temporalsList, 'dd/MMM/yyyy HH:mm')}
${#temporals.setFormat(temporalsSet, 'dd/MMM/yyyy HH:mm')}

/*
 * Format date with the specified pattern and locale
 * Also works with arrays, lists or sets
 */
${#temporals.format(temporal, 'dd/MMM/yyyy HH:mm', locale)}
${#temporals.arrayFormat(temporalsArray, 'dd/MMM/yyyy HH:mm', locale)}
${#temporals.listFormat(temporalsList, 'dd/MMM/yyyy HH:mm', locale)}
${#temporals.setFormat(temporalsSet, 'dd/MMM/yyyy HH:mm', locale)}

/*
 * Format date with ISO-8601 format
 * Also works with arrays, lists or sets
 */
${#temporals.formatISO(temporal)}
${#temporals.arrayFormatISO(temporalsArray)}
${#temporals.listFormatISO(temporalsList)}
${#temporals.setFormatISO(temporalsSet)}

/*
 * Obtain date properties
 * Also works with arrays, lists or sets
 */
${#temporals.day(temporal)}                    // also arrayDay(...), listDay(...), etc.
${#temporals.month(temporal)}                  // also arrayMonth(...), listMonth(...), etc.
${#temporals.monthName(temporal)}              // also arrayMonthName(...), listMonthName(...), etc.
${#temporals.monthNameShort(temporal)}         // also arrayMonthNameShort(...), listMonthNameShort(...), etc.
${#temporals.year(temporal)}                   // also arrayYear(...), listYear(...), etc.
${#temporals.dayOfWeek(temporal)}              // also arrayDayOfWeek(...), listDayOfWeek(...), etc.
${#temporals.dayOfWeekName(temporal)}          // also arrayDayOfWeekName(...), listDayOfWeekName(...), etc.
${#temporals.dayOfWeekNameShort(temporal)}     // also arrayDayOfWeekNameShort(...), listDayOfWeekNameShort(...), etc.
${#temporals.hour(temporal)}                   // also arrayHour(...), listHour(...), etc.
${#temporals.minute(temporal)}                 // also arrayMinute(...), listMinute(...), etc.
${#temporals.second(temporal)}                 // also arraySecond(...), listSecond(...), etc.
${#temporals.nanosecond(temporal)}             // also arrayNanosecond(...), listNanosecond(...), etc.

/*
 * Create temporal (java.time.Temporal) objects from its components
 */
${#temporals.create(year,month,day)}                                // return a instance of java.time.LocalDate
${#temporals.create(year,month,day,hour,minute)}                    // return a instance of java.time.LocalDateTime
${#temporals.create(year,month,day,hour,minute,second)}             // return a instance of java.time.LocalDateTime
${#temporals.create(year,month,day,hour,minute,second,nanosecond)}  // return a instance of java.time.LocalDateTime

/*
 * Create a temporal (java.time.Temporal) object for the current date and time
 */
${#temporals.createNow()}                      // return a instance of java.time.LocalDateTime
${#temporals.createNowForTimeZone(zoneId)}     // return a instance of java.time.ZonedDateTime
${#temporals.createToday()}                    // return a instance of java.time.LocalDate
${#temporals.createTodayForTimeZone(zoneId)}   // return a instance of java.time.LocalDate

/*
 * Create a temporal (java.time.Temporal) object for the provided date
 */
${#temporals.createDate(isoDate)}              // return a instance of java.time.LocalDate
${#temporals.createDateTime(isoDate)}          // return a instance of java.time.LocalDateTime
${#temporals.createDate(isoDate, pattern)}     // return a instance of java.time.LocalDate
${#temporals.createDateTime(isoDate, pattern)} // return a instance of java.time.LocalDateTime
```
 
### Y en los formularios

Para poder trabajar con los formularios correctamente, necesitamos hacer una transformación del formato de fecha (que viene en un `String`) desde el formulario para hacer un _binding_ correcto con nuestro _Command Object_ u objeto entidad. Para ello, utilizamos la anotación de Spring `@DateTimeFormat` con el patrón correcto. He aquí un ejemplo:

```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDate fechaNacimiento;	
```

El formulario sería como cualquier otro que incluya un campo de tipo `dateTime`:

```html
<form method="POST" action="#" th:action="@{/nuevo/submit}" th:object="${alumno}">
	<input type="text" name="nombre" th:field="*{nombre}" placeholder="Nombre..."/>
	<input type="text" name="apellidos" th:field="*{apellidos}" placeholder="Apellidos..." /> 
	<input type="email" name="email" th:field="*{email}" placeholder="Email..." />
	<input type="date" name="fechaNacimiento" th:field="*{fechaNacimiento}" />
	<input type="submit" name="enviar" value="Enviar" />
</form>
```
