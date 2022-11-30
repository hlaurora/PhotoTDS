package dao;

import java.util.List;

import dominio.Usuario;

public interface IAdaptadorUsuarioDAO {
	public void registrarUsuario(Usuario usuario);
	public void borrarUsuario(Usuario usuario);
	public void borrarTodosUsuario();
	public void modificarUsuario(Usuario usuario);
	public Usuario recuperarUsuario(int id);
	public List<Usuario> recuperarTodosUsuarios();
	//public void cambiarFotoPerfil(Usuario usuario, File fotoPerfil);
}
