package controlador;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import dominio.DescuentoEdad;
import dominio.DescuentoPopularidad;
import dominio.Foto;
import dominio.Notificacion;
import dominio.Publicacion;
import dominio.RepoPublicaciones;
import dominio.RepoUsuarios;
import dominio.Usuario;
import premium.Servicios;
import umu.tds.fotos.*;

public class Controlador implements IFotosListener{

	private static Controlador unicaInstancia;

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorPublicacionDAO adaptadorPublicacion;
	private IAdaptadorComentarioDAO adaptadorComentario;
	private IAdaptadorNotificacionDAO adaptadorNotificacion;
	private RepoUsuarios repoUsuarios;
	private RepoPublicaciones repoPublicaciones;

	private Usuario usuarioActual;
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

	/**
	 * Establece el usuario que ha iniciado sesión
	 */	public void setUsuario(Usuario u) {
		usuarioActual = u;
	}

	/**
	 * Devuelve el usuario que ha iniciado sesión
	 */
	public Usuario getUsuarioActual() {
		return this.usuarioActual;
	}


	///////////////////
	///// USUARIO /////
	///////////////////

	/**
	 * Comprueba si el usuario con ese nombre de usuario está registrado
	 */
	public boolean esUsuarioRegistrado(String nombreUsuario) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario) != null;
	}

	/**
	 * Comprueba si puede iniciar sesión un usuario con el nombre de usuario
	 *  y la contraseña dadas
	 */
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

	/**
	 * Registra un nuevo usuario comprobando antes si ya hay un usuario
	 *  registrado con ese nombre de usuario
	 */
	public boolean registrarUsuario(String email, String nombre, String apellidos,
			String nombreUsuario, String password, LocalDate fechaNaci) {

		if (esUsuarioRegistrado(nombreUsuario))
			return false;

		Usuario usuario = new Usuario(email, nombre, apellidos, nombreUsuario, password, fechaNaci);
		adaptadorUsuario.registrarUsuario(usuario);
		repoUsuarios.addUsuario(usuario);
		return true;
	}

	/**
	 * Registra la foto de perfil de un usuario
	 */
	public void registrarFotoPerfil(String nombreUsuario, File fotoPerfil) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		u.setFotoPerfil(fotoPerfil);
		adaptadorUsuario.modificarUsuario(u);
	}

	/**
	 * Registra el texto de presentación de un usuario
	 */
	public void registrarTextoPresentacion(String nombreUsuario, String textoPresentacion) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		u.setTextoPresentacion(textoPresentacion);
		adaptadorUsuario.modificarUsuario(u);
	}

	/**
	 * Registra la contraseña dada para un usuario
	 */
	public void registrarContraseña(String nombreUsuario, String password) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		u.setPassword(password);
		adaptadorUsuario.modificarUsuario(u);
	}

	/**
	 * Devuelve el usuario con ese nombre de usuario
	 */
	public Usuario getUsuario(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		if (u != null)
			return u;
		return repoUsuarios.getUsuarioEmail(nombreUsuario);
	}

	/**
	 * Devuelve el nombre de usuario de un usuario que ha iniciado sesión
	 *  con su nombre de usuario o email
	 */
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

	/**
	 * Devuelve un dato pedido del perfil del usuario
	 */
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

	/**
	 * Devuelve la ruta de la foto de perfil del usuario
	 */
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
		return u.isPremium();
	}

	/**
	 * Busca la lista de usuarios que contengan la cadena dada en su nombre de usuario
	 */
	public List<Usuario> buscarUsuarios(String cadena) {
		return RepoUsuarios.getUnicaInstancia().getUsuarios()
				.stream()
				.filter(u -> u.getNombreUsuario().contains(cadena) ||
						u.getNombre().contains(cadena) ||
						u.getEmail().contains(cadena))
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 *Convierte en premium a un usuario 
	 */
	public void hacerPremium(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		u.setPremium(true);
		adaptadorUsuario.modificarUsuario(u);
	}

	/**
	 * Anular la suscripción premium del usuario
	 */
	public void anularPremium(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		u.setPremium(false);
		adaptadorUsuario.modificarUsuario(u);
	}


	///////////////////	
	///Publicaciones///
	///////////////////	

	/**
	 * Registra una nueva foto para un usuario
	 */
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
			s.addNotificacion(n);
			adaptadorUsuario.modificarUsuario(s);
		}
		adaptadorUsuario.modificarUsuario(u);

		return true;
	}

	/**
	 * Elimina una Publicación p de un usuario
	 */
	public boolean eliminarPublicacion(Publicacion p) {
		Usuario u = p.getUsuario();
		
		if (p instanceof Foto) {
	        u.removeFoto((Foto) p);
	    } else if (p instanceof Album) {
	        ((Album) p).getFotos().forEach(f -> {
	            repoPublicaciones.removePublicacion(f);
	            adaptadorPublicacion.borrarPublicacion(f);
	        });
	        u.removeAlbum((Album) p);
	    }

	    if (repoPublicaciones.removePublicacion(p)) {
	        adaptadorPublicacion.borrarPublicacion(p);
	        adaptadorUsuario.modificarUsuario(u);
	        return true;
	    }
	    return false;
	}

	/**
	 * Devuelve la lista de Fotos de un usuario dado
	 */
	public List<Foto> getFotos(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);		
		List<Foto> fotos = u.getFotos().stream()
				.sorted(Comparator.comparing(Foto::getFecha).reversed())
				.collect(Collectors.toList());
		return fotos;
	}

	/**
	 * Devuelve el número de publicaciones de un usuario dado
	 */
	public int getNumPublicaciones(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return (u.getFotos().size() + u.getAlbumes().size());
	}

	/////////////
	///Álbumes///
	/////////////

	/**
	 * Comprueba si el usuario dado tiene un álbum con ese nombre
	 */
	public boolean existeAlbum(String nombreUsuario, String nombreAlbum) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		return u.getAlbumes()
				.stream()
				.anyMatch(a -> a.getTitulo().equals(nombreAlbum));
	}

	/**
	 * Registra un nuevo álbum
	 */
	public Album registrarAlbum(String titulo, String comentario, String nombreUsuario) {
		Usuario u = repoUsuarios.getUsuario(nombreUsuario);
		List<String> hashtags = this.extraerHashtags(comentario);
		Album album = new Album(titulo, LocalDateTime.now(), comentario, hashtags);
		album.setUsuario(u);
		u.addAlbum(album);
		adaptadorPublicacion.registrarPublicacion(album);
		repoPublicaciones.addAlbum(album);
		adaptadorUsuario.modificarUsuario(u);
		return album;
	}

	/**
	 * Añade una foto a un álbum
	 */
	public boolean añadirFotoAlbum(int idAlbum, String ruta, String comentario) {
		Album a = (Album)repoPublicaciones.getPublicacion(idAlbum);

		if (a.getFotos().size() < 16) {
			Usuario u = repoUsuarios.getUsuario(a.getUsuario().getNombreUsuario());
			List<String> hashtags = new LinkedList<String>();
			Publicacion p = new Foto("titulo", LocalDateTime.now(), comentario, hashtags, ruta);
			p.setUsuario(u);
			RepoPublicaciones.getUnicaInstancia().addPublicacion((Foto)p);
			adaptadorPublicacion.registrarPublicacion((Foto)p);
			a.addFoto((Foto)p);			

			adaptadorPublicacion.modificarPublicacion(a);
			adaptadorUsuario.modificarUsuario(u);
			return true;
		}
		return false;
	}	

	/**
	 * Elimina una foto de un álbum
	 */
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

	/**
	 * Añade un me gusta a la una Foto
	 */
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

	/**
	 * Devuelve una lista con las 10 fotos con más me gustas del usuario
	 */
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

	/**
	 * Añade un comentario a una Publicación
	 */
	public void añadirComentario(int idPublicacion, String texto, String nombreUsuario) {
		Publicacion p = repoPublicaciones.getPublicacion(idPublicacion);

		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);		
		Comentario comentario = new Comentario(texto, LocalDateTime.now());
		comentario.setUsuario(u);

		p.addComentario(comentario);

		List<String> hashtags = extraerHashtags(texto);
		for(String h : hashtags) {
			((Foto)p).addHashtag(h);
		}

		adaptadorComentario.registrarComentario(comentario);
		adaptadorPublicacion.modificarPublicacion(p);
	}	

	/**
	 * Añade un comentario a una Publicación sin añadir los hashtags
	 */
	public void añadirComentarioSinHashtag(int idPublicacion, String texto, String nombreUsuario) {
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

	/**
	 * Comprueba si el primer usuario sigue al segundo
	 */
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

	/**
	 * El primer usuario pasa a seguir al segundo
	 */
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

	/**
	 * Devuelve una lista con las 20 fotos más recientes subidas por el usuario y 
	 * los usuarios a los que sigue, ordenadas de más a menos reciente
	 */
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

	/**
	 * Busca las publicaciones que tienen ese hashtag
	 * Devuelve la lista con el hashtag y , el nombre de usuario y
	 * el número de seguidores del usuario que publicó la foto
	 */
	public List<String> buscarHashtags(String hashtag) {
		hashtag = hashtag.substring(1);
		List <String> lista = new LinkedList<String>();	
		for(Publicacion p : repoPublicaciones.getPublicaciones()) {
			for(String h : p.getHashtags()) {
				if(h.contains(hashtag)) {
					lista.add(h.substring(1) + " -> " + p.getUsuario().getNombreUsuario() + ", "
							+ p.getUsuario().getSeguidores().size() + " seguidores");
				}
			}
		}

		return lista;
	}

	/**
	 * Extrae y devuelve la lista de hashtags que hay en un comentario
	 * Coge como máximo 4
	 * Busca el símbolo '#' seguido de una palabra que tiene un máximo de 15 letras
	 */
	private List<String> extraerHashtags(String com) {
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

	/**
	 * Le pasa al CargadorFotos la ruta del archivo XML
	 */
	public void cargarFotos(String fotos) {
		cargador.setArchivoFotos(fotos);
	}

	/**
	 * Método que se ejecuta cuando se produce un evento de cambio de fotos.
	 * Registra las nuevas fotos en el sistema para el usuario actual
	 */
	@Override
	public void enterarCambioFotos(FotosEvent event) {
		Fotos fotos = event.getFotos();
		for (umu.tds.fotos.Foto foto : fotos.getFoto()) {
			registrarFoto(usuarioActual.getNombreUsuario(), foto.getPath(), foto.getDescripcion());
		}
	}

	////////////////////
	///Notificaciones///
	////////////////////

	/**
	 * Vacía la lista de notificaciones de un usuario
	 */
	public void vaciarNotificaciones(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		u.getNotificaciones().clear();
		adaptadorUsuario.modificarUsuario(u);
	}
	
	////////////////
	///Descuentos///
	////////////////
	public boolean aplicarDescuentoEdad(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		DescuentoEdad d = new DescuentoEdad();
		return d.aplicarDescuento(u);
	}
	
	public boolean aplicarDescuentoPpoularidad(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		DescuentoPopularidad d = new DescuentoPopularidad();
		return d.aplicarDescuento(u);
	}
	

	///////////////
	///Servicios///
	///////////////

	/**
	 * Llama al servicio para generar el pdf pedido por el usuario
	 */
	public void generarPdf(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		Servicios.crearPdf(u);
	}

	/**
	 * Llama al servicio para generar el excel pedido por el usuario
	 */
	public void generarExcel(String nombreUsuario) {
		Usuario u = RepoUsuarios.getUnicaInstancia().getUsuario(nombreUsuario);
		Servicios.crearExcel(u);
	}


	/**
	 * Inicializa los adaptadores
	 */
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
		// Para vaciar la base de datos
		/*adaptadorPublicacion.borrarTodasPublicaciones();
		adaptadorUsuario.borrarTodosUsuario();
		adaptadorComentario.borrarTodosComentarios();
		adaptadorNotificacion.borrarTodasNotificaciones();*/
	}

	/**
	 * Inicializa los repositorios
	 */
	private void inicializarRepositorios() {
		repoUsuarios = RepoUsuarios.getUnicaInstancia();
		repoPublicaciones = RepoPublicaciones.getUnicaInstancia();	
	}

}
