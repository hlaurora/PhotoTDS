package gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ImgTabla extends DefaultTableCellRenderer{
	
	public 	Component getTableCellRendererComponent(JTable table, Object o,
			boolean bln, boolean bln1, int i, int i1) {
		if (o instanceof JLabel) {
			JLabel lbl = (JLabel)o;
			return lbl;
		}
		return super.getTableCellRendererComponent(table, o, bln, bln1, i, i1);
	}

}
