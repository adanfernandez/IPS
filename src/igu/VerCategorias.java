package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logica.Categoria;
import renders.Tabla;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerCategorias extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tm;
	private ArrayList<Categoria> categorias;
	private JPanel panel;
	private JButton btnVolver;

	
	

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public VerCategorias(ArrayList<Categoria> categorias) throws IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDorsales.class.getResource("/img/logo.jpeg")));
		setTitle("Categor\u00EDas");
		this.categorias = categorias;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 661, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		contentPane.add(getPanel(), BorderLayout.SOUTH);
	}
	
	private JScrollPane getScrollPane() throws IOException {
		
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setFocusTraversalPolicyProvider(true);
			scrollPane.setBorder(new LineBorder(Color.BLACK, 3));
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	private JTable getTable() throws IOException {
		if (table == null) {
			table = new JTable();
			table.setDragEnabled(true);
			table.setFont(new Font("Tahoma", Font.PLAIN, 11));
			// table.setFocusTraversalPolicyProvider(true);
			table.setEnabled(true);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// table.setRowSelectionAllowed(false);
			// table.setFillsViewportHeight(true);
			// table.setFocusCycleRoot(true);
			// table.setRowHeight(150);

			propiedadesTabla();
		}
		return getTabla();
	}
	
	private void propiedadesTabla() throws IOException {
		table.setDefaultRenderer(Object.class, new Tabla());
		String[] titulos = { "Género","Nombre de la categoría", "Edad inicial", "Edad final"};
		tm = new DefaultTableModel(null, titulos);
		table.setModel(tm);
		table.setEnabled(false);
		añadirFilas();

	}

	private JTable getTabla() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int column = table.getColumnModel().getColumnIndexAtX(
						arg0.getX());
				int row = arg0.getY() / table.getRowHeight();

				if (row < table.getRowCount() && row >= 0
						&& column < table.getColumnCount() && column >= 0) {
					Object value = table.getValueAt(row, column);
					if (value instanceof JButton) {
						((JButton) value).doClick();

					}
				}
			}

		});
		return table;
	}

	
	public void añadirFilas() {
			
			

			
			for(int i = 0; i < categorias.size(); i++)
			{	
				
				String genero = categorias.get(i).getGenero();
				String nombre = categorias.get(i).getNombre();
				int edadi = categorias.get(i).getEdadi();
				String edadf= String.valueOf(categorias.get(i).getEdadf());
				if(Integer.parseInt(edadf)==Integer.MAX_VALUE)
					edadf = "Mayor que " + edadi;
					
				
				Object[] añadir = new Object[]{genero, nombre, edadi, edadf};
				tm.addRow(añadir);
			}	
			

	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnVolver());
		}
		return panel;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		return btnVolver;
	}
}
