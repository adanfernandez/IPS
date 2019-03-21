package renders;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

public class RendererDorsales extends SubstanceDefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
       this.setFont(new Font("Dialog", Font.BOLD, 14));
       this.setHorizontalAlignment(JLabel.CENTER);
        return this;
    }
}
