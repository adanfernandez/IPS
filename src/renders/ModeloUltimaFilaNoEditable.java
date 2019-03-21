package renders;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class ModeloUltimaFilaNoEditable extends DefaultTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloUltimaFilaNoEditable(Object[][] data, Object[] titulos) {
		super(data,titulos);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		//if( super.getRowCount() - 1 != row) //ULTIMA FILA NO EDITABLE

//		return !(super.getRowCount() -1 == row && super.getColumnCount() -1 == column); //ULTIMA CELDA NO EDITABLE 
		boolean ultimaCeldaNoEditable = !(super.getRowCount() -1 == row && super.getColumnCount() -1 == column);
		boolean celdaPrimeraFilaSegundaColumnaNoEditable =  !(0 == row && 1 == column);
		return ultimaCeldaNoEditable && celdaPrimeraFilaSegundaColumnaNoEditable;
		
	}
}
