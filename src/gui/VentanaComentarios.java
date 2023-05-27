package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

	public VentanaComentarios(Foto f) {
		setBounds(100, 100, 280, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.foto = f;

		setContentPane(contentPane);	
		this.addListaComentarios();
	}

	/**
	 * Crea la JList de comentarios de la foto f
	 */
	private void addListaComentarios() {

		panelLista = new JPanel();
		contentPane.add(panelLista);

		comentarios = foto.getComentarios();
		listaComentarios = new JList<Comentario>(comentarios.toArray(new Comentario[comentarios.size()]));

		listaComentarios.setCellRenderer(createListRenderer());
		listaComentarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new JScrollPane(listaComentarios);
		this.fixedSize(scrollPane, 270, 240);
		panelLista.add(scrollPane);

	}

	/**
	 * CellRenderer para la lista de comentarios 
	 */
	private static ListCellRenderer<? super Comentario> createListRenderer(){
		return new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				JPanel panelCom = new JPanel();
				panelCom.setLayout(new BoxLayout(panelCom, BoxLayout.X_AXIS));

				Comentario comentario = (Comentario) value;

				Icon icono = escalarImagen(comentario.getUsuario().getFotoPerfil().getPath());
				JLabel iconoLabel = new JLabel(icono);
				panelCom.add(iconoLabel);

				JTextArea textoArea = new JTextArea(comentario.getTexto());
				textoArea.setLineWrap(true);
				textoArea.setWrapStyleWord(true);
				textoArea.setSize(new Dimension(200, 100));
				panelCom.add(textoArea);

				return panelCom;
			}
		};
	}

	/**
	 * Escala la imagen para añadir a la JList de comentarios
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
