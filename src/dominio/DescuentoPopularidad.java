package dominio;

public class DescuentoPopularidad extends Descuento {
	
	private int numFotos = 10;
	private int numMeGusta = 50;
	
	public boolean aplicarDescuento(Usuario usuario) {
	    long fotosSuperanMeGusta = usuario.getFotos().stream()
	            .filter(foto -> foto.getMeGustas() >= numMeGusta)
	            .count();
	    
	    return fotosSuperanMeGusta >= numFotos;
	}

}
