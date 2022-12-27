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
	
	
	// Devuelve todas las fotos
	public List<Publicacion> getPublicaciones(){
		List<Publicacion> publicaciones = new LinkedList<Publicacion>();
		for (Publicacion f : fotosPorId.values()) {
			publicaciones.add(f);
		}
		for (Publicacion a : albumesPorId.values()) {
			publicaciones.add(a);
		}
		return publicaciones;
		//return new LinkedList<Publicacion>(fotosPorId.values());
	}
	
	// Devuelve una foto	
	public Publicacion getPublicacion(int id) {
		if (fotosPorId.containsKey(id))
			return fotosPorId.get(id);	
		else return albumesPorId.get(id);
	}

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
		for (Publicacion p: publicacionesBD) {
			if (p.getClass().equals(Foto.class))
				fotosPorId.put(p.getId(), p);
			else albumesPorId.put(p.getId(), p);
		}			
	}

}
