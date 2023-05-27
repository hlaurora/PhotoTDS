package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

import dominio.Album;
import dominio.Comentario;
import dominio.Foto;
import dominio.Usuario;

public class TestPublicacion {

	private static Usuario usuario1;
	private static Foto foto1;
	private static Album album1;
	private static ArrayList<String> hashtags = new ArrayList<String>();;

	@BeforeClass
	public static void configurar() {
		usuario1 = new Usuario("usu1@gmail", "nombre1", "apellido1", "usu1", "usu1", LocalDate.of(2001, 01, 01));
		hashtags.add("#hola");
		foto1 = new Foto("foto1", LocalDateTime.now(), "foto uno", hashtags, "#hola hashtag");
		album1 = new Album("album1", LocalDateTime.now(), "album uno", hashtags);
	}

	@Test
	public void test1() {
		assertEquals(foto1.getHashtags(), hashtags);
	}
	
	@Test
	public void test2() {
		usuario1.addAlbum(album1);
		usuario1.getAlbumes().get(0).addFoto(foto1);
		assertEquals(usuario1.getAlbumes().get(0).getFotos().get(0), foto1);
	}
	
	@Test
	public void test3() {
		album1.addMeGustas();
		assertEquals(foto1.getMeGustas(), 1);
	}
	
	@Test
	public void test4() {
		Comentario c = new Comentario("comentario", LocalDateTime.now());
		foto1.addComentario(c);
		assertEquals(foto1.getComentarios().size(), 1);
	}

}
