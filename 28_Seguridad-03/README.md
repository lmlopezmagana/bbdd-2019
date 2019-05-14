
# Ejemplo 28 - Ejemplo de uso de Spring Security con servicio de autenticación (avanzado)  

Vamos a trabajar sobre el ejemplo anterior añadiendo dos elementos:

1. El modelo de seguridad tendrá herencia
2. En función del tipo concreto de usuario, queremos redirigir a URLs diferentes. Para implementar esto último, nos basaremos en la solución del siguiente [tutorial](http://websystique.com/spring-security/spring-security-4-role-based-login-example/). 

## Paso 1: Cambios en el modelo de usuario

Introducimos dos cambios en el modelo de usuario:

1. Por un lado, utilizaremos herencia de tipo _Joined_. Nuestra clase base será abstracta. 
2. El usuario base implementará el interfaz `UserDetails` (en el ejemplo anterior utilizamos otra estrategia, la de construir el objeto `UserDetails` a partir de `UserBuilder`). Esto nos obligará a implmenetar todos los métodos de la interfaz.

La clase base, como decimos abstracta, quedaría de la siguiente forma:

```java
@Data @NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements UserDetails {
	
	@Id @GeneratedValue
	private long id;
	
	private String nombre;
	
	private String apellidos;
	
	@Column(unique = true)
	private String email;

	private String password;
	
	private boolean cuentaCaducada;
	private boolean cuentaBloqueada;
	private boolean credencialesCaducadas;

	public Usuario(String nombre, String apellidos, String email, String password) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.cuentaCaducada = false;
		this.cuentaBloqueada = false;
		this.credencialesCaducadas = false;
	}


	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();


	@Override
	public String getUsername() {
		return email;
	}


	@Override
	public boolean isAccountNonExpired() {
		return !cuentaCaducada;
	}


	@Override
	public boolean isAccountNonLocked() {
		return !cuentaBloqueada;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return !credencialesCaducadas;
	}


	@Override
	public boolean isEnabled() {
		return !cuentaBloqueada;
	}
	
}
```

El interfaz `UserDetails` nos obliga a implementar una serie de métodos que indican si la cuenta de usuario no está bloqueada, está habilitado o sus credenciales no han expirado.

A partir de aquí, extendemos dicha clase en otras dos, `Empleado` y `Cliente`. En cada una de ellas implementamos el método abstracto de `Usuario`, que indica el rol o roles que tendrá cada clase.

```java
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Empleado extends Usuario{
	
	private LocalDate fechaAltaSS;

	public Empleado(String nombre, String apellidos, String email, String password, 
			LocalDate fechaAltaSS) {
		super(nombre, apellidos, email, password);
		this.fechaAltaSS = fechaAltaSS;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

}

``` 

```java
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Cliente extends Usuario {
	
	private LocalDate fechaNacimiento;

	public Cliente(String nombre, String apellidos, String email, String password, 
			LocalDate fechaNacimiento) {
		super(nombre, apellidos, email, password);
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
}

```

### Paso 1.1 Servicios y repositorios

En principio, no necesitamos crear para este ejemplo ni repositorios ni servicios de `Cliente` o `Empleado`, ya que podemos gestionar las inserciones o selecciones a través del servicio de `Usuario`. Si tuviéramos relaciones más complejas con otro tipo de entidades, posiblemente sea necesario crearlos.

## Paso 2. Cambios en nuestro servicio `UserDetailsService`

Dado que en este ejemplo nuestra clase `Usuario` implementa la interfaz `UserDetailsService`, tenemos que cambiar la logica de nuestro servicio `UserDetailsServiceImpl`, para que deje de utilizar `UserBuilder`. El código queda bastante simplificado.

```java
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	UsuarioServicio usuarioServicio;
	
	public UserDetailsServiceImpl(UsuarioServicio servicio) {
		this.usuarioServicio = servicio;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioServicio.buscarPorEmail(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		} 
		
		return usuario;
				
	}

}

``` 


## Paso 3. Cambios en la seguridad

En el ejemplo anterior, teníamos el siguiente código en la clase `SecurityConfig`:

```java
	.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
```

Con este fragmento de código, le estamos indicando al sistema que si el login se produce con éxito, nos redirija a la página desde la cual se nos redirigió previamente al login, o en su defecto, a la raiz.

En este caso, **queremos tener redirecciones diferentes para roles diferentes**: el empleado, una vez logueado con éxito, accederá a la parte de administración, y el cliente, a la zona de clientes. ¿Cómo podemos conseguirlo? A la hora de configurar el formulario de login estamos usando (casi sin saberlo) una instancia de la clase `FormLoginConfigurer`, y uno de sus métodos (heredado de otra clase) es `successHandler`. Este método nos permite, en lugar de indicar una url de _éxito_, establecer dicha url a traves de un manejador. Dicho manejador debe ser una instancia de una clase que implemente la interfaz `AuthenticationSuccessHandler`. En lugar de implementar directamente la interfaz, podemos extender una clase, llamada `SimpleUrlAuthenticationSuccessHandler`, que ya implementa esa interfaz, y nos proporciona la funcionalidad básica de redirigir a una URL.


```java
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			System.out.println("Can't redirect");
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/*
	 * This method extracts the roles of currently logged-in user and returns
	 * appropriate URL according to his/her role.
	 */
	protected String determineTargetUrl(Authentication authentication) {
		String url = "";

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}

		if (isAdmin(roles)) {
			url = "/admin/";
		} else if (isUser(roles)) {
			url = "/";
		} else {
			url = "/acceso-denegado";
		}

		return url;
	}

	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_USER")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}


```

La sistemática de esta clase no es complicada: una vez que el usuario se ha logueado correctamente, lo redirigimos a una URL; dicha URL se determina en función del rol del usuario (el usuario se nos presenta aquí a través de un objeto de tipo `Authentication`, que ya nombramos en el ejemplo anterior).
