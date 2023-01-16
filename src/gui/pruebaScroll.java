package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;

public class pruebaScroll extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pruebaScroll frame = new pruebaScroll();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public pruebaScroll() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		//panel = new JPanel();
		//panel.setMaximumSize(new Dimension(32767, 500));
		//contentPane.add(panel);
		//panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.setViewportView(panel);
		contentPane.add(scrollPane);
		this.addPaneles();
	}
	
	public void addPaneles() {
		for (int i = 0; i < 10; i++) {
			JPanel panel_1 = new JPanel();
			JButton b = new JButton("Boton " + i);
			panel_1.add(b);
			scrollPane.add(panel_1);
		}
	}

}
