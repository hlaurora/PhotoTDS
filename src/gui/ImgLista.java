package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;

public class ImgLista extends DefaultListCellRenderer {

	public 	Component getListCellRendererComponent(JList list, Object o, int i,
			boolean bln, boolean bln1) {
		if (o instanceof PanelPublicacion) {
			PanelPublicacion panel = (PanelPublicacion)o;
			return panel;
		}
		return super.getListCellRendererComponent(list, o, i, bln, bln);
	}
}
