package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import dominio.Album;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAñadirPublicacion extends JFrame {

	private JPanel contentPane;

	private JLabel lblTitulo;
	private JLabel lblFoto;
	private JTextArea textArea;
	private JPanel panelBotones;
	private JButton btnCompartir;
	private JButton btnCancelar;
	
	private String ruta;
	private String usuarioActual;
	private PanelPerfilUsuario panelPerfil;
	private String comentario = "";
	private Color LILA = new Color(134, 46, 150);
	private Font fontBtn = new Font("HP Simplified Hans", Font.BOLD, 15);
	
	private JPanel ventanaActual;
	

	/**
	 * Create the frame.
	 */
	public VentanaAñadirPublicacion(String u, String r, JPanel p) {		
		this.ruta = r;
		this.usuarioActual = u;
			
		this.ventanaActual = p;
		//this.panelPerfil = p;
		
		setBounds(100, 100, 551, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblTitulo = new JLabel("Escribe un comentario (Maximo 200 caracteres)");
		lblTitulo.setFont(new Font("HP Simplified Hans", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitulo.gridx = 9;
		gbc_lblTitulo.gridy = 0;
		contentPane.add(lblTitulo, gbc_lblTitulo);
		
		lblFoto = new JLabel();
		lblFoto.setIcon(this.mostrarFoto(ruta));
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.gridheight = 3;
		gbc_lblFoto.gridwidth = 8;
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.gridx = 0;
		gbc_lblFoto.gridy = 0;
		contentPane.add(lblFoto, gbc_lblFoto);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textArea.getText().length() >= 200)
			    {
			        e.consume();
			    }
			}
		});
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 9;
		gbc_textArea.gridy = 1;
		contentPane.add(textArea, gbc_textArea);
		
		panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.anchor = GridBagConstraints.EAST;
		gbc_panelBotones.fill = GridBagConstraints.VERTICAL;
		gbc_panelBotones.gridx = 9;
		gbc_panelBotones.gridy = 3;
		contentPane.add(panelBotones, gbc_panelBotones);
		
		btnCompartir = new JButton("Compartir");
		//this.addManejadorBtnCompartir(btnCompartir);
		btnCompartir.setForeground(LILA);
		btnCompartir.setFont(fontBtn);
		panelBotones.add(btnCompartir);
		
		btnCancelar = new JButton("Cancelar");
		this.addManejadorBtnCancelar(btnCancelar);
		btnCancelar.setForeground(LILA);
		btnCancelar.setFont(fontBtn);
		panelBotones.add(btnCancelar);
	}
	
	private void addManejadorBtnCancelar(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	private void addManejadorBtnCompartir(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comentario = textArea.getText();
				if (Controlador.getUnicaInstancia().registrarFoto(usuarioActual, ruta, comentario)) {
					dispose();
					if (ventanaActual.getClass() == PanelPerfilUsuario.class)
						((PanelPerfilUsuario)ventanaActual).actualizar();
					else if (ventanaActual.getClass() == VentanaPrincipal.class) 
						((VentanaPrincipal)ventanaActual).mostrarPublicaciones();
				}
			}
		});
	}
		
	private void addManejadorBtnCrearAlbum(JButton btn, String nombreAlbum) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comentario = textArea.getText();
				Album a = Controlador.getUnicaInstancia().registrarAlbum(nombreAlbum, comentario, usuarioActual);
				Controlador.getUnicaInstancia().añadirFotoAlbum(a.getId(), ruta, comentario);
				((PanelPerfilUsuario)ventanaActual).actualizar();
				dispose();
			}
		});
	}
	
	private void addManejadorBtnAñadirFotoAlbum(JButton btn, int idAlbum, String ruta) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comentario = textArea.getText();
				Controlador.getUnicaInstancia().añadirFotoAlbum(idAlbum, ruta, comentario);
				//((VentanaAlbum)ventanaActual).mostrarFotos();
				dispose();
			}
		});
	}
	
	public void compartirFoto() {
		this.addManejadorBtnCompartir(btnCompartir);
	}
	
	public void crearAlbum(String nombreAlbum) {
		btnCompartir.setText("Crear Album");
		this.addManejadorBtnCrearAlbum(btnCompartir, nombreAlbum);
	}
	
	public void añadirFotoAlbum(int idAlbum, String ruta) {
		btnCompartir.setText("Añadir Foto");
		this.addManejadorBtnAñadirFotoAlbum(btnCompartir, idAlbum, ruta);
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
