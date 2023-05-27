package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import controlador.Controlador;
import dominio.Comentario;
import dominio.Foto;
import dominio.RepoPublicaciones;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class PanelPublicacion extends JPanel {
	
	private String ruta;
	private String fotoPerfil;
	private String nombreUsuario;
	private int idFoto;
	private int pos;

	private JLabel lblNumMg;
	private JLabel lblImagen;
	private Foto foto;
	private JFrame frame;
	
	private JButton btnVerComentarios;
	
	/**
	 * Create the panel.
	 */
	public PanelPublicacion(Foto foto, int p, JFrame f) {
		this.frame = f;
		this.foto = foto;
		this.ruta = foto.getRuta();
		this.fotoPerfil = foto.getUsuario().getFotoPerfil().getPath();
		this.nombreUsuario = foto.getUsuario().getNombreUsuario();		
		this.idFoto = foto.getId();
		this.pos = p;
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 49, 90, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{5, 14, 0, 0, 0, 0, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		this.addManejadorBotonComentario(btnComentario);
		btnComentario.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/comentario.png")));
		GridBagConstraints gbc_btnComentario = new GridBagConstraints();
		gbc_btnComentario.insets = new Insets(0, 0, 5, 5);
		gbc_btnComentario.gridx = 2;
		gbc_btnComentario.gridy = 2;
		add(btnComentario, gbc_btnComentario);
		
		lblImagen = new JLabel("");
		this.fixedSize(lblImagen, 150, 90);
		lblImagen.setIcon(escalarImagen(lblImagen, ruta));
		this.addManejadorFoto(lblImagen);
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.gridheight = 5;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 0;
		gbc_lblImagen.gridy = 1;
		add(lblImagen, gbc_lblImagen);
		
		btnVerComentarios = new JButton(foto.getComentarios().size() + " Comentarios");
		btnVerComentarios.setForeground(Constantes.LILA);
		this.addManejadorBotonVerComentarios(btnVerComentarios);
		GridBagConstraints gbc_btnVerComentarios = new GridBagConstraints();
		gbc_btnVerComentarios.insets = new Insets(0, 0, 5, 5);
		gbc_btnVerComentarios.gridx = 3;
		gbc_btnVerComentarios.gridy = 2;
		add(btnVerComentarios, gbc_btnVerComentarios);
		
		lblNumMg = new JLabel(foto.getMeGustas() + " Me gustas");
		GridBagConstraints gbc_lblNumMg = new GridBagConstraints();
		gbc_lblNumMg.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumMg.gridx = 4;
		gbc_lblNumMg.gridy = 2;
		add(lblNumMg, gbc_lblNumMg);
		
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
	
	private void addManejadorFoto(JLabel lbl) {
		lbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				verFoto(ruta);
		
			}
		});
	}
	
	private void addManejadorBotonMeGusta(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controlador.getUnicaInstancia().darMeGusta(idFoto);
				lblNumMg.setText(foto.getMeGustas() + " Me gustas");
				btn.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/relleno.png")));
			}
		});
	}
	
	private void addManejadorBotonVerComentarios(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaComentarios vc = new VentanaComentarios(foto);
				vc.setLocationRelativeTo(null);
				vc.setVisible(true);
			}
		});
	}
	
	private void addManejadorBotonComentario(JButton btn) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog ventanaTexto = new JDialog();
				
				JLabel etiqueta = new JLabel("Escribe tu comentario (máx 120)");
				etiqueta.setForeground(Constantes.LILA);
				etiqueta.setFont(Constantes.NORMAL_15);
				
				ventanaTexto.getContentPane().setLayout(new FlowLayout());
				JTextArea texto = new JTextArea(5, 23);
				
				texto.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						if(texto.getText().length() >= 120)
					    {
					        e.consume();
					    }
					}
				});
				
				
				texto.setLineWrap(true);
				texto.setWrapStyleWord(true);
				
				ventanaTexto.getContentPane().add(etiqueta);
			
				ventanaTexto.getContentPane().add(texto);
				
				JButton aceptar = new JButton("Aceptar");
				aceptar.setForeground(Constantes.LILA);
				aceptar.setFont(Constantes.NEGRITA_15);
				aceptar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ventanaTexto.dispose();
						String comentario = texto.getText();
						Controlador.getUnicaInstancia().añadirComentarioSinHashtag(foto.getId(), comentario, nombreUsuario);
						btnVerComentarios.setText(foto.getComentarios().size() + " Comentarios");
					}
				});
				
				ventanaTexto.getContentPane().add(aceptar);
				
				ventanaTexto.setSize(230, 180);
				ventanaTexto.setLocationRelativeTo(null);
				ventanaTexto.setVisible(true);
			}			
		});
	}	
	
	public int getIndex() {
		return this.pos;
	}
	
	private void verFoto(String ruta) {
		JDialog dialog = new JDialog(frame, false);
		dialog.setSize(460, 400);
		JLabel label = new JLabel();
		this.fixedSize(label, 450, 390);
		//Cargar la imagen
		label.setIcon(escalarImagen(label, ruta));
		//Agregar el label a la ventana de diálogo
		dialog.getContentPane().add(label);
		//Mostrar la ventana de diálogo
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
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
