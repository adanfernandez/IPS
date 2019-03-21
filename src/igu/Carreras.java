 package igu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import logica.Carrera;
import renders.Tabla;
import src.BaseDatos;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Toolkit;

public class Carreras extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnVolver;
	private JPanel pnCarreras;
	private JPanel pnBajo;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tm;
	private Boton boton;
	List<Carrera> carreras;
	List<Carrera>v;
	private JLabel lblCarreras;
	
	
	
	/**
	 * Create the dialog.
	 * @param vp 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Carreras(List<Carrera> carreras) throws IOException, ParseException, ClassNotFoundException, SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Carreras.class.getResource("/img/logo.jpeg")));
		setTitle("Carreras");
		this.carreras = carreras;
		//actualizarLista();
		setResizable(false);
		setBounds(100, 100, 981, 508);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnCarreras(), BorderLayout.CENTER);
		getContentPane().add(getPnBajo(), BorderLayout.SOUTH);
		boton = new Boton();
		añadirFilas();
		
	}
	
	//AQUI SE VUELVE A CARGAR TODO  habria que invocarlo cuando sea necesario. Donde es necesario?//
	 void actualizarLista() throws ClassNotFoundException, SQLException, ParseException
	{
		List<Carrera>todasLasCarreras = BaseDatos.carrerasTotal();
		for (Carrera c : todasLasCarreras)
			c.setparticipantes(BaseDatos.devuelveParticipantes(c.getNombre(),c.getFecha_celebracion()));
		
		for(int i=0;i<todasLasCarreras.size();i++)
		{
			for(int j=0;j<carreras.size();j++)
			{
				if(carreras.get(j).getNombre().equals(todasLasCarreras.get(i).getNombre())
						&& carreras.get(j).getFecha_celebracion().equals(todasLasCarreras.get(i).getFecha_celebracion()))
				{
					carreras.set(j, todasLasCarreras.get(i));
				}
			}
		}
	}
	
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setMnemonic('V');
			btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
			
					dispose();
					
				}
			});
		}
		return btnVolver;
	}
	private JPanel getPnCarreras() throws IOException {
		if (pnCarreras == null) {
			pnCarreras = new JPanel();
			pnCarreras.setLayout(new BorderLayout(0, 0));
			pnCarreras.add(getScrollPane());
			pnCarreras.add(getLblCarreras(), BorderLayout.NORTH);
		}
		return pnCarreras;
	}
	private JPanel getPnBajo() {
		if (pnBajo == null) {
			pnBajo = new JPanel();
			pnBajo.add(getBtnVolver());
		}
		return pnBajo;
	}
	
	
	private JScrollPane getScrollPane() throws IOException {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() throws IOException {
		if (table == null) {
			table = new JTable();
			table.setEnabled(true);
			propiedadesTabla();
			
		}
		return getTabla();
	}
	
	private void propiedadesTabla() throws IOException
	{
		table.setDefaultRenderer(Object.class, new Tabla());
		
		String [] titulos = {"Fecha", "Nombre", "Total de participantes", "Estado", "                       "};
		tm = new DefaultTableModel(null, titulos);
		table.setModel(tm);
		table.setRowHeight(30);
		table.setEnabled(false);
	}
	
	private JTable getTabla()
	{
		table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				int column = table.getColumnModel().getColumnIndexAtX(arg0.getX());
				int row = arg0.getY()/table.getRowHeight();
				if(row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0)
				{
					Object value = table.getValueAt(row, column);
					if(value instanceof JButton)
					{
						((JButton) value).doClick();
					}
				}
			}
		
		});
		return table;
	}
	
	
	
	public void añadirFilas() throws IOException, ParseException
	{		
		limpiar();
		String fecha;
		String nombre;
		String totalParticipantes="";
		String estado;
		
		ordenar();
		for(int i = 0; i < v.size(); i++)
		{
			fecha = v.get(i).getFecha_celebracion().toString();
			String[] fechaNac = fecha.toString().split("-");
			String año = fechaNac[0];
			String mes = fechaNac[1];
			String dia = fechaNac[2];
			
			
			
			
			nombre = v.get(i).getNombre();
			try {
				totalParticipantes = BaseDatos.numeroCorredores(v.get(i))+"";
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			estado = v.get(i).getEstado();
			Object[] añadir = new Object[]{dia + " / " + mes + " / " + año, nombre, totalParticipantes, estado, añadir(i)};
			tm.addRow(añadir);	
		}
		
	}
	
	
	private void limpiar(){
		
		 try {
	            DefaultTableModel modelo=(DefaultTableModel) table.getModel();
	            int filas=table.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);          
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
	
	
	
	
	/**
	 * Ordenación por selección
	 * @param a array de enteros, después de la llamada quedará ordenado
	 */
	public void ordenar () 
	{
		List<Carrera> a = carreras;
		
		int n= a.size();
		int posmin;
		for (int i=0;i<n-1;i++) 
		{
			// Buscar la posicion del mas pequeño de los que quedan
			posmin= i;
			for (int j= i+1; j < n; j++)
				if (a.get(j).getFecha_celebracion().compareTo(a.get(posmin).getFecha_celebracion())==-1)
					posmin= j;
			
			// Intercambia el que toca con el más pequeño
			Intercambiar (a,i,posmin);

		}
		
		v=a;
	}
	
	/**
	 * Intercambiar los elementos de las posiciones i, j en el array v
	 *  
	 */
	private void Intercambiar (List<Carrera> v, int i, int j)
	{
		Carrera t;
		t= v.get(i);
		v.set(i, v.get(j));
		v.set(j, t);
	}
	
	
	
	
	
	public class Boton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			JButton bt = (JButton)e.getSource();
			int fila = Integer.parseInt(bt.getActionCommand());

			Carrera carrera = null;
			/*ArrayList<Carrera> carreras = new ArrayList<Carrera>();
			try {
				carreras = BaseDatos.carrerasTotal();//TODAS LAS CARRERAS OTRA VEZ!!				
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/


			String nombre = table.getValueAt(fila, 1).toString();
				
			for(int i = 0; i < carreras.size(); i++)
			{
				if(carreras.get(i).getNombre().equals(nombre))
				{
					carrera = carreras.get(i);
				}
			}

				
			try {
				carrera.cargarCategorias();
				if(carrera.getEstado().equals("PENDIENTE")) {
					
					carrera.setCategorias(BaseDatos.devolverCategorias(carrera));
					carrera.setCuotas(BaseDatos.devolverCuotas(carrera));
					CreacionCarreras cc =	new CreacionCarreras();
					cc.rellenarDatos(carrera);
					cc.setVisible(true);
				}
				else {
				VentanaCorredor vO = new VentanaCorredor(carrera);
				vO.setVisible(true);}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
				dispose();
			
	
		}
	}
	
	
	
	private JButton añadir(int i) throws IOException{
		
		
		JButton bt = new JButton();		
		bt.setActionCommand(String.valueOf(i));
		bt.addActionListener(boton);
		bt.setText("Configurar/Ver Corredores");
		bt.setEnabled(true);
		bt.setToolTipText("Configurar la carrera");
		return bt;		
}
	
	
		
	private JLabel getLblCarreras() {
		if (lblCarreras == null) {
			lblCarreras = new JLabel("Carreras");
			lblCarreras.setHorizontalAlignment(SwingConstants.CENTER);
			lblCarreras.setFont(new Font("Tahoma", Font.BOLD, 30));
		}
		return lblCarreras;
	}
}