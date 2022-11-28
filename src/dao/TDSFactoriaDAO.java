package dao;

public class TDSFactoriaDAO extends FactoriaDAO {

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() { 
	 return AdaptadorUsuarioTDS.getUnicaInstancia(); 
	 } 
}
