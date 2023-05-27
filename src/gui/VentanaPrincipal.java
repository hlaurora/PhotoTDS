package gui;

import java.awt.Font;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JList;

import controlador.Controlador;
import dominio.Foto;
import javax.swing.JPanel;
import java.awt.BorderLayout;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import pulsador.IEncendidoListener;
import pulsador.Luz;

//public class VentanaPrincipal extends JFrame{
public class VentanaPrincipal extends JPanel{

	private FrmPrincipal frmPrincipal;
	
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
	private JList<String> jListaDescuentos;
	
	private String usuarioActual;
	private String fotoPerfil;
		
	private PanelPerfilUsuario panelPerfilUsuario;
	private JPanel panelPublicaciones;
	
	private VentanaPrincipal ventanaPrincipal;
	private Component rigidArea_2;
	private Luz luz;
	private JPanel panelNoti;
	private JButton btnNoti;

	/**
	 * Create the application.
	 */
	public VentanaPrincipal(FrmPrincipal frm) {	
		this.frmPrincipal = frm;
		this.usuarioActual = Controlador.getUnicaInstancia().getUsuarioActual().getNombreUsuario();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
				
		this.fotoPerfil = Controlador.getUnicaInstancia().getFotoPerfil(usuarioActual);
		crearPanelNorte();
		crearPanelPublicaciones();
		ventanaPrincipal = this;
	}
	
	
	private void crearPanelNorte() {
		panelNorte = new JPanel();
		frmPrincipal.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		
		this.fixedSize(panelNorte, 590, 50);
		
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
		btnAddFoto.setFont(Constantes.NEGRITA_20);
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
		btnBuscar.setFont(Constantes.NEGRITA_15);
		btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.fixedSize(btnBuscar, 70, 40);
		panel.add(btnBuscar);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(15, 20));
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
	
		luz = new Luz();
		panelNorte.add(luz);
		luz.setColor(Constantes.LILA);
		this.addManejadorPulsador(luz);
		
		panelNoti = new JPanel();
		panelNorte.add(panelNoti);
		btnNoti = new JButton("");
		this.fixedSize(btnNoti, 36, 34);
		panelNoti.add(btnNoti);
		btnNoti.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/notificacion.png")));
		this.addManejadorBotonNotificaciones(btnNoti);
		
		btnMenu = new JButton("Menú");
		btnMenu.setForeground(Constantes.LILA);
		btnMenu.setFont(Constantes.NEGRITA_15);
		this.fixedSize(btnMenu, 55, 40);
		panelNorte.add(btnMenu);
		this.addManejadorBotonMenu(btnMenu);
		
	}
	

	//Crear el panel de las publicaciones
	private void crearPanelPublicaciones() {
		panelPublicaciones = new JPanel();
		panelPublicaciones.setLayout(new BoxLayout(panelPublicaciones, BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane(panelPublicaciones);
				
		frmPrincipal.setResizable(true);
		this.fixedSize(scrollPane, 590, 450);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frmPrincipal.getContentPane().add(scrollPane, BorderLayout.CENTER);

		
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
			this.fixedSize(publi, frmPrincipal.getWidth()-55, 90);
			panelPublicaciones.add(publi);
			
		}
		panelPublicaciones.revalidate();
		panelPublicaciones.repaint();
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
		
		//Para Generar el pdf con los seguidores
		JMenuItem generarPdf = new JMenuItem("Generar PDF");
		popupMenu.add(generarPdf);
		this.addManejadorGenerarPdf(generarPdf);
		
		//Para Generar el excel con los seguidores
		JMenuItem generarExcel = new JMenuItem("Generar Excel");
		popupMenu.add(generarExcel);
		this.addManejadorGenerarExcel(generarExcel);
		
		JMenuItem topMeGusta = new JMenuItem("Top me Gusta");
		popupMenu.add(topMeGusta);
		this.addManejadorTopMeGusta(topMeGusta);
		
		JMenuItem anularPremium = new JMenuItem("Anular Premium");
		popupMenu.add(anularPremium);
		this.addManejadorAnularPremium(anularPremium);
		
	}
	
	private void addListaDescuentos() {		
		List<String> listaDescuentos = new LinkedList<String>();
		listaDescuentos.add("Descuento por edad: jóvenes(18-35)");
		listaDescuentos.add("Descuento por edad: mayores de 65");
		listaDescuentos.add("Descuento por MeGustas");
		
		jListaDescuentos = new JList<String>(listaDescuentos.toArray
								(new String[listaDescuentos.size()]));
		jListaDescuentos.setCellRenderer(createListRenderer());
		jListaDescuentos.setPreferredSize(new Dimension(220, ABORT));
		jListaDescuentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JDialog dialog = new JDialog();
		dialog.setLocationRelativeTo(btnMenu);
		dialog.setTitle("Descuentos premium");
		dialog.setSize(230, 100);
		dialog.getContentPane().add(jListaDescuentos);
		dialog.setVisible(true);
		
		jListaDescuentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indice = jListaDescuentos.getSelectedIndex();
				if (indice != -1) {
					String mensaje = null;
					if (indice == 0) {
						mensaje = "Tras aplicar descuento de jóvenes vas a pagar 15€";
					} else if(indice == 1) {
						mensaje = "Tras aplicar descuento para mayores de 65 vas a pagar 12€";
					} else if(indice == 2) {
						mensaje = "Tras aplicar descuento por me gustas vas a pagar 10 €";
					}
					int result = JOptionPane.showConfirmDialog(frmPrincipal, mensaje,
							"Confirm Dialog", JOptionPane.CANCEL_OPTION);	
					if (result == JOptionPane.YES_OPTION) {
						dialog.setVisible(false);
						Controlador.getUnicaInstancia().hacerPremium(usuarioActual);
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
				if(Controlador.getUnicaInstancia().isPremium(usuarioActual)) {
					crearMenuOpcionesPremium();
				}
				else {
					crearPopupMenu();
				}
				popupMenu.show(btn, e.getX() , e.getY());
			}
		});
	}
	
	//Botón Perfil (abre el perfil del usuario actual)
	private void addManejadorBotonPerfil(JButton btn) {
		panelPerfilUsuario = new PanelPerfilUsuario(this, usuarioActual);
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmPrincipal.getContentPane().removeAll();
				frmPrincipal.setContentPane(panelPerfilUsuario);
				frmPrincipal.getContentPane().revalidate();;
				frmPrincipal.getContentPane().repaint();
			}
		});
	}
	
	//Botón addFoto (abre el fileChooser y añade foto)
	private void addManejadorBotonAddFoto(JButton btn) {
		btnAddFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAddPublicacion v = new VentanaAddPublicacion(usuarioActual, ventanaPrincipal);
				v.setLocationRelativeTo(btn);
				v.setVisible(true);				
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
	
	
	private void addManejadorBotonNotificaciones(JButton btn) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaNotificaciones vn = new VentanaNotificaciones(ventanaPrincipal, usuarioActual);
				vn.setLocationRelativeTo(btn);
				vn.setVisible(true);
				vn.addWindowListener(new WindowAdapter() {
				       @Override
				       public void windowClosed(WindowEvent e) {
				    	  // Controlador.getUnicaInstancia().vaciarNotificaciones(usuarioActual);
				       }
				});
			}
		});
	}
	
	private void addManejadorGenerarPdf(JMenuItem generarPdf) {
		generarPdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 Controlador.getUnicaInstancia().generarPdf(usuarioActual);
			}
		});
	}
	
	private void addManejadorGenerarExcel(JMenuItem generarExcel) {
		generarExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 Controlador.getUnicaInstancia().generarExcel(usuarioActual);
			}
		});
		
	}
	
	private void addManejadorAnularPremium(JMenuItem anularPremium) {
		anularPremium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frmPrincipal, "¿Estás seguro de anular tu suscripción?",
						"Confirm Dialog", JOptionPane.CANCEL_OPTION);	
				if (result == JOptionPane.YES_OPTION) {
					Controlador.getUnicaInstancia().anularPremium(usuarioActual);
				} 				
			}
		});
		
	}
	
	private void addManejadorPulsador(Luz l) {
		l.addEncendidoListener( new IEncendidoListener() {
			public void enteradoCambioEncendido(EventObject arg0) {
				JFileChooser chooseXml = new JFileChooser();
				int seleccion = chooseXml.showOpenDialog(frmPrincipal);
				if(seleccion != JFileChooser.CANCEL_OPTION) {
					Controlador.getUnicaInstancia().cargarFotos(chooseXml.getSelectedFile().getPath());
					mostrarPublicaciones();
				}
			}
		});
	}

	
	private void addManejadorTopMeGusta(JMenuItem topMeGusta) {
		topMeGusta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearListaTopMg();
			}
		});
	}
	
	//Abre perfil del usuario indicado al seleccionar en la lista
	public void abrirPerfil(String nombreUsuario) {
		panelPerfilUsuario = new PanelPerfilUsuario(this, nombreUsuario);		
		frmPrincipal.getContentPane().removeAll();
		frmPrincipal.setContentPane(panelPerfilUsuario);
		frmPrincipal.getContentPane().revalidate();;
		frmPrincipal.getContentPane().repaint();
	}
	
	
	
	
	
	
	public void crearListaTopMg() {
		List<Foto> listaTopMg = Controlador.getUnicaInstancia().getTopMeGusta(usuarioActual);
				
		JList<Foto> jlistaTopMg = new JList<Foto>(listaTopMg.toArray
								(new Foto[listaTopMg.size()]));
		jlistaTopMg.setCellRenderer(createListRendererFotos());

		JScrollPane scrollListaFotos = new JScrollPane(jlistaTopMg);
		scrollListaFotos.setPreferredSize(frmPrincipal.getSize());
		scrollListaFotos.setBackground(null);
		JDialog dialog = new JDialog();
		dialog.setLocationRelativeTo(panelNorte);
		dialog.setTitle("Fotos con más Me Gustas");
		dialog.setSize(350, 500);
		dialog.getContentPane().add(scrollListaFotos);
		dialog.setVisible(true);
	}
	
	private static ListCellRenderer<? super Foto> createListRendererFotos() {
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
					label.setSize(80, 50);
					Foto foto = (Foto) value;
					label.setIcon(escalarImagen(foto.getRuta()));
					label.setText(String.format("%s ", foto.getMeGustas() + " Me gustas"));
					}
				return c;
			}
		};
		};
	}
	
	public void cerrar() {
		this.frmPrincipal.dispose();
	}
	
	public FrmPrincipal getFrm() {
		return this.frmPrincipal;
	}
	
	private static Icon escalarImagen(String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				130, 
				100, 
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
