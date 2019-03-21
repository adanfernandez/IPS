package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Toolkit;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import src.BaseDatos;
import utilidades.Util;
import logica.Carrera;
import logica.Corredor;
import logica.Participante;
import renders.ModeloNoEditable;
//import renders.RendererSubstance;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


/**
 * 
 * @author Nicolás
 *VentanaClasificacion se le pasa por parametro una Carrera y muestra su clasificacion
 */
public class VentanaClasificacion extends JFrame {
	private Carrera carrera;
	private JPanel contentPane;
	private JLabel lblClasificacion;
	private JTabbedPane tabbedPane;
	private JPanel panelClasificacionAbsoluta;
	private JScrollPane scrollPane_2;
	private JTable tableAbs;
//	private DefaultTableModel modeloTabla;
//	private DefaultListModel modeloLista;
	private ModeloNoEditable modeloTabla;
	private JPanel panelClasificacionMasc;
	private JScrollPane scrollPane;
	private JTable tableMasc;
	private JPanel panelClasificacionFem;
	private JScrollPane scrollPane_1;
	private JTable tableFem;

//	protected JLabel areaDescripcion;
	
	/**
	 * Create the frame.
	 */
	public VentanaClasificacion(Carrera carrera) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaClasificacion.class.getResource("/img/logo.jpeg")));
		this.carrera= carrera;
		
		List<Participante> aux = new ArrayList<Participante>();
		for(Participante p:carrera.getparticipantes())
		{
			if(p.getEstado().equals("PAGADO"))
			{
				aux.add(p);
			}
		}
		carrera.setparticipantes(aux);
		setTitle("Clasificacion");
//		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaClasificacion.class.getResource("/img/llave.JPG")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 803, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLabel_1(), BorderLayout.NORTH);
		contentPane.add(getTabbedPane());
	}
	private JLabel getLabel_1() {
		if (lblClasificacion == null) {
			lblClasificacion = new JLabel("Clasificaciones");
			lblClasificacion.setFont(new Font("Tahoma", Font.PLAIN, 50));
		}
		return lblClasificacion;
	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Clasificacion Absoluta", null, getPanelClasificacionAbsoluta(), null);
			tabbedPane.setDisplayedMnemonicIndexAt(0, 0);
			tabbedPane.setMnemonicAt(0, 86);
			tabbedPane.addTab("Clasificacion Masculina", null, getPanelClasificacionMasc(), null);
			tabbedPane.addTab("Clasificacion Femenina", null, getPanelClasificacionFem(), null);
		}
		return tabbedPane;
	}
	private JPanel getPanelClasificacionAbsoluta() {
		if (panelClasificacionAbsoluta == null) {
			panelClasificacionAbsoluta = new JPanel();
			panelClasificacionAbsoluta.setLayout(new BorderLayout(0, 0));
			panelClasificacionAbsoluta.add(getScrollPane_2(), BorderLayout.CENTER);
		}
		return panelClasificacionAbsoluta;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setViewportView(getTableAbs());
		}
		return scrollPane_2;
	}
	private JTable getTableAbs() {
		if (tableAbs == null) {
			tableAbs = new JTable();
			String[] nombreColumnas = {"Nombre","Tiempo","Posicion","Sexo","Club"};
			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			tableAbs = new JTable(modeloTabla);
			//
//			tableAbs.setDefaultRenderer(Object.class, new RendererSubstance());
			//evitar que ewl usuario pueda intercambiar columnas
			tableAbs.getTableHeader().setReorderingAllowed(false);
			//incrementar alto fila
			tableAbs.setRowHeight(30);
			//modificar ancho de la columna 0
			tableAbs.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(200);
			tableAbs.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String codigoSelec = (String)tableAbs.getValueAt(tableAbs.getSelectedRow(), 0);
//					textArea_1.setText(inmobiliaria.getDescripcionMansion(codigoSelec));
//					if(e.getClickCount()==2){
//						modeloLista.addElement(codigoSelec);
//					}
				}
			});
			añadirFilas("absoluta");
		}
		return tableAbs;
	}
	private void añadirFilas(String categoria){
		Object[] nuevaFila = new Object[5];
		List<Participante> relacionCorredores;
		if(categoria.equals("absoluta"))
			relacionCorredores=carrera.clasificacionAbsoluta();
		else if(categoria.equals("masculina"))
			relacionCorredores=carrera.clasificacionMasculina();
		else
			relacionCorredores=carrera.clasificacionFemenina();
		
		for (int i = 0; i< relacionCorredores.size();i++){
			nuevaFila[0]=relacionCorredores.get(i).getCorredor().getNombre();
			nuevaFila[1]=relacionCorredores.get(i).getTiempo();
			if(relacionCorredores.get(i).getTiempo()==0.0){
				nuevaFila[1]="--";
			}
			nuevaFila[2]=i+1;
			if(relacionCorredores.get(i).getCorredor().getGenero())
				nuevaFila[3]="Masculino";
			else
				nuevaFila[3]="Femenino";
			nuevaFila[4]=relacionCorredores.get(i).getClub().getNombreClub();
			modeloTabla.addRow(nuevaFila);
		}
		
	}
	private JPanel getPanelClasificacionMasc() {
		if (panelClasificacionMasc == null) {
			panelClasificacionMasc = new JPanel();
			panelClasificacionMasc.setLayout(new BorderLayout(0, 0));
			panelClasificacionMasc.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panelClasificacionMasc;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTableMasc());
		}
		return scrollPane;
	}
	private JTable getTableMasc() {
		if (tableMasc == null) {
			tableMasc = new JTable();
			String[] nombreColumnas = {"Nombre","Tiempo","Posicion","Sexo","Club"};
			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			tableMasc = new JTable(modeloTabla);
			//
//			tableMasc.setDefaultRenderer(Object.class, new RendererSubstance());
			//evitar que ewl usuario pueda intercambiar columnas
			tableMasc.getTableHeader().setReorderingAllowed(false);
			//incrementar alto fila
			tableMasc.setRowHeight(30);
			//modificar ancho de la columna 0
			tableMasc.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(200);
//			tableMasc.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseClicked(MouseEvent e) {
////					String codigoSelec = (String)tableMasc.getValueAt(tableMasc.getSelectedRow(), 0);
////					textArea_1.setText(inmobiliaria.getDescripcionMansion(codigoSelec));
////					if(e.getClickCount()==2){
////						modeloLista.addElement(codigoSelec);
////					}
//				}
//			});
			añadirFilas("masculina");
		}
		return tableMasc;
	}
		
	
	private JPanel getPanelClasificacionFem() {
		if (panelClasificacionFem == null) {
			panelClasificacionFem = new JPanel();
			panelClasificacionFem.setLayout(new BorderLayout(0, 0));
			panelClasificacionFem.add(getScrollPane_1(), BorderLayout.CENTER);
		}
		return panelClasificacionFem;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTableFem());
		}
		return scrollPane_1;
	}
	private JTable getTableFem() {
		if (tableFem== null) {
			tableFem = new JTable();
			String[] nombreColumnas = {"Nombre","Tiempo","Posicion","Sexo","Club"};
			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			tableFem = new JTable(modeloTabla);
			//
//			tableFem.setDefaultRenderer(Object.class, new RendererSubstance());
			//evitar que ewl usuario pueda intercambiar columnas
			tableFem.getTableHeader().setReorderingAllowed(false);
			//incrementar alto fila
			tableFem.setRowHeight(30);
			//modificar ancho de la columna 0
			tableFem.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(200);
			tableFem.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String codigoSelec = (String)tableFem.getValueAt(tableFem.getSelectedRow(), 0);
//					textArea_1.setText(inmobiliaria.getDescripcionMansion(codigoSelec));
//					if(e.getClickCount()==2){
//						modeloLista.addElement(codigoSelec);
//					}
				}
			});
			añadirFilas("femenina");
		}
		return tableFem;
	}
}