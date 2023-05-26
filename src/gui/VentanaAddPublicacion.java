package gui;

import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;

public class VentanaAddPublicacion extends JFrame {

	private JPanel contentPane;
	private JFileChooser fileChooser;
	private File selectedFile;
	private String usuario;
	private JPanel ventanaAct;

	/**
	 * Create the frame.
	 */
	public VentanaAddPublicacion(String usuario, JPanel ventanaAct) {
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.usuario = usuario;
		this.ventanaAct = ventanaAct;
		
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JEditorPane editorPane = new JEditorPane(); 
		contentPane.add(editorPane); 
		editorPane.setText("Puedes arrastrar el fichero aquí:");
		editorPane.setForeground(Constantes.LILA);
		editorPane.setFont(Constantes.NEGRITA_15);
		editorPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		editorPane.setEditable(false); 
		editorPane.setDropTarget(new DropTarget() { 
			public synchronized void drop(DropTargetDropEvent evt) { 
				try { 
					evt.acceptDrop(DnDConstants.ACTION_COPY); 
					List<File> droppedFiles = (List<File>)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor); 
					for (File file : droppedFiles) { 
						compartirFoto(file); 
					} 
					dispose();
				} catch (Exception ex) { 
					ex.printStackTrace(); 
				} 
			} 
		});
		
		JButton button = new JButton("Seleccionar de tu ordenador");
		button.setMaximumSize(new Dimension(400, 23));
		button.setForeground(Constantes.LILA);
		button.setFont(Constantes.NEGRITA_15);
		this.addManejadorButton(button);
		contentPane.add(button);
	}
	
	public void addManejadorButton(JButton btn) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				int seleccion = fileChooser.showOpenDialog(btn);
				if (seleccion != JFileChooser.CANCEL_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					compartirFoto(selectedFile);
				}	
				dispose();
			}
		});
	}
		
	public void compartirFoto(File file) {
		VentanaPublicacion vap = new VentanaPublicacion(usuario, file.getPath());
		vap.setVisible(true);	
		vap.compartirFoto(ventanaAct);	
	}

}
