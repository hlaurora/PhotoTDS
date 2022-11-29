package controlador;

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
	
	public boolean esUsuarioRegistrado(String login) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}
	
	public boolean loginUsuario(String nombre, String password) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (u != null && u.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String nombre, String email, String password, String login, String nombreCompleto, LocalDate fechaNaci) {
		
		if (esUsuarioRegistrado(nombre))
			return false;
		
		Usuario usuario = new Usuario(nombre, email, password, login, nombreCompleto, fechaNaci);
		adaptadorUsuario.registrarUsuario(usuario);
		repoUsuarios.addUsuario(usuario);
		return true;
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
	}
	
	private void inicializarRepositorios() {
		repoUsuarios = RepoUsuarios.getUnicaInstancia();
	}
	
	public boolean existeUsuario(String nombre) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(nombre) != null;
	}
	
	
	

}
