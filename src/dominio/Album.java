package dominio;

import java.time.LocalDate;
import java.util.List;

public class Album extends Publicacion {
	
	public Album(String titulo, LocalDate fecha, String descripcion,
			int meGustas, List<String> hashtags) {
		super(titulo, fecha, descripcion, meGustas, hashtags);
	}
	

}
