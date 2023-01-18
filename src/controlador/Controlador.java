package controlador;

import java.awt.Image;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorPublicacionDAO;
import dao.IAdaptadorUsuarioDAO;
import dominio.Album;
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
	
	
	///////////////////
	///// USUARIO /////
	///////////////////
	
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
	
	public void registrarContraseña(String nombreUsuario, String password) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		u.setPassword(password);
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
	
	
	public List<Foto> getFotos(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getFotos();
	}
	
	public List<Album> getAlbumes(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getAlbumes();
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
		
		listaFotos = listaFotos.stream()
					.sorted(Comparator.comparing(Foto::getFecha).reversed())
					.limit(20)
					.collect(Collectors.toList());
		
		return listaFotos;
	}
	
	public List<Foto> getTopMeGusta(String nombreUsuario){
		
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		
		List<Foto> fotos = u.getFotos().stream()
				.sorted(Comparator.comparing(Foto::getMeGustas).reversed())
				.limit(10)
				.collect(Collectors.toList());
		
		return fotos;
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
		List<String> hashtags = this.extraerHashtags(comentario);
		Publicacion p = new Foto("titulo", LocalDateTime.now(), comentario, hashtags, ruta);
		p.setUsuario(u);
		u.addFoto((Foto)p);
		repoPublicaciones.addPublicacion(p);
		//RepoPublicaciones.getUnicaInstancia().addPublicacion((Foto)p);
		adaptadorPublicacion.registrarPublicacion((Foto)p);
		adaptadorUsuario.modificarUsuario(u);
		//System.out.println(p.getDescripcion());
		return true;
	}
	
	public boolean eliminarPublicacion(Publicacion p) {
		Usuario u = p.getUsuario();
		
		if (p.getClass().equals(Foto.class))
			u.removeFoto((Foto)p);
		else
			u.removeAlbum((Album)p);
		
		repoPublicaciones.removePublicacion(p);
		adaptadorPublicacion.borrarPublicacion(p);
		
		adaptadorUsuario.modificarUsuario(u);
		
		return true;
	}
	
	
	public int getMeGustas(int id) {
		//Foto f = (Foto) repoPublicaciones.getPublicacion(id);
		Foto f = (Foto) RepoPublicaciones.getUnicaInstancia().getPublicacion(id);
		return f.getMeGustas();
	}
	
	public void darMeGusta(int id) {
		/*Foto f = (Foto) repoPublicaciones.getPublicacion(id);
		f.addMeGustas();*/
		Publicacion p = repoPublicaciones.getPublicacion(id);
		if (p.getClass().equals(Album.class)) {
			for (Foto f : ((Album)p).getFotos()) {
				f.addMeGustas();
				adaptadorPublicacion.modificarPublicacion(f);
			}
		}
		p.addMeGustas();
			
		adaptadorPublicacion.modificarPublicacion(p);
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
	
	public boolean isPremium(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getPremium();
	}
	
	public Usuario getUsuario(String nombreUsuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
	}
	
	public void hacerPremium(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		u.setPremium(true);
		adaptadorUsuario.modificarUsuario(u);
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
	
	
	public boolean existeAlbum(String nombreUsuario, String nombreAlbum) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		for (Album a : u.getAlbumes()) {
			if (a.getTitulo().equals(nombreAlbum)) {
				return true;
			}
		}
		return false;
	}
	
	
	public Album registrarAlbum(String titulo, String comentario, String nombreUsuario) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		List<String> hashtags = new LinkedList<String>();
		Album album = new Album(titulo, LocalDateTime.now(), comentario, hashtags);
		album.setUsuario(u);
		u.addAlbum(album);
		adaptadorPublicacion.registrarPublicacion(album);
		repoPublicaciones.addAlbum(album);
		adaptadorUsuario.modificarUsuario(u);
		return album;
	}
	
	public void añadirFotoAlbum(int idAlbum, String ruta, String comentario) {
		Album a = (Album)repoPublicaciones.getPublicacion(idAlbum);
		
		Usuario u = repoUsuarios.getUsuario(a.getUsuario().getNombreUsuario());
		List<String> hashtags = new LinkedList<String>();
		Publicacion p = new Foto("titulo", LocalDateTime.now(), comentario, hashtags, ruta);
		p.setUsuario(u);
		//u.addFoto((Foto)p);
		RepoPublicaciones.getUnicaInstancia().addPublicacion((Foto)p);
		adaptadorPublicacion.registrarPublicacion((Foto)p);

		a.addFoto((Foto)p);
		
		adaptadorPublicacion.modificarPublicacion(a);		
	}
	
	public List<String> buscarHashtags(String hashtag) {
		//HashMap<String, Foto> lista = new HashMap<String, Foto>();
		List <String> lista = new LinkedList<String>();	
		for(Publicacion p : repoPublicaciones.getPublicaciones()) {
			for(String h : p.getHashtags()) {
				//System.out.println(h);
				if(h.contains(hashtag)) {
					lista.add(h.substring(1) + " -> " + p.getUsuario().getSeguidores().size());
					/*if(p.getClass().equals(Album.class)) {
						lista.put(hashtag, ((Album)p).getFotos().get(0));
					}
					else
						lista.put(h, (Foto)p);*/
				}
			}
		}
		
		return lista;
	}
	
	private List<String> extraerHashtags(String com) {
		//Máximo 4 hashtags
		//Símbolo '#' seguido de una palabra que tiene un máximo de 15 letras
		List<String> hashtags = new LinkedList<String>();
		String[] palabras = com.split(" ");
		int numHash = 0;
		for (String p : palabras) {
			if (p.startsWith("#") && (p.length()<=16) && (numHash<4)) {
				hashtags.add(p);
				numHash++;
			}
		}
		return hashtags;
	}
	
	
	
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
