package dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Album extends Publicacion {
	
	private List<Foto> fotos;
	
	public Album(String titulo, LocalDateTime fecha, String descripcion,
			List<String> hashtags) {
		super(titulo, fecha, descripcion, hashtags);
		this.fotos = new LinkedList<Foto>();
	}
	
	public void addFoto(Foto f) {
		this.fotos.add(f);
	}
	
	public List<Foto> getFotos() {
		return this.fotos;
	}
	
	public void removeFoto(Foto f) {
		this.fotos.remove(f);
	}

}
