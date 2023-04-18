package dao;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}
	
	public IAdaptadorPublicacionDAO getPublicacionDAO() {
		return AdaptadorPublicacionTDS.getUnicaInstancia();
	}
	
	public IAdaptadorComentarioDAO getComentarioDAO() {
		return AdaptadorComentarioTDS.getUnicaInstancia();
	}
}