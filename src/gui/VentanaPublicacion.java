package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import dominio.Album;
import dominio.Foto;
import dominio.Publicacion;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPublicacion extends JFrame {

	private JPanel contentPane;

	private JLabel lblTitulo;
	private JLabel lblFoto;
	private JTextArea textArea;
	private JPanel panelBotones;
	private JButton btnAceptar;
	private JButton btnCancelar;

	private String ruta;
	private String usuarioActual;
	private String comentario = "";
	private JLabel lblNombreAlbum;


	/**
	 * Crea la ventana
	 */
	public VentanaPublicacion(String u, String r) {	
		this.ruta = r;
		this.usuarioActual = u;

		setBounds(100, 100, 600, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		lblNombreAlbum = new JLabel("");
		lblNombreAlbum.setForeground(Constantes.LILA);
		lblNombreAlbum.setFont(Constantes.NEGRITA_15);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 11;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNombreAlbum, gbc_lblNewLabel);
		lblNombreAlbum.setVisible(false);

		lblTitulo = new JLabel("Escribe un comentario (Maximo 120 caracteres)");
		lblTitulo.setFont(Constantes.NORMAL_15);
		lblTitulo.setForeground(Constantes.LILA);
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitulo.gridx = 10;
		gbc_lblTitulo.gridy = 1;
		contentPane.add(lblTitulo, gbc_lblTitulo);

		lblFoto = new JLabel();
		lblFoto.setIcon(this.mostrarFoto(ruta));
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.gridheight = 3;
		gbc_lblFoto.gridwidth = 8;
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.gridx = 0;
		gbc_lblFoto.gridy = 1;
		contentPane.add(lblFoto, gbc_lblFoto);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textArea.getText().length() >= 120)
				{
					e.consume();
				}
			}
		});
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 10;
		gbc_textArea.gridy = 2;
		contentPane.add(textArea, gbc_textArea);

		panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.anchor = GridBagConstraints.EAST;
		gbc_panelBotones.fill = GridBagConstraints.VERTICAL;
		gbc_panelBotones.gridx = 10;
		gbc_panelBotones.gridy = 4;
		contentPane.add(panelBotones, gbc_panelBotones);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(Constantes.LILA);
		btnAceptar.setFont(Constantes.NEGRITA_15);
		panelBotones.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		this.addManejadorBtnCancelar(btnCancelar);
		btnCancelar.setForeground(Constantes.LILA);
		btnCancelar.setFont(Constantes.NEGRITA_15);
		panelBotones.add(btnCancelar);
	}	

	// Manejador para el botón de cancelar -> cierra la ventana
	private void addManejadorBtnCancelar(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	// Manejador para el botón de compartir la publicacion
	private void addManejadorBtnCompartir(JButton btn, JPanel ventanaActual) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comentario = textArea.getText();
				if (Controlador.getUnicaInstancia().registrarFoto(usuarioActual, ruta, comentario)) {
					if (ventanaActual.getClass() == PanelPerfilUsuario.class) {
						((PanelPerfilUsuario)ventanaActual).actualizar();
					}
					else if (ventanaActual.getClass() == VentanaPrincipal.class) {
						((VentanaPrincipal)ventanaActual).mostrarPublicaciones();
					}
				}
				dispose();
			}
		});
	}

	// Manejador para el botón de crear un álbum
	private void addManejadorBtnCrearAlbum(JButton btn, String nombreAlbum, PanelPerfilUsuario panel) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comentario = textArea.getText();
				Album a = Controlador.getUnicaInstancia().registrarAlbum(nombreAlbum, comentario, usuarioActual);
				Controlador.getUnicaInstancia().añadirFotoAlbum(a.getId(), ruta, comentario);
				panel.actualizar();
				dispose();
			}
		});
	}

	// Manejador para el botón de añadir una foto a un álbum
	private void addManejadorBtnAñadirFotoAlbum(JButton btn, int idAlbum, String ruta, VentanaAlbum va) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comentario = textArea.getText();
				Controlador.getUnicaInstancia().añadirFotoAlbum(idAlbum, ruta, comentario);
				va.mostrarFotos();
				dispose();
			}
		});
	}

	private void addManejadorBtnOk(JButton btn, Foto f) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comentario = textArea.getText();
				if (! comentario.isEmpty())
					Controlador.getUnicaInstancia().añadirComentario(f.getId(), comentario, usuarioActual);
				dispose();
			}
		});
	}

	// Para ver una foto
	public void verFoto(Foto f) {
		btnCancelar.setVisible(false);
		btnAceptar.setText("OK");
		addManejadorBtnOk(btnAceptar, f);
	}

	// Para ver una foto de un álbum -> también se añade el título del álbum
	public void verFotoAlbum(Foto f, String titulo) {
		lblNombreAlbum.setText(titulo);
		lblNombreAlbum.setVisible(true);
		btnCancelar.setVisible(false);
		btnAceptar.setText("OK");
		addManejadorBtnOk(btnAceptar, f);
	}

	// Para compartir una foto -> el botón de aceptar se llama 'compartir'
	public boolean compartirFoto(JPanel panel) {
		btnAceptar.setText("Compartir");
		this.addManejadorBtnCompartir(btnAceptar, panel);
		return true;
	}

	// Para crear el álbum
	public void crearAlbum(String nombreAlbum, PanelPerfilUsuario panel) {
		btnAceptar.setText("Crear Album");
		this.addManejadorBtnCrearAlbum(btnAceptar, nombreAlbum, panel);
	}

	// Para agregar una foto al álbum
	public void añadirFotoAlbum(int idAlbum, String ruta, VentanaAlbum va) {
		btnAceptar.setText("Agregar al Album");
		this.addManejadorBtnAñadirFotoAlbum(btnAceptar, idAlbum, ruta, va);
	}

	private Icon mostrarFoto(String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				230, 
				190, 
				Image.SCALE_DEFAULT));
		return icono;
	}

}
