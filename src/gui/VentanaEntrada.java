package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.EtchedBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaEntrada {

	private JFrame frmEntrada;
	private JPasswordField passwordField;
	private JTextField textEmail;
	private JButton btnIniciarSesion;
	
	public Color Lila = new Color(134, 46, 150);
	public Font lblFont = new Font("Arial", Font.PLAIN, 15);
	public Font btnFont = new Font("Arial", Font.BOLD, 15);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEntrada window = new VentanaEntrada();
					window.frmEntrada.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaEntrada() {
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frmEntrada = new JFrame();
		frmEntrada.setBounds(100, 100, 383, 457);
		frmEntrada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEntrada.getContentPane().setLayout(new BoxLayout(frmEntrada.getContentPane(), BoxLayout.Y_AXIS));
	
		crearPanelTitulo();
		crearPanelInicioSesion();
		crearPanelSur();
		
	}
	
	private void crearPanelTitulo() {
		JPanel panelTitulo = new JPanel();
		//panelNorte.setBackground(new Color(255, 255, 255));
		frmEntrada.getContentPane().add(panelTitulo);
		
		JLabel lblPhotoTDS = new JLabel("PhotoTDS");
		lblPhotoTDS.setForeground(new Color(134, 46, 150));
		lblPhotoTDS.setIcon(new ImageIcon(VentanaEntrada.class.getResource("/imagenes/logo.png")));
		lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 35));
		panelTitulo.add(lblPhotoTDS);
	}

	private void crearPanelInicioSesion() {
		JPanel panelInicioSesion = new JPanel();
		panelInicioSesion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmEntrada.getContentPane().add(panelInicioSesion);
		GridBagLayout gbl_panelInicioSesion = new GridBagLayout();
		gbl_panelInicioSesion.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 10, 0};
		gbl_panelInicioSesion.rowHeights = new int[]{20, 0, 20, 0, 20, 0, 30, 0};
		gbl_panelInicioSesion.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelInicioSesion.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelInicioSesion.setLayout(gbl_panelInicioSesion);
		
		JLabel lblEmail = new JLabel("Nombre de usuario o email");
		lblEmail.setFont(lblFont);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 1;
		panelInicioSesion.add(lblEmail, gbc_lblEmail);
		
		textEmail = new JTextField();
		GridBagConstraints gbc_textEmail = new GridBagConstraints();
		gbc_textEmail.gridwidth = 4;
		gbc_textEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textEmail.gridx = 2;
		gbc_textEmail.gridy = 1;
		panelInicioSesion.add(textEmail, gbc_textEmail);
		textEmail.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setFont(lblFont);
		GridBagConstraints gbc_lblContraseña = new GridBagConstraints();
		gbc_lblContraseña.anchor = GridBagConstraints.WEST;
		gbc_lblContraseña.insets = new Insets(0, 0, 5, 5);
		gbc_lblContraseña.gridx = 1;
		gbc_lblContraseña.gridy = 3;
		panelInicioSesion.add(lblContraseña, gbc_lblContraseña);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 4;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		panelInicioSesion.add(passwordField, gbc_passwordField);
		
		btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setForeground(Lila);
		btnIniciarSesion.setFont(btnFont);
		GridBagConstraints gbc_btnIniciarSesion = new GridBagConstraints();
		gbc_btnIniciarSesion.fill = GridBagConstraints.BOTH;
		gbc_btnIniciarSesion.gridwidth = 5;
		gbc_btnIniciarSesion.insets = new Insets(0, 0, 5, 5);
		gbc_btnIniciarSesion.gridx = 1;
		gbc_btnIniciarSesion.gridy = 5;
		panelInicioSesion.add(btnIniciarSesion, gbc_btnIniciarSesion);
	}
	
	private void crearPanelSur() {
		JPanel panelSur = new JPanel();
		//panelSur.setBackground(Color.WHITE);
		frmEntrada.getContentPane().add(panelSur);
		panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelSur.add(rigidArea);
		
		JLabel lblCrearCuenta = new JLabel("¿No tienes una cuenta?");
		lblCrearCuenta.setFont(lblFont);
		lblCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSur.add(lblCrearCuenta);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelSur.add(rigidArea_2);
		
		JButton btnCrearCuenta = new JButton("Crear una cuenta");
		/*
		btnCrearCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registro window = new Registro();
				window.frmRegistro.setVisible(true);
				frmEntrada.dispose();
			}
		});*/
		
		
		btnCrearCuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaRegistro window = new VentanaRegistro();
				window.frmRegistro.setVisible(true);
				frmEntrada.dispose();
			}
		});
		
		btnCrearCuenta.setMaximumSize(new Dimension(350, 40));
		btnCrearCuenta.setPreferredSize(new Dimension(200, 23));
		btnCrearCuenta.setSize(new Dimension(500, 23));
		btnCrearCuenta.setForeground(Lila);
		btnCrearCuenta.setFont(btnFont);
		btnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSur.add(btnCrearCuenta);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelSur.add(rigidArea_1);	
	}
	
}
