package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Comentario;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorComentarioTDS implements IAdaptadorComentarioDAO{

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorComentarioTDS unicaInstancia = null;


	public static AdaptadorComentarioTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorComentarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorComentarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/**
	 * Registra un comentario
	 */
	public void registrarComentario(Comentario comentario) {
		Entidad eComentario = null;

		//Si ya está registrado no lo registramos
		eComentario = servPersistencia.recuperarEntidad(comentario.getId());
		if (eComentario != null) return;

		//Registramos primero el usuario
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(comentario.getUsuario());

		eComentario = new Entidad();

		Propiedad usuario = new Propiedad("usuario", String.valueOf(comentario.getUsuario().getId()));
		Propiedad texto = new Propiedad("texto", comentario.getTexto());
		Propiedad fecha = new Propiedad("fecha", comentario.getFecha().toString());

		eComentario.setNombre("comentario");

		// Añadimos sus propiedades
		eComentario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(usuario, texto, fecha)));

		// Registrar la entidad Comentario
		eComentario = servPersistencia.registrarEntidad(eComentario);
		// Asignar identificador único(el que genera el servicio de persistencia)
		comentario.setId(eComentario.getId());
	}

	/**
	 * Borra un comentario
	 */
	public void borrarComentario(Comentario comentario) {
		Entidad eComentario = servPersistencia.recuperarEntidad(comentario.getId());
		servPersistencia.borrarEntidad(eComentario);
	}

	/**
	 * Borra todos los comentarios
	 */
	public void borrarTodosComentarios() {
		ArrayList<Entidad> eComentarios = servPersistencia.recuperarEntidades("comentario");
		for (Entidad ec : eComentarios)
			servPersistencia.borrarEntidad(ec);
	}

	/**
	 * Modifica un comentario
	 */
	public void modificarComentario(Comentario comentario) {
		Entidad eComentario = servPersistencia.recuperarEntidad(comentario.getId());

		for (Propiedad p : eComentario.getPropiedades()) {
			if (p.getNombre().equals("id")) {
				p.setValor(String.valueOf(comentario.getId()));
			} else if (p.getNombre().equals("usuario")){
				p.setValor(String.valueOf(comentario.getUsuario().getId()));
			}  else if(p.getNombre().equals("texto")) {
				p.setValor(comentario.getTexto());
			} else if(p.getNombre().equals("fecha")) {
				p.setValor(comentario.getFecha().toString());
			} 
			servPersistencia.modificarPropiedad(p);	
		}
	}

	/**
	 * Recupera un comentario con el id dado
	 */
	public Comentario recuperarComentario(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if(PoolDAO.getUnicaInstancia().contiene(id))
			return (Comentario)PoolDAO.getUnicaInstancia().getObjeto(id);
		// Si no, la recupera de la bd
		Entidad eComentario = servPersistencia.recuperarEntidad(id);

		String texto;
		LocalDateTime fecha;
		// Recuperar propiedades
		texto = servPersistencia.recuperarPropiedadEntidad(eComentario, "texto");
		fecha = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eComentario, "fecha"));

		Comentario comentario = new Comentario(texto, fecha);
		comentario.setId(id);

		// Añadirlo al pool
		PoolDAO.getUnicaInstancia().addObjeto(id, comentario);

		// Recuperar usuario llamando al adaptador
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int idUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eComentario, "usuario"));

		Usuario usuario = adaptadorUsuario.recuperarUsuario(idUsuario);
		comentario.setUsuario(usuario);

		return comentario;
	}

	/**
	 * Recupera todos los comentarios
	 */
	public List<Comentario> recuperarTodosComentarios() {
		List<Entidad> eComentarios = servPersistencia.recuperarEntidades("comentario");
		List<Comentario> comentarios = new LinkedList<Comentario>();

		for (Entidad ec : eComentarios) {
			comentarios.add(recuperarComentario(ec.getId()));
		}
		return comentarios;
	}
}