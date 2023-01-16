package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import controlador.Controlador;
import dominio.Foto;
import dominio.Usuario;

public class VentanaBusqueda extends JFrame {

	private JPanel contentPane;
	//private JPanel panelUsuarios;
	private JPanel panelLista;
	private List<Usuario> usuarios;
	private JList<Usuario> listaUsuarios;
	private JList<String> listaFotos;
	private String perfil="";
	private VentanaPrincipal ventanaPrincipal;

	/**
	 * Create the frame.
	 */
	public VentanaBusqueda(VentanaPrincipal vp, String cadena) {
		this.ventanaPrincipal = vp;
		//setBounds(100, 100, 230, 300);
		setBounds(100, 100, 230, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		panelLista = new JPanel();
		contentPane.add(panelLista);
		
		if (cadena.startsWith("#")) {
			this.addListaHashtags(cadena);
		}
		
		else
			this.addListaUsuarios(cadena);
		
	}

	private void addListaUsuarios(String nombre) {
		
		usuarios = Controlador.getUnicaInstancia().buscarUsuarios(nombre);
		
		listaUsuarios = new JList<Usuario>(usuarios.toArray
								(new Usuario[usuarios.size()]));
		listaUsuarios.setCellRenderer(createListRenderer());
		listaUsuarios.setPreferredSize(new Dimension(200, ABORT));
		listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane pane = new JScrollPane(listaUsuarios);
		panelLista.add(pane);
		
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Usuario u;
				if ((u = listaUsuarios.getSelectedValue()) != null) {
					perfil = u.getNombreUsuario();
					ventanaPrincipal.abirPerfil(perfil);
					dispose();
				}
			}
		});
	}
	
	private void addListaHashtags(String hashtag) {
		List<String> listaHashtags = Controlador.getUnicaInstancia().buscarHashtags(hashtag);
		
		//List<String> hashtags = new ArrayList(listaHashtags.values());		
		
		listaFotos = new JList<String>(listaHashtags.toArray
								(new String[listaHashtags.size()]));
		listaFotos.setCellRenderer(createListRenderer2());
		listaFotos.setPreferredSize(new Dimension(200, ABORT));
		listaFotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollListaFotos = new JScrollPane(listaFotos);

		panelLista.add(scrollListaFotos);
		
		/*listaFotos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Usuario u;
				if ((u = listaFotos.getSelectedValue()) != null) {
					perfil = u.getNombreUsuario();
					ventanaPrincipal.abirPerfil(perfil);
					dispose();
				}
			}
		});*/

		
	}

	private static ListCellRenderer<? super Usuario> createListRenderer() {
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
					Usuario contacto = (Usuario) value;
					label.setIcon(escalarImagen(contacto.getFotoPerfil().getPath()));
					label.setText(String.format("%s ", contacto.getNombre()));
					}
				return c;
			}
		};
		};
	}
	
	private static ListCellRenderer<? super String> createListRenderer2() {
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
					String hashtag = (String) value;
					//label.setIcon(escalarImagen(foto.getRuta()));
					label.setText(hashtag);
					}
				return c;
			}
		};
		};
	}
	
	private static Icon escalarImagen(String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				40, 
				40, 
				Image.SCALE_DEFAULT));
		return icono;
	}
}
