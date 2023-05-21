package dao;

import java.util.List;

import dominio.Notificacion;

public interface IAdaptadorNotificacionDAO {
	public void registrarNotificacion(Notificacion notificacion);
	public void borrarNotificacion(Notificacion notificacion);
	public void borrarTodasNotificaciones();
	public void modificarNotificacion(Notificacion notificacion);
	public Notificacion recuperarNotificacion(int id);
	public List<Notificacion> recuperarTodasNotificaciones();
}
