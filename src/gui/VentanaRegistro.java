package gui;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.TitledBorder;

/*
@SuppressWarnings("serial")
public class Registro extends JDialog {*/
public class VentanaRegistro {

	public JFrame frmRegistro;
	private JTextField textEmail;
	private JTextField textNombre;
	private JTextField textNombreUsuario;
	private JPasswordField passwordField;
	
	public Color Lila = new Color(134, 46, 150);
	public Font lblFont = new Font("Arial", Font.PLAIN, 15);
	public Font btnFont = new Font("Arial", Font.BOLD, 15);
	
	/*public Registro(JFrame owner){
		super(owner, "Registro Usuario", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.crearPanelRegistro();
		//this.initialize();
		//Registro window = new Registro();
		//window.frmRegistro.setVisible(true);
	}*/
	public VentanaRegistro(){
		//super("Registro Usuario");
		//this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//this.setResizable(false);
		//this.initialize();
		//Registro window = new Registro();
		//window.frmRegistro.setVisible(true);
		crearPanelRegistro();
	}
	
	
	/*

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro window = new Registro();
					window.frmRegistro.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Registro() {
		initialize();
	}

	
	private void initialize() { */
	private void crearPanelRegistro() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frmRegistro = new JFrame();
		frmRegistro.setBounds(100, 100, 422, 539);
		frmRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegistro.getContentPane().setLayout(new BoxLayout(frmRegistro.getContentPane(), BoxLayout.Y_AXIS));

		crearPanelTitulo();
		crearLblDescripcion();
		crearPanelDatos();
		crearPanelBotones();
	
		/*JTextArea textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 4;
		gbc_textArea.gridy = 3;
		panel.add(textArea, gbc_textArea);*/

	}
	
	private void crearPanelTitulo() {
		JPanel panelTitulo = new JPanel();
		frmRegistro.getContentPane().add(panelTitulo);
		
		JLabel lblPhotoTDS = new JLabel("PhotoTDS");
		lblPhotoTDS.setForeground(new Color(134, 46, 150));
		lblPhotoTDS.setIcon(new ImageIcon(VentanaRegistro.class.getResource("/imagenes/logo.png")));
		lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 35));
		panelTitulo.add(lblPhotoTDS);
	}
	
	
	private void crearLblDescripcion() {
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		frmRegistro.getContentPane().add(rigidArea_1);
		
		JTextArea txtrDescripcion = new JTextArea();
		//txtrDescripcion.setFont(new Font("Monospaced", Font.BOLD, 15));
		txtrDescripcion.setFont(btnFont);
		txtrDescripcion.setForeground(Lila);
		txtrDescripcion.setMaximumSize(new Dimension(2147483647, 50));
		txtrDescripcion.setLineWrap(true);
		txtrDescripcion.setText(" Si te registras podrás compartir fotos y ver las fotos de tus amigos");
		txtrDescripcion.setEditable(false);
		frmRegistro.getContentPane().add(txtrDescripcion);
	}
	
	private void crearPanelDatos() {
		JPanel panelDatos = new JPanel();
		panelDatos.setMaximumSize(new Dimension(32767, 300));
		frmRegistro.getContentPane().add(panelDatos);
		GridBagLayout gbl_panelDatos = new GridBagLayout();
		gbl_panelDatos.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 10, 0};
		gbl_panelDatos.rowHeights = new int[]{20, 0, 0, 0, 0, 20, 0, 0, 0, 0};
		gbl_panelDatos.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelDatos.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panelDatos.setLayout(gbl_panelDatos);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(lblFont);
		lblEmail.setRequestFocusEnabled(false);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 1;
		panelDatos.add(lblEmail, gbc_lblEmail);
		
		textEmail = new JTextField();
		GridBagConstraints gbc_textEmail = new GridBagConstraints();
		gbc_textEmail.gridwidth = 4;
		gbc_textEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textEmail.gridx = 2;
		gbc_textEmail.gridy = 1;
		panelDatos.add(textEmail, gbc_textEmail);
		textEmail.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre completo");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		lblNombre.setFont(lblFont);
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		panelDatos.add(lblNombre, gbc_lblNombre);
		
		textNombre = new JTextField();
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.gridwidth = 4;
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridx = 2;
		gbc_textNombre.gridy = 2;
		panelDatos.add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);
		
		JLabel lblNombreUsuario = new JLabel("Nombre de usuario");
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		lblNombreUsuario.setFont(lblFont);
		gbc_lblNombreUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreUsuario.gridx = 1;
		gbc_lblNombreUsuario.gridy = 3;
		panelDatos.add(lblNombreUsuario, gbc_lblNombreUsuario);
		
		textNombreUsuario = new JTextField();
		GridBagConstraints gbc_textNombreUsuario = new GridBagConstraints();
		gbc_textNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textNombreUsuario.gridwidth = 4;
		gbc_textNombreUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombreUsuario.gridx = 2;
		gbc_textNombreUsuario.gridy = 3;
		panelDatos.add(textNombreUsuario, gbc_textNombreUsuario);
		textNombreUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		GridBagConstraints gbc_lblContraseña = new GridBagConstraints();
		lblContraseña.setFont(lblFont);
		gbc_lblContraseña.anchor = GridBagConstraints.WEST;
		gbc_lblContraseña.insets = new Insets(0, 0, 5, 5);
		gbc_lblContraseña.gridx = 1;
		gbc_lblContraseña.gridy = 4;
		panelDatos.add(lblContraseña, gbc_lblContraseña);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridwidth = 4;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		panelDatos.add(passwordField, gbc_passwordField);
		
		JPanel panelDate = new JPanel();
		panelDate.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelDate.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelDate = new GridBagConstraints();
		gbc_panelDate.gridwidth = 4;
		gbc_panelDate.insets = new Insets(0, 0, 5, 5);
		gbc_panelDate.fill = GridBagConstraints.BOTH;
		gbc_panelDate.gridx = 1;
		gbc_panelDate.gridy = 6;
		panelDatos.add(panelDate, gbc_panelDate);
		
		JLabel lblDate = new JLabel("Fecha de nacimiento");
		lblDate.setFont(lblFont);
		panelDate.add(lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(100, 25));
		panelDate.add(dateChooser);
		
		JPanel panelFecha = new JPanel();
		panelFecha.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelFecha = new GridBagConstraints();
		gbc_panelFecha.gridwidth = 4;
		gbc_panelFecha.insets = new Insets(0, 0, 5, 5);
		gbc_panelFecha.fill = GridBagConstraints.BOTH;
		gbc_panelFecha.gridx = 1;
		gbc_panelFecha.gridy = 7;
		panelDatos.add(panelFecha, gbc_panelFecha);
		
		JButton btnElegirFoto = new JButton("+");
		btnElegirFoto.setFont(btnFont);
		btnElegirFoto.setForeground(Lila);
		btnElegirFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
			}
		});
		
		JLabel lblAñadirFoto = new JLabel("Añadir foto del usuario(opcional)");
		lblAñadirFoto.setFont(lblFont);
		panelFecha.add(lblAñadirFoto);
		panelFecha.add(btnElegirFoto);
		
		JPanel panelPresentacion = new JPanel();
		panelPresentacion.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelPresentacion = new GridBagConstraints();
		gbc_panelPresentacion.gridwidth = 4;
		gbc_panelPresentacion.insets = new Insets(0, 0, 0, 5);
		gbc_panelPresentacion.fill = GridBagConstraints.BOTH;
		gbc_panelPresentacion.gridx = 1;
		gbc_panelPresentacion.gridy = 8;
		panelDatos.add(panelPresentacion, gbc_panelPresentacion);
		
		JLabel lblPresentacion = new JLabel("Añadir presentación(opcional)");
		lblPresentacion.setFont(lblFont);
		panelPresentacion.add(lblPresentacion);
		
		JButton btnPresentacion = new JButton("...");
		btnPresentacion.setFont(btnFont);
		btnPresentacion.setForeground(Lila);
		btnPresentacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaTextoPresentacion window = new VentanaTextoPresentacion();
				window.frmPresentacion.setVisible(true);
			}
		});
		panelPresentacion.add(btnPresentacion);		
	}
	
	private void crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		panelBotones.setPreferredSize(new Dimension(700, 10));
		frmRegistro.getContentPane().add(panelBotones);
		GridBagLayout gbl_panelBotones = new GridBagLayout();
		gbl_panelBotones.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0};
		gbl_panelBotones.rowHeights = new int[]{30, 0, 0, 0, 0};
		gbl_panelBotones.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBotones.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelBotones.setLayout(gbl_panelBotones);
		
		JButton btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		btnAceptar.setFont(btnFont);
		btnAceptar.setForeground(Lila);
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 7;
		gbc_btnAceptar.gridy = 1;
		panelBotones.add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnCancelar.setFont(btnFont);
		btnCancelar.setForeground(Lila);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 8;
		gbc_btnCancelar.gridy = 1;
		panelBotones.add(btnCancelar, gbc_btnCancelar);
	}
	

}
