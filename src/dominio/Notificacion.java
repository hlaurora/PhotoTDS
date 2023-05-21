package dominio;

import java.time.LocalDateTime;

public class Notificacion {

	private int id;
	private Publicacion publicacion;
	//private Usuario usuario;
	private LocalDateTime fecha;
	
	//public Notificacion(Publicacion p, Usuario u) {
	public Notificacion(LocalDateTime fecha) {
		this.id = 0;
		this.publicacion = null;
		this.fecha = fecha;
		//this.usuario = u;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	/*public Usuario getUsuario() {
		return usuario;
	}*/
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	
}
