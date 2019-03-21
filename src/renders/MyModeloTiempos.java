package renders;

import javax.swing.table.*;

public class MyModeloTiempos extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public MyModeloTiempos(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
   }
	@Override
	public boolean isCellEditable(int row, int column) {
       if(column==2) {return true;}
		return false;
    }
	
}
