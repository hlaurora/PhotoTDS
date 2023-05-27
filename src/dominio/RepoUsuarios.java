package dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorUsuarioDAO;

public class RepoUsuarios {

	private Map<String, Usuario> usuariosPorNombreUs;
	private Map<String, Usuario> usuariosPorEmail;
	private static RepoUsuarios unicainstancia = new RepoUsuarios();
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

	private RepoUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			usuariosPorNombreUs = new HashMap<String, Usuario>();
			usuariosPorEmail = new HashMap<String, Usuario>();
			this.cargarRepositorio();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public static RepoUsuarios getUnicaInstancia() {
		return unicainstancia;
	}

	// Devuelve todos los usuarios
	public List<Usuario> getUsuarios(){
		return new LinkedList<Usuario>(usuariosPorNombreUs.values());
	}

	// Devuelve un usuario
	public Usuario getUsuario(String nombreUsuario) {
		return usuariosPorNombreUs.get(nombreUsuario);	
	}

	public Usuario getUsuarioEmail(String email) {
		return usuariosPorEmail.get(email);
	}

	// Añade usuario al repositorio (por nombre de usuario)
	public void addUsuario(Usuario usuario){
		usuariosPorNombreUs.put(usuario.getNombreUsuario(), usuario);
		usuariosPorEmail.put(usuario.getEmail(), usuario);
	}

	// Borra un usuario del repositorio
	public void removeUsuario(Usuario usuario) {
		usuariosPorNombreUs.remove(usuario.getNombreUsuario());
		usuariosPorEmail.remove(usuario.getEmail());
	}


	private void cargarRepositorio() throws DAOException{
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario u: usuariosBD) {
			usuariosPorNombreUs.put(u.getNombreUsuario(), u);
			usuariosPorEmail.put(u.getEmail(), u);
		}

	}
}
