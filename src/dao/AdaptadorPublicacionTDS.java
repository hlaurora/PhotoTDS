package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Album;
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
	
	/*
	public void registrarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = null;
		
		//Si ya está registrada no la registramos
		ePublicacion = servPersistencia.recuperarEntidad(publicacion.getId());
		if (ePublicacion != null) return;
		
		//Crear entidad Publicacion
		ePublicacion = new Entidad();
		ePublicacion.setNombre("publicacion");
		
		Propiedad titulo = new Propiedad("titulo", publicacion.getTitulo());
		Propiedad fecha = new Propiedad("fecha", publicacion.getFecha().toString());
		Propiedad descripcion = new Propiedad("descripcion", publicacion.getDescripcion());
		Propiedad meGustas = new Propiedad("meGustas", String.valueOf(publicacion.getMeGustas()));
		Propiedad hashtags = new Propiedad("hashtags", publicacion.getHashtags().toString());

		ePublicacion.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(titulo, fecha, descripcion, meGustas, hashtags)));
		
		//registrar la entidad publicacion
		ePublicacion = servPersistencia.registrarEntidad(ePublicacion);
		//asignar identificador único(el que genera el servicio de persistencia)
		publicacion.setId(ePublicacion.getId());		
	}*/
	
	//Registramos la publicación (dif si es foto o álbum)
	public void registrarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = null;
		
		//Si ya está registrada no la registramos
		ePublicacion = servPersistencia.recuperarEntidad(publicacion.getId());
		if (ePublicacion != null) return;
		
		//Registramos el usuario
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(publicacion.getUsuario());
		
		//Crear entidad Publicacion
		ePublicacion = new Entidad();
		//ePublicacion.setNombre("publicacion");
		
		Propiedad titulo = new Propiedad("titulo", publicacion.getTitulo());
		Propiedad usuario = new Propiedad("usuario", String.valueOf(publicacion.getUsuario().getId()));
		Propiedad fecha = new Propiedad("fecha", publicacion.getFecha().toString());
		Propiedad descripcion = new Propiedad("descripcion", publicacion.getDescripcion());
		Propiedad hashtags = new Propiedad("hashtags", publicacion.getHashtags().toString());
		Propiedad meGustas = new Propiedad("meGustas", String.valueOf(publicacion.getMeGustas()));

		if (publicacion.getClass().equals(Foto.class)) {
			Propiedad ruta = new Propiedad("ruta", ((Foto)publicacion).getRuta());
			ePublicacion.setNombre("foto");
			
			ePublicacion.setPropiedades(new ArrayList<Propiedad>(
					Arrays.asList(titulo, fecha, descripcion, hashtags, usuario, meGustas, ruta)));
		}
		
		//Si es un álbum
		
		else {
			ePublicacion.setNombre("album");
			ePublicacion.setPropiedades(new ArrayList<Propiedad>(
					Arrays.asList(titulo, fecha, descripcion, hashtags, usuario, meGustas)));
		}

		
		//registrar la entidad publicacion
		ePublicacion = servPersistencia.registrarEntidad(ePublicacion);
		//asignar identificador único(el que genera el servicio de persistencia)
		publicacion.setId(ePublicacion.getId());		
	}
	
	
	public void borrarPublicacion(Publicacion publicacion) {
		Entidad ePublicacion = servPersistencia.recuperarEntidad(publicacion.getId());
		servPersistencia.borrarEntidad(ePublicacion);
	}
	
	
	public void borrarTodasPublicaciones() {
		ArrayList<Entidad> ePublicaciones = servPersistencia.recuperarEntidades("foto");
		for (Entidad eu : ePublicaciones) 
			servPersistencia.borrarEntidad(eu);
	}

	
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
				p.setValor(publicacion.getHashtags().toString());
			} else if(p.getNombre().equals("meGustas")) {
				p.setValor(String.valueOf(publicacion.getMeGustas()));
			} else if (p.getNombre().equals("ruta")) {
				p.setValor( ((Foto)publicacion).getRuta());
			}
			servPersistencia.modificarPropiedad(p);
		}
	}
	
	//Recuperamos foto o álbum
	public Publicacion recuperarPublicacion(int id) {
		
		//Si la entidad está en el pool la devuelve directamente
		if(PoolDAO.getUnicaInstancia().contiene(id))
			return (Publicacion) PoolDAO.getUnicaInstancia().getObjeto(id);
		
		//Si no, la recupera de la bd
		Entidad ePublicacion = servPersistencia.recuperarEntidad(id);		
		
		String titulo;
		LocalDate fecha;
		String descripcion;
		String cadenaHashtags;
		int meGustas;
		List<String> hashtags;
		
		//Recuperar propiedades
		titulo = servPersistencia.recuperarPropiedadEntidad(ePublicacion, "titulo");
		fecha = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(ePublicacion, "fecha"));		
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
			publicacion = new Album(titulo, fecha, descripcion, hashtags);
		}
		
		publicacion.setId(id);
		publicacion.setMeGustas(meGustas);

		
		//añadirlo al pool
		PoolDAO.getUnicaInstancia().addObjeto(id, publicacion);
		
		//recuperar usuario llamando al adaptador
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int idUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(ePublicacion, "usuario"));
		
		Usuario usuario = adaptadorUsuario.recuperarUsuario(idUsuario);
		publicacion.setUsuario(usuario);
		
		return publicacion;
	}
	
	
	public List<Publicacion> recuperarTodasPublicaciones(){
		List<Entidad> ePublicaciones = servPersistencia.recuperarEntidades("foto");
		List<Publicacion> publicaciones = new LinkedList<Publicacion>();
		
		for (Entidad ePublicacion : ePublicaciones) {
			publicaciones.add(recuperarPublicacion(ePublicacion.getId()));
		}
		
		return publicaciones;
	}


}
