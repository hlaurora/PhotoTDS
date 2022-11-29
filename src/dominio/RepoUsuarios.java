package dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorUsuarioDAO;

public class RepoUsuarios {

	private Map<String, Usuario> usuarios;
	private static RepoUsuarios unicainstancia = new RepoUsuarios();
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private RepoUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			usuarios = new HashMap<String, Usuario>();
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
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario u:usuarios.values())
			lista.add(u);
		return lista;	
	}
	
	// Devuelve un usuario
	public Usuario getUsuario(int id) {
		for (Usuario u : usuarios.values()) {
			if (u.getId()==id) return u;
		}
		return null;
	}
	
	public Usuario getUsuario(String nombre) {
		return usuarios.get(nombre);
	}
	
	// Añade usuario al repositorio (por nombre de usuario)
	public void addUsuario(Usuario usuario){
		usuarios.put(usuario.getNombre(), usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		usuarios.remove(usuario.getNombre());
	}
	
	private void cargarRepositorio() throws DAOException{
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario u: usuariosBD)
			usuarios.put(u.getNombre(), u);
	}
}
