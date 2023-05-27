package gui;

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
	 * Crea el frame para añadir una foto usando drag and drop o seleccionando del ordenador
	 * con un JFileChooser
	 */
	public VentanaAddPublicacion(String usuario, JPanel ventanaAct) {
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.usuario = usuario;
		this.ventanaAct = ventanaAct;

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		//Panel para el drag and drop
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

		//Botón para seleccionar de tu ordenador
		JButton button = new JButton("Seleccionar de tu ordenador");
		button.setMaximumSize(new Dimension(400, 23));
		button.setForeground(Constantes.LILA);
		button.setFont(Constantes.NEGRITA_15);
		this.addManejadorButton(button);
		contentPane.add(button);
	}

	//Manejador para el botón -> se abre un JFileChooser
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

	//Se abre VentanaPublicacion con la foto seleccionada para añadir un comentario
	// y darle a compartir
	public void compartirFoto(File file) {
		VentanaPublicacion vap = new VentanaPublicacion(usuario, file.getPath());
		vap.setVisible(true);	
		vap.compartirFoto(ventanaAct);	
	}

}
