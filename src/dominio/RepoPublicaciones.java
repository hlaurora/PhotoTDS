package dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorPublicacionDAO;

public class RepoPublicaciones {
	
	private Map<Integer, Publicacion> fotosPorId;
	private Map<Integer, Publicacion> albumesPorId;
	private static RepoPublicaciones unicainstancia = new RepoPublicaciones();
	private FactoriaDAO dao;
	private IAdaptadorPublicacionDAO adaptadorPublicacion;

	private RepoPublicaciones() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorPublicacion = dao.getPublicacionDAO();
			fotosPorId = new HashMap<Integer, Publicacion>();
			albumesPorId = new HashMap<Integer, Publicacion>();
			this.cargarRepositorio();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public static RepoPublicaciones getUnicaInstancia() {
		return unicainstancia;
	}
	
	/*
	// Devuelve todas las publicaciones
	public List<Publicacion> getPublicaciones(){
		return new LinkedList<Publicacion>(publicacionesPorId.values());
	}
	
	// Devuelve una publicacion	
	public Publicacion getPublicacion(int id) {
		return publicacionesPorId.get(id);	
	}*/

	// Añade publicacion al repositorio (por id)
	public void addPublicacion(Publicacion publicacion){
		if (publicacion.getClass().equals(Foto.class)) {
			fotosPorId.put(publicacion.getId(), publicacion);
		}
		else 
			albumesPorId.put(publicacion.getId(), publicacion);
	}
	
	public void removePublicacion(Publicacion publicacion) {
		if (publicacion.getClass().equals(Foto.class)) {
			fotosPorId.remove(publicacion.getId());
		}
		else 
			albumesPorId.remove(publicacion.getId(), publicacion);
		publicacionesPorId.remove(publicacion.getId());
	}
	
	/*
	public void removeTodosUsuarios() {
		//usuarios.remove(usuario.getId());
		for (Usuario u : usuariosPorNombreUs.values()) {
			usuariosPorNombreUs.remove(u);
		}
	}*/
	
	private void cargarRepositorio() throws DAOException{
		List<Publicacion> publicacionesBD = adaptadorPublicacion.recuperarTodasPublicaciones();
		for (Publicacion publicacion: publicacionesBD) {
			fotosPorId.put(publicacion.getId(), publicacion);
			
		}
			
	}

}
