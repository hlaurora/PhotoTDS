package gui;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

import controlador.Controlador;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;

import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelPerfilUsuario extends JPanel {
	
	private VentanaPrincipal ventanaPrincipal;
	
	private JPanel panelNorte;
	private JPanel panelPerfil;
	private JPanel panelPublicaciones;
	private JLabel lblPhotoTDS;
	private JButton btnAddFoto;
	private JButton btnAddAlbum;
	private JTextField textBuscar;
	private JButton btnBuscar;
	private JLabel lblFotoPerfil;
	private JButton btnNewButton;
	private JLabel lblNewLabel_1;
	private JLabel lblNumPublicaciones;
	private JLabel lblNumSeguidores;
	private JLabel lblNumSeguidos;
	private JLabel lblEmail;
	private JButton btnEditarPerfil;
	private JLabel lblNombreUsuario;
	
	private Font fuenteLabel = new Font("HP Simplified Hans", Font.PLAIN, 15);
	private Color Lila = new Color(134, 46, 150);
	private JButton btnFotos;
	private JButton btnAlbumes;
	private JPanel panelFotos;
	private JPanel panelAlbumes;
	private JLabel lblNewLabel;
	private JTable table;
	
	private JFileChooser fileChooser;
	private File selectedFile;
	
	private String usuario;
	private String email;
	private String fotoPerfil;
	private JLabel lblNewLabel_2;


	/**
	 * Create the panel.
	 */
	public PanelPerfilUsuario(VentanaPrincipal vp, String nombreUsuario) {
		
		ventanaPrincipal = vp;
		this.usuario = nombreUsuario;
		this.fotoPerfil = Controlador.getUnicaInstancia().getFotoPerfil(usuario);
		
		setSize(575, 624);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.crearPanelNorte();
		this.crearPanelPerfil();
		this.crearPanelPublicaciones();

	}
	
	private void crearPanelNorte() {
		panelNorte = new JPanel();
		panelNorte.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.fixedSize(panelNorte, 300, 60);
		add(panelNorte);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[]{10, 0, 0, 20, 0, 0, 0, 0, 0, 0, 20, 0, 0, 10, 0};
		gbl_panelNorte.rowHeights = new int[]{0, 0, 0};
		gbl_panelNorte.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelNorte.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelNorte.setLayout(gbl_panelNorte);
		{
			lblPhotoTDS = new JLabel("PhotoTDS");
			lblPhotoTDS.setForeground(Lila);
			lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 25));
			GridBagConstraints gbc_lblPhotoTDS = new GridBagConstraints();
			gbc_lblPhotoTDS.gridwidth = 2;
			gbc_lblPhotoTDS.insets = new Insets(0, 0, 0, 5);
			gbc_lblPhotoTDS.gridx = 1;
			gbc_lblPhotoTDS.gridy = 1;
			panelNorte.add(lblPhotoTDS, gbc_lblPhotoTDS);
		}
		{
			btnAddFoto = new JButton(" + ");
			btnAddFoto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fileChooser = new JFileChooser();
					int seleccion = fileChooser.showOpenDialog(btnAddAlbum);
					if (seleccion != JFileChooser.CANCEL_OPTION) {
						//Foto foto = new
						selectedFile = fileChooser.getSelectedFile();
						System.out.println(selectedFile.getAbsolutePath());
						//Controlador.getUnicaInstancia().re
					}
				
				}
			});
			btnAddFoto.setForeground(Lila);
			btnAddFoto.setFont(new Font("HP Simplified Hans", Font.BOLD, 20));
			GridBagConstraints gbc_btnAddFoto = new GridBagConstraints();
			gbc_btnAddFoto.insets = new Insets(0, 0, 0, 5);
			gbc_btnAddFoto.gridx = 5;
			gbc_btnAddFoto.gridy = 1;
			panelNorte.add(btnAddFoto, gbc_btnAddFoto);
		}
		{
			btnAddAlbum = new JButton("A+");
			btnAddAlbum.setForeground(Lila);
			btnAddAlbum.setFont(new Font("HP Simplified Hans", Font.BOLD, 20));
			GridBagConstraints gbc_btnAddAlbum = new GridBagConstraints();
			gbc_btnAddAlbum.insets = new Insets(0, 0, 0, 5);
			gbc_btnAddAlbum.gridx = 6;
			gbc_btnAddAlbum.gridy = 1;
			panelNorte.add(btnAddAlbum, gbc_btnAddAlbum);
		}
		{
			textBuscar = new JTextField();
			fixedSize(textBuscar, 90, 27);
			GridBagConstraints gbc_textBuscar = new GridBagConstraints();
			gbc_textBuscar.gridwidth = 2;
			gbc_textBuscar.insets = new Insets(0, 0, 0, 5);
			gbc_textBuscar.fill = GridBagConstraints.HORIZONTAL;
			gbc_textBuscar.gridx = 7;
			gbc_textBuscar.gridy = 1;
			panelNorte.add(textBuscar, gbc_textBuscar);
			textBuscar.setColumns(10);
		}
		{
			btnBuscar = new JButton("Buscar");
			btnBuscar.setForeground(Lila);
			btnBuscar.setFont(new Font("HP Simplified Hans", Font.BOLD, 15));
			this.fixedSize(btnBuscar, 65, 35);
			GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
			gbc_btnBuscar.insets = new Insets(0, 0, 0, 5);
			gbc_btnBuscar.gridx = 9;
			gbc_btnBuscar.gridy = 1;
			panelNorte.add(btnBuscar, gbc_btnBuscar);
		}
		{
			lblFotoPerfil = new JLabel("");
			//lblFotoPerfil.setIcon(new ImageIcon(PanelPerfilUsuario.class.getResource("/imagenes/usuario48.png")));
			//lblFotoPerfil.setIcon(new ImageIcon(fotoPerfil));
			this.fixedSize(lblFotoPerfil, 60, 60);
			this.añadirPerfil(lblFotoPerfil, fotoPerfil);
			GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
			gbc_lblFotoPerfil.insets = new Insets(0, 0, 0, 5);
			gbc_lblFotoPerfil.gridx = 11;
			gbc_lblFotoPerfil.gridy = 1;
			//this.añadirImagen(lblFotoPerfil, fotoPerfil);
			panelNorte.add(lblFotoPerfil, gbc_lblFotoPerfil);
		}
		{
			btnNewButton = new JButton("HUECO");
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.gridx = 12;
			gbc_btnNewButton.gridy = 1;
			panelNorte.add(btnNewButton, gbc_btnNewButton);
		}
	}
	
	private void crearPanelPerfil() {
		panelPerfil = new JPanel();
		panelPerfil.setMaximumSize(new Dimension(4398, 200));
		this.fixedSize(panelPerfil, 300, 160);
		panelPerfil.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panelPerfil);
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[]{10, 100, 20, 0, 100, 20, 0, 20, 0, 0};
		gbl_panelPerfil.rowHeights = new int[]{10, 0, 10, 0, 10, 0, 20, 0, 5, 0};
		gbl_panelPerfil.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelPerfil.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelPerfil.setLayout(gbl_panelPerfil);
		{
			lblNewLabel_1 = new JLabel();
			//lblNewLabel_1.setIcon(new ImageIcon(PanelPerfilUsuario.class.getResource("/imagenes/usuario48.png")));
			this.fixedSize(lblNewLabel_1, 80, 80);
			this.añadirPerfil(lblNewLabel_1, fotoPerfil);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel_1.gridheight = 5;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 1;
			this.fixedSize(lblNewLabel_1, 70, 70);
			panelPerfil.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			//lblEmail = new JLabel("email@email.e");
			email = Controlador.getUnicaInstancia().getEmail(usuario);
			lblEmail = new JLabel(email);

			lblEmail.setFont(fuenteLabel);
			GridBagConstraints gbc_lblEmail = new GridBagConstraints();
			gbc_lblEmail.anchor = GridBagConstraints.WEST;
			gbc_lblEmail.gridwidth = 2;
			gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
			gbc_lblEmail.gridx = 3;
			gbc_lblEmail.gridy = 1;
			panelPerfil.add(lblEmail, gbc_lblEmail);
		}
		{
			btnEditarPerfil = new JButton("Editar Perfil");
			btnEditarPerfil.setForeground(Lila);
			btnEditarPerfil.setFont(fuenteLabel);
			GridBagConstraints gbc_btnEditarPerfil = new GridBagConstraints();
			gbc_btnEditarPerfil.insets = new Insets(0, 0, 5, 5);
			gbc_btnEditarPerfil.gridx = 6;
			gbc_btnEditarPerfil.gridy = 1;
			panelPerfil.add(btnEditarPerfil, gbc_btnEditarPerfil);
		}
		{
			lblNumPublicaciones = new JLabel("13 Publicaciones");
			lblNumPublicaciones.setFont(fuenteLabel);
			GridBagConstraints gbc_lblNumPublicaciones = new GridBagConstraints();
			gbc_lblNumPublicaciones.anchor = GridBagConstraints.WEST;
			gbc_lblNumPublicaciones.gridwidth = 2;
			gbc_lblNumPublicaciones.insets = new Insets(0, 0, 5, 5);
			gbc_lblNumPublicaciones.gridx = 3;
			gbc_lblNumPublicaciones.gridy = 3;
			panelPerfil.add(lblNumPublicaciones, gbc_lblNumPublicaciones);
		}
		{
			lblNumSeguidores = new JLabel("7 seguidores");
			lblNumSeguidores.setFont(fuenteLabel);
			GridBagConstraints gbc_lblNumSeguidores = new GridBagConstraints();
			gbc_lblNumSeguidores.anchor = GridBagConstraints.WEST;
			gbc_lblNumSeguidores.insets = new Insets(0, 0, 5, 5);
			gbc_lblNumSeguidores.gridx = 6;
			gbc_lblNumSeguidores.gridy = 3;
			panelPerfil.add(lblNumSeguidores, gbc_lblNumSeguidores);
		}
		{
			lblNumSeguidos = new JLabel("9 Seguidos");
			lblNumSeguidos.setFont(fuenteLabel);
			GridBagConstraints gbc_lblNumSeguidos = new GridBagConstraints();
			gbc_lblNumSeguidos.anchor = GridBagConstraints.WEST;
			gbc_lblNumSeguidos.insets = new Insets(0, 0, 5, 0);
			gbc_lblNumSeguidos.gridx = 8;
			gbc_lblNumSeguidos.gridy = 3;
			panelPerfil.add(lblNumSeguidos, gbc_lblNumSeguidos);
		}
		{
			//lblNombreUsuario = new JLabel("Nombre Usuario");
			lblNombreUsuario = new JLabel(usuario);
			lblNombreUsuario.setFont(fuenteLabel);
			//lblNombreUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
			GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
			gbc_lblNombreUsuario.anchor = GridBagConstraints.WEST;
			gbc_lblNombreUsuario.gridwidth = 4;
			gbc_lblNombreUsuario.insets = new Insets(0, 0, 5, 5);
			gbc_lblNombreUsuario.gridx = 3;
			gbc_lblNombreUsuario.gridy = 5;
			panelPerfil.add(lblNombreUsuario, gbc_lblNombreUsuario);
		}
		{
			btnFotos = new JButton("FOTOS");
			btnFotos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(panelPublicaciones.getLayout());
					cl.show(panelPublicaciones, "panelFotos");
				}
			});
			this.fixedSize(btnFotos, 80, 30);
			btnFotos.setFont(fuenteLabel);
			btnFotos.setForeground(Lila);
			GridBagConstraints gbc_btnFotos = new GridBagConstraints();
			gbc_btnFotos.anchor = GridBagConstraints.EAST;
			gbc_btnFotos.gridwidth = 2;
			gbc_btnFotos.insets = new Insets(0, 0, 5, 5);
			gbc_btnFotos.gridx = 4;
			gbc_btnFotos.gridy = 7;
			panelPerfil.add(btnFotos, gbc_btnFotos);
		}
		{
			btnAlbumes = new JButton("ALBUMES");
			btnAlbumes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(panelPublicaciones.getLayout());
					cl.show(panelPublicaciones, "panelAlbumes");
				}
			});
			this.fixedSize(btnAlbumes, 80, 30);
			btnAlbumes.setFont(fuenteLabel);
			btnAlbumes.setForeground(Lila);
			GridBagConstraints gbc_btnAlbumes = new GridBagConstraints();
			gbc_btnAlbumes.anchor = GridBagConstraints.WEST;
			gbc_btnAlbumes.insets = new Insets(0, 0, 5, 5);
			gbc_btnAlbumes.gridx = 6;
			gbc_btnAlbumes.gridy = 7;
			panelPerfil.add(btnAlbumes, gbc_btnAlbumes);
		}
	}
	
	private void crearPanelPublicaciones() {
		panelPublicaciones = new JPanel();
		panelPublicaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panelPublicaciones);
		panelPublicaciones.setLayout(new CardLayout(0, 0));
		{
			panelFotos = new JPanel();
			panelPublicaciones.add(panelFotos, "panelFotos");
			{
				lblNewLabel_2 = new JLabel("FOTOS");
				panelFotos.add(lblNewLabel_2);
			}
			{
				table = new JTable();
				panelFotos.add(table);
			}
		}
		{
			panelAlbumes = new JPanel();
			panelPublicaciones.add(panelAlbumes, "panelAlbumes");
			{
				lblNewLabel = new JLabel("Albumes");
				panelAlbumes.add(lblNewLabel);
			}
		}
	}
	
	private void añadirImagen(JLabel lbl, String ruta) {
		ImageIcon image = new ImageIcon(PanelPerfilUsuario.class.getResource(ruta));
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				lbl.getWidth()-7, 
				lbl.getHeight()-7, 
				Image.SCALE_DEFAULT));
		lbl.setIcon(icono);
	}
	
	private void añadirPerfil(JLabel lbl, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				lbl.getWidth()-7, 
				lbl.getHeight()-7, 
				Image.SCALE_DEFAULT));
		lbl.setIcon(icono);
	}
	
	
	/**
	 * Fija el tamaño de un componente
	 */
	private void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(new Dimension(100000, 100));
		o.setPreferredSize(d);
		o.setSize(d);
	}

}
