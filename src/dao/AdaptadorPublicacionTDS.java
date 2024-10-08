package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import dominio.Album;
import dominio.Comentario;
import dominio.Foto;
import dominio.Publicacion;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorPublicacionTDS implements IAdaptadorPublicacionDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorPublicacionTDS unicaInstancia = null;

	public static AdaptadorPublicacionTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorPublicacionTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorPublicacionTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}


	/**
	 * Registra una Publicacion
	 */
	public void registrarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = null;

		//Si ya está registrada no la registramos
		ePublicacion = servPersistencia.recuperarEntidad(publicacion.getId());
		if (ePublicacion != null) return;

		//Si es un álbum, registramos primero las fotos
		if (publicacion.getClass().equals(Album.class)) {
			for (Foto f : ((Album)publicacion).getFotos()) {
				this.registrarPublicacion(f);
			}
		}

		//Registramos el usuario
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(publicacion.getUsuario());

		//Crear entidad Publicacion
		ePublicacion = new Entidad();

		Propiedad titulo = new Propiedad("titulo", publicacion.getTitulo());
		Propiedad usuario = new Propiedad("usuario", String.valueOf(publicacion.getUsuario().getId()));
		Propiedad fecha = new Propiedad("fecha", publicacion.getFecha().toString());
		Propiedad descripcion = new Propiedad("descripcion", publicacion.getDescripcion());
		Propiedad hashtags = new Propiedad("hashtags", this.obtenerCasdenaHashtags(publicacion.getHashtags()));
		Propiedad meGustas = new Propiedad("meGustas", String.valueOf(publicacion.getMeGustas()));
		Propiedad comentarios = new Propiedad("comentarios", this.obtenerIdComentarios(publicacion.getComentarios()));

		if (publicacion.getClass().equals(Foto.class)) {
			Propiedad ruta = new Propiedad("ruta", ((Foto)publicacion).getRuta());
			ePublicacion.setNombre("foto");

			ePublicacion.setPropiedades(new ArrayList<Propiedad>(
					Arrays.asList(titulo, fecha, descripcion, hashtags, usuario, meGustas, comentarios, ruta)));
		}

		//Si es un álbum
		else {
			Propiedad fotos = new Propiedad("fotos", obtenerIdFotos(((Album)publicacion).getFotos()));
			ePublicacion.setNombre("album");
			ePublicacion.setPropiedades(new ArrayList<Propiedad>(
					Arrays.asList(titulo, fecha, descripcion, hashtags, usuario, meGustas, comentarios, fotos)));
		}

		//registrar la entidad publicacion
		ePublicacion = servPersistencia.registrarEntidad(ePublicacion);
		//asignar identificador único(el que genera el servicio de persistencia)
		publicacion.setId(ePublicacion.getId());		
	}

	/**
	 * Borra una Publicacion
	 */
	public void borrarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = servPersistencia.recuperarEntidad(publicacion.getId());
		servPersistencia.borrarEntidad(ePublicacion);
	}

	/**
	 * Borra todas las Publicaciones
	 */
	public void borrarTodasPublicaciones() {
		ArrayList<Entidad> eFotos = servPersistencia.recuperarEntidades("foto");
		ArrayList<Entidad> eAlbumes = servPersistencia.recuperarEntidades("album");
		for (Entidad eu : eFotos) 
			servPersistencia.borrarEntidad(eu);
		for (Entidad eu : eAlbumes) 
			servPersistencia.borrarEntidad(eu);
	}

	/**
	 * Modifica una Publicacion
	 */
	public void modificarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = servPersistencia.recuperarEntidad(publicacion.getId());

		for (Propiedad p: ePublicacion.getPropiedades()) {
			if (p.getNombre().equals("id")) {
				p.setValor(String.valueOf(publicacion.getId()));
			} else if (p.getNombre().equals("usuario")){
				p.setValor(String.valueOf(publicacion.getUsuario().getId()));
			} else if(p.getNombre().equals("titulo")) {
				p.setValor(publicacion.getTitulo());
			} else if(p.getNombre().equals("fecha")) {
				p.setValor(publicacion.getFecha().toString());
			} else if(p.getNombre().equals("descripcion")) {
				p.setValor(publicacion.getDescripcion());
			} else if(p.getNombre().equals("hashtags")) {
				p.setValor(this.obtenerCasdenaHashtags(publicacion.getHashtags()));
			} else if(p.getNombre().equals("meGustas")) {
				p.setValor(String.valueOf(publicacion.getMeGustas()));
			} else if(p.getNombre().equals("comentarios")) {
				p.setValor(this.obtenerIdComentarios(publicacion.getComentarios()));
			} else if (p.getNombre().equals("ruta")) {
				p.setValor(((Foto)publicacion).getRuta());
			} else if (p.getNombre().equals("fotos")) {
				p.setValor(obtenerIdFotos(((Album)publicacion).getFotos()));
			}
			servPersistencia.modificarPropiedad(p);
		}
	}

	/**
	 * Recupera una Publicacion
	 */
	public Publicacion recuperarPublicacion(int id) {	
		//Si la entidad está en el pool la devuelve directamente
		if(PoolDAO.getUnicaInstancia().contiene(id))
			return (Publicacion) PoolDAO.getUnicaInstancia().getObjeto(id);

		//Si no, la recupera de la bd
		Entidad ePublicacion = servPersistencia.recuperarEntidad(id);		

		String titulo;
		LocalDateTime fecha;
		String descripcion;
		String cadenaHashtags;
		int meGustas;
		List<String> hashtags;

		//Recuperar propiedades
		titulo = servPersistencia.recuperarPropiedadEntidad(ePublicacion, "titulo");
		fecha = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(ePublicacion, "fecha"));		
		descripcion = servPersistencia.recuperarPropiedadEntidad(ePublicacion, "descripcion");
		cadenaHashtags = servPersistencia.recuperarPropiedadEntidad(ePublicacion, "hashtags");
		hashtags = new ArrayList<String>(Arrays.asList(cadenaHashtags.split(" ")));
		meGustas = (Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(ePublicacion, "meGustas")));

		Publicacion publicacion;

		if(ePublicacion.getNombre().equals("foto")) {
			String ruta = servPersistencia.recuperarPropiedadEntidad(ePublicacion, "ruta");
			publicacion = new Foto(titulo, fecha, descripcion, hashtags, ruta);
		}

		else {
			List<Foto> fotos = obtenerFotosDesdeId(servPersistencia.recuperarPropiedadEntidad(ePublicacion, "fotos"));
			publicacion = new Album(titulo, fecha, descripcion, hashtags);
			for (Foto f : fotos) {
				((Album)publicacion).addFoto(f);
			}			
		}
		publicacion.setId(id);
		publicacion.setMeGustas(meGustas);

		//añadirlo al pool
		PoolDAO.getUnicaInstancia().addObjeto(id, publicacion);

		//recuperar los comentarios
		List<Comentario> comentarios = this.obtenerComentariosDesdeId(servPersistencia.recuperarPropiedadEntidad(ePublicacion, "comentarios"));
		for(Comentario c : comentarios) {
			publicacion.addComentario(c);
		}

		//recuperar usuario llamando al adaptador
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int idUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(ePublicacion, "usuario"));

		Usuario usuario = adaptadorUsuario.recuperarUsuario(idUsuario);
		publicacion.setUsuario(usuario);

		return publicacion;
	}

	/**
	 * Recupera todas las Publicaciones
	 */
	public List<Publicacion> recuperarTodasPublicaciones(){
		List<Entidad> eFotos = servPersistencia.recuperarEntidades("foto");
		List<Entidad> eAlbumes = servPersistencia.recuperarEntidades("album");
		List<Publicacion> publicaciones = new LinkedList<Publicacion>();

		for (Entidad ePublicacion : eFotos) {
			publicaciones.add(recuperarPublicacion(ePublicacion.getId()));
		}

		for (Entidad ePublicacion : eAlbumes) {
			publicaciones.add(recuperarPublicacion(ePublicacion.getId()));
		}

		return publicaciones;
	}

	/**
	 * Obtiene los id de una lista de Fotos
	 */
	private String obtenerIdFotos(List<Foto> listaFotos) {
		String aux = "";
		for(Foto f : listaFotos) {
			aux += f.getId() + " ";
		}
		return aux.trim();
	}

	/**
	 * Obtiene una lista de Fotos desde la cadena con los id
	 */
	private List<Foto> obtenerFotosDesdeId(String fotos) {
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

	/**
	 * Obtiene una cadena con todos los hashtags de la lista
	 */
	private String obtenerCasdenaHashtags(List<String> hashtags) {
		String cadenaHashtags="";

		for (String h :  hashtags) {
			cadenaHashtags += h + " ";
		}

		return cadenaHashtags;
	}

	/**
	 * Obtiene la cadena con los id de los comentarios de la lista
	 */
	private String obtenerIdComentarios(List<Comentario> listaComentarios) {
		String aux = "";
		for(Comentario c : listaComentarios) {
			aux += c.getId() + " ";
		}
		return aux.trim();
	}

	/**
	 * Obtiene los comentarios desde la lista de ids
	 */
	private List<Comentario> obtenerComentariosDesdeId(String comentarios) {
		Comentario c;
		List<Comentario> listaComentarios = new LinkedList<Comentario>();	
		StringTokenizer strTok = new StringTokenizer(comentarios, " ");
		AdaptadorComentarioTDS adaptadorComentario = AdaptadorComentarioTDS.getUnicaInstancia();

		while(strTok.hasMoreTokens()) {
			c = adaptadorComentario.recuperarComentario(Integer.valueOf((String) strTok.nextElement()));
			listaComentarios.add(c);
		}
		return listaComentarios;
	}

}
