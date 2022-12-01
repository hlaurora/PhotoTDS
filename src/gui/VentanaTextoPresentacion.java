package gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaTextoPresentacion extends JDialog {

	public JFrame frmPresentacion;
	
	private JPanel panel;
	private JLabel lblDescripcion;
	private JTextArea textArea;
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private String textoPresentacion = "";
	
	public Color Lila = new Color(134, 46, 150);
	public Font btnFont = new Font("Arial", Font.BOLD, 15);

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextoPresentacion window = new TextoPresentacion();
					window.frmPresentacion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public VentanaTextoPresentacion(JFrame owner) {
		super(owner, "Texto Presentación", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
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
		frmPresentacion = new JFrame();
		frmPresentacion.setBounds(100, 100, 530, 315);
		//frmPresentacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frmPresentacion.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 10, 0, 0, 0, 0, 0, 0, 0, 10, 0, 10, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 20, 0, 0, 20, 10, 0, 5, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblDescripcion = new JLabel("Escribe tu presentación (Máximo 200 caracteres)");
		lblDescripcion.setFont(new Font("Arial", Font.BOLD, 15));
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		lblDescripcion.setForeground(Lila);
		gbc_lblDescripcion.gridwidth = 8;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 2;
		gbc_lblDescripcion.gridy = 1;
		panel.add(lblDescripcion, gbc_lblDescripcion);
		
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
		textArea.setFont(new Font("Arial", Font.PLAIN, 15));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.gridheight = 3;
		gbc_textArea.gridwidth = 10;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 3;
		panel.add(textArea, gbc_textArea);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textoPresentacion = textArea.getText();
				frmPresentacion.dispose();
			}
		});
		btnAceptar.setMaximumSize(new Dimension(100, 23));
		btnAceptar.setPreferredSize(new Dimension(90, 23));
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.anchor = GridBagConstraints.EAST;
		btnAceptar.setFont(btnFont);
		btnAceptar.setForeground(Lila);
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 7;
		gbc_btnAceptar.gridy = 7;
		panel.add(btnAceptar, gbc_btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmPresentacion.dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridwidth = 2;
		btnCancelar.setFont(btnFont);
		btnCancelar.setForeground(Lila);
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 8;
		gbc_btnCancelar.gridy = 7;
		panel.add(btnCancelar, gbc_btnCancelar);
	}
	
	public String getTexto() {
		//System.out.println(textoPresentacion);
		return textoPresentacion;
	}

}
