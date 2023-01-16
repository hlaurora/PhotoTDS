package gui;

import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import controlador.Controlador;
import dominio.Foto;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//public class VentanaPrincipal extends JFrame{
public class VentanaPrincipal extends JPanel{

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
	private JPopupMenu popupMenu;
	private JButton btnMenu;
	private JFileChooser fileChooser;
	private File selectedFile;
	private JList<String> jListaDescuentos;
	
	private String usuarioActual;
	private String fotoPerfil;
	private Font fontBtn = new Font("Arial", Font.BOLD, 15);
		
	private PanelPerfilUsuario panelPerfilUsuario;
	private JPanel panelPublicaciones;
	
	private VentanaPrincipal ventanaPrincipal;
	private Component rigidArea_2;
	

	/*public void mostrarVentana() {
		setLocationRelativeTo(null);
		setVisible(true);
	}*/

	/**
	 * Create the application.
	 */
	public VentanaPrincipal(FrmPrincipal frm, String u) {	
		this.frmPrincipal = frm;
		this.usuarioActual = Controlador.getUnicaInstancia().getNombreUsuario(u);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
				
		panelPerfilUsuario = new PanelPerfilUsuario(this, usuarioActual);
		this.fotoPerfil = Controlador.getUnicaInstancia().getFotoPerfil(usuarioActual);
		
		//frmPrincipal = new JFrame();
		//frmPrincipal.setBounds(100, 100, 548, 570);
		//frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		crearPanelNorte();
		crearPanelPublicaciones();
		if (Controlador.getUnicaInstancia().isPremium(usuarioActual))
			crearMenuOpcionesPremium();
		else
			crearPopupMenu();
		ventanaPrincipal = this;
	}
	
	
	private void crearPanelNorte() {
		panelNorte = new JPanel();
		frmPrincipal.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		
		this.fixedSize(panelNorte, 550, 50);
		
		lblPhotoTDS = new JLabel("PhotoTDS");
		lblPhotoTDS.setForeground(Constantes.LILA);
		lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 30));
		lblPhotoTDS.setPreferredSize(new Dimension(170, 0));
		panelNorte.add(lblPhotoTDS);
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(10, 10));
		panelNorte.add(rigidArea);
		
		//Botón para añadir fotos
		btnAddFoto = new JButton("+");
		this.addManejadorBotonAddFoto(btnAddFoto);
		btnAddFoto.setForeground(Constantes.LILA);
		btnAddFoto.setFont(new Font("HP Simplified Hans", Font.BOLD, 20));
		this.fixedSize(btnAddFoto, 40, 40);
		panelNorte.add(btnAddFoto);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setMaximumSize(new Dimension(20, 20));
		panelNorte.add(horizontalStrut);
		
		//Crea panel norte
		panel = new JPanel();
		panelNorte.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		//TextField para buscar usuarios y publicaciones
		textField = new JTextField();
		this.fixedSize(textField, 90, 27);
		panel.add(textField);
		textField.setColumns(10);
		
		//Botón para buscar
		btnBuscar = new JButton("Buscar");
		this.addManejadorBotonBuscar();
		btnBuscar.setForeground(Constantes.LILA);
		btnBuscar.setFont(fontBtn);
		btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.fixedSize(btnBuscar, 70, 40);
		panel.add(btnBuscar);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelNorte.add(rigidArea_1);
		
		//Botón para abrir el panel del perfil del usuario
		btnPerfil = new JButton("");
		addManejadorBotonPerfil(btnPerfil);
		btnPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.fixedSize(btnPerfil, 50, 50);
		this.añadirPerfil(btnPerfil, fotoPerfil);
		panelNorte.add(btnPerfil);
		
		rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelNorte.add(rigidArea_2);
		
		btnMenu = new JButton("Menu");
		btnMenu.setForeground(Constantes.LILA);
		btnMenu.setFont(fontBtn);
		this.fixedSize(btnMenu, 60, 40);
		panelNorte.add(btnMenu);
		this.addManejadorBotonMenu(btnMenu);
		
	}
	
	//Crear el panel de las publicaciones
	private void crearPanelPublicaciones() {
		panelPublicaciones = new JPanel();
		
		frmPrincipal.getContentPane().add(panelPublicaciones, BorderLayout.CENTER);
		scrollPane = new JScrollPane(panelPublicaciones);
		frmPrincipal.setResizable(true);
		this.fixedSize(scrollPane, 600, 450);
		//this.fixedSize(scrollPane, 600, (frmPrincipal.getHeight()-panelNorte.getHeight()));
		//panelPublicaciones.add(scrollPane);
		
		//scrollPane.setViewportView(panelPublicaciones);
		frmPrincipal.getContentPane().add(scrollPane, BorderLayout.CENTER);
		panelPublicaciones.setLayout(new BoxLayout(panelPublicaciones, BoxLayout.Y_AXIS));
		
		this.mostrarPublicaciones();
	}
	
	//Añade las publicaciones al panel
	public void mostrarPublicaciones() {
		panelPublicaciones.removeAll();
		JPanel publi;
		int i = 0;
		List<Foto> fotos = Controlador.getUnicaInstancia().getFotosSeguidos(usuarioActual);
		for (Foto f : fotos) {
			publi = new PanelPublicacion(f, i, frmPrincipal);
			i++;
			this.fixedSize(publi, frmPrincipal.getWidth()-40, 90);
			panelPublicaciones.add(publi);
		}
	}
	
	
	private void añadirPerfil(JButton btn, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				btn.getWidth()-7, 
				btn.getHeight()-7, 
				Image.SCALE_DEFAULT));
		btn.setIcon(icono);
	}
	
	private void crearPopupMenu() {
		popupMenu = new JPopupMenu();
		JMenuItem premium = new JMenuItem("Premium");
		popupMenu.add(premium);
		premium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addListaDescuentos();
			}
		});
	}
	
	private void crearMenuOpcionesPremium() {
		popupMenu = new JPopupMenu();
		JMenuItem generarPdf = new JMenuItem("Generar PDF");
		popupMenu.add(generarPdf);
		
	}
	
	private void addListaDescuentos() {
		//List<String> listaHashtags = Controlador.getUnicaInstancia().buscarHashtags(hashtag);
		
		List<String> listaDescuentos = new LinkedList<String>();
		listaDescuentos.add("Descuento por edad");
		listaDescuentos.add("Descuento por MeGustas");
		
		jListaDescuentos = new JList<String>(listaDescuentos.toArray
								(new String[listaDescuentos.size()]));
		jListaDescuentos.setCellRenderer(createListRenderer());
		jListaDescuentos.setPreferredSize(new Dimension(200, ABORT));
		jListaDescuentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollListaFotos = new JScrollPane(jListaDescuentos);

		JDialog dialog = new JDialog();
		dialog.setLocationRelativeTo(btnMenu);
		dialog.setTitle("Descuentos premium");
		dialog.setSize(200, 200);
		dialog.add(scrollListaFotos);
		dialog.setVisible(true);
		
		
		jListaDescuentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String d;
				if ((d = jListaDescuentos.getSelectedValue()) != null) {
					int result = JOptionPane.showConfirmDialog(frmPrincipal, "Va a pagar " + d,
							"Confirm Dialog", JOptionPane.CANCEL_OPTION);	
					if (result == JOptionPane.YES_OPTION) {
						dialog.setVisible(false);
						Controlador.getUnicaInstancia().hacerPremium(usuarioActual);
					   // Handle 'Yes'
					} else {
					   // Handle 'No'
					}
				}
			}
		});

		
	}
	
	private static ListCellRenderer<? super String> createListRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value,
					int index, boolean isSelected,
					boolean cellHasFocus) {
			{
				Component c = super.getListCellRendererComponent(
							list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel) {
					JLabel label = (JLabel) c;
					String descuento = (String) value;
					label.setText(descuento);
					}
				return c;
			}
		};
		};
	}
	
	//MANEJADORES DE LOS BOTONES
	
	// Botón menú
	private void addManejadorBotonMenu(JButton btn) {
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(btn, e.getX() , e.getY());
			}
		});
	}
	
	//Botón Perfil (abre el perfil del usuario actual)
	private void addManejadorBotonPerfil(JButton btn) {
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmPrincipal.setContentPane(panelPerfilUsuario);
			}
		});
	}
	
	//Botón addFoto (abre el fileChooser y añade foto)
	private void addManejadorBotonAddFoto(JButton btn) {
		btnAddFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				int seleccion = fileChooser.showOpenDialog(btnAddFoto);
				if (seleccion != JFileChooser.CANCEL_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					VentanaPublicacion vap = new VentanaPublicacion(usuarioActual,
							selectedFile.getPath(), ventanaPrincipal);
					vap.setVisible(true);
					vap.setLocationRelativeTo(btnAddFoto);
					vap.compartirFoto();
				}				
			}
		});
	}
	
	//Botón buscar (usuarios cuyo nombre/nombreUsuario/email coincide con el buscado)
	private void addManejadorBotonBuscar() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cadena = textField.getText();
				if (!cadena.isEmpty()) {
					VentanaBusqueda vb = new VentanaBusqueda(ventanaPrincipal, cadena);
					vb.setLocationRelativeTo(btnBuscar);
					vb.setVisible(true);
				}
			}
		});
	}
	
	//Abre perfil del usuario indicado al seleccionar en la lista
	public void abirPerfil(String nombreUsuario) {
		frmPrincipal.setContentPane(new PanelPerfilUsuario(this, nombreUsuario));
	}
	
	public int getWidth() {
		return frmPrincipal.getWidth();
	}
	
	public String getUsuario() {
		return this.usuarioActual;
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
