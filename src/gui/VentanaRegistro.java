package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

import controlador.Controlador;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class VentanaRegistro extends JDialog {

	public JFrame frmRegistro;
	
	private JTextArea textDescripcion;
	private JTextField textEmail;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textNombreUsuario;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	
	private Date fechaNacimiento;
	private JDateChooser dateChooser;
	
	private JLabel lblPhotoTDS;
	private JLabel lblEmail;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblNombreUsuario;
	private JLabel lblContraseña;
	private JLabel lblContraseña2;
	private JLabel lblDate;
	private JLabel lblAñadirFoto;
	private JLabel lblPresentacion;
	private JLabel lblObligatorio;
	
	private JPanel panelTitulo;
	private JPanel panelDatos;
	private JPanel panelDate;
	private JPanel panelFoto;
	private JPanel panelPresentacion;
	private JPanel panelBotones;
	
	private JButton btnElegirFoto;
	private JButton btnPresentacion;
	private JButton btnRegistrar;
	private JButton btnCancelar;
	
	private JFileChooser fileChooser;
	private File fotoPerfil;
	
	public Color Lila = new Color(134, 46, 150);
	public Font lblFont = new Font("Arial", Font.PLAIN, 15);
	public Font btnFont = new Font("Arial", Font.BOLD, 15);
	private int seleccion = 5;
	
	private VentanaTextoPresentacion ventanaTexto;
	private boolean abreTextArea = false;
	private String textoPresentacion = "";
	
	public VentanaRegistro(JFrame owner){
		super(owner, "Registro Usuario", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		crearPanelRegistro();
	}
	
	
	private void crearPanelRegistro() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		frmRegistro = new JFrame();
		frmRegistro.setBounds(100, 100, 422, 568);
		frmRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegistro.getContentPane().setLayout(new BoxLayout(frmRegistro.getContentPane(), BoxLayout.Y_AXIS));

		crearPanelTitulo();
		crearLblDescripcion();
		crearPanelDatos();
		crearPanelBotones();
	}
	
	private void crearPanelTitulo() {
		panelTitulo = new JPanel();
		frmRegistro.getContentPane().add(panelTitulo);
		
		lblPhotoTDS = new JLabel("PhotoTDS");
		lblPhotoTDS.setForeground(new Color(134, 46, 150));
		lblPhotoTDS.setIcon(new ImageIcon(VentanaRegistro.class.getResource("/imagenes/logo.png")));
		lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 35));
		panelTitulo.add(lblPhotoTDS);
	}
	
	
	private void crearLblDescripcion() {
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		frmRegistro.getContentPane().add(rigidArea_1);
		
		textDescripcion = new JTextArea();
		textDescripcion.setFont(btnFont);
		textDescripcion.setForeground(Lila);
		textDescripcion.setMaximumSize(new Dimension(2147483647, 50));
		textDescripcion.setLineWrap(true);
		textDescripcion.setText(" Si te registras podrás compartir fotos y ver las fotos de tus amigos");
		textDescripcion.setEditable(false);
		frmRegistro.getContentPane().add(textDescripcion);
	}
	
	private void crearPanelDatos() {
		panelDatos = new JPanel();
		panelDatos.setMaximumSize(new Dimension(32767, 300));
		frmRegistro.getContentPane().add(panelDatos);
		GridBagLayout gbl_panelDatos = new GridBagLayout();
		gbl_panelDatos.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 10, 0};
		gbl_panelDatos.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelDatos.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelDatos.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		panelDatos.setLayout(gbl_panelDatos);
		
		lblEmail = new JLabel("Email*");
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
		
		lblNombre = new JLabel("Nombre*");
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
		
		lblApellidos = new JLabel("Apellidos*");
		GridBagConstraints gbc_lblApellidos = new GridBagConstraints();
		lblApellidos.setFont(lblFont);
		gbc_lblApellidos.anchor = GridBagConstraints.WEST;
		gbc_lblApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos.gridx = 1;
		gbc_lblApellidos.gridy = 3;
		panelDatos.add(lblApellidos, gbc_lblApellidos);
		
		textApellidos = new JTextField();
		GridBagConstraints gbc_textApellidos = new GridBagConstraints();
		gbc_textApellidos.gridwidth = 4;
		gbc_textApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_textApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textApellidos.gridx = 2;
		gbc_textApellidos.gridy = 3;
		panelDatos.add(textApellidos, gbc_textApellidos);
		textApellidos.setColumns(10);
		
		lblNombreUsuario = new JLabel("Nombre de usuario*");
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		lblNombreUsuario.setFont(lblFont);
		gbc_lblNombreUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreUsuario.gridx = 1;
		gbc_lblNombreUsuario.gridy = 4;
		panelDatos.add(lblNombreUsuario, gbc_lblNombreUsuario);
		
		textNombreUsuario = new JTextField();
		GridBagConstraints gbc_textNombreUsuario = new GridBagConstraints();
		gbc_textNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textNombreUsuario.gridwidth = 4;
		gbc_textNombreUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombreUsuario.gridx = 2;
		gbc_textNombreUsuario.gridy = 4;
		panelDatos.add(textNombreUsuario, gbc_textNombreUsuario);
		textNombreUsuario.setColumns(10);
		
		lblContraseña = new JLabel("Contraseña*");
		GridBagConstraints gbc_lblContraseña = new GridBagConstraints();
		lblContraseña.setFont(lblFont);
		gbc_lblContraseña.anchor = GridBagConstraints.WEST;
		gbc_lblContraseña.insets = new Insets(0, 0, 5, 5);
		gbc_lblContraseña.gridx = 1;
		gbc_lblContraseña.gridy = 5;
		panelDatos.add(lblContraseña, gbc_lblContraseña);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridwidth = 4;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 5;
		panelDatos.add(passwordField, gbc_passwordField);
		
		lblContraseña2 = new JLabel("Repetir contraseña*");
		GridBagConstraints gbc_lblContraseña2 = new GridBagConstraints();
		lblContraseña2.setFont(lblFont);
		gbc_lblContraseña2.anchor = GridBagConstraints.WEST;
		gbc_lblContraseña2.insets = new Insets(0, 0, 5, 5);
		gbc_lblContraseña2.gridx = 1;
		gbc_lblContraseña2.gridy = 6;
		panelDatos.add(lblContraseña2, gbc_lblContraseña2);
		
		passwordField2 = new JPasswordField();
		GridBagConstraints gbc_passwordField2 = new GridBagConstraints();
		gbc_passwordField2.gridwidth = 4;
		gbc_passwordField2.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField2.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField2.gridx = 2;
		gbc_passwordField2.gridy = 6;
		panelDatos.add(passwordField2, gbc_passwordField2);
		
		panelDate = new JPanel();
		panelDate.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelDate.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelDate = new GridBagConstraints();
		gbc_panelDate.gridwidth = 4;
		gbc_panelDate.insets = new Insets(0, 0, 5, 5);
		gbc_panelDate.fill = GridBagConstraints.BOTH;
		gbc_panelDate.gridx = 1;
		gbc_panelDate.gridy = 7;
		panelDatos.add(panelDate, gbc_panelDate);
		
		lblDate = new JLabel("Fecha de nacimiento*");
		lblDate.setFont(lblFont);
		panelDate.add(lblDate);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd/MM/yyyy"); 
		dateChooser.setPreferredSize(new Dimension(115, 23));
		panelDate.add(dateChooser);
	//---------------
		
		panelFoto = new JPanel();
		panelFoto.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelFoto = new GridBagConstraints();
		gbc_panelFoto.gridwidth = 4;
		gbc_panelFoto.insets = new Insets(0, 0, 5, 5);
		gbc_panelFoto.fill = GridBagConstraints.BOTH;
		gbc_panelFoto.gridx = 1;
		gbc_panelFoto.gridy = 8;
		panelDatos.add(panelFoto, gbc_panelFoto);
		
		btnElegirFoto = new JButton("+");
		btnElegirFoto.setPreferredSize(new Dimension(43, 23));
		btnElegirFoto.setFont(btnFont);
		btnElegirFoto.setForeground(Lila);
		btnElegirFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fileChooser = new JFileChooser();
				seleccion = fileChooser.showOpenDialog(frmRegistro);
				//System.out.println(seleccion);
			}
		});
		
		lblAñadirFoto = new JLabel("Añadir foto del usuario(opcional)");
		lblAñadirFoto.setFont(lblFont);
		panelFoto.add(lblAñadirFoto);
		panelFoto.add(btnElegirFoto);
		
		panelPresentacion = new JPanel();
		panelPresentacion.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelPresentacion = new GridBagConstraints();
		gbc_panelPresentacion.gridwidth = 4;
		gbc_panelPresentacion.insets = new Insets(0, 0, 5, 5);
		gbc_panelPresentacion.fill = GridBagConstraints.BOTH;
		gbc_panelPresentacion.gridx = 1;
		gbc_panelPresentacion.gridy = 9;
		panelDatos.add(panelPresentacion, gbc_panelPresentacion);
		
		lblPresentacion = new JLabel("Añadir presentación (opcional)");
		lblPresentacion.setFont(lblFont);
		panelPresentacion.add(lblPresentacion);
		
		btnPresentacion = new JButton("...");
		btnPresentacion.setPreferredSize(new Dimension(50, 23));
		btnPresentacion.setFont(btnFont);
		btnPresentacion.setForeground(Lila);
		btnPresentacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				abreTextArea = true;
				ventanaTexto = new VentanaTextoPresentacion(frmRegistro);
				ventanaTexto.frmPresentacion.setVisible(true);
			}
		});
		panelPresentacion.add(btnPresentacion);		
		
		lblObligatorio = new JLabel("*Este campo es obligatorio");
		lblObligatorio.setFont(new Font("Arial", Font.PLAIN, 13));
		GridBagConstraints gbc_lblObligatorio = new GridBagConstraints();
		gbc_lblObligatorio.anchor = GridBagConstraints.WEST;
		gbc_lblObligatorio.gridwidth = 3;
		gbc_lblObligatorio.insets = new Insets(0, 0, 0, 5);
		gbc_lblObligatorio.gridx = 1;
		gbc_lblObligatorio.gridy = 10;
		panelDatos.add(lblObligatorio, gbc_lblObligatorio);
	}
	
	private void crearPanelBotones() {
		panelBotones = new JPanel();
		panelBotones.setPreferredSize(new Dimension(700, 10));
		frmRegistro.getContentPane().add(panelBotones);
		GridBagLayout gbl_panelBotones = new GridBagLayout();
		gbl_panelBotones.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0};
		gbl_panelBotones.rowHeights = new int[]{30, 0, 0, 0, 0};
		gbl_panelBotones.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBotones.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelBotones.setLayout(gbl_panelBotones);
		
		btnRegistrar = new JButton("Registrar");
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		btnRegistrar.setFont(btnFont);
		btnRegistrar.setForeground(Lila);
		gbc_btnRegistrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrar.gridx = 7;
		gbc_btnRegistrar.gridy = 1;
		panelBotones.add(btnRegistrar, gbc_btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(btnFont);
		btnCancelar.setForeground(Lila);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 8;
		gbc_btnCancelar.gridy = 1;
		panelBotones.add(btnCancelar, gbc_btnCancelar);
		
		addManejadorBotonRegistrar(btnRegistrar);
		addManejadorBotonCandelar(btnCancelar);
		
	}
	
	private void addManejadorBotonRegistrar(JButton btnRegistrar) {
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean ok = comprobarCampos();
				if (ok) {
					String nombreUsuario = textNombreUsuario.getText();				
					String password = new String(passwordField.getPassword());
					fechaNacimiento = dateChooser.getDate(); 	
					boolean registrado = Controlador.getUnicaInstancia().registrarUsuario(
							textEmail.getText(), 
							textNombre.getText(),
							textApellidos.getText(),
							nombreUsuario,
							password, 
							fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					if (seleccion == 0) {
						fotoPerfil = fileChooser.getSelectedFile();
						Controlador.getUnicaInstancia().registrarFotoPerfil(nombreUsuario, fotoPerfil);
					}
					
					if (abreTextArea) {
						textoPresentacion = ventanaTexto.getTexto();
						//if(!ventanaTexto.getTexto().isEmpty()) {
						if (!textoPresentacion.isEmpty()) {
							Controlador.getUnicaInstancia().registrarTextoPresentacion(nombreUsuario, textoPresentacion);
						}
					}
				
					if (registrado) {
						JOptionPane.showMessageDialog(frmRegistro, "Usuario registrado correctamente.", "Registro",
								JOptionPane.INFORMATION_MESSAGE);
						VentanaEntrada entrada = new VentanaEntrada();
						entrada.frmEntrada.setVisible(true);
						frmRegistro.dispose();
					} 
					else {
							JOptionPane.showMessageDialog(frmRegistro, "Este usuario ya está registrado.\n",
									"Registro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	private void addManejadorBotonCandelar(JButton btnCancelar) {
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmRegistro.dispose();
			}
		});
	}
	
	//Comprobar que los campos de registro están bien
	private boolean comprobarCampos() {
		boolean camposOK = true;
		
		String nombreUsuario = textNombreUsuario.getText().trim();
		String password = new String(passwordField.getPassword());
		String password2 = new String(passwordField2.getPassword());

		if (textEmail.getText().trim().isEmpty() || textNombre.getText().trim().isEmpty() ||
				textApellidos.getText().trim().isEmpty() || nombreUsuario.isEmpty() ||
				password.isEmpty() || password2.isEmpty() || dateChooser.getDate()==null) {
			lblObligatorio.setForeground(Color.red);	
			camposOK = false;
		}
		else lblObligatorio.setForeground(Color.black);	
		
		if (!password.equals(password2)) {
			JOptionPane.showMessageDialog(frmRegistro, "Las contraseñas no coinciden.\n",
					"Registro", JOptionPane.ERROR_MESSAGE);
			camposOK = false;
		}
		
		return camposOK;
	}
	
	@SuppressWarnings("deprecation")
	public void editarPerfil() {
		textDescripcion.setVisible(false);
		lblObligatorio.setVisible(false);
		textEmail.disable();
		
		btnRegistrar.setText("Modificar");
		/*
		private JTextField textNombre;
		private JTextField textApellidos;
		private JTextField textNombreUsuario;
		private JPasswordField passwordField;
		private JPasswordField passwordField2;*/
	}

}








