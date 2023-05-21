package dominio;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public abstract class Publicacion {
	
	private int id;
	private String titulo;
	private Usuario usuario;
	private LocalDateTime fecha;
	private String descripcion;
	private int meGustas;
	private List<String> hashtags;
	private List<Comentario> comentarios;
	
	public Publicacion(String titulo, LocalDateTime fecha,
				String descripcion, List<String> hashtags) {
		this.id = 0;
		this.titulo = titulo;
		this.usuario = null;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.meGustas = 0;
		this.hashtags = hashtags;
		this.comentarios = new LinkedList<Comentario>();
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
	
	public LocalDateTime getFecha() {
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

	public void addMeGustas() {
		this.meGustas += 1;
	}
	
	public void setMeGustas(int num) {
		this.meGustas = num;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	
	public void addHashtag(String h) {
		this.hashtags.add(h);
	}
	
	public List<Comentario> getComentarios(){
		return comentarios;
	}
	
	public void addComentario(Comentario c) {
		this.comentarios.add(c);
	}

}
