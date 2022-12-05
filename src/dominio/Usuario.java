package dominio;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Usuario {

	private int id;
	private String email;
	private String nombre;
	private String apellidos;
	private String nombreUsuario;
	private String password;
	private LocalDate fechaNacimiento;
	private File fotoPerfil;
	private String textoPresentacion;
	private Boolean isPremium;
	private List<Foto> fotos;
	private List<Usuario> seguidores;
	
	public Usuario(String email, String nombre, String apellidos, String nombreUsuario,
			String password, LocalDate fechaNacimiento)
	{
		this.id = 0;
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.fotoPerfil = new File("/imagenes/usuario.png");
		this.textoPresentacion = "";
		this.fotos = new LinkedList<Foto>();
		this.seguidores = new LinkedList<Usuario>();
		this.isPremium = false;
	};
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setTextoPresentacion(String textoPresentacion) {
		this.textoPresentacion = textoPresentacion;
	}

	public File getFotoPerfil() {
		return fotoPerfil;
	}
	
	public void setFotoPerfil(File fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	public String getTextoPresentacion() {
		return textoPresentacion;
	}
	
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public List<Foto> getFotos() {
		return fotos;
	}
	
	public void addFoto(Foto foto) {
		fotos.add(foto);
	}
	
	public List<Usuario> getSeguidores(){
		return seguidores;
	}
	
	public void addSeguidor(Usuario seguidor) {
		seguidores.add(seguidor);
	}
	
	public Boolean getIsPremium() {
		return isPremium;
	}
	
	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}

}
