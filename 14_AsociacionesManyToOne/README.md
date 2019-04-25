
# Ejemplo 14 - Ejemplo de dos entidades con una asociación ManyToOne

Continuamos desde el [ejemplo asdfg]

## ¿Qué tenemos ya hecho?

Ya tenemos creada una entidad, así como su repositorio, un servicio y un controlador con algunos métodos.

## ¿Qué vamos a crear?

1. Una nueva entidad, que está asociada con la anterior.
2. La asociación entre ambas entidades.

## ¿Cuál es nuestro modelo de datos?

![diagrama uml](./uml.jpg) 

## Algo de teoría sobre asociaciones _ManyToOne_

Una asociación _muchos-a-uno_ es una asociación que relaciona dos clases, de forma que una instancia del lado _uno_ (en nuestro caso, curso), puede asociarse con varias instancias del lado _muchos_, (en nuestro caso, los alumnos).

Para poder implementar esto en nuestra aplicación necesitamos:

1. Trasladar la entidad del lado _muchos_.
2. Trasladar la entidad del lado _uno_.
3. Añadir los elementos necesarios para implementar la asociación.

### ¿Cómo impacta esto en el resto de nuestro sistema?

Como norma general:

- La entidad del lado _muchos_ tendrá su repositorio y su servicio.
- La entidad el lado _uno_ también tendrá su repositorio y su servicio.



