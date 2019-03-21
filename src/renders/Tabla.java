package renders;



import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Tabla extends DefaultTableCellRenderer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        
    		if(o instanceof JLabel)
    		{
    			JLabel lb = (JLabel)o;
    			lb.setEnabled(true);
    			return lb;
    		}
    		if(o instanceof JButton)
    		{
    			JButton bt = (JButton)o;
    			bt.setEnabled(true);
    			return bt;
    		}
    		if(o instanceof JSpinner)
    		{
    			JSpinner jt = (JSpinner)o;
    			jt.setEnabled(true);
    			return jt;
    		}
    		
    	
            return super.getTableCellRendererComponent(table, o, isSelected, hasFocus, row, column);
    } 
	
	
		public boolean isCellEditable(int row, int column){
			return false;
		}
	
    
	
    
  
   
}


