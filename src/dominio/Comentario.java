package dominio;

import java.time.LocalDateTime;

public class Comentario {
	
	private int id;
	private String texto;
	private Usuario usuario;
	private LocalDateTime fecha;
	
	public Comentario (String texto, LocalDateTime fecha) {
		this.texto = texto;
		this.usuario = null;
		this.fecha = fecha;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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


}
