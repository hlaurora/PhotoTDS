package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO{

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	
	public static AdaptadorUsuarioTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}
	
	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;
		
		//Si ya está registrado no lo registramos
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		if (eUsuario != null) return;
		
		//Crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		
		Propiedad nombre = new Propiedad("nombre", usuario.getNombre());
		Propiedad email = new Propiedad("email", usuario.getEmail());
		Propiedad nombreCompleto = new Propiedad("nombreCompleto", usuario.getNombreCompleto());
		Propiedad fechaNaci = new Propiedad("fechaNacimiento", usuario.getFechaNacimiento().toString());

		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(nombre, email, nombreCompleto, fechaNaci)));
		
		//registrar la entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		//asignar identificador único(el que genera el servicio de persistencia)
		usuario.setId(eUsuario.getId());		
	}
	
	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.borrarEntidad(eUsuario);
	}
	
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		for (Propiedad p: eUsuario.getPropiedades()) {
			if (p.getNombre().equals("id")) {
				p.setValor(String.valueOf(usuario.getId()));
			} else if(p.getNombre().equals("nombre")) {
				p.setValor(usuario.getNombre());
			} else if(p.getNombre().equals("email")) {
				p.setValor(usuario.getEmail());
			} else if(p.getNombre().equals("nombreCompleto")) {
				p.setValor(usuario.getNombreCompleto());
			}
			servPersistencia.modificarPropiedad(p);
		}
	}
	
	public Usuario recuperarUsuario(int id) {
		
		//Si la entidad está en el pool la devuelve directamente
		if(PoolDAO.getUnicaInstancia().contiene(id))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(id);
		
		//Si no, la recupera de la bd
		Entidad eUsuario;
		String nombre;
		String email;
		String nombreCompleto;
		Date fechaNaci;
		
		eUsuario = servPersistencia.recuperarEntidad(id);
		
		//Recuperar propiedades
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		nombreCompleto = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombreCompleto");
		fechaNaci = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNaci");
		
		Usuario usuario = new Usuario(nombre, email, nombreCompleto, fechaNaci);
		
		
		return usuario;
	}
	
	public List<Usuario> recuperarTodosUsuarios(){
		
		return usuarios;
	}
}
