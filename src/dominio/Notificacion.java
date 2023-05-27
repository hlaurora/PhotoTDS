package dominio;

import java.time.LocalDateTime;

public class Notificacion {

	private int id;
	private Publicacion publicacion;
	private LocalDateTime fecha;

	public Notificacion(LocalDateTime fecha) {
		this.publicacion = null;
		this.fecha = fecha;
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

	public LocalDateTime getFecha() {
		return fecha;
	}


}
