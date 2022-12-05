package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import controlador.Controlador;
import dominio.Foto;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame{

	public JFrame frmPrincipal;
	
	private JLabel lblPhotoTDS;
	private JPanel panelNorte;
	private Component rigidArea;
	private JButton btnAddFoto;
	private JPanel panel;
	private JButton btnPerfil;
	private Component horizontalStrut;
	private JTextField textField;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private Component rigidArea_1;
	private JMenu menu;
	private JPopupMenu popupMenu;
	
	private String usuario;
	private String fotoPerfil;
	private Color LILA = new Color(134, 46, 150);
		
	private PanelPerfilUsuario panelPerfilUsuario;
	private JPanel panelPublicaciones;

	/**
	 * Create the application.
	 */
	public VentanaPrincipal(String u) {	
		this.usuario = Controlador.getUnicaInstancia().getNombreUsuario(u);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
				
		panelPerfilUsuario = new PanelPerfilUsuario(this, usuario);
		this.fotoPerfil = Controlador.getUnicaInstancia().getFotoPerfil(usuario);
		
		
		frmPrincipal = new JFrame();
		frmPrincipal.setBounds(100, 100, 548, 570);
		frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		crearPanelNorte();
		crearPanelPublicaciones();
	}
	
	
	private void crearPanelNorte() {
		panelNorte = new JPanel();
		frmPrincipal.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		
		lblPhotoTDS = new JLabel("PhotoTDS");
		lblPhotoTDS.setForeground(LILA);
		lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 30));
		lblPhotoTDS.setPreferredSize(new Dimension(170, 0));
		panelNorte.add(lblPhotoTDS);
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setMaximumSize(new Dimension(70, 20));
		rigidArea.setPreferredSize(new Dimension(50, 20));
		panelNorte.add(rigidArea);
		
		btnAddFoto = new JButton("+");
		btnAddFoto.setForeground(LILA);
		btnAddFoto.setFont(new Font("HP Simplified Hans", Font.BOLD, 20));
		this.fixedSize(btnAddFoto, 40, 40);
		panelNorte.add(btnAddFoto);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panelNorte.add(horizontalStrut);
		
		panel = new JPanel();
		panelNorte.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		this.fixedSize(textField, 90, 27);
		panel.add(textField);
		textField.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setForeground(LILA);
		btnBuscar.setFont(new Font("HP Simplified Hans", Font.BOLD, 15));
		btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.fixedSize(btnBuscar, 70, 40);
		panel.add(btnBuscar);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelNorte.add(rigidArea_1);
		
		btnPerfil = new JButton("");
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmPrincipal.setContentPane(panelPerfilUsuario);
			}
		});
		btnPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.fixedSize(btnPerfil, 50, 50);
		this.añadirPerfil(btnPerfil, fotoPerfil);
		
		panelNorte.add(btnPerfil);

		menu = new JMenu("New menu");
		panelNorte.add(menu);
		
		popupMenu = new JPopupMenu();
		addPopup(menu, popupMenu);
	}
	
	private void crearPanelPublicaciones() {
		scrollPane = new JScrollPane();
		frmPrincipal.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panelPublicaciones = new JPanel();
		scrollPane.setViewportView(panelPublicaciones);
		panelPublicaciones.setLayout(new BoxLayout(panelPublicaciones, BoxLayout.Y_AXIS));
		
		JPanel publi;
		for (Foto f : Controlador.getUnicaInstancia().getFotos(usuario)) {
			publi = this.panelPublicacion(f.getRuta(), fotoPerfil, usuario);
			panelPublicaciones.add(publi);
		}
	}
	

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	
	private void añadirPerfil(JButton btn, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				btn.getWidth()-7, 
				btn.getHeight()-7, 
				Image.SCALE_DEFAULT));
		btn.setIcon(icono);
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

	
	public int getWidth() {
		return frmPrincipal.getWidth();
	}

	public JPanel panelPublicacion(String r, String fp, String nu) {
		JPanel panelPublicacion = new JPanel();
		this.fixedSize(panelPublicacion, 500, 80);
		panelPublicacion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 49, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{5, 14, 0, 0, 0, 0, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelPublicacion.setLayout(gridBagLayout);
		
		JButton btnMeGusta = new JButton("");
		btnMeGusta.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/icons8-me-gusta-16.png")));
		GridBagConstraints gbc_btnMeGusta = new GridBagConstraints();
		gbc_btnMeGusta.insets = new Insets(0, 0, 5, 5);
		gbc_btnMeGusta.gridx = 1;
		gbc_btnMeGusta.gridy = 2;
		panelPublicacion.add(btnMeGusta, gbc_btnMeGusta);
		
		JButton btnComentario = new JButton("");
		btnComentario.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/comentario.png")));
		GridBagConstraints gbc_btnComentario = new GridBagConstraints();
		gbc_btnComentario.insets = new Insets(0, 0, 5, 5);
		gbc_btnComentario.gridx = 2;
		gbc_btnComentario.gridy = 2;
		panelPublicacion.add(btnComentario, gbc_btnComentario);
		
		JLabel lblNumMg = new JLabel("9 Me Gustas");
		GridBagConstraints gbc_lblNumMg = new GridBagConstraints();
		gbc_lblNumMg.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumMg.gridx = 3;
		gbc_lblNumMg.gridy = 2;
		panelPublicacion.add(lblNumMg, gbc_lblNumMg);
		
		JLabel lblImagen = new JLabel("");
		this.fixedSize(lblImagen, 150, 90);
		lblImagen.setIcon(escalarImagen(lblImagen, r));
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.gridheight = 5;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 0;
		gbc_lblImagen.gridy = 1;
		panelPublicacion.add(lblImagen, gbc_lblImagen);
		
		JLabel lblFotoPerfil = new JLabel("");
		this.fixedSize(lblFotoPerfil, 60, 60);
		lblFotoPerfil.setIcon(escalarImagen(lblFotoPerfil, fp));
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 1;
		gbc_lblFotoPerfil.gridy = 4;
		panelPublicacion.add(lblFotoPerfil, gbc_lblFotoPerfil);
		
		JLabel lblNombreUsuario = new JLabel(nu);
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreUsuario.gridx = 2;
		gbc_lblNombreUsuario.gridy = 4;
		panelPublicacion.add(lblNombreUsuario, gbc_lblNombreUsuario);

		return panelPublicacion;
	}
	
	
	private Icon escalarImagen(JLabel lbl, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				lbl.getWidth()-4, 
				lbl.getHeight()-4, 
				Image.SCALE_DEFAULT));
		return icono;
	}
}
