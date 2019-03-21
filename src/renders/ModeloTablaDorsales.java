package renders;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaDorsales extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ModeloTablaDorsales(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
   }
	@Override
	public boolean isCellEditable(int row, int column) {
		return column==2;
    }
}
