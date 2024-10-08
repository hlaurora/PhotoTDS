package gui;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador;
import dominio.Album;
import dominio.Foto;
import dominio.Publicacion;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu;

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
	private JLabel lblFotoPerfilGrande;
	private JLabel lblNumPublicaciones;
	private JLabel lblNumSeguidores;
	private JLabel lblNumSeguidos;
	private JLabel lblEmail;
	private JButton btnEditarPerfil;
	private JLabel lblNombreUsuario;
	private JFileChooser fileChooser;
	private File selectedFile;
	private JMenuItem eliminar;

	private JButton btnFotos;
	private JButton btnAlbumes;
	private JPanel panelFotos;
	private JPanel panelAlbumes;
	private JTable tableFotos;
	private DefaultTableModel tmFotos;
	private JTable tableAlbumes;
	private DefaultTableModel tmAlbum;

	private String usuarioPerfil;
	private String usuarioActual;
	private String email;
	private String fotoPerfil;
	private List<Foto> fotosUsuario;
	private int numFotos;
	private int numAlbumes;
	private PanelPerfilUsuario panelAct;

	private JPopupMenu popupEliminar;
	private JButton btnVolver;

	/**
	 * Crea el panel.
	 */
	public PanelPerfilUsuario(VentanaPrincipal vp, String nombreUsuario) {

		ventanaPrincipal = vp;
		this.usuarioPerfil = nombreUsuario;		
		this.fotoPerfil = Controlador.getUnicaInstancia().getFotoPerfil(usuarioPerfil);
		this.usuarioActual = Controlador.getUnicaInstancia().getUsuarioActual().getNombreUsuario();
		fotosUsuario = new LinkedList<Foto>();

		setSize(575, 824);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.crearPanelNorte();
		this.crearPanelPerfil();
		this.crearMenuEliminar();
		this.crearPanelPublicaciones();
		panelAct = this;
	}

	private void crearPanelNorte() {
		panelNorte = new JPanel();
		panelNorte.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.fixedSize(panelNorte, ventanaPrincipal.getFrm().getWidth(), 60);
		add(panelNorte);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[]{10, 0, 0, 30, 0, 0, 0, 0, 0, 0, 20, 0, 0, 10, 0};
		gbl_panelNorte.rowHeights = new int[]{0, 0, 0};
		gbl_panelNorte.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelNorte.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelNorte.setLayout(gbl_panelNorte);		
		{
			lblPhotoTDS = new JLabel("PhotoTDS");
			lblPhotoTDS.setForeground(Constantes.LILA);
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
			if (usuarioPerfil.equals(usuarioActual))
				this.addManejadorBotonAddFoto(btnAddFoto);
			btnAddFoto.setForeground(Constantes.LILA);
			btnAddFoto.setFont(Constantes.NEGRITA_20);
			GridBagConstraints gbc_btnAddFoto = new GridBagConstraints();
			gbc_btnAddFoto.insets = new Insets(0, 0, 0, 5);
			gbc_btnAddFoto.gridx = 5;
			gbc_btnAddFoto.gridy = 1;
			panelNorte.add(btnAddFoto, gbc_btnAddFoto);
		}
		{
			btnAddAlbum = new JButton("A+");
			if (usuarioPerfil.equals(usuarioActual)) 
				this.addManejadorAddAlbum(btnAddAlbum);
			btnAddAlbum.setForeground(Constantes.LILA);
			btnAddAlbum.setFont(Constantes.NEGRITA_20);
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
			this.addManejadorBotonBuscar();
			btnBuscar.setForeground(Constantes.LILA);
			btnBuscar.setFont(Constantes.NEGRITA_15);
			this.fixedSize(btnBuscar, 75, 35);
			GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
			gbc_btnBuscar.insets = new Insets(0, 0, 0, 5);
			gbc_btnBuscar.gridx = 9;
			gbc_btnBuscar.gridy = 1;
			panelNorte.add(btnBuscar, gbc_btnBuscar);
		}
		{
			lblFotoPerfil = new JLabel("");
			this.fixedSize(lblFotoPerfil, 60, 60);
			this.añadirPerfil(lblFotoPerfil, fotoPerfil);
			GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
			gbc_lblFotoPerfil.insets = new Insets(0, 0, 0, 5);
			gbc_lblFotoPerfil.gridx = 11;
			gbc_lblFotoPerfil.gridy = 1;
			panelNorte.add(lblFotoPerfil, gbc_lblFotoPerfil);
		}
		{
			btnVolver = new JButton("");
			this.addManejadorBotonVolver(btnVolver);
			btnVolver.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/flecha-izquierda.png")));
			GridBagConstraints gbc_btnVolver = new GridBagConstraints();
			gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
			gbc_btnVolver.gridx = 12;
			gbc_btnVolver.gridy = 1;
			panelNorte.add(btnVolver, gbc_btnVolver);
		}
	}

	private void crearPanelPerfil() {
		panelPerfil = new JPanel();
		this.fixedSize(panelPerfil, ventanaPrincipal.getFrm().getWidth(), 150);
		panelPerfil.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panelPerfil);
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[]{10, 100, 20, 0, 100, 20, 0, 20, 0, 0};
		gbl_panelPerfil.rowHeights = new int[]{10, 0, 10, 0, 10, 0, 20, 0, 5, 0};
		gbl_panelPerfil.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelPerfil.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelPerfil.setLayout(gbl_panelPerfil);
		{
			lblFotoPerfilGrande = new JLabel();
			this.fixedSize(lblFotoPerfilGrande, 80, 80);
			this.añadirPerfil(lblFotoPerfilGrande, fotoPerfil);
			GridBagConstraints gbc_lblFotoPerfilGrande = new GridBagConstraints();
			gbc_lblFotoPerfilGrande.fill = GridBagConstraints.VERTICAL;
			gbc_lblFotoPerfilGrande.gridheight = 5;
			gbc_lblFotoPerfilGrande.insets = new Insets(0, 0, 5, 5);
			gbc_lblFotoPerfilGrande.gridx = 1;
			gbc_lblFotoPerfilGrande.gridy = 1;
			this.fixedSize(lblFotoPerfilGrande, 70, 70);
			panelPerfil.add(lblFotoPerfilGrande, gbc_lblFotoPerfilGrande);
		}
		{
			email = Controlador.getUnicaInstancia().getDato("email", usuarioPerfil);
			lblEmail = new JLabel(email);

			lblEmail.setFont(Constantes.NORMAL_15);
			GridBagConstraints gbc_lblEmail = new GridBagConstraints();
			gbc_lblEmail.anchor = GridBagConstraints.WEST;
			gbc_lblEmail.gridwidth = 2;
			gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
			gbc_lblEmail.gridx = 3;
			gbc_lblEmail.gridy = 1;
			panelPerfil.add(lblEmail, gbc_lblEmail);
		}

		{
			btnEditarPerfil = new JButton("");

			if (usuarioPerfil.equals(usuarioActual)){
				btnEditarPerfil.setText("Editar Perfil");
				this.addManejadorBotonEditarPerfil();
			}

			else if(Controlador.getUnicaInstancia().sigue(usuarioActual, usuarioPerfil)) {
				btnEditarPerfil.setText("Siguiendo");
			}

			else {
				btnEditarPerfil.setText("Seguir");
				this.addManejadorBotonSeguir();
			}

			btnEditarPerfil.setForeground(Constantes.LILA);
			btnEditarPerfil.setFont(Constantes.NEGRITA_15);
			GridBagConstraints gbc_btnEditarPerfil = new GridBagConstraints();
			gbc_btnEditarPerfil.insets = new Insets(0, 0, 5, 5);
			gbc_btnEditarPerfil.gridx = 6;
			gbc_btnEditarPerfil.gridy = 1;
			panelPerfil.add(btnEditarPerfil, gbc_btnEditarPerfil);
		}
		{
			lblNumPublicaciones = new JLabel("");
			lblNumPublicaciones.setText(Controlador.getUnicaInstancia().getNumPublicaciones(usuarioPerfil)
					+ " Publicaciones");
			lblNumPublicaciones.setFont(Constantes.NORMAL_15);
			GridBagConstraints gbc_lblNumPublicaciones = new GridBagConstraints();
			gbc_lblNumPublicaciones.anchor = GridBagConstraints.WEST;
			gbc_lblNumPublicaciones.gridwidth = 2;
			gbc_lblNumPublicaciones.insets = new Insets(0, 0, 5, 5);
			gbc_lblNumPublicaciones.gridx = 3;
			gbc_lblNumPublicaciones.gridy = 3;
			panelPerfil.add(lblNumPublicaciones, gbc_lblNumPublicaciones);
		}
		{
			lblNumSeguidores = new JLabel(Controlador.getUnicaInstancia().getNumSeguidores(usuarioPerfil) 
					+ " Seguidores");
			lblNumSeguidores.setFont(Constantes.NORMAL_15);
			GridBagConstraints gbc_lblNumSeguidores = new GridBagConstraints();
			gbc_lblNumSeguidores.anchor = GridBagConstraints.WEST;
			gbc_lblNumSeguidores.insets = new Insets(0, 0, 5, 5);
			gbc_lblNumSeguidores.gridx = 6;
			gbc_lblNumSeguidores.gridy = 3;
			panelPerfil.add(lblNumSeguidores, gbc_lblNumSeguidores);
		}
		{
			lblNumSeguidos = new JLabel(Controlador.getUnicaInstancia().getNumSeguidos(usuarioPerfil)
					+ " Seguidos");
			lblNumSeguidos.setFont(Constantes.NORMAL_15);
			GridBagConstraints gbc_lblNumSeguidos = new GridBagConstraints();
			gbc_lblNumSeguidos.anchor = GridBagConstraints.WEST;
			gbc_lblNumSeguidos.insets = new Insets(0, 0, 5, 0);
			gbc_lblNumSeguidos.gridx = 8;
			gbc_lblNumSeguidos.gridy = 3;
			panelPerfil.add(lblNumSeguidos, gbc_lblNumSeguidos);
		}
		{
			lblNombreUsuario = new JLabel(usuarioPerfil);
			lblNombreUsuario.setFont(Constantes.NORMAL_15);
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
			btnFotos.setFont(Constantes.NEGRITA_15);
			btnFotos.setForeground(Constantes.LILA);
			GridBagConstraints gbc_btnFotos = new GridBagConstraints();
			gbc_btnFotos.anchor = GridBagConstraints.EAST;
			gbc_btnFotos.gridwidth = 2;
			gbc_btnFotos.insets = new Insets(0, 0, 5, 5);
			gbc_btnFotos.gridx = 4;
			gbc_btnFotos.gridy = 7;
			panelPerfil.add(btnFotos, gbc_btnFotos);
		}
		{
			btnAlbumes = new JButton("ÁLBUMES");
			btnAlbumes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(panelPublicaciones.getLayout());
					cl.show(panelPublicaciones, "panelAlbumes");
				}
			});
			this.fixedSize(btnAlbumes, 100, 30);
			btnAlbumes.setFont(Constantes.NEGRITA_15);
			btnAlbumes.setForeground(Constantes.LILA);
			GridBagConstraints gbc_btnAlbumes = new GridBagConstraints();
			gbc_btnAlbumes.anchor = GridBagConstraints.WEST;
			gbc_btnAlbumes.insets = new Insets(0, 0, 5, 5);
			gbc_btnAlbumes.gridx = 6;
			gbc_btnAlbumes.gridy = 7;
			panelPerfil.add(btnAlbumes, gbc_btnAlbumes);
		}
	}

	public void crearPanelPublicaciones() {
		panelPublicaciones = new JPanel();
		this.fixedSize(panelPublicaciones, ventanaPrincipal.getFrm().getWidth(), 250);
		panelPublicaciones.setMaximumSize(new Dimension(10000, 100000));
		panelPublicaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panelPublicaciones);
		panelPublicaciones.setLayout(new CardLayout(0, 0));

		{
			panelFotos = new JPanel();
			panelPublicaciones.add(panelFotos, "panelFotos");
			{
				String titulos[] = {"col1", "col2", "col3"};
				tmFotos = new DefaultTableModel(null, titulos);
				{
					tableFotos = new JTable();
					tableFotos.setRowHeight(70);
					tableFotos.setModel(tmFotos);
					tableFotos.setTableHeader(null);
					tableFotos.setDefaultRenderer(Object.class, new ImgTabla());
				}
				tableFotos.setDefaultEditor(Object.class, null);  
				for (int i = 0; i < 3; i++) {
					tableFotos.getColumnModel().getColumn(i).setPreferredWidth(170);
				}
				this.mostrarFotos();

				JScrollPane scrollPane = new JScrollPane(tableFotos);
				this.fixedSize(scrollPane, 500, 300);
				panelFotos.add(scrollPane);

			}
		}
		{
			panelAlbumes = new JPanel();
			panelPublicaciones.add(panelAlbumes, "panelAlbumes");
			{
				String titulos[] = {"col1", "col2", "col3", "col4"};
				tmAlbum = new DefaultTableModel(null, titulos);
				{
					tableAlbumes = new JTable();
					tableAlbumes.setRowHeight(70);
					tableAlbumes.setModel(tmAlbum);
					tableAlbumes.setTableHeader(null);
					tableAlbumes.setDefaultRenderer(Object.class, new ImgTabla());
				}
				tableAlbumes.setDefaultEditor(Object.class, null);  
				for (int i = 0; i < 4; i++) {
					tableAlbumes.getColumnModel().getColumn(i).setPreferredWidth(120);
				}
				this.mostrarAlbumes();

				JScrollPane scrollPane2 = new JScrollPane(tableAlbumes);
				this.fixedSize(scrollPane2, 520, 300);
				panelAlbumes.add(scrollPane2);
			}
		}
	}

	public void crearMenuEliminar() {
		popupEliminar = new JPopupMenu();
		eliminar = new JMenuItem("Eliminar");
		popupEliminar.add(eliminar);
	}

	//////////////////////////////////////
	///// MANEJADORES DE LOS BOTONES /////
	//////////////////////////////////////


	// Botón addFoto
	private void addManejadorBotonAddFoto(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAddPublicacion v = new VentanaAddPublicacion(usuarioActual, panelAct);
				v.setLocationRelativeTo(btn);
				v.setVisible(true);
			}
		});
	}

	// Botón addAlbum
	private void addManejadorAddAlbum(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreAlbum = JOptionPane.showInputDialog("Introduzca el nombre del nuevo álbum:");
				if (!Controlador.getUnicaInstancia().existeAlbum(usuarioActual, nombreAlbum)) {
					fileChooser = new JFileChooser();
					int seleccion = fileChooser.showOpenDialog(btn);
					if (seleccion != JFileChooser.CANCEL_OPTION) {
						selectedFile = fileChooser.getSelectedFile();
						VentanaPublicacion vap = new VentanaPublicacion(usuarioActual,
								selectedFile.getPath());
						vap.setVisible(true);	
						vap.setLocationRelativeTo(btnAddAlbum);
						vap.crearAlbum(nombreAlbum, panelAct);
						lblNumPublicaciones.setText(Controlador.getUnicaInstancia().getNumPublicaciones(usuarioPerfil)
								+ " Publicaciones");
					}
				}
				else {
					JOptionPane.showMessageDialog(panelAct, "Ya existe un album con este nombre.\n",
							"PanelPerfilUsuario", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	// Botón buscar (usuarios cuyo nombre/nombreUsuario/email coincide con el buscado)
	private void addManejadorBotonBuscar() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cadena = textBuscar.getText();
				if (!cadena.isEmpty()) {
					VentanaBusqueda vb = new VentanaBusqueda(ventanaPrincipal, cadena);
					vb.setLocationRelativeTo(btnBuscar);
					vb.setVisible(true);
				}
			}
		});
	}

	// Botón volver: vuelve a la VentanaPrincipal
	private void addManejadorBotonVolver(JButton btnVolver) {
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrmPrincipal f = new FrmPrincipal();
				VentanaPrincipal vp = new VentanaPrincipal(f);
				vp.setVisible(true);
				f.setVisible(true);
				ventanaPrincipal.cerrar();
			}
		});
	}

	// Botón editarPerfil
	private void addManejadorBotonEditarPerfil() {
		btnEditarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistro ventanaRegistro = new VentanaRegistro(ventanaPrincipal.getFrm());
				ventanaRegistro.editarPerfil(usuarioPerfil, panelAct);
				ventanaRegistro.mostrarVentana();
			}
		});
	}

	// Para actualizar la foto si la cambia
	public void actualizarFoto() {
		this.añadirPerfil(lblFotoPerfil, Controlador.getUnicaInstancia().getFotoPerfil(usuarioPerfil));
		this.añadirPerfil(lblFotoPerfilGrande, Controlador.getUnicaInstancia().getFotoPerfil(usuarioPerfil));		
	}

	// Botón seguir
	private void addManejadorBotonSeguir() {
		btnEditarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controlador.getUnicaInstancia().seguirUsuario(usuarioActual, usuarioPerfil);
				lblNumSeguidores.setText(Controlador.getUnicaInstancia().getNumSeguidores(usuarioPerfil) + " Seguidores");
			}
		});
	}

	// Manejador para la tabla de fotos
	private void addManejadorTablaFotos(JTable tabla) {
		int numFilas = tabla.getRowCount();
		List<Foto> fotosUsuario = Controlador.getUnicaInstancia().getFotos(usuarioPerfil);

		MouseListener[] listeners = tabla.getMouseListeners();
		//Eliminar todos los MouseListener de la tabla
		for(MouseListener listener : listeners){
			tabla.removeMouseListener(listener);
		}

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tabla.rowAtPoint(evt.getPoint());
				int col = tabla.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {   	
					int pos = (col+((row%numFilas)*3));
					if (pos <= fotosUsuario.size()-1) {
						if (SwingUtilities.isLeftMouseButton(evt)) {
							VentanaPublicacion va = new VentanaPublicacion(usuarioActual,
									fotosUsuario.get(pos).getRuta());
							va.setLocationRelativeTo(panelAct);
							va.verFoto(fotosUsuario.get(pos));
							va.setVisible(true);
						} 
						else if (SwingUtilities.isRightMouseButton(evt)&&(usuarioActual.endsWith(usuarioPerfil))) {
							popupEliminar.show(tabla, evt.getX(), evt.getY());
							addManejadorEliminar(eliminar, fotosUsuario.get(pos)); 
						}
					}
				}
			}
		});
	}

	// Manejador para el menú Eliminar
	private void addManejadorEliminar(JMenuItem item, Publicacion p) {
		ActionListener[] listeners = eliminar.getActionListeners();
		//Eliminar todos los ActionListeners del botón
		for(ActionListener listener : listeners){
			eliminar.removeActionListener(listener);
		}

		eliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Controlador.getUnicaInstancia().eliminarPublicacion(p)) {
					mostrarFotos();
					mostrarAlbumes();
					lblNumPublicaciones.setText(Controlador.getUnicaInstancia().getNumPublicaciones(usuarioPerfil)
							+ " Publicaciones");
				}
			}
		});
	}

	// Manejador para la tabla de álbumes
	private void addManejadorTablaAlbum(JTable tabla) {
		int numFilas = tabla.getRowCount();
		List<Album> albumesUsuario = Controlador.getUnicaInstancia().getAlbumes(usuarioPerfil);

		MouseListener[] listeners = tabla.getMouseListeners();
		//Eliminar todos los MouseListener de la tabla
		for(MouseListener listener : listeners){
			tabla.removeMouseListener(listener);
		}

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tabla.rowAtPoint(evt.getPoint());
				int col = tabla.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					int pos = (col+((row%numFilas)*4));
					if (pos <= albumesUsuario.size()-1) {
						if (SwingUtilities.isLeftMouseButton(evt)) {
							VentanaAlbum va = new VentanaAlbum(albumesUsuario.get(pos));
							va.setLocationRelativeTo(tabla);
							va.setVisible(true);
						}
						else if (SwingUtilities.isRightMouseButton(evt)) {
							popupEliminar.show(tabla, evt.getX(), evt.getY());
							addManejadorEliminar(eliminar, albumesUsuario.get(pos)); 
						}
					}
				}
			}
		});
	}

	// Rellena la tabla con las fotos del  usuario de más a menos reciente
	private void mostrarFotos() {
		fotosUsuario = Controlador.getUnicaInstancia().getFotos(usuarioPerfil);

		//Limpiamos la tabla
		for (int i = 0; i < tableFotos.getRowCount(); i++) {
			tmFotos.removeRow(i);
			i-=1;
		}

		numFotos = fotosUsuario.size();
		String ruta;
		//Calculamos el número de filas
		int numFilas = 0;
		if ((0 < numFotos) && (numFotos <= 3)) {
			numFilas = 1;
		}
		else if (numFotos > 3) numFilas = (numFotos/3) + 1;

		//Rellenamos la tabla
		int j;
		int f = 0;

		if (numFilas == 1) {
			tmFotos.addRow(new Object[] {null,null, null});
			for (j = 0; j < fotosUsuario.size(); j++) {
				ruta = fotosUsuario.get(j).getRuta();
				tmFotos.setValueAt(new JLabel(imagenCelda(ruta)), 0, j);
			}
		}

		else {
			int i = 0;
			while (i < numFilas-1) {
				tmFotos.addRow(new Object[] {null,null, null});
				for (j = 0; j<3; j++) {
					ruta = fotosUsuario.get(f).getRuta();
					tmFotos.setValueAt(new JLabel(imagenCelda(ruta)), i, j);
					f++;
				}
				i++;
			}
			//Última fila
			tmFotos.addRow(new Object[] {null, null, null});
			while (f < numFotos) {
				ruta = fotosUsuario.get(f).getRuta();
				tmFotos.setValueAt(new JLabel(imagenCelda(ruta)), i, f%3);
				f++;
			}
		}	
		this.addManejadorTablaFotos(tableFotos);
	}

	// Rellena la tabla con los álbumes del usuario (foto principal de cada álbum)
	private void mostrarAlbumes() {

		List<Album> albumesUsuario = Controlador.getUnicaInstancia().getAlbumes(usuarioPerfil);

		//Limpiamos la tabla
		for (int i = 0; i < tableAlbumes.getRowCount(); i++) {
			tmAlbum.removeRow(i);
			i-=1;
		}

		numAlbumes = albumesUsuario.size();

		Collections.reverse(albumesUsuario);
		String ruta;
		//Calculamos el número de filas
		int numFilas = 0;
		if ((0 < numAlbumes) && (numAlbumes <= 4)) {
			numFilas = 1;
		}
		else if (numAlbumes > 3) numFilas = (numAlbumes/4) + 1;

		//Rellenamos la tabla
		int j;
		int f = 0;

		if (numFilas == 1) {
			tmAlbum.addRow(new Object[] {null, null, null, null});
			for (j = 0; j < albumesUsuario.size(); j++) {
				ruta = albumesUsuario.get(j).getFotos().get(0).getRuta();
				tmAlbum.setValueAt(new JLabel(imagenCelda(ruta)), 0, j);
			}
		}

		else {
			int i = 0;
			while (i < numFilas-1) {
				tmAlbum.addRow(new Object[] {null ,null, null, null});
				for (j = 0; j<4; j++) {
					ruta = albumesUsuario.get(f).getFotos().get(0).getRuta();
					tmAlbum.setValueAt(new JLabel(imagenCelda(ruta)), i, j);
					f++;
				}
				i++;
			}
			//Última fila
			tmAlbum.addRow(new Object[] {null, null, null, null});
			while (f < numAlbumes) {
				ruta = albumesUsuario.get(f).getFotos().get(0).getRuta();
				tmAlbum.setValueAt(new JLabel(imagenCelda(ruta)), i, f%4);
				f++;
			}
		}	
		this.addManejadorTablaAlbum(tableAlbumes);
	}

	// Llama a mostrar fotos y álbumes para actualizar las tablas
	public void actualizar() {
		lblNumPublicaciones.setText(Controlador.getUnicaInstancia().getNumPublicaciones(usuarioPerfil)
				+ " Publicaciones");
		mostrarFotos();
		mostrarAlbumes();
	}


	/**
	 * Fija el tamaño de un componente
	 */
	public void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
		o.setSize(d);
	}

	/**
	 * Cambia el tamaño de la imagen para la etiqueta de la foto de perfil
	 */
	private void añadirPerfil(JLabel lbl, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				lbl.getWidth()-7, 
				lbl.getHeight()-7, 
				Image.SCALE_DEFAULT));
		lbl.setIcon(icono);
	}

	/**
	 * Fija el tamaño de una imagen para las celdas de las tablas
	 */
	private Icon imagenCelda(String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				170, 
				70-4, 
				Image.SCALE_DEFAULT));
		return icono;
	}
}
