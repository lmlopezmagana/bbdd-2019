# Ejemplo de paginación con Thymeleaf y Spring

## Autoría del ejemplo

Este ejemplo es una adaptación del ejemplo de Branislav Lazic que se puede encontrar en [https://github.com/BranislavLazic/spring-thymeleaf-pagination](https://github.com/BranislavLazic/spring-thymeleaf-pagination)


## Necesidad de paginar resultados

Cuando se comienza el desarrollo de sistemas que acceden a bases de datos y presentan sus resultados, suele ser muy común comenzar con unos pocos datos de ejemplo en cada tabla. Supongamos una aplicación de venta on-line de productos tecnológicos y su catálogo de productos. La ejecución de una consulta como:

```sql
SELECT * FROM PRODUCTOS
```

Puede producir decenas, sino miles, de resultados. _¿Deberían mostrarse todos a la vez en la misma pantalla?_ La **respuesta es que no**, ni por usabilidad, ni por eficiencia. Dependiendo del entorno donde tengamos desplegada nuestra aplicación, dicha consulta podría tardar en procesarse varios minutos (mientras el sgbd procesa la consulta, devuelve los resultados al sistema de plantillas, este procesa y renderiza la plantilla y la devuelve al navegador).

Al paginar los resultados, el usuario podrá navegar con más facilidad entre ellos, y aunque tengamos que hacer varias peticiones hasta llegar al resultado deseado, dichas peticiones se procesarán con más rapidez.

## Paginación en cliente _vs._ Paginación en servidor.

Algunos programadores, a la hora de paginar, optan por la solución de hacerlo en cliente. Esto significa que se solicitan al servidor todos los registros, este los devuelve, y a través de algún tipo de algoritmo o biblioteca de Javascript, se realiza el procesamiento de los mismos. Esta aproximación puede ser válida en algún contexto y, si bien es cierto que nos aporta la usabilidad, seguimos teniendo el problema del rendimiento, ya que se siguen solicitando todos los registros al servidor (aunque estos no sean visualizados de una vez). Si queremos ganar tanto en usabilidad como en rendimiento,  


## Algo de teoría: consultas que devuelven _páginas_ de resultados

Hasta ahora, todas las consultas que hemos realizado y que devolvían más de un resultado las hemos tratado como `List<T>` o  `Stream<T>`.

Sin embargo, Spring Data Jpa nos ofrece también la posibilidad de que devuelvan un [`org.springframework.data.domain.Page<T>`](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html). Se trata de una _sublista_ de objetos, que nos permite obtener información sobre la posición con respecto a la lista completa.

Algunos de los métodos de este interfaz son:

- `getTotalElements()`: devuelve el número total de elementos.
- `getTotalPages()`: devuelve el número total de páginas.

Para realizar una consulta que devuelva un objeto `Page<T>`, debemos usar alguna de los versiones de los métodos en el repositorio que reciben un objeto de tipo `org.springframework.data.domain.Pageable`. Este objeto encapsula la información necesaria para realizar una consulta paginada. Existen diferentes formas de construir objetos de este tipo, aunque una de las más habituales suele ser a través de la clase [`org.springframework.data.domain.PageRequest`](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageRequest.html) y su método `of(int page, int size)`. Esto nos devuelve un objeto `Pageble` (en realidad, de tipo `PageRequest`) no ordenado. También existen otras versiones de este método con posibilidad de devolver los resultados de forma ordenada.

## Pasemos a la práctica

Para implementar un listado paginado, vamos a necesitar lo siguiente:

1. Tener un servicio que nos permita realizar consultas paginadas (que reciba un objeto de tipo `Pageable` y devuelva objetos de tipo `Page<T>`).
2. Un controlador que utilice el servicio del punto anterior, y que permita recoger parámetros de la url. Dichos parámetros serán _el tamaño de página_ y la _página que se quiere visualizar_.
3. Un modelo que permita encapsultar la información sobre la paginación: número de botones a visualizar, conjunto de tamaños de página disponibles, ...).
4. Una plantilla que sea capaz de renderizar los enlaces de paginación.

### Cómo manejar la información de paginación


Para encapsular la información sobre la paginación, en este ejemplo se crea un modelo, llamado `Pager` (esta clase no es propia de Spring, la crea el autor del ejemplo). Con esta clase manejamos:

- El número total de botones de enlace a visualizar.
- El número del enlace a la página que se va a renderizar como el primero.
- El número del enlace a la página que se va a renderizar como la última.   

### Algunos elementos en el controlador

El controlador que va a manejar el listado paginado recibe dos parámetros especiales, llamados `pageSize` (el tamaño de página) y `page` (el número de página a mostrar). Ambos son de tipo `Optional<Integer>`, ya no que son parámetros obligatorios. 

> Supongamos que hacemos una consulta que tiene 1000 resultados, y el tamaño de página está establecido en 20. ¿Cuántas páginas de resultados tendremos? 1000 / 20 = 50 páginas. Tenemos que ofrecer, a través de enlaces, la posibilidad de que el usuario pueda navegar por las diferentes páginas. Sin embargo, no sería ni estético ni operativo _pintar_ 50 enlaces diferentes.
> Para solucionar este problema, vamos a establecer un número de enlaces (botones) a mostrar, y adicionalmente añadiremos un botón de primera y última página, y otro de página anterior y página siguiente.

Paso a paso, el método hace lo siguiente:

**1. Tamaño de página**

```java
int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
```

Calcula el tamaño de página. Si se ha pasado un tamaño de págia como parámetro, se toma; si no, se toma el tamaño de página inicial, establecido como una constante.

**2. Página actual a renderizar**

```java
int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
``` 

Calcula la página que se va a mostar. Si no se recibe dicho parámetro, o es menor que 0, se devuelve el valor de la página inicial. En otro caso, se devuelve el valor del parámetro decrementado en 1.

**3. Consulta paginada**

```java
Page<Person> persons = personService.findAllPageable(PageRequest.of(evalPage, evalPageSize));
```

Conociendo el tamaño de página y la página a consultar, realizamos la consulta paginada, y obtenemos el resultado en un objeto de tipo `Page<Person>`.

**4. Construcción de la información de paginación**

```java
Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);
```

Ya tenemos toda la información suficiente para construir el modelo de información para paginar.


**5. Paso de información a la plantilla**

```java
model.addAttribute("persons", persons);
model.addAttribute("selectedPageSize", evalPageSize);
model.addAttribute("pageSizes", PAGE_SIZES);
model.addAttribute("pager", pager);
```

### Plantilla

De la plantilla podemos destacar dos grandes partes:

**1. Visualización de resultados** 

El bloque de código que visualiza el conjunto de resultados es idéntico al de cualquier consulta anterior, aunque no utilizáramos paginación:

```java
<tr th:each="person : ${persons}">
	<td th:text="${person.firstName}"></td>
	<td th:text="${person.lastName}"></td>
	<td th:text="${person.age}"></td>
</tr>
```

**2. Renderizado de los elementos de paginación**

Como elementos de paginación, tenemos dos conjuntos de elementos:

_a. Un desplegable, que contiene los diferentes tamaños de páginas_

```html
<select class="form-control pagination" id="pageSizeSelect">
	<option th:each="pageSize : ${pageSizes}" th:text="${pageSize}"
			th:value="${pageSize}"
			th:selected="${pageSize} == ${selectedPageSize}"></option>
</select>
```

Para manejar la recarga de la página cuando se seleccione un valor diferente en el desplegable, podemos usar jQuery.

```html
<script th:src="@{/js/app.js}"></script>
```

```javascript
$(document).ready(function() {
	changePageAndSize();
});

// Permite recargar la página cada vez que cambia el tamaño de página
function changePageAndSize() {
	$('#pageSizeSelect').change(function(evt) {
		window.location.replace("/?pageSize=" + this.value + "&page=1");
	});
}
```
b. El conjunto de botones para navegar entre paǵinas.

Tenemos que renderizar:

- Botón de página inicial:

```html
<li th:class="${persons.number == 0} ? disabled">
	<a class="pageLink"
	   th:href="@{/(pageSize=${selectedPageSize}, page=1)}">&laquo;</a>
</li>

```

- Botón de página anterior:

```html
<li th:class="${persons.number == 0} ? disabled">
	<a class="pageLink" th:href="@{/(pageSize=${selectedPageSize}, page=${persons.number})}">&larr;</a>
</li>

```

- Botones de enlaces numerados a páginas:

```html
<li th:class="${persons.number == (page - 1)} ? 'active pointer-disabled'"
	th:each="page : ${#numbers.sequence(pager.startPage, pager.endPage)}">
	<a class="pageLink" th:href="@{/(pageSize=${selectedPageSize}, page=${page})}" th:text="${page}"></a>
</li>

```

- Botón de página siguiente:

```html
<li th:class="${persons.number + 1 == persons.totalPages} ? disabled">
	<a class="pageLink" th:href="@{/(pageSize=${selectedPageSize}, page=${persons.number + 2})}">&rarr;</a>
</li>

```

- Botón de última página:

```html
<li th:class="${persons.number + 1 == persons.totalPages} ? disabled">
	<a class="pageLink" th:href="@{/(pageSize=${selectedPageSize}, page=${persons.totalPages})}">&raquo;</a>
</li>

```