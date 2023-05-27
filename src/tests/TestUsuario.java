package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.AdaptadorUsuarioTDS;
import dominio.Usuario;

public class TestUsuario {

	private static Usuario usuario1;
	private static Usuario usuario2;


	@BeforeClass
	public static void configurar() {
		usuario1 = new Usuario("usu1@gmail", "nombre1", "apellido1", "usu1", "usu1", LocalDate.of(2001, 01, 01));
		usuario2 = new Usuario("usu2@gmail", "nombre2", "apellido2", "usu2", "usu2", LocalDate.of(2001, 01, 01));
	}

	@Test
	public void test1() {
		AdaptadorUsuarioTDS.getUnicaInstancia().registrarUsuario(usuario1);
		Usuario recuperado = AdaptadorUsuarioTDS.getUnicaInstancia().recuperarUsuario(usuario1.getId());
		assertEquals(usuario1, recuperado);
	}

	@Test
	public void test2() {
		usuario1.setPremium(true);
		assertTrue(usuario1.isPremium());
	}

	@Test
	public void test3() {
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(usuario2);
		assertNotEquals(adaptadorUsuario.recuperarUsuario(usuario1.getId()),
				adaptadorUsuario.recuperarUsuario(usuario2.getId()));
	}

	@Test
	public void test4() {
		usuario1.addSeguidor(usuario2);
		assertTrue(usuario1.getSeguidores().contains(usuario2));
	}

	@AfterClass
	public static void borrar() {
		AdaptadorUsuarioTDS.getUnicaInstancia().borrarUsuario(usuario1);
		AdaptadorUsuarioTDS.getUnicaInstancia().borrarUsuario(usuario2);
	}

}
