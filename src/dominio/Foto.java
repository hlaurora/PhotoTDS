package dominio;

import java.time.LocalDate;
import java.util.List;

public class Foto extends Publicacion{
	
	private String ruta;

	public Foto(String titulo,  LocalDate fecha, String descripcion, 
			List<String> hashtags, String ruta) {
		super(titulo, fecha, descripcion, hashtags);
		this.ruta = ruta;
	}
	
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	

}
