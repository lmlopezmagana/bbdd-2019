
# Ejemplo 17 - Tipos de _fetching_

## Algo de teoría

Ya veíamos en el [ejemplo 10](https://github.com/lmlopezmagana/bbdd-2019/tree/master/10_AnotacionesEntidades) que JPA nos permitía indicar cuando sí queríamos _leer_ todos los campos de una entidad en la base de datos, y cuando queríamos hacerlo que solo se hiciera si lo pedimos explícitamente.

JPA ofrece dos tipos diferentes de fetching:

- EAGER: se trata del tipo _ansioso_. Si un atributo o asociación tiene este tipo, quiere decir que siempre se _traerá_ sus datos desde la base de datos.
- LAZY: se trata del tipo _perezoso_. Si un atributo o asociación tiene este tipo, solo se _traerá_ sus datos cuando lo solicitemos explícitamente.

Cuando usamos **asociaciones**, JPA aplica por defecto una estrategia de fetching, pero depende del tipo de asociación:

|Tipo de asociación | Tipo de _fecthing_ por defecto |
|-------------------|--------------------------------| 
| `@ManyToOne` | `EAGER` |
| `@OneToOne` | `EAGER` |
| `@OneToMany` | `LAZY` |
| `@OneToMany` | `LAZY` |

## ¿Qué tipo de _fetching_ utilizar?

Por desgracia, la respuesta es **depende**. Si leemos el [artículo](https://vladmihalcea.com/hibernate-facts-the-importance-of-fetch-strategy/) de Vlad Mihalcea sobre este tema, encontramos la idea de que el diseño del _fetching_ tiene que venir asociado al diseño mismo de las clases y entidades, y no tiene que ser una tarea que dejemos para el final. Una mala estrategia en este sentido puede impactar en un mal rendimiento de nuestro sistema cuando este esté en producción.

En el caso de las asociaciones, nos deberíamos hacer la siguiente pregunta: si tengo 2 entidades, `A` y `B`, y ambas están asociadas: ¿siempre que quiera conseguir los datos de `A`, quiero obtener los de `B`? Si la respuesta es NO, quiere decir que no necesitamos el _fetching_ ansioso. Si al respuesta es SÍ, tendremos que pensar en el impacto que esto puede producir.

