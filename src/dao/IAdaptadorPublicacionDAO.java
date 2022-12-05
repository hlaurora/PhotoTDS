package dao;

import java.util.List;

import dominio.Publicacion;

public interface IAdaptadorPublicacionDAO {
	
	public void registrarPublicacion(Publicacion publicacion);
	public void borrarPublicacion(Publicacion publicacion);
	public void borrarTodasPublicaciones();
	public void modificarPublicacion(Publicacion publicacion);
	public Publicacion recuperarPublicacion(int id);
	public List<Publicacion> recuperarTodasPublicaciones();
	//public void cambiarFotoPerfil(Usuario usuario, File fotoPerfil);

}
