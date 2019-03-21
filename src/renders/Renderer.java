package renders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

public class Renderer extends SubstanceDefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;

		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	
	       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	       this.setFont(new Font("Dialog", Font.BOLD, 14));
	       this.setHorizontalAlignment(JLabel.CENTER);
	       if (row%2==0) {
	    	   this.setBackground(Color.white);
	       }
	       
	       else {
	    	   this.setBackground(new Color(Integer.parseInt("e8eaef",  16 )));
	       }
	       if (value.toString().length()>5)
	       {
	    	   
		       if (value.toString().substring(value.toString().length()-5).equals("horas")) { 
		    	   if(Integer.parseInt(value.toString().substring(0,value.toString().length()-6))>=48)
		    		   this.setBackground(Color.red);
				   else {}
		    	   }
		       else {}
	       }
	       else {}
	       		
	       return this;
	    }
}