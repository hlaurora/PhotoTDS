package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Notificacion;
import dominio.Publicacion;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorNotificacionTDS implements IAdaptadorNotificacionDAO{

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorNotificacionTDS unicaInstancia = null;


	public static AdaptadorNotificacionTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorNotificacionTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorNotificacionTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/**
	 * Registra una Notificacion
	 */
	@Override
	public void registrarNotificacion(Notificacion notificacion) {
		Entidad eNotificacion = null;

		//Si ya está registrada no la registramos
		eNotificacion = servPersistencia.recuperarEntidad(notificacion.getId());
		if (eNotificacion != null) return;

		//Registramos primero la publicacion
		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		adaptadorPublicacion.registrarPublicacion(notificacion.getPublicacion());

		eNotificacion = new Entidad();

		Propiedad publicacion = new Propiedad("publicacion", String.valueOf(notificacion.getPublicacion().getId()));
		Propiedad fecha = new Propiedad("fecha", notificacion.getFecha().toString());

		eNotificacion.setNombre("notificacion");

		eNotificacion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(publicacion, fecha)));

		//Registrar la entidad Notificacion
		eNotificacion = servPersistencia.registrarEntidad(eNotificacion);
		//Asignar identificador único(el que genera el servicio de persistencia)
		notificacion.setId(eNotificacion.getId());

	}

	/**
	 * Borra una notificacion
	 */
	@Override
	public void borrarNotificacion(Notificacion notificacion) {
		Entidad eNotificacion = servPersistencia.recuperarEntidad(notificacion.getId());
		servPersistencia.borrarEntidad(eNotificacion);		
	}

	/**
	 * Borra todas las notificaciones
	 */
	@Override
	public void borrarTodasNotificaciones() {
		ArrayList<Entidad> eNotificaciones = servPersistencia.recuperarEntidades("notificacion");
		for (Entidad en : eNotificaciones)
			servPersistencia.borrarEntidad(en);		
	}

	/**
	 * Modifica una notificacion
	 */
	@Override
	public void modificarNotificacion(Notificacion notificacion) {
		Entidad eNotificacion = servPersistencia.recuperarEntidad(notificacion.getId());

		for (Propiedad p : eNotificacion.getPropiedades()) {
			if (p.getNombre().equals("id")) {
				p.setValor(String.valueOf(notificacion.getId()));
			} else if (p.getNombre().equals("publicacion")){
				p.setValor(String.valueOf(notificacion.getPublicacion().getId()));
			} else if(p.getNombre().equals("fecha")) {
				p.setValor(notificacion.getFecha().toString());
			} 
			servPersistencia.modificarPropiedad(p);	
		}		
	}

	/**
	 * Recupera una notificacion
	 */
	@Override
	public Notificacion recuperarNotificacion(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(id))
			return (Notificacion)PoolDAO.getUnicaInstancia().getObjeto(id);
		// Si no, la recupera de la bd
		Entidad eNotificacion = servPersistencia.recuperarEntidad(id);

		LocalDateTime fecha;
		fecha = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eNotificacion, "fecha"));

		Notificacion notificacion = new Notificacion(fecha);
		notificacion.setId(id);
		// Añadirlo al pool
		PoolDAO.getUnicaInstancia().addObjeto(id, notificacion);

		AdaptadorPublicacionTDS adaptadorPublicacion = AdaptadorPublicacionTDS.getUnicaInstancia();
		int idPublicacion = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eNotificacion, "publicacion"));

		Publicacion publicacion = adaptadorPublicacion.recuperarPublicacion(idPublicacion);
		notificacion.setPublicacion(publicacion);

		return notificacion;
	}

	/**
	 * Recupera todas las notificaciones
	 */
	@Override
	public List<Notificacion> recuperarTodasNotificaciones() {
		List<Entidad> eNotificaciones = servPersistencia.recuperarEntidades("notificacion");
		List<Notificacion> notificaciones = new LinkedList<Notificacion>();

		for (Entidad en : eNotificaciones) {
			notificaciones.add(recuperarNotificacion(en.getId()));
		}
		return notificaciones;
	}

}
