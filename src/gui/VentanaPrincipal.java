package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal {

	private JFrame frmPrincipal;
	
	private JLabel lblPhotoTDS;
	private JPanel panelNorte;
	private Component rigidArea;
	private JButton btnNewButton;
	private JPanel panel;
	private JButton btnNewButton_1;
	private Component horizontalStrut;
	private JTextField textField;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JList list;
	private Component rigidArea_1;
	private JMenu menu;
	private JPopupMenu popupMenu;
	private JLabel lblNewLabel;
	private JLabel lblPureba;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frmPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
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
		frmPrincipal = new JFrame();
		frmPrincipal.setBounds(100, 100, 548, 570);
		frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		crearPanelNorte();
	}
	
	
	private void crearPanelNorte() {
		panelNorte = new JPanel();
		frmPrincipal.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		
		lblPhotoTDS = new JLabel("PhotoTDS");
		lblPhotoTDS.setForeground(new Color(134, 46, 150));
		lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 35));
		lblPhotoTDS.setPreferredSize(new Dimension(170, 0));
		lblPhotoTDS.setForeground(new Color(134, 46, 150));
		lblPhotoTDS.setFont(new Font("HP Simplified Hans", Font.PLAIN, 30));
		panelNorte.add(lblPhotoTDS);
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setMaximumSize(new Dimension(70, 20));
		rigidArea.setPreferredSize(new Dimension(50, 20));
		panelNorte.add(rigidArea);
		
		btnNewButton = new JButton("");
		this.fixedSize(btnNewButton, 40, 40);
		btnNewButton.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/icons8-más-30.png")));
		panelNorte.add(btnNewButton);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panelNorte.add(horizontalStrut);
		
		panel = new JPanel();
		panelNorte.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		/*
		textField.setMaximumSize(new Dimension(300, 30));
		textField.setPreferredSize(new Dimension(300, 30));
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);*/
		this.fixedSize(textField, 100, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		btnBuscar = new JButton("");
		btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBuscar.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/icons8-búsqueda-30.png")));
		this.fixedSize(btnBuscar, 40, 40);
		panel.add(btnBuscar);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panelNorte.add(rigidArea_1);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.fixedSize(btnNewButton_1, 50, 50);
		btnNewButton_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/usuario48.png")));
		panelNorte.add(btnNewButton_1);

		menu = new JMenu("New menu");
		panelNorte.add(menu);
		
		popupMenu = new JPopupMenu();
		addPopup(menu, popupMenu);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmPrincipal.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		scrollPane.setViewportView(list);
		
		this.crearPanelPublicacion();

		
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void añadirImagen(JLabel lbl, String ruta) {
		ImageIcon image = new ImageIcon(VentanaPrincipal.class.getResource(ruta));
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				lbl.getWidth()-7, 
				lbl.getHeight()-7, 
				Image.SCALE_DEFAULT));
		lbl.setIcon(icono);
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



	private void crearPanelPublicacion() {
		JPanel panelPublicacion = new JPanel();
		panelPublicacion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		int ancho = frmPrincipal.getWidth();
		this.fixedSize(panelPublicacion, ancho, 10);
		frmPrincipal.getContentPane().add(panelPublicacion, BorderLayout.CENTER);
		
		lblNewLabel = new JLabel("");
		//lblNewLabel.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/icons8-búsqueda-60.png")));
		panelPublicacion.add(lblNewLabel);
	}


}
