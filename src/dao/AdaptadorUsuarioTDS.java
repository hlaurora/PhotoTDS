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
import dominio.Album;
import dominio.Foto;
import dominio.Notificacion;
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

	/**
	 * Registra un usuario
	 */
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

		//Registramos los albumes
		for (Album album : usuario.getAlbumes()) {
			adaptadorPublicacion.registrarPublicacion(album);
		}

		//Registramos los seguidores
		for (Usuario s : usuario.getSeguidores()) {
			this.registrarUsuario(s);
		}

		//Registramos los seguidos
		for (Usuario s : usuario.getSeguidos()) {
			this.registrarUsuario(s);
		}

		//Registramos las notificaciones
		AdaptadorNotificacionTDS adaptadorNotificacion = AdaptadorNotificacionTDS.getUnicaInstancia();
		for (Notificacion n : usuario.getNotificaciones()) {
			adaptadorNotificacion.registrarNotificacion(n);
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
		Propiedad premium = new Propiedad("premium", usuario.isPremium().toString());
		List<Publicacion> fotosUsuario = new LinkedList<Publicacion>();
		for (Foto f : usuario.getFotos()) {
			fotosUsuario.add(f);
		}
		Propiedad fotos = new Propiedad("fotos", obtenerIdPublicaciones(fotosUsuario));
		Propiedad seguidores = new Propiedad("seguidores", obtenerIdSeguidores(usuario.getSeguidores()));
		Propiedad seguidos = new Propiedad("seguidos", obtenerIdSeguidores(usuario.getSeguidos()));
		List<Publicacion> albumesUsuario = new LinkedList<Publicacion>();
		for (Album a : usuario.getAlbumes()) {
			albumesUsuario.add(a);
		}
		Propiedad albumes = new Propiedad("albumes", obtenerIdPublicaciones(albumesUsuario));
		List<Notificacion> notificacionesUsuario = new LinkedList<Notificacion>();
		for (Notificacion n : usuario.getNotificaciones()) {
			notificacionesUsuario.add(n);
		}
		Propiedad notificaciones = new Propiedad("notificaciones", 
				obtenerIdNotificaciones(notificacionesUsuario));


		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(email, nombre, apellidos, nombreUsuario, password, premium, 
						fechaNaci, fotoPerfil, textoPresentacion, fotos, seguidores, 
						seguidos, albumes, notificaciones)));

		//registrar la entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		//asignar identificador único(el que genera el servicio de persistencia)
		usuario.setId(eUsuario.getId());		
	}

	/**
	 * Borra un usuario
	 */
	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());	
		servPersistencia.borrarEntidad(eUsuario);
	}

	/**
	 * Borra todos los usuarios
	 */
	public void borrarTodosUsuario() {
		ArrayList<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		for (Entidad eu : eUsuarios) 
			servPersistencia.borrarEntidad(eu);
	}

	/**
	 * Modifica un usuario
	 */
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
			} else if(p.getNombre().equals("premium")) {
				p.setValor(usuario.isPremium().toString());
			} else if(p.getNombre().equals("fotos")) {
				List<Publicacion> publicaciones = new LinkedList<Publicacion>();
				for (Foto f : usuario.getFotos()) {
					publicaciones.add(f);
				}
				p.setValor(obtenerIdPublicaciones(publicaciones));
			} else if(p.getNombre().equals("seguidores")) {
				p.setValor(obtenerIdSeguidores(usuario.getSeguidores()));
			} else if(p.getNombre().equals("seguidos")) {
				p.setValor(obtenerIdSeguidores(usuario.getSeguidos()));
			} else if(p.getNombre().equals("albumes")) {
				List<Publicacion> publicaciones = new LinkedList<Publicacion>();
				for (Album a : usuario.getAlbumes()) {
					publicaciones.add(a);
				}
				p.setValor(obtenerIdPublicaciones(publicaciones));
			} else if(p.getNombre().equals("notificaciones")) {
				List<Notificacion> notificaciones = new LinkedList<Notificacion>();
				for (Notificacion n : usuario.getNotificaciones()) {
					notificaciones.add(n);
				}
				p.setValor(obtenerIdNotificaciones(notificaciones));
			}
			servPersistencia.modificarPropiedad(p);
		}
	}

	/**
	 * Recupera un usuario
	 */
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
		Boolean premium;
		List<Publicacion> fotos = new LinkedList<Publicacion>();
		List<Usuario> seguidores = new LinkedList<Usuario>();
		List<Usuario> seguidos = new LinkedList<Usuario>();
		List<Publicacion> albumes = new LinkedList<Publicacion>();
		List<Notificacion> notificaciones = new LinkedList<Notificacion>();

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
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));
		Usuario usuario = new Usuario(email, nombre, apellidos, nombreUsuario, password, fechaNaci);
		usuario.setPremium(premium);
		usuario.setFotoPerfil(fotoPerfil);
		usuario.setTextoPresentacion(textoPresentacion);
		usuario.setId(id);

		//añadirlo al pool
		PoolDAO.getUnicaInstancia().addObjeto(id, usuario);

		fotos = obtenerPublicacionesDesdeId(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fotos"));
		for (Publicacion f : fotos) {
			usuario.addFoto((Foto)f);
		}

		albumes = obtenerPublicacionesDesdeId(servPersistencia.recuperarPropiedadEntidad(eUsuario, "albumes"));
		for (Publicacion a : albumes) {
			usuario.addAlbum((Album)a);
		}

		seguidores = obtenerSeguidoresDesdeId(servPersistencia.recuperarPropiedadEntidad(eUsuario, "seguidores"));
		for (Usuario s : seguidores) {
			usuario.addSeguidor(s);
		}

		seguidos = obtenerSeguidoresDesdeId(servPersistencia.recuperarPropiedadEntidad(eUsuario, "seguidos"));
		for (Usuario s : seguidos) {
			usuario.addSeguido(s);
		}

		notificaciones = obtenerNotificacionsDesdeId(servPersistencia.recuperarPropiedadEntidad(
				eUsuario, "notificaciones"));
		for (Notificacion n : notificaciones) {
			usuario.addNotificacion(n);
		}

		return usuario;
	}

	/**
	 * Recupera todos los usuarios
	 */
	public List<Usuario> recuperarTodosUsuarios(){
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}


	/**
	 * Obtiene el id de la lista de fotos o de albumes pasada como parámetro
	 */
	private String obtenerIdPublicaciones(List<Publicacion> listaPublicaciones) {
		String aux = "";
		for(Publicacion p : listaPublicaciones) {
			aux += p.getId() + " ";
		}
		return aux.trim();
	}

	/**
	 * Obtiene una lista de publicaciones desde los id
	 */
	private List<Publicacion> obtenerPublicacionesDesdeId(String ids){
		Publicacion p;
		List<Publicacion> listaPublicaciones = new LinkedList<Publicacion>();
		StringTokenizer strTok = new StringTokenizer(ids, " ");
		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		while(strTok.hasMoreTokens()) {
			p = adaptadorPublicacion.recuperarPublicacion(Integer.valueOf((String) strTok.nextElement()));
			listaPublicaciones.add(p);
		}
		return listaPublicaciones;
	}

	/**
	 * Obtiene una cadena con la lista de seguidores del usuario
	 */
	private String obtenerIdSeguidores(List<Usuario> listaSeguidores) {
		String aux = "";
		for(Usuario s : listaSeguidores) {
			aux += s.getId() + " ";
		}
		return aux.trim();
	}

	/**
	 * Obtiene la lista de Usuarios seguidores desde la cadena seguidores
	 */
	private List<Usuario> obtenerSeguidoresDesdeId(String seguidores){
		Usuario s;
		List<Usuario> listaSeguidores = new LinkedList<Usuario>();
		StringTokenizer strTok = new StringTokenizer(seguidores, " ");
		while(strTok.hasMoreTokens()) {
			s = this.recuperarUsuario(Integer.valueOf((String) strTok.nextElement()));
			listaSeguidores.add(s);
		}
		return listaSeguidores;
	}

	/**
	 * Obtiene una cadena con los id de las notificaciones de la lista
	 */
	private String obtenerIdNotificaciones(List<Notificacion> listaNotificaciones) {
		String aux = "";
		for(Notificacion n : listaNotificaciones) {
			aux += n.getId() + " ";
		}
		return aux.trim();
	}

	/**
	 * Obtiene una lista de notificaciones desde la cadena
	 */
	private List<Notificacion> obtenerNotificacionsDesdeId(String notificaciones){
		Notificacion n;
		List<Notificacion> listaNotificaciones = new LinkedList<Notificacion>();
		StringTokenizer strTok = new StringTokenizer(notificaciones, " ");
		AdaptadorNotificacionTDS adaptadorNotificacion = AdaptadorNotificacionTDS.getUnicaInstancia();
		while(strTok.hasMoreTokens()) {
			n = adaptadorNotificacion.recuperarNotificacion(Integer.valueOf((String) strTok.nextElement()));
			listaNotificaciones.add(n);
		}
		return listaNotificaciones;
	}
}
