package renders;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Class<?> getColumnClass(int columnIndex) {
      Class c = String.class;
      switch (columnIndex) {
        case 3:
          c = Boolean.class;
          break;
      }
      return c;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return column == 3;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void setValueAt(Object aValue, int row, int column) {
      if (aValue instanceof Boolean && column == 3) {
        Vector rowData = (Vector)getDataVector().get(row);
        rowData.set(3, (boolean)aValue);
        fireTableCellUpdated(row, column);
      }
    }
  }