package gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ImgTabla extends DefaultTableCellRenderer{
	
	public 	Component getTableCellRendererComponent(JTable table, Object o,
			boolean bln, boolean bln1, int i, int i1) {
		if (o instanceof JLabel) {
			JLabel lbl = (JLabel)o;
			return lbl;
		}
		else if (o instanceof JPanel) {
			JPanel panel = (JPanel)o;
			return panel;
		}
		else if (o instanceof PanelPublicacion) {
			PanelPublicacion panel = (PanelPublicacion)o;
			return panel;
		}
		return super.getTableCellRendererComponent(table, o, bln, bln1, i, i1);
	}

}
