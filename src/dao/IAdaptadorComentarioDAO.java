package dao;

import java.util.List;

import dominio.Comentario;

public interface IAdaptadorComentarioDAO {
	public void registrarComentario(Comentario comentario);
	public void borrarComentario(Comentario comentario);
	public void borrarTodosComentarios();
	public void modificarComentario(Comentario comentario);
	public Comentario recuperarComentario(int id);
	public List<Comentario> recuperarTodosComentarios();
}
