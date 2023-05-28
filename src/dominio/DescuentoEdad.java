package dominio;

import java.time.LocalDate;
import java.time.Period;

public class DescuentoEdad extends Descuento {

	public boolean aplicarDescuento(Usuario usuario) {
		LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(usuario.getFechaNacimiento(), fechaActual);
        int edad = periodo.getYears();
        
        if (( edad >= 18 && edad <= 30) || (edad >= 65)) {
        	return true;
        }
        return false;
	}
}
