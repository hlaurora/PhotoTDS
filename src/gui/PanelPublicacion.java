package gui;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import controlador.Controlador;
import dominio.Foto;
import dominio.RepoPublicaciones;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelPublicacion extends JPanel {
	
	private String ruta;
	private String fotoPerfil;
	private String nombreUsuario;
	private int idFoto;

	private JLabel lblNumMg;
	private JLabel lblImagen;
	private Foto foto;
	
	/**
	 * Create the panel.
	 */
	//public PanelPublicacion(int id, String r, String fp, String nu) {
	public PanelPublicacion(Foto foto) {
		/*this.ruta = r;
		this.fotoPerfil = fp;
		this.nombreUsuario = nu;
		this.idFoto = id;*/
		//this.idFoto = id;
		//this.foto = Controlador.getUnicaInstancia().getFoto(id);
		this.foto = foto;
		this.ruta = foto.getRuta();
		this.fotoPerfil = foto.getUsuario().getFotoPerfil().getPath();
		this.nombreUsuario = foto.getUsuario().getNombreUsuario();		
		this.idFoto = foto.getId();
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 49, 90, 0, 0};
		gridBagLayout.rowHeights = new int[]{5, 14, 0, 0, 0, 0, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnMeGusta = new JButton("");
		this.addManejadorBotonMeGusta(btnMeGusta);
		btnMeGusta.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/icons8-me-gusta-16.png")));
		GridBagConstraints gbc_btnMeGusta = new GridBagConstraints();
		gbc_btnMeGusta.insets = new Insets(0, 0, 5, 5);
		gbc_btnMeGusta.gridx = 1;
		gbc_btnMeGusta.gridy = 2;
		add(btnMeGusta, gbc_btnMeGusta);
		
		JButton btnComentario = new JButton("\r\n");
		btnComentario.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/comentario.png")));
		GridBagConstraints gbc_btnComentario = new GridBagConstraints();
		gbc_btnComentario.insets = new Insets(0, 0, 5, 5);
		gbc_btnComentario.gridx = 2;
		gbc_btnComentario.gridy = 2;
		add(btnComentario, gbc_btnComentario);
		
		//lblNumMg = new JLabel(Controlador.getUnicaInstancia().getMeGustas(idFoto) + " Me gustas");
		lblNumMg = new JLabel(foto.getMeGustas() + " Me gustas");
		GridBagConstraints gbc_lblNumMg = new GridBagConstraints();
		gbc_lblNumMg.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumMg.gridx = 3;
		gbc_lblNumMg.gridy = 2;
		add(lblNumMg, gbc_lblNumMg);
		
		lblImagen = new JLabel("");
		this.fixedSize(lblImagen, 150, 90);
		lblImagen.setIcon(escalarImagen(lblImagen, ruta));
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.gridheight = 5;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 0;
		gbc_lblImagen.gridy = 1;
		add(lblImagen, gbc_lblImagen);
		
		JLabel lblFotoPerfil = new JLabel("");
		this.fixedSize(lblFotoPerfil, 50, 50);
		lblFotoPerfil.setIcon(escalarImagen(lblFotoPerfil, fotoPerfil));
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 1;
		gbc_lblFotoPerfil.gridy = 4;
		add(lblFotoPerfil, gbc_lblFotoPerfil);
		
		JLabel lblNombreUsuario = new JLabel(nombreUsuario);
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreUsuario.gridx = 2;
		gbc_lblNombreUsuario.gridy = 4;
		add(lblNombreUsuario, gbc_lblNombreUsuario);

	}
	
	private void addManejadorBotonMeGusta(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controlador.getUnicaInstancia().darMeGusta(idFoto);
				lblNumMg.setText(Controlador.getUnicaInstancia().getMeGustas(idFoto) + " Me gustas");
			}
		});
	}
	
	
	private Icon escalarImagen(JLabel lbl, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				lbl.getWidth()-4, 
				lbl.getHeight()-4, 
				Image.SCALE_DEFAULT));
		return icono;
	}
	
	/**
	 * Fija el tamaño de un componente
	 */
	private void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
		o.setSize(d);
	}

}
