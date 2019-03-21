package igu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import logica.Carrera;
import src.BaseDatos;
import utilidades.Util;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class VentanaCargaCronometraje extends JFrame {

	private JPanel contentPane;
	private JPanel panelBoton;
	private JButton btnImportarExcel;
	private JScrollPane scrollPane;
	private JTable table;
	private Vector<String> headers = new Vector<String>();
	private DefaultTableModel model = null;
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private int tableWidth = 0; // set the tableWidth
	private int tableHeight = 0; // set the tableHeight
	private JFileChooser jChooser;
	private JButton btnCargarEnSistema;
	private String fileName;
	private String fecha;
	private VentanaTiempos PanelAnterior;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			//		VentanaCargaCronometraje frame = new VentanaCargaCronometraje();
				//	frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaCargaCronometraje(VentanaTiempos ventanaAnterior) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCargaCronometraje.class.getResource("/img/logo.jpeg")));
		PanelAnterior=ventanaAnterior;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelBoton(), BorderLayout.NORTH);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
	}

	private JPanel getPanelBoton() {
		if (panelBoton == null) {
			panelBoton = new JPanel();
			panelBoton.add(getBtnImportarExcel());
			panelBoton.add(getBtnCargarEnSistema());
		}
		return panelBoton;
	}

	private JButton getBtnImportarExcel() {
		if (btnImportarExcel == null) {
			btnImportarExcel = new JButton("Importar Excel");
			btnImportarExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int respuesta = getSelector().showOpenDialog(null);
					File file = jChooser.getSelectedFile();
					if (respuesta == JFileChooser.APPROVE_OPTION) {
						if (!file.getName().endsWith("xls")) {
							JOptionPane.showMessageDialog(null, "Please select only Excel file.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							try{
							String nombreYfecha = file.getName();
							fileName=nombreYfecha.split(",")[0];
							System.out.println(fileName);
							fecha =  nombreYfecha.split(",")[1].split(".xls")[0];
							System.out.println(fecha);
							fillData(file);
							model = new DefaultTableModel(data, headers);
							tableWidth = model.getColumnCount() * 150;
							tableHeight = model.getRowCount() * 25;
							table.setPreferredSize(new Dimension(tableWidth, tableHeight));

							table.setModel(model);
							btnCargarEnSistema.setEnabled(true);
							}catch(RuntimeException e){
								JOptionPane.showMessageDialog(null,"comprueba el nombre del fichero formato: <Nombre carrera>,<fecha>.xml");
							}
							
						}
					}
				}
			});
		}
		return btnImportarExcel;
	}

	// //METODO QUE CARGA EL CONTENIDO EN LA BBDD, SI ALGUN DATO ESTA MAL NO LO
	// CARGA, NO IMPLICA QUE NO CARGUE LA TOTALIDAD DEL FICHERO, SOLO AQUELLOS
	// DATOS QUE TENGAN EL FORMATO CORRECTO
	// void cargaContenido(File archivo) throws FileNotFoundException,
	// IOException, SQLException {
	// boolean errorFormato = false;
	// boolean errorPresencia = false;
	// boolean errorNombreCarrera = false;
	//
	// String carrera = archivo.getName();
	// String nombreCarrera = obtenNombreCarrera(carrera);
	//
	// if(!gc.comprobadorCarrera(nombreCarrera)) {
	// errorNombreCarrera = true;
	// }
	//
	//
	// String cadena; //ESTRUCTURA DEL FICHERO: TIEMPO DNI
	// FileReader f = new FileReader(archivo);
	// BufferedReader b = new BufferedReader(f);
	//
	// while((cadena = b.readLine())!=null) {
	// String[] partes = cadena.split(" "); //dividimos las partes
	// if(partes.length==2) {
	// if(partes[0].equals("---")) { //sin tiempo?
	// DataBaseManager.añadirTiempoAtleta(nombreCarrera, partes[0], partes[1]);
	// //buscamos su dni en la bbdd y le asignamos su tiempo null
	// }
	// else { //con tiempo?
	// if(gc.comprobadorTiempos(partes[0])) { //si el tiempo es valido buscamos
	// su dni en la bbdd y le asignamos su tiempo
	// DataBaseManager.añadirTiempoAtleta(nombreCarrera, partes[0], partes[1]);
	// }
	// else {
	// errorFormato = true;
	// }
	// }
	// //Lo que conseguimos así es que añada los corredores cuyo formato es
	// correcto, los que tengan un formato incorrecto han de ser revisados por
	// el cliente
	//
	// //Vamos a comprobar tambien que el corredor esté en la carrera, si no
	// está lo daremos a conocer:
	// if(!gc.comprobadorPresencia(partes[1],nombreCarrera)) {
	// errorPresencia = true;
	// }
	// }
	// else {
	// errorFormato = true;
	// }
	// }
	// if(errorNombreCarrera) {
	// JOptionPane.showMessageDialog(null, "La carrera referente al nombre del
	// fichero no existe en la base de datos.");
	// sinFallosNombreCarrera = false;
	// }
	// else {
	// if(errorFormato) {
	// JOptionPane.showMessageDialog(null, "Algunos tiempos no han sido añadidos
	// a la base de datos. Por favor, compruebe el fichero de tiempos.");
	// sinFallosFormato=false;
	// }
	// if(errorPresencia) {
	// JOptionPane.showMessageDialog(null, "Alguno de los corredores del fichero
	// no se encuentra en ésta carrera, por tanto no ha sido añadido.");
	// sinFallosDni = false;
	// }
	// }
	//
	// b.close();
	// }

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setEnabled(false);
		}
		return table;
	}

	public JFileChooser getSelector() {
		if (jChooser == null) {
			jChooser = new JFileChooser();
			jChooser.setMultiSelectionEnabled(true);
			jChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel", "xls"));
			// Fija en el directorio de ejecucion del propio programa
			String curDir = System.getProperty("user.dir");
			jChooser.setCurrentDirectory(new File(curDir));
			// //con esta en el escritorio
			// String desktopPath = System.getProperty("user.home")+"/Desktop";
			// //si el operativo esta en español hay que cambiar desktop por
			// escritorio
			// selector.setCurrentDirectory(new File(desktopPath));
		}
		return jChooser;
	}

	private Map<Integer, Double> datos;

	/**
	 * Fill JTable with Excel file data.
	 *
	 * @param file
	 *            file :contains xls file to display in jTable
	 */
	void fillData(File file) {

		Workbook workbook = null;
		datos = new HashMap<Integer, Double>();
		try {
			try {
				workbook = Workbook.getWorkbook(file);
			} catch (IOException ex) {
				Logger.getLogger(VentanaCargaCronometraje.class.getName()).log(Level.SEVERE, null, ex);
			}
			Sheet sheet = workbook.getSheet(0);
			headers.clear();
			
			for (int i = 0; i < sheet.getColumns(); i++) {
				Cell cell1 = sheet.getCell(i, 0);
				headers.add(cell1.getContents());
			}

			data.clear();

			// boolean pasa=true;
			for (int j = 1; j < sheet.getRows(); j++) {

				Vector<String> d = new Vector<String>();

				for (int i = 0; i < sheet.getColumns(); i++) {
					// pasa= true;
					Cell cell = sheet.getCell(i, j);
//					cell.getContents().replaceAll("A", "-1");
//					if (!cell.getContents().equals("-1")) {
						int identificador = 0;
						try {
							identificador = Integer.parseInt(sheet.getCell(0, j).getContents());
							double tiempo = Double.parseDouble(sheet.getCell(1, j).getContents());
							datos.put(identificador, tiempo);
							d.add(cell.getContents());
						} catch (Exception e) {
							d.add(cell.getContents() + " No se cargara en el sistema");
							// JOptionPane.showMessageDialog(this, " El sistema
							// solo cargara ficheros con numeros");
						}
//					}

				}
				boolean pasa = true;
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).get(0).equals(d.get(0))) {
						pasa = false;

					}

				}

				if (pasa)
					data.add(d);
				else {
					Vector<String> q = new Vector<String>();
					q.add(d.get(0) + " identificador repetido No se cargara en el sistema");
					q.add(d.get(1));
					data.add(q);
					// JOptionPane.showMessageDialog(this, "identificador
					// repetido");
				}

				//
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}
		System.out.print(datos);
	}

	private JButton getBtnCargarEnSistema() {
		if (btnCargarEnSistema == null) {
			btnCargarEnSistema = new JButton("Cargar en sistema");
			btnCargarEnSistema.setEnabled(false);
			btnCargarEnSistema.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						BaseDatos.añadirTiempoAtleta(datos, fileName, Util.convierteStringToDate(fecha));
					
						Carrera car =PanelAnterior.getCarrera();
						VentanaCorredor vC=	PanelAnterior.ventanaCorredor;
						PanelAnterior.salir();
						car.setparticipantes(BaseDatos.devuelveParticipantes(car.getNombre(), car.getFecha_celebracion()));
						try {
							VentanaTiempos Vt = new VentanaTiempos(car,vC);
							Vt.setVisible(true);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						salir();
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(null,"comprueba el nombre del fichero");
						e.printStackTrace();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,"enciende la BBDD");
						e.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnCargarEnSistema;
	}

	protected void salir() {
		this.dispose();
		
	}
}
