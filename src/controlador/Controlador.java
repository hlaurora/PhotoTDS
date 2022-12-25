package controlador;

import java.awt.Image;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorPublicacionDAO;
import dao.IAdaptadorUsuarioDAO;
import dominio.Foto;
import dominio.Publicacion;
import dominio.RepoPublicaciones;
import dominio.RepoUsuarios;
import dominio.Usuario;

public class Controlador {
	
	private static Controlador unicaInstancia;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorPublicacionDAO adaptadorPublicacion;
	private RepoUsuarios repoUsuarios;
	private RepoPublicaciones repoPublicaciones;
	
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
		else {
			u = RepoUsuarios.getUnicaInstancia().getUsuarioEmail(nombreUsuario);
			if (u != null && u.getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String email, String nombre, String apellidos,
			String nombreUsuario, String password, LocalDate fechaNaci) {
		
		if (esUsuarioRegistrado(nombreUsuario))
			return false;
		
		Usuario usuario = new Usuario(email, nombre, apellidos, nombreUsuario, password, fechaNaci);
		//usuario.setFotoPerfil(new File("imagenes/usuario48.png"));
		adaptadorUsuario.registrarUsuario(usuario);
		repoUsuarios.addUsuario(usuario);
		return true;
	}
	
	public void registrarFotoPerfil(String nombreUsuario, File fotoPerfil) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		u.setFotoPerfil(fotoPerfil);
		adaptadorUsuario.modificarUsuario(u);
	}
	
	public void registrarTextoPresentacion(String nombreUsuario, String textoPresentacion) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		u.setTextoPresentacion(textoPresentacion);
		adaptadorUsuario.modificarUsuario(u);
	}
	
	public String getNombreUsuario(String s) {
		//Si ha entrado con el nombre de usuario pues lo devolvemos
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(s);
		if (u != null ) {
			return s;
		}
		//Si no lo buscamos
		else {
			u = RepoUsuarios.getUnicaInstancia().getUsuarioEmail(s);
			return u.getNombreUsuario();
		}
	}
	
	/*
	public String getEmail(String s) {
		//Si ha entrado con el email pues lo devolvemos
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuarioEmail(s);
		if (u != null ) {
			return s;
		}
		//Si no lo buscamos
		else {
			u = RepoUsuarios.getUnicaInstancia().getUsuario(s);
			return u.getEmail();
		}
	}*/
	
	public String getDato(String d, String usuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(usuario);
		String dato = "";
		if (d.equals("nombre")) {
			dato = u.getNombre();
		}
		else if (d.equals("apellidos")) {
			dato = u.getApellidos();
		}
		else if (d.equals("email")) {
			dato = u.getEmail();
		}
		return dato;
	}
	
	public LocalDate getFechaNacimiento(String usuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(usuario);
		LocalDate fecha = u.getFechaNacimiento();
		return fecha;
	}
	
	public String getFotoPerfil(String usuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(usuario);
		String ruta = u.getFotoPerfil().getPath();
		return ruta;
	}
	
	
	public List<Foto> getFotos(String nombreUsuario){
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getFotos();
	}
	
	public Foto getFoto(int id) {
		Foto f = (Foto)RepoPublicaciones.getUnicaInstancia().getPublicacion(id);
		return f;
	}
	
	public List<Foto> getFotosSeguidos(String nombreUsuario){
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		List<Foto> listaFotos = new ArrayList<>();
		for (Usuario s : u.getSeguidos()) {
			for (Foto f : s.getFotos()) {
				listaFotos.add(f);
			}
		}
		for (Foto f : u.getFotos()) {
			listaFotos.add(f);
		}
		
		Collections.sort(listaFotos);
		Collections.reverse(listaFotos);
		
		/*if (listaFotos.size() <= 3) {
			return listaFotos;
		}
		return listaFotos.subList(0, 2);*/
		if (listaFotos.size() <= 20) {
			return listaFotos;
		}
		return listaFotos.subList(0, 19);
	}
	
	public int getNumSeguidores(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getSeguidores().size();
	}
	
	public int getNumSeguidos(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getSeguidos().size();
	}
	
	
	public boolean registrarFoto(String nombreUsuario, String ruta, String comentario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);		
		List<String> hashtags = new LinkedList<String>();
		Publicacion p = new Foto("titulo", LocalDate.now(), comentario, hashtags, ruta);
		p.setUsuario(u);
		u.addFoto((Foto)p);
		//repoPublicaciones.addPublicacion(p);
		RepoPublicaciones.getUnicaInstancia().addPublicacion((Foto)p);
		adaptadorPublicacion.registrarPublicacion((Foto)p);
		adaptadorUsuario.modificarUsuario(u);
		return true;
	}
	
	
	public int getMeGustas(int id) {
		Foto f = (Foto) repoPublicaciones.getPublicacion(id);
		return f.getMeGustas();
	}
	
	public void darMeGusta(int id) {
		Foto f = (Foto) repoPublicaciones.getPublicacion(id);
		f.addMeGustas();
		adaptadorPublicacion.modificarPublicacion(f);
	}
	
	public List<Usuario> buscarUsuarios (String cadena) {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		
		//usuarios = RepoUsuarios.getUnicaInstancia().getUsuarios();
		
		for (Usuario u : RepoUsuarios.getUnicaInstancia().getUsuarios()) {
			if ((u.getNombreUsuario().contains(cadena)) ||
					(u.getNombre().contains(cadena) && !usuarios.contains(u)) ||
					 (u.getEmail().contains(cadena) && !usuarios.contains(u))) {
				usuarios.add(u);
			}
		}
		
		return usuarios;
	}
	
	public boolean sigue (String seguidor, String seguido) {
		//recuperamos seguidor
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(seguidor);
		//recuperamos seguido
		Usuario s = RepoUsuarios.getUnicaInstancia().getUsuario(seguido);
		
		if (s.getSeguidores().contains(u)) {
			return true;
		}
		return false;
	}
	
	public void seguirUsuario (String seguidor, String seguido) {
		//recuperamos seguidor
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(seguidor);
		//recuperamos seguido
		Usuario s = RepoUsuarios.getUnicaInstancia().getUsuario(seguido);
		
		s.addSeguidor(u);
		u.addSeguido(s);
		adaptadorUsuario.modificarUsuario(s);
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
		adaptadorPublicacion = factoria.getPublicacionDAO();
		//adaptadorPublicacion.borrarTodasPublicaciones();
		//adaptadorUsuario.borrarTodosUsuario();
	}
	
	private void inicializarRepositorios() {
		repoUsuarios = RepoUsuarios.getUnicaInstancia();
		repoPublicaciones = RepoPublicaciones.getUnicaInstancia();
	}
	
}
