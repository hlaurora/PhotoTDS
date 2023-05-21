package controlador;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.IAdaptadorComentarioDAO;
import dao.IAdaptadorNotificacionDAO;
import dao.IAdaptadorPublicacionDAO;
import dao.IAdaptadorUsuarioDAO;
import dominio.Album;
import dominio.Comentario;
import dominio.Foto;
import dominio.Notificacion;
import dominio.Publicacion;
import dominio.RepoPublicaciones;
import dominio.RepoUsuarios;
import dominio.Usuario;
import umu.tds.fotos.*;

public class Controlador implements IFotosListener{
	
	private static Controlador unicaInstancia;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorPublicacionDAO adaptadorPublicacion;
	private IAdaptadorComentarioDAO adaptadorComentario;
	private IAdaptadorNotificacionDAO adaptadorNotificacion;
	private RepoUsuarios repoUsuarios;
	private RepoPublicaciones repoPublicaciones;
	private CargadorFotos cargador = new CargadorFotos();
	
	private Controlador() {
		inicializarAdaptadores();
		inicializarRepositorios();
		cargador.addFotosListener(this);
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
	
	public Usuario getUsuario(String nombreUsuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
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

	public List<Album> getAlbumes(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getAlbumes();
	}
	
	public boolean isPremium(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getPremium();
	}
	
	public List<Usuario> buscarUsuarios(String cadena) {
	    return RepoUsuarios.getUnicaInstancia().getUsuarios()
	            .stream()
	            .filter(u -> u.getNombreUsuario().contains(cadena) ||
	                    u.getNombre().contains(cadena) ||
	                    u.getEmail().contains(cadena))
	            .distinct()
	            .collect(Collectors.toList());
	}

	public void hacerPremium(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		u.setPremium(true);
		adaptadorUsuario.modificarUsuario(u);
	}
	
	
	///////////////////	
	///Publicaciones///
	///////////////////	

	public boolean registrarFoto(String nombreUsuario, String ruta, String comentario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);		
		List<String> hashtags = this.extraerHashtags(comentario);
		Publicacion p = new Foto("titulo", LocalDateTime.now(), comentario, hashtags, ruta);
		p.setUsuario(u);
		u.addFoto((Foto)p);
		repoPublicaciones.addPublicacion(p);
		adaptadorPublicacion.registrarPublicacion((Foto)p);
		
		//Notificar a sus seguidores 
		Notificacion n = new Notificacion(LocalDateTime.now());
		n.setPublicacion(p);
		adaptadorNotificacion.registrarNotificacion(n);
		for(Usuario s : u.getSeguidores()) {
			System.out.println("seguido por "+s.getNombreUsuario());
			s.addNotificacion(n);
			adaptadorUsuario.modificarUsuario(s);
		}
		adaptadorUsuario.modificarUsuario(u);

		
		return true;
	}
	
	public boolean eliminarPublicacion(Publicacion p) {
		Usuario u = p.getUsuario();
		
		if (p.getClass().equals(Foto.class))
			u.removeFoto((Foto)p);
		else
			u.removeAlbum((Album)p);
		
		if (repoPublicaciones.removePublicacion(p)) {
			adaptadorPublicacion.borrarPublicacion(p);
			adaptadorUsuario.modificarUsuario(u);
			return true;
		}
		return false;
	}
	
	public Foto getFoto(int id) {
		Foto f = (Foto)RepoPublicaciones.getUnicaInstancia().getPublicacion(id);
		return f;
	}
	
	public List<Foto> getFotos(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);		
		List<Foto> fotos = u.getFotos().stream()
				.sorted(Comparator.comparing(Foto::getFecha).reversed())
				.collect(Collectors.toList());
		return fotos;
	}
		
	public int getNumPublicaciones(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return (u.getFotos().size() + u.getAlbumes().size());
	}
	
	/////////////
	///Álbumes///
	/////////////
	
	public boolean existeAlbum(String nombreUsuario, String nombreAlbum) {
	    Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
	    return u.getAlbumes()
	            .stream()
	            .anyMatch(a -> a.getTitulo().equals(nombreAlbum));
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
	
	public boolean añadirFotoAlbum(int idAlbum, String ruta, String comentario) {
		Album a = (Album)repoPublicaciones.getPublicacion(idAlbum);
		
		if (a.getFotos().size() < 16) {
			Usuario u = repoUsuarios.getUsuario(a.getUsuario().getNombreUsuario());
			List<String> hashtags = new LinkedList<String>();
			Publicacion p = new Foto("titulo", LocalDateTime.now(), comentario, hashtags, ruta);
			p.setUsuario(u);
			//u.addFoto((Foto)p);
			RepoPublicaciones.getUnicaInstancia().addPublicacion((Foto)p);
			adaptadorPublicacion.registrarPublicacion((Foto)p);

			a.addFoto((Foto)p);
			
			//falta modificar el usuario en repo???
			
			
			adaptadorPublicacion.modificarPublicacion(a);
			adaptadorUsuario.modificarUsuario(u);
			return true;
		}
		return false;
	}	
	
	public boolean eliminarFotoAlbum(int idAlbum, Foto foto) {
		Album a = (Album)repoPublicaciones.getPublicacion(idAlbum);
		Usuario u = repoUsuarios.getUsuario(a.getUsuario().getNombreUsuario());
		
		a.removeFoto(foto);
		adaptadorPublicacion.modificarPublicacion(a);
		adaptadorUsuario.modificarUsuario(u);

		return true;
	}	
	
	///////////////
	///Me gustas///
	///////////////
	
	public int getMeGustas(int id) {
		Foto f = (Foto) RepoPublicaciones.getUnicaInstancia().getPublicacion(id);
		return f.getMeGustas();
	}
	
	public void darMeGusta(int id) {
	    Publicacion p = repoPublicaciones.getPublicacion(id);
	    p.addMeGustas();
	    adaptadorPublicacion.modificarPublicacion(p);

	    if (p instanceof Album) {
	        ((Album) p).getFotos()
	                .stream()
	                .peek(Foto::addMeGustas)
	                .forEach(adaptadorPublicacion::modificarPublicacion);
	    }
	}	
	
	public List<Foto> getTopMeGusta(String nombreUsuario){	
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		
		List<Foto> fotos = u.getFotos().stream()
				.sorted(Comparator.comparing(Foto::getMeGustas).reversed())
				.limit(10)
				.collect(Collectors.toList());
		
		return fotos;
	}
	
	/////////////////
	///Comentarios///
	/////////////////
	
	public void añadirComentario(int idPublicacion, String texto, String nombreUsuario) {
		
		Publicacion p = repoPublicaciones.getPublicacion(idPublicacion);
		
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);		
		Comentario comentario = new Comentario(texto, LocalDateTime.now());
		comentario.setUsuario(u);
		
		p.addComentario(comentario);
		
		adaptadorComentario.registrarComentario(comentario);
		adaptadorPublicacion.modificarPublicacion(p);
	}	
	
	
	
	//////////////////
	////Seguidores////
	//////////////////
	
	public boolean sigue(String seguidor, String seguido) {
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
		
		if(!this.sigue(seguidor, seguido)) {
			s.addSeguidor(u);
			u.addSeguido(s);
			adaptadorUsuario.modificarUsuario(s);
			adaptadorUsuario.modificarUsuario(u);
		}
	}
	
	public int getNumSeguidores(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getSeguidores().size();
	}
	
	public int getNumSeguidos(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getSeguidos().size();
	}
	
	public List<Foto> getFotosSeguidos(String nombreUsuario){
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		List<Foto> listaFotos = new ArrayList<>();		
		
		for (Foto f : u.getFotos()) {
			listaFotos.add(f);
		}
		
		for (Usuario s : u.getSeguidos()) {
			for (Foto f : s.getFotos()) {
				listaFotos.add(f);
			}
		}
		
		listaFotos = listaFotos.stream()
					.sorted(Comparator.comparing(Foto::getFecha).reversed())
					.limit(20)
					.collect(Collectors.toList());
		return listaFotos;
	}
	
	
	//////////////
	///Hashtags///
	//////////////
	
	public List<String> buscarHashtags(String hashtag) {
		//HashMap<String, Foto> lista = new HashMap<String, Foto>();
		hashtag = hashtag.substring(1);
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
		String[] palabras = com.split(" ");
		
		List<String> hashtags = Arrays.stream(palabras)
			    .filter(p -> p.startsWith("#") && p.length() <= 16)
			    .limit(4)
			    .collect(Collectors.toList());

		return hashtags;
	}	
	
	
	///////////////////
	///CargadorFotos///
	///////////////////
	
	public void cargarFotos(String fotos) {
		cargador.setArchivoFotos(fotos);
	}
	
	
	@Override
	public void enterarCambioFotos(FotosEvent event) {
		Fotos fotos = event.getFotos();
		for (umu.tds.fotos.Foto foto : fotos.getFoto()) {
			registrarFoto("auro", foto.getPath(), foto.getDescripcion());
			System.out.println(foto.getPath());
		}
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
		adaptadorComentario = factoria.getComentarioDAO();
		adaptadorNotificacion = factoria.getNotificacionDAO();
		//adaptadorPublicacion.borrarTodasPublicaciones();
		//adaptadorUsuario.borrarTodosUsuario();
		//adaptadorComentario.borrarTodosComentarios();
		//adaptadorNotificacion.borrarTodasNotificaciones();
	}
	
	private void inicializarRepositorios() {
		repoUsuarios = RepoUsuarios.getUnicaInstancia();
		repoPublicaciones = RepoPublicaciones.getUnicaInstancia();
		
		for (Usuario u: repoUsuarios.getUsuarios()) {
			System.out.println(u.getNombreUsuario());
			for(Notificacion n: u.getNotificaciones()) {
				System.out.println(n.getFecha());
			}
		}
	}
	
}
