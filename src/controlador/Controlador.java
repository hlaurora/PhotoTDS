package controlador;

import java.io.File;
import java.time.LocalDate;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorUsuarioDAO;
import dominio.RepoUsuarios;
import dominio.Usuario;

public class Controlador {
	
	private static Controlador unicaInstancia;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private RepoUsuarios repoUsuarios;
	
	private Controlador() {
		inicializarAdaptadores();
		inicializarRepositorios();
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	public boolean esUsuarioRegistrado(String nombreUsuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario) != null;
	}
	
	public boolean loginUsuario(String nombreUsuario, String password) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		if (u != null && u.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String email, String nombre, String apellidos,
			String nombreUsuario, String password, LocalDate fechaNaci) {
		
		if (esUsuarioRegistrado(nombreUsuario))
			return false;
		
		Usuario usuario = new Usuario(email, nombre, apellidos, nombreUsuario, password, fechaNaci);
		adaptadorUsuario.registrarUsuario(usuario);
		repoUsuarios.addUsuario(usuario);
		return true;
	}
	
	public void registrarFotoPerfil(String nombreUsuario, File fotoPerfil) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		u.setFotoPerfil(fotoPerfil);
		adaptadorUsuario.modificarUsuario(u);
	}
	
	/*
	public boolean borrarUsuario(Usuario u) {
		if (!esUsuarioRegistrado(u.getNombre()))
			return false;
		UsuarioDAO usuarioDAO = 
		
		return true;
	}*/
	
	
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		//adaptadorUsuario.borrarTodosUsuario();
	}
	
	private void inicializarRepositorios() {
		repoUsuarios = RepoUsuarios.getUnicaInstancia();
		//repoUsuarios.removeTodosUsuarios();
	}
	
}
