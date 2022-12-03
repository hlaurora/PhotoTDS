package dominio;

import java.time.LocalDate;
import java.util.List;

public abstract class Publicacion {
	
	private int id;
	private String titulo;
	private Usuario usuario;
	private LocalDate fecha;
	private String descripcion;
	private int meGustas;
	private List<String> hashtags;
	
	public Publicacion(String titulo, LocalDate fecha,
				String descripcion, List<String> hashtags) {
		this.id = 0;
		this.titulo = titulo;
		this.usuario = null;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.meGustas = 0;
		this.hashtags = hashtags;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getMeGustas() {
		return meGustas;
	}

	public void setMeGustas(int meGustas) {
		this.meGustas = meGustas;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}

}
