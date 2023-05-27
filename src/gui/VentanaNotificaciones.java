package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import dominio.Foto;
import dominio.Notificacion;
import dominio.Usuario;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;

public class VentanaNotificaciones extends JFrame {

	private JPanel contentPane;
	private JPanel panelNorte;
	private JPanel panelNotis;
	private JLabel lblNorte;
	private Usuario usuario;
	private List<Notificacion> notificaciones;
	private JList<Notificacion> listaNotificaciones;
	private JScrollPane scrollPane;
	private JButton btnAceptar;
	private VentanaPrincipal ventanaPrincipal;

	/**
	 * Crea una ventana para mostrar las notificaciones que tiene un usuario
	 */
	public VentanaNotificaciones(VentanaPrincipal v, String nombreUsuario) {
		this.usuario = Controlador.getUnicaInstancia().getUsuario(nombreUsuario);
		this.ventanaPrincipal = v;
		setBounds(100, 100, 300, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		panelNorte = new JPanel();
		contentPane.add(panelNorte);

		lblNorte = new JLabel("");
		lblNorte.setForeground(Constantes.LILA);
		lblNorte.setFont(Constantes.NEGRITA_15);

		if (usuario.getNotificaciones().size() > 0) {
			lblNorte.setText("Hay " + usuario.getNotificaciones().size() + " nuevas notificaciones");
			panelNorte.add(lblNorte);
			crearPanelNotificaciones();
		}

		else {
			setSize(new Dimension(300, 80));
			lblNorte.setText("No hay nuevas notificaciones");
			panelNorte.add(lblNorte);
		}

	}

	public void crearPanelNotificaciones() {
		panelNotis = new JPanel();
		contentPane.add(panelNotis);

		// Copia de la lista de notificaciones para mostrar
		notificaciones = new ArrayList<>(usuario.getNotificaciones());
		listaNotificaciones = new JList<Notificacion>(notificaciones.toArray
				(new Notificacion[notificaciones.size()]));
		listaNotificaciones.setCellRenderer(createListRenderer());
		listaNotificaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new JScrollPane(listaNotificaciones);
		fixedSize(scrollPane, 250, 200);
		panelNotis.add(scrollPane);

		listaNotificaciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Notificacion n;
				if ((n = listaNotificaciones.getSelectedValue()) != null) {
					String perfil = n.getPublicacion().getUsuario().getNombreUsuario();
					ventanaPrincipal.abrirPerfil(perfil);
					dispose();
				}
			}
		});

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(Constantes.NEGRITA_15);
		btnAceptar.setForeground(Constantes.LILA);
		panelNotis.add(btnAceptar);
		this.addManejadorBotonAceptar();
	}

	/**
	 * CellRenderer para la lista de notificaciones 
	 */
	private static ListCellRenderer<? super Notificacion> createListRenderer() {
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
						Notificacion noti = (Notificacion) value;
						String ruta = ((Foto) noti.getPublicacion()).getRuta();
						label.setIcon(escalarImagen(ruta));

						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						String fecha = noti.getFecha().format(formatter);

						label.setText(String.format("%s - %s", noti.getPublicacion().getUsuario().getNombreUsuario(), 
								fecha));
					}
					return c;
				}
			};
		};
	}

	/**
	 * Manejador del botón aceptar -> vacía las notificaciones del usuario
	 */
	public void addManejadorBotonAceptar() {
		btnAceptar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Controlador.getUnicaInstancia().vaciarNotificaciones(usuario.getNombreUsuario());
			}
		});
	}

	/**
	 * Escala una imagen para la lista de notificaciones
	 */
	private static Icon escalarImagen(String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				40, 
				40, 
				Image.SCALE_DEFAULT));
		return icono;
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


}
