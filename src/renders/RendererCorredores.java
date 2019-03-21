package renders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

public class RendererCorredores extends SubstanceDefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		this.setFont(new Font("Dialog", Font.BOLD, 14));
		this.setHorizontalAlignment(JLabel.CENTER);
		if (row % 2 == 0) {
			this.setBackground(Color.white);
		}

		else {
			this.setBackground(new Color(Integer.parseInt("e8eaef", 16)));
		}
		if (column == 7) {
			if (value.toString().length() > 0) {
				if (Double.parseDouble(value.toString()) < 0) {
					this.setForeground(new Color(Integer.parseInt("9ab2f9", 16)));
				} else if (Double.parseDouble(value.toString()) > 0) {
					this.setForeground(new Color(Integer.parseInt("f98484", 16)));
				} else {
					this.setForeground(Color.black);
				}
			}
		} else {
			this.setForeground(Color.black);
		}
		return this;
	}
}