package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

import controlador.Controlador;
import dominio.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaEntrada {

	private JFrame frmEntrada;
	private JPasswordField passwordField;
	private JTextField textUsuario;
	private JButton btnIniciarSesion;

	/**
	 * Create the application.
	 */
	public VentanaEntrada() {
		initialize();
	}

	public void mostrarVentana() {
		frmEntrada.setLocationRelativeTo(null);
		frmEntrada.setVisible(true);
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
		frmEntrada = new JFrame();
		frmEntrada.setBounds(100, 100, 383, 457);
		frmEntrada.setLocationRelativeTo(null);
		frmEntrada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEntrada.getContentPane().setLayout(new BoxLayout(frmEntrada.getContentPane(), BoxLayout.Y_AXIS));

		crearPanelTitulo();
		crearPanelInicioSesion();
		crearPanelSur();

	}

	private void crearPanelTitulo() {
		JPanel panelTitulo = new JPanel();
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

		JLabel lblUsuario = new JLabel("Nombre de usuario o email");
		lblUsuario.setFont(Constantes.NORMAL_15);
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 1;
		panelInicioSesion.add(lblUsuario, gbc_lblUsuario);

		textUsuario = new JTextField();
		GridBagConstraints gbc_textUsuario = new GridBagConstraints();
		gbc_textUsuario.gridwidth = 4;
		gbc_textUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUsuario.gridx = 2;
		gbc_textUsuario.gridy = 1;
		panelInicioSesion.add(textUsuario, gbc_textUsuario);
		textUsuario.setColumns(10);

		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setFont(Constantes.NORMAL_15);
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
		btnIniciarSesion.setForeground(Constantes.LILA);
		btnIniciarSesion.setFont(Constantes.NEGRITA_15);
		GridBagConstraints gbc_btnIniciarSesion = new GridBagConstraints();
		gbc_btnIniciarSesion.fill = GridBagConstraints.BOTH;
		gbc_btnIniciarSesion.gridwidth = 5;
		gbc_btnIniciarSesion.insets = new Insets(0, 0, 5, 5);
		gbc_btnIniciarSesion.gridx = 1;
		gbc_btnIniciarSesion.gridy = 5;
		panelInicioSesion.add(btnIniciarSesion, gbc_btnIniciarSesion);
		addManejadorBotonIniciarSesion(btnIniciarSesion);
	}

	private void crearPanelSur() {
		JPanel panelSur = new JPanel();
		frmEntrada.getContentPane().add(panelSur);
		panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelSur.add(rigidArea);

		JLabel lblCrearCuenta = new JLabel("¿No tienes una cuenta?");
		lblCrearCuenta.setFont(Constantes.NORMAL_15);
		lblCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSur.add(lblCrearCuenta);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		panelSur.add(rigidArea_2);

		JButton btnCrearCuenta = new JButton("Crear una cuenta");				
		btnCrearCuenta.setMaximumSize(new Dimension(350, 40));
		btnCrearCuenta.setPreferredSize(new Dimension(200, 23));
		btnCrearCuenta.setSize(new Dimension(500, 23));
		btnCrearCuenta.setForeground(Constantes.LILA);
		btnCrearCuenta.setFont(Constantes.NEGRITA_15);
		btnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSur.add(btnCrearCuenta);

		addManejadorBotonCrearCuenta(btnCrearCuenta);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelSur.add(rigidArea_1);	
	}

	private void addManejadorBotonIniciarSesion(JButton btnIniciarSesion) {
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean registrado = Controlador.getUnicaInstancia().loginUsuario(
						textUsuario.getText(), 
						new String (passwordField.getPassword()));
				if (registrado) {
					FrmPrincipal frm = new FrmPrincipal();
					Usuario u = Controlador.getUnicaInstancia().getUsuario(textUsuario.getText());
					Controlador.getUnicaInstancia().setUsuario(u);
					VentanaPrincipal vPrincipal = new VentanaPrincipal(frm);
					vPrincipal.setVisible(true);

					frm.setVisible(true);
					frmEntrada.dispose();
				} else 
					JOptionPane.showMessageDialog(frmEntrada, "Nombre de usuario o contraseña no válido",
							"Error", JOptionPane.ERROR_MESSAGE);			
			}
		});
	}

	private void addManejadorBotonCrearCuenta(JButton btnCrearCuenta) {
		btnCrearCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaRegistro ventanaRegistro = new VentanaRegistro(frmEntrada);
				ventanaRegistro.mostrarVentana();
				frmEntrada.dispose();
			}
		});
	}



}
