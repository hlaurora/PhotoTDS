package dao;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import dominio.Foto;
import dominio.Publicacion;
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
		
		//Registramos primero las fotos
		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		for (Foto foto : usuario.getFotos()) {
			adaptadorPublicacion.registrarPublicacion(foto);
		}
		
		//Crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		
		Propiedad email = new Propiedad("email", usuario.getEmail());
		Propiedad nombre = new Propiedad("nombre", usuario.getNombre());
		Propiedad apellidos = new Propiedad("apellidos", usuario.getApellidos());		
		Propiedad nombreUsuario = new Propiedad("nombreUsuario", usuario.getNombreUsuario());
		Propiedad password = new Propiedad("password", usuario.getPassword());
		Propiedad fechaNaci = new Propiedad("fechaNacimiento", usuario.getFechaNacimiento().toString());
		Propiedad fotoPerfil = new Propiedad("fotoPerfil", usuario.getFotoPerfil().toString());
		Propiedad textoPresentacion = new Propiedad("textoPresentacion", usuario.getTextoPresentacion());
		Propiedad fotos = new Propiedad("fotos", obtenerIdFotos(usuario.getFotos()));
		
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(email, nombre, apellidos, nombreUsuario, password, 
						fechaNaci, fotoPerfil, textoPresentacion, fotos)));
		
		//registrar la entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		//asignar identificador único(el que genera el servicio de persistencia)
		usuario.setId(eUsuario.getId());		
	}
	
	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.borrarEntidad(eUsuario);
	}
	
	public void borrarTodosUsuario() {
		ArrayList<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		for (Entidad eu : eUsuarios) 
			servPersistencia.borrarEntidad(eu);
	}
	
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		for (Propiedad p: eUsuario.getPropiedades()) {
			if (p.getNombre().equals("id")) {
				p.setValor(String.valueOf(usuario.getId()));
			} else if(p.getNombre().equals("email")) {
				p.setValor(usuario.getEmail());
			} else if(p.getNombre().equals("nombre")) {
				p.setValor(usuario.getNombre());
			} else if(p.getNombre().equals("apellidos")) {
				p.setValor(usuario.getApellidos());
			} else if(p.getNombre().equals("nombreUsuario")) {
				p.setValor(usuario.getNombreUsuario());
			} else if(p.getNombre().equals("password")) {
				p.setValor(usuario.getPassword());
			} else if(p.getNombre().equals("fechaNacimiento")) {
				p.setValor(usuario.getFechaNacimiento().toString());
			} else if(p.getNombre().equals("fotoPerfil")) {
				p.setValor(usuario.getFotoPerfil().toString());
			} else if(p.getNombre().equals("textoPresentacion")) {
				p.setValor(usuario.getTextoPresentacion());
			} else if(p.getNombre().equals("fotos")) {
				p.setValor(obtenerIdFotos(usuario.getFotos()));
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
		String email;
		String nombre;
		String apellidos;
		String nombreUsuario;
		String password;
		LocalDate fechaNaci;
		File fotoPerfil;
		String textoPresentacion;
		List<Foto> fotos = new LinkedList<Foto>();
		
		eUsuario = servPersistencia.recuperarEntidad(id);
		
		//Recuperar propiedades
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "apellidos");
		nombreUsuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombreUsuario");
		password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		fechaNaci = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		fotoPerfil = new File(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fotoPerfil"));
		textoPresentacion = servPersistencia.recuperarPropiedadEntidad(eUsuario, "textoPresentacion");
		Usuario usuario = new Usuario(email, nombre, apellidos, nombreUsuario, password, fechaNaci);
		usuario.setFotoPerfil(fotoPerfil);
		usuario.setTextoPresentacion(textoPresentacion);
		usuario.setId(id);
		
		//añadirlo al pool
		PoolDAO.getUnicaInstancia().addObjeto(id, usuario);

		fotos = obtenerFotosDesdeId(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fotos"));
		
		for (Foto f : fotos) {
			usuario.addFoto(f);
		}
		
		return usuario;
	}
	
	public List<Usuario> recuperarTodosUsuarios(){
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();
		
		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		
		//System.out.println(usuarios);
		return usuarios;
	}
	/*
	public void cambiarFotoPerfil(Usuario u, File fotoPerfil) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(u.getId());

		for (Propiedad p: eUsuario.getPropiedades()) {
			if (p.getNombre().equals("id")) {
		Propiedad p.setValor(String.valueOf(usuario.getId()));
		servPersistencia.modificarPropiedad(p);
	}*/
	
	private String obtenerIdFotos(List<Foto> listaFotos) {
		String aux = "";
		for(Foto f : listaFotos) {
			aux += f.getId() + " ";
		}
		return aux.trim();
	}
	
	private List<Foto> obtenerFotosDesdeId(String fotos){
		Publicacion f;
		List<Foto> listaFotos = new LinkedList<Foto>();
		StringTokenizer strTok = new StringTokenizer(fotos, " ");
		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		while(strTok.hasMoreTokens()) {
			f = adaptadorPublicacion.recuperarPublicacion(Integer.valueOf((String) strTok.nextElement()));
			listaFotos.add((Foto)f);
		}
		return listaFotos;
	}
}
