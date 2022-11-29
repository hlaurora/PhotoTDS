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
	
	public void registrarUsuario(String nombre, String email, String nombreCompleto, LocalDate fechaNaci) {
		Usuario usuario = new Usuario(nombre, email, nombreCompleto, fechaNaci);
		adaptadorUsuario.registrarUsuario(usuario);
		repoUsuarios.addUsuario(usuario);
	}
	
	
	
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
