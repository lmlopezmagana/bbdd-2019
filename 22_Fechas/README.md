
# Ejemplo 22 - Cómo manejar fechas con JPA 2.2 y Java 8

Java 8 supuso un gran cambio con respecto a las versiones anteriores en muchos aspectos. Uno de ellos fue la introducción de un nuevo [api de fechas y horas](https://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html).

Alguno de los tipos de datos más usados son:

- [`java.time.LocalDate`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
- [`java.time.LocalDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html)

El api de persistencia JPA, sin embargo, no ha introducido hasta la versión 2.2 (que es la actual) la posibilidad de manejar de _forma nativa_ estos tipos de datos. Hasta ahora, tenían que usarse conversores o soluciones propietarias para ello. Podemos utilizar Hibernate, desde su versión 5.3, como _vendor_ de JPA 2.2, para así aprovechar esta funcionalidad.




