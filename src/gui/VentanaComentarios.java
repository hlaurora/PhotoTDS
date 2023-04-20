package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import dominio.Comentario;
import dominio.Foto;

public class VentanaComentarios extends JFrame {

	private JPanel contentPane;
	private Foto foto;
	
	private JPanel panelLista;
	private List<Comentario> comentarios;
	private JList<Comentario> listaComentarios;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public VentanaComentarios(Foto f) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.foto = f;

		setContentPane(contentPane);
		
		this.addListaComentarios();
	
	}
	
	private void addListaComentarios() {
		
		panelLista = new JPanel();
		contentPane.add(panelLista);
		
		comentarios = foto.getComentarios();
		listaComentarios = new JList<Comentario>(comentarios.toArray(new Comentario[comentarios.size()]));
		
		listaComentarios.setCellRenderer(createListRenderer());
		listaComentarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane = new JScrollPane(listaComentarios);
		this.fixedSize(scrollPane, 180, 180);
		panelLista.add(scrollPane);

	}

	private static ListCellRenderer<? super Comentario> createListRenderer(){
		return new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel) {
					JLabel label = (JLabel) c;
					Comentario comentario = (Comentario) value;
					label.setIcon(escalarImagen(comentario.getUsuario().getFotoPerfil().getPath()));
					label.setText(String.format("%s ", comentario.getTexto()));
				}
				return c;
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
	public void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
		o.setSize(d);
	}
	
}


/**	
		
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
				
		listaFotos = new JList<String>(listaHashtags.toArray
								(new String[listaHashtags.size()]));
		listaFotos.setCellRenderer(createListRenderer2());
		listaFotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new JScrollPane(listaFotos);
		this.fixedSize(scrollPane, 180, 180);
		panelLista.add(scrollPane);	
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
	
	/**
	 * Fija el tamaño de un componente
	 
	public void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
		o.setSize(d);
	}
}

 * 
 * 
 * 
 */


