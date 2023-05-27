package dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Foto extends Publicacion {
	
	private String ruta;

	public Foto(String titulo,  LocalDateTime fecha, String descripcion, 
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
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    if (!super.equals(obj)) {
	        return false;
	    }
	    Foto other = (Foto) obj;
	    return Objects.equals(ruta, other.ruta);
	}
}
