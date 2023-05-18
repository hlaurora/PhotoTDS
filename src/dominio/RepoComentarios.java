/*package dominio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorComentarioDAO;

public class RepoComentarios {
	
	private Map<Integer, Comentario> comentariosPorId;
	private static RepoComentarios unicainstancia = new RepoComentarios();
	private FactoriaDAO dao;
	private IAdaptadorComentarioDAO adaptadorComentario;
	
	private RepoComentarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorComentario = dao.getComentarioDAO();
			comentariosPorId = new HashMap<Integer, Comentario>();
			this.cargarRepositorio();
		}  catch (DAOException eDAO) {
			eDAO.printStackTrace();
		   }	
	}
	
	public static RepoComentarios getUnicaInstancia() {
		return unicainstancia;
	}
	
	// Devuelve todos los comentarios
	/*public List<Comentario> getComentarios() {
		return new LinkedList<Comentario>(comentariosPorId.values());
	}*/
	/*
	// Devuelve un comentario
	public Comentario getComentario(int id) {
		return comentariosPorId.get(id);
	}
	
	// Añade un comentario al repositorio
	public void addComentario(Comentario comentario) {
		comentariosPorId.put(comentario.getId(), comentario);
	}
	
	// Elimina un comentario del repositorio
	public void removeComentario(Comentario comentario) {
		comentariosPorId.remove(comentario.getId());
	}
	
	private void cargarRepositorio() throws DAOException {
		List<Comentario> comentariosBD = adaptadorComentario.recuperarTodosComentarios();
		for (Comentario c : comentariosBD) {
			comentariosPorId.put(c.getId(), c);
		}
	}
	
}*/
