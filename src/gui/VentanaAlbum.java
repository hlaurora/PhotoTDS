package gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador;
import dominio.Album;
import dominio.Foto;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class VentanaAlbum extends JFrame {

	private JPanel contentPane;
	
	private JPanel panelNorte;
	private JButton btnAddFoto;
	private JLabel lblTitulo;
	private JScrollPane scrollPanelFotos;
	private JTable tablaFotos;
	private DefaultTableModel tmFotos;
	
	private Album album;
	private String titulo;
	private int numFotos;
	private List<Foto> listaFotos;
	private JPanel panelSur;
	private JButton btnCerrar;
	private JFileChooser fileChooser;
	private File selectedFile;
	private JPanel panelAct;

	private Color LILA = new Color(134, 46, 150);
	private Font fontBtn = new Font("HP Simplified Hans", Font.BOLD, 15);
	private JButton btnNewButton;
	private JLabel lblNumMg;
	private JLabel lblFotoPerfil;
	private JButton btnMeGusta;


	/**
	 * Create the frame.
	 */
	public VentanaAlbum(Album a) {
		
		setBounds(100, 100, 504, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		this.album = a;
		this.titulo = album.getTitulo();
		this.numFotos = album.getFotos().size();
		this.listaFotos = album.getFotos();
		
		this.crearPanelNorte();
		this.crearPanelFotos();	
		this.crearPanelSur();
		
		this.panelAct = this.contentPane;
	}

	
	public void crearPanelNorte() {
		panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			lblTitulo = new JLabel(titulo);
			lblTitulo.setForeground(LILA);
			lblTitulo.setFont(fontBtn);
			lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelNorte.add(lblTitulo);
		}
		{
			lblNumMg = new JLabel(album.getMeGustas() + " Me gustas");
			panelNorte.add(lblNumMg);
		}
		{
			lblFotoPerfil = new JLabel("");
			lblFotoPerfil.setIcon(escalarImagen(lblFotoPerfil, 
					album.getUsuario().getFotoPerfil().getPath()));
			panelNorte.add(lblFotoPerfil);
		}
	}
	
	public void crearPanelFotos() {
		//scrollPanelFotos = new JScrollPane();
		//scrollPanelFotos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//contentPane.add(scrollPanelFotos, BorderLayout.CENTER);
		JPanel panelFotos = new JPanel();
		contentPane.add(panelFotos, BorderLayout.CENTER);
		{
			tablaFotos = new JTable();
			//scrollPanelFotos.setViewportView(tablaFotos);
			{
				String titulos[] = {"col1", "col2", "col3", "col4"};
				tmFotos = new DefaultTableModel(null, titulos);
				{
					//tablaFotos = new JTable();
					tablaFotos.setRowHeight(70);
					tablaFotos.setModel(tmFotos);
					tablaFotos.setTableHeader(null);
					tablaFotos.setDefaultRenderer(Object.class, new ImgTabla());
				}
				tablaFotos.setDefaultEditor(Object.class, null);  
				for (int i = 0; i < 4; i++) {
					tablaFotos.getColumnModel().getColumn(i).setPreferredWidth(120);
				}
				
				this.mostrarFotos();
			
				//scrollPanelFotos.add(tablaFotos);
				panelFotos.add(tablaFotos);
			}
		}
		
		
	}
	
	private void crearPanelSur() {
		panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		{
			btnMeGusta = new JButton("");
			this.addManejadorBotonMeGusta(btnMeGusta);
			btnMeGusta.setIcon(new ImageIcon(PanelPublicacion.class.getResource("/imagenes/icons8-me-gusta-16.png")));
			panelSur.add(btnMeGusta);
		}
		{
			btnAddFoto = new JButton(" + ");
			btnAddFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnAddFoto.setForeground(LILA);
			btnAddFoto.setFont(fontBtn);
			this.addManejadorBtnAddFoto(btnAddFoto);
			panelSur.add(btnAddFoto);
		}
		{
			btnCerrar = new JButton("Cerrar");
			btnCerrar.setAlignmentX(Component.RIGHT_ALIGNMENT);
			btnCerrar.setForeground(LILA);
			btnCerrar.setFont(fontBtn);
			this.addManejadorBtnCerrar(btnCerrar);
			panelSur.add(btnCerrar);
		}
	}
	
	public void mostrarFotos() {
		
		//Limpiamos la tabla
		for (int i = 0; i < tablaFotos.getRowCount(); i++) {
			tmFotos.removeRow(i);
			i-=1;
		}
				
		String ruta;
		//Calculamos el número de filas
		int numFilas = 0;
		if ((0 < numFotos) && (numFotos <= 4)) {
			numFilas = 1;
		}
		else if (numFotos > 3) numFilas = (numFotos/4) + 1;
		
		//Rellenamos la tabla
		int j;
		int f = 0;
		
		if (numFilas == 1) {
			tmFotos.addRow(new Object[] {null, null, null, null});
			for (j = 0; j < numFotos; j++) {
				ruta = listaFotos.get(j).getRuta();
				tmFotos.setValueAt(new JLabel(imagenCelda(ruta)), 0, j);
			}
		}
		
		else {
			int i = 0;
			while (i < numFilas-1) {
				tmFotos.addRow(new Object[] {null ,null, null, null});
				for (j = 0; j<4; j++) {
					ruta = listaFotos.get(f).getRuta();
					tmFotos.setValueAt(new JLabel(imagenCelda(ruta)), i, j);
					f++;
				}
				i++;
			}
			//Última fila
			tmFotos.addRow(new Object[] {null, null, null, null});
			while (f < numFotos) {
				ruta = listaFotos.get(f).getRuta();
				tmFotos.setValueAt(new JLabel(imagenCelda(ruta)), i, f%4);
				f++;
			}
		}	
		this.addManejadorTablaFotos(tablaFotos);
	}
	
	private void addManejadorTablaFotos(JTable tabla) {
		int numFilas = tabla.getRowCount();
		tabla.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent evt) {
		        int row = tabla.rowAtPoint(evt.getPoint());
		        int col = tabla.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	int pos = (col+((row%numFilas)*4));
		        	VentanaPublicacion va = new VentanaPublicacion(album.getUsuario().getNombreUsuario(),
		        			album.getFotos().get(pos).getRuta(), panelAct);
		        	//VentanaAlbum va = new VentanaAlbum(albumesUsuario.get(pos));
		        	va.setLocationRelativeTo(tabla);
		        	va.verFoto();
		        	va.setVisible(true);
		       }
		    }
		});
	}
	
	private void addManejadorBtnAddFoto(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				int seleccion = fileChooser.showOpenDialog(btn);
				if (seleccion != JFileChooser.CANCEL_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					VentanaPublicacion vap = new VentanaPublicacion(album.getUsuario().getNombreUsuario(),
							selectedFile.getPath(), panelAct);
					vap.setVisible(true);	
					vap.setLocationRelativeTo(btnAddFoto);
					vap.añadirFotoAlbum(album.getId(), selectedFile.getPath());
				}
			}
		});
	}
	
	private void addManejadorBotonMeGusta(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controlador.getUnicaInstancia().darMeGusta(album.getId());
				lblNumMg.setText(album.getMeGustas() + " Me gustas");
				//lblNumMg.setText(Controlador.getUnicaInstancia().getMeGustas(album.getId()) + " Me gustas");
			}
		});
	}
	
	private void addManejadorBtnCerrar(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	private Icon imagenCelda(String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				170, 
				70-4, 
				Image.SCALE_DEFAULT));
		return icono;
	}
	
	private Icon escalarImagen(JLabel lbl, String ruta) {
		ImageIcon image = new ImageIcon(ruta);
		Icon icono = new ImageIcon(image.getImage().getScaledInstance(
				lbl.getWidth()-4, 
				lbl.getHeight()-4, 
				Image.SCALE_DEFAULT));
		return icono;
	}
	
}
