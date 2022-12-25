package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import dominio.Usuario;

public class VentanaBusqueda extends JFrame {

	private JPanel contentPane;
	private JPanel panelUsuarios;
	private List<Usuario> usuarios;
	private JList<Usuario> listaUsuarios;
	private String perfil="";
	private VentanaPrincipal ventanaPrincipal;
	

	/**
	 * Create the frame.
	 */
	public VentanaBusqueda(VentanaPrincipal vp, String cadena) {
		this.ventanaPrincipal = vp;
		setBounds(100, 100, 230, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		panelUsuarios = new JPanel();
		contentPane.add(panelUsuarios);
		
		usuarios = Controlador.getUnicaInstancia().buscarUsuarios(cadena);
		
		listaUsuarios = new JList<Usuario>(usuarios.toArray
								(new Usuario[usuarios.size()]));
		listaUsuarios.setCellRenderer(createListRenderer());
		listaUsuarios.setPreferredSize(new Dimension(200, ABORT));
		listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane pane = new JScrollPane(listaUsuarios);
		panelUsuarios.add(pane);
		
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
	
	
	private static Icon escalarImagen(String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				40, 
				40, 
				Image.SCALE_DEFAULT));
		return icono;
	}
}
