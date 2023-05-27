package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrmPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FrmPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 110, 620, 560);
		setLocationRelativeTo(null);

		contentPane = new JPanel();

		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel);
	}

}
