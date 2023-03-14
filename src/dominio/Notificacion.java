package dominio;

public class Notificacion {

	private Publicacion publicacion;
	private Usuario usuario;
	
	public Notificacion(Publicacion p, Usuario u) {
		this.publicacion = p;
		this.usuario = u;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	
	
	
}
