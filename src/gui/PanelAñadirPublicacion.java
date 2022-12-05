package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PanelAñadirPublicacion extends JPanel {

	private VentanaPrincipal ventanaPrincipal;
	/**
	 * Create the panel.
	 */
	public PanelAñadirPublicacion(VentanaPrincipal vp) {
		
		ventanaPrincipal = vp;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0};
		gridBagLayout.rowHeights = new int[]{5, 0, 0, 0, 0, 0, 0, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblEscribe = new JLabel("Escribe un comentario (máximo 200 caracteres)");
		lblEscribe.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblEscribe = new GridBagConstraints();
		gbc_lblEscribe.insets = new Insets(0, 0, 5, 5);
		gbc_lblEscribe.gridx = 9;
		gbc_lblEscribe.gridy = 1;
		add(lblEscribe, gbc_lblEscribe);
		
		JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.gridwidth = 8;
		gbc_lblNewLabel.gridheight = 5;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 4;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 9;
		gbc_textArea.gridy = 2;
		add(textArea, gbc_textArea);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 9;
		gbc_panel.gridy = 6;
		add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnCompartir = new JButton("Compartir");
		btnCompartir.setHorizontalAlignment(SwingConstants.RIGHT);
		btnCompartir.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(btnCompartir);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnCancelar.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnCancelar);

	}

}
