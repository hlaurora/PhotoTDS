package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;

public class V2 {

	private JFrame frame;
	private JTextField textBuscar;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					V2 window = new V2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public V2() {
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
		frame = new JFrame();
		frame.getContentPane().setSize(new Dimension(33, 10));
		frame.setBounds(100, 100, 542, 589);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.fixedSize(panel, frame.getWidth(), 60);
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 30, 0, 0, 10, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("PhotoTDS");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridwidth = 8;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnAñadirPublicacion = new JButton("+");
		btnAñadirPublicacion.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_btnAñadirPublicacion = new GridBagConstraints();
		gbc_btnAñadirPublicacion.insets = new Insets(0, 0, 0, 5);
		gbc_btnAñadirPublicacion.gridx = 9;
		gbc_btnAñadirPublicacion.gridy = 0;
		panel.add(btnAñadirPublicacion, gbc_btnAñadirPublicacion);
		
		textBuscar = new JTextField();
		GridBagConstraints gbc_textBuscar = new GridBagConstraints();
		gbc_textBuscar.gridwidth = 3;
		gbc_textBuscar.insets = new Insets(0, 0, 0, 5);
		gbc_textBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbc_textBuscar.gridx = 10;
		gbc_textBuscar.gridy = 0;
		panel.add(textBuscar, gbc_textBuscar);
		textBuscar.setColumns(10);
		
		JButton btnBuscar = new JButton("");
		btnBuscar.setIcon(null);
		this.fixedSize(btnBuscar, 40, 30);
		this.añadirImagen(btnBuscar, "/imagenes/lupa.png");
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 0, 5);
		gbc_btnBuscar.gridx = 13;
		gbc_btnBuscar.gridy = 0;
		panel.add(btnBuscar, gbc_btnBuscar);
		
		JButton btnFotoPerfil = new JButton("");
		btnFotoPerfil.setIcon(null);
		this.fixedSize(btnFotoPerfil, 50, 40);
		this.añadirImagen(btnFotoPerfil, "/imagenes/usuario.png");
		btnFotoPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnFotoPerfil = new GridBagConstraints();
		gbc_btnFotoPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_btnFotoPerfil.anchor = GridBagConstraints.EAST;
		gbc_btnFotoPerfil.gridx = 15;
		gbc_btnFotoPerfil.gridy = 0;
		panel.add(btnFotoPerfil, gbc_btnFotoPerfil);
		
		JButton btnMenu = new JButton("");
		btnMenu.setSize(new Dimension(33, 9));
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.anchor = GridBagConstraints.EAST;
		gbc_btnMenu.insets = new Insets(0, 0, 0, 5);
		gbc_btnMenu.gridx = 16;
		gbc_btnMenu.gridy = 0;
		panel.add(btnMenu, gbc_btnMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane);
		
		JList<PanelPublicacion> list = new JList<PanelPublicacion>( );
		scrollPane.setViewportView(list);
		
		panel = new PanelPublicacion();
		list.add(panel, 0);
		//this.crearPanelPublicacion();
	}
	
	
	private void crearPanelPublicacion() {
		JPanel panelPublicacion = new JPanel();

		panelPublicacion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		int ancho = frame.getWidth();
		this.fixedSize(panelPublicacion, ancho, 90);
		//panelPublicacion.repaint();
		frame.getContentPane().add(panelPublicacion);
		GridBagLayout gbl_panelPublicacion = new GridBagLayout();
		gbl_panelPublicacion.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelPublicacion.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelPublicacion.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelPublicacion.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelPublicacion.setLayout(gbl_panelPublicacion);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridheight = 1;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panelPublicacion.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JButton btnMeGusta = new JButton("Me gusta");
		GridBagConstraints gbc_btnMeGusta = new GridBagConstraints();
		gbc_btnMeGusta.insets = new Insets(0, 0, 5, 5);
		gbc_btnMeGusta.gridx = 3;
		gbc_btnMeGusta.gridy = 0;
		panelPublicacion.add(btnMeGusta, gbc_btnMeGusta);
		
		JButton btnComentario = new JButton("");
		btnComentario.setIcon(new ImageIcon(V2.class.getResource("/imagenes/comentario.png")));
		GridBagConstraints gbc_btnComentario = new GridBagConstraints();
		gbc_btnComentario.insets = new Insets(0, 0, 5, 5);
		gbc_btnComentario.gridx = 5;
		gbc_btnComentario.gridy = 0;
		panelPublicacion.add(btnComentario, gbc_btnComentario);
		
		int numMeGustas = 5;
		
		JLabel lblNumMeGustas = new JLabel("%i Me gustas", numMeGustas);
		GridBagConstraints gbc_lblNumMeGustas = new GridBagConstraints();
		gbc_lblNumMeGustas.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumMeGustas.gridx = 7;
		gbc_lblNumMeGustas.gridy = 0;
		panelPublicacion.add(lblNumMeGustas, gbc_lblNumMeGustas);
		
		/*
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 4;
		panelPublicacion.add(textField, gbc_textField);
		textField.setColumns(10);*/
		
	}
	
	
	
	//De un video
	private void añadirImagen(JButton btn, String ruta) {
		ImageIcon image = new ImageIcon(V2.class.getResource(ruta));
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
	
}
