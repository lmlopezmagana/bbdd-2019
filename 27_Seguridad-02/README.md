
# Ejemplo 27 - Ejemplo de uso de Spring Security con servicio de autenticación  

Como aprendíamos en el [ejemplo anterior](https://github.com/lmlopezmagana/bbdd-2019/tree/master/26_Seguridad-01), el proceso de autenticación nos permite responder a la pregunta ¿y tú, quién eres?. En dicho ejemplo, hemos hecho la proceso  en memoria. Vamos a proceder ahora a crear un servicio de autenticación, que nos permitirá dar toda la versatilidad a dicho proceso.

## Modelos de autenticación

- _In Memory_: lo hemos aprendido en el ejemplo anterior.
- _JDBC_: los usuarios se almacenan en una base de datos relacional, accedida a través de Jdbc.
- _ldap_: los usuarios están en un almacén de seguridad, como por ejemplo de directorio activo de un servidor Windows.
- ***User Details Service***: se accede a la información de autenticación a través de un servicio. 

## Interfaces clave

Dentro del modelo de clases e interfaces de Spring Security, encontramos algunos de ellos que serán claves en el proceso de autenticación, como son:

- [`org.springframework.security.core.userdetails.UserDetails`](https://docs.spring.io/spring-security/site/docs/5.2.0.BUILD-SNAPSHOT/api/org/springframework/security/core/userdetails/UserDetails.html): **Proporciona información básica de un usuario**.
Las implementaciones no son utilizadas directamente por Spring Security por motivos de seguridad. Simplemente almacenan información de usuario que luego se encapsula en objectos de tipo `Authentication`. Esto permite separar  la información del usuario no relacionada con la seguridad (como direcciones de correo electrónico, números de teléfono, etc.). **Suele interesar implementar esta interfaz en lugar a usar directamente la clase `org.springframework.security.core.userdetails.User`**. 
- [`org.springframework.security.core.userdetails.UserDetailsService`](https://docs.spring.io/spring-security/site/docs/5.2.0.BUILD-SNAPSHOT/api/org/springframework/security/core/userdetails/UserDetailsService.html): interfaz principal que carga los datos de un usuario. Se utiliza en todo el framework como un DAO de usuarios. Solo proporciona un método, y este es de solo lectura. 
 - [`org.springframework.security.core.GrantedAuthority`](https://docs.spring.io/spring-security/site/docs/5.2.0.BUILD-SNAPSHOT/api/org/springframework/security/core/GrantedAuthority.html). Representa una autorización (un privilegio concreto) otorgado a un objeto de tipo `Authentication`.Ppodemos considerar a cada `GrantedAuthority` como un privilegio individual:  `READ_AUTHORITY`, `WRITE_PRIVILEGE` o incluso `CAN_EXECUTE_AS_ROOT`. _Lo importante a entender es que el nombre es **arbitrario**_. De manera similar, en Spring Security, podemos pensar en cada rol como una `GrantedAuthority` de _grano grueso_ que se representa como una cadena y tiene el prefijo "ROLE". 


## Paso 1: Nuestro modelo de usuario

En vista de lo que hemos leído en el apartado anterior, podemos plantear dos posibles soluciones para la implementación de nuestro modelo `Usuario`:

- Que nuestra propia clase `Usuario` implemente la interfaz `UserDetails` (siempre es una buena opción).
- A partir de nuestra clase `Usuario`, utilizar la clase `UserBuilder` para construir un objeto de tipo `UserDetails` en las circunstancias que sean necesarias (en este ejmplo, usaremos esta segunda opción).

Partimos de un diseño de clase de usuario básico:

```java
@Data @NoArgsConstructor
@Entity
public class Usuario {
	
	@Id @GeneratedValue
	private long id;
	
	private String nombre;
	
	private String apellidos;
	
	@Column(unique = true)
	private String email;

	private String password;

	private boolean admin;

	public Usuario(String nombre, String apellidos, String email, String password, boolean admin) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
		
}
``` 

Como es lógico, creamos un repositorio y servicio para esta entidad. Veamos el código, porque incluimos una consulta:

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findFirstByEmail(String email);

}

```

```java
@Service
public class UsuarioServicio extends BaseService<Usuario, Long, UsuarioRepository>{
	
	public Usuario buscarPorEmail(String email) {
		return repositorio.findFirstByEmail(email);
	}

}

```

Dicha consulta nos permitirá encontrar un usuario en base a su email, que será el campo que utilicemos en este ejemplo como _username_.

## Paso 2: Implementación de `UserDetailsService`

Creamos una nueva clase que implemente este interfaz:

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
		
		UserBuilder userBuilder = null;
		
		if (usuario != null) {
			userBuilder = User.withUsername(usuario.getEmail());
			userBuilder.disabled(false);
			userBuilder.password(usuario.getPassword());
			if (usuario.isAdmin()) {
				// Este caso indica que un ADMIN también puede hacer todo lo que hace un USER
				userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));				
			} else {
				userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"));				
			}
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
			
		return userBuilder.build();
		
	}

}

```
El único método que nos obliga a implementar la interfaz es `loadByUsername`, un método que pueda buscar un usuario donde estén almacenados en base al campo _username_ (como decíamos antes, para nosotros, en este ejemplo, será el email). Veamos el algoritmo que seguimos

1. Usando el servicio de usuario, lo buscamos en base a su _username_ (email).
2. Si encontramos el usuario
	1. Comenzamos creando un `UserBuilder` con el nombre del usuario encontrado.
	2. Le indicamos que dicho usuario no está deshabilitado.
	3. Le asignamos la contraseña.
	4. Asignamos los roles correspondientes si es administrador o no.
3. Si no lo encontramos.
	1. Lanzamos la excepción `UsernameNotFoundException`
4. Devolvemos el `UserDetails` creado a partir del `UserBuilder` configurado.

## Paso 3: Actualización de la configuración de la seguridad

Vamos a cambiar nuestro método de configuración de autenticación para usar nuestro servicio `UserDetailsService`, así como para codificar las constraseñas. Para ello, usaremos un nuevo bean de tipo `BCryptPasswordEncoder`. También modificamos la autorización, permitiendo a todo el mundo el acceso al logout.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/css/**","/js/**","/webjars/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll();
		
		// Añadimos esto para poder seguir accediendo a la consola de H2
		// con Spring Security habilitado.
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
	}

```

## Paso adicional: Datos de prueba

Creamos a traves de un bean de tipo `CommandLineRunner` algunos datos de prueba para poder loguearnos. ¡OJO! Hay que tener en cuenta antes de almacenar un usuario **tenemos que codificar su contraseña**.

```java
	@Bean
	public CommandLineRunner init(UsuarioServicio servicio, BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			
			Usuario u = new Usuario();
			u.setAdmin(false);
			u.setNombre("Luis Miguel");
			u.setApellidos("López");
			u.setEmail("luismi.lopez@email.com");
			u.setPassword(passwordEncoder.encode("1234"));
			
			servicio.save(u);
			
			
			Usuario a = new Usuario();
			a.setAdmin(true);
			a.setNombre("Ángel");
			a.setApellidos("Narajo");
			a.setEmail("angel.naranjo@email.com");
			a.setPassword(passwordEncoder.encode("1234"));
			
			servicio.save(a);
			
		};
	}

```