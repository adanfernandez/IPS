package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Carrera;
import logica.Participante;
import renders.ModeloNoEditable;
import renders.ModeloTablaDorsales;
import renders.RendererDorsales;
import src.BaseDatos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;

public class VentanaDorsales extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lbTitulo;
	private Carrera carrera;
	private JPanel pnBoton;
	private JLabel lbVacia0;
	private JButton btDorsales;
	private JPanel pnTab;
	private JScrollPane scrollPane;
	private JTable tablaDorsales;
	private ModeloTablaDorsales modeloTabla;
	private List<Participante> corredoresSinDorsal;
	private List<Participante> corredoresConDorsal;
	private JButton btnListaDorsales;
	private JButton btnListaPendiente;
	private VentanaCorredor vO;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() {
	 * 
	 * @SuppressWarnings("deprecation") public void run() { VentanaDorsales
	 * frame = null; try { frame = new VentanaDorsales("Carrera popular", new
	 * Date(2017-1900,9,20), "ABIERTA"); } catch (ClassNotFoundException |
	 * SQLException | ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * frame.setVisible(true); } }); }
	 */

	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @wbp.parser.constructor
	 */
	public VentanaDorsales(Carrera carrera) throws ClassNotFoundException, SQLException, ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDorsales.class.getResource("/img/logo.jpeg")));
		corredoresSinDorsal = BaseDatos.corredoresSinDorsales(carrera.getNombre(), carrera.getFecha_celebracion());
		corredoresConDorsal = BaseDatos.corredoresConDorsales(carrera.getNombre(), carrera.getFecha_celebracion());
		setTitle("Dorsales");
		this.carrera = carrera;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 791, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
	}

	public VentanaDorsales(Carrera carrera, VentanaCorredor vO)
			throws ClassNotFoundException, SQLException, ParseException {
		this(carrera);
		this.vO = vO;
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDorsales.class.getResource("/img/logo.jpeg")));
	}

	private JPanel getPanel() throws ClassNotFoundException, SQLException, ParseException {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getLbTitulo(), BorderLayout.NORTH);
			panel.add(getPnBoton(), BorderLayout.SOUTH);
			panel.add(getPnTab(), BorderLayout.CENTER);
		}
		return panel;
	}

	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Tabla dorsales Carrera: " + carrera.getNombre());
			lbTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTitulo;
	}

	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setLayout(new GridLayout(0, 5, 0, 0));
			pnBoton.add(getLbVacia0());
			pnBoton.add(getBtnListaPendiente());
			pnBoton.add(getBtDorsales());
			pnBoton.add(getBtnListaDorsales());
		}
		return pnBoton;
	}

	private JLabel getLbVacia0() {
		if (lbVacia0 == null) {
			lbVacia0 = new JLabel("");
		}
		return lbVacia0;
	}

	private JButton getBtDorsales() {
		if (btDorsales == null) {
			btDorsales = new JButton("Asignar dorsales");
			if (corredoresSinDorsal.isEmpty()) {
				btDorsales.setEnabled(false);
			} else {
				btDorsales.setEnabled(true);
			}
			btDorsales.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						asignacionDeDorsales();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
		}
		return btDorsales;
	}

	private void asignacionDeDorsales() throws ClassNotFoundException, SQLException, ParseException, IOException {
		// Comprobar que los dorsales manuales están bien
		boolean correcto = true;
		boolean[] dorsales = new boolean[10 + carrera.getNumeroParticipantes()];
		for (int i = 0; i < corredoresSinDorsal.size(); i++) {
			if (!getTablaDorsales().getValueAt(i, 2).equals("")) {
				if (!compruebaDorsal(String.valueOf(getTablaDorsales().getValueAt(i, 2)))) {
					JOptionPane.showMessageDialog(null, "Uno de los dorsales introducidos no es un número");
					correcto = false;
					break;
				} else {
					int n = Integer.parseInt(String.valueOf(getTablaDorsales().getValueAt(i, 2)));
					if (n < 1 || n > carrera.getNumeroParticipantes()) {
						JOptionPane.showMessageDialog(null,
								"Has introducido un dorsal menor o igual que cero o mayor que el número permitido para esta carrera");
						correcto = false;
						break;
					} else {
						if (dorsales[n - 1]) {
							JOptionPane.showMessageDialog(null, "Uno de los dorsales está repetido");
							correcto = false;
							break;
						} else {
							dorsales[n - 1] = true;
						}
					}
				}
			}
		}

		// Guardar los dorsales en la base de datos
		if (correcto) {

			for (int i = 0; i < corredoresSinDorsal.size(); i++) {
				if (!getTablaDorsales().getValueAt(i, 2).equals("")) {
					corredoresSinDorsal.get(i)
							.setDorsal(Integer.parseInt(String.valueOf(getTablaDorsales().getValueAt(i, 2))));
				} else {
					for (int j = 10; j < dorsales.length; j++) {
						if (!dorsales[j]) {
							corredoresSinDorsal.get(i).setDorsal(j + 1);
							dorsales[j] = true;
							break;
						}
					}
				}
				BaseDatos.cambiarDorsalesCorredor(corredoresSinDorsal.get(i), carrera.getNombre(),
						carrera.getFecha_celebracion());
			}
			for (int i = 0; i < corredoresSinDorsal.size(); i++) {
				for (int j = 0; j<vO.carrera.getVectorOrdenado().size();j++) {
					if (corredoresSinDorsal.get(i).getCorredor().getDni().equals(vO.carrera.getVectorOrdenado().get(j).getCorredor().getDni())) {
						vO.carrera.getVectorOrdenado().get(j).setDorsal(corredoresSinDorsal.get(i).getDorsal());
					}
				}
			}
			
			vO.añadirFilasEstado();

			corredoresConDorsal = corredoresSinDorsal;
			getListaCorredoresDorsal();
			vO.getBtnCerrarCarrera().setEnabled(true);
		}
	}

	public boolean compruebaDorsal(String texto) {
		char[] txt = texto.toCharArray();
		for (int i = 0; i < texto.length(); i++) {
			if (!Character.isDigit(txt[i])) {
				return false;
			}
		}
		return true;
	}

	private JPanel getPnTab() throws ClassNotFoundException, SQLException, ParseException {
		if (pnTab == null) {
			pnTab = new JPanel();
			pnTab.setLayout(new BorderLayout(0, 0));
			pnTab.add(getScrollPane());
		}
		return pnTab;
	}

	private JScrollPane getScrollPane() throws ClassNotFoundException, SQLException, ParseException {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.add(getTablaDorsales());
			scrollPane.setViewportView(getTablaDorsales());
		}
		return scrollPane;
	}

	private JTable getTablaDorsales() throws ClassNotFoundException, SQLException, ParseException {
		if (tablaDorsales == null) {
			String[] nombreColumnas = { "DNI", "Nombre", "Dorsal" };
			modeloTabla = new ModeloTablaDorsales(nombreColumnas, 0);
			tablaDorsales = new JTable(modeloTabla);
			tablaDorsales.setDefaultRenderer(Object.class, new RendererDorsales());
			tablaDorsales.getTableHeader().setReorderingAllowed(false);
			tablaDorsales.setRowHeight(30);
			añadirFilas(modeloTabla, false);
		}
		return tablaDorsales;
	}

	private void añadirFilas(DefaultTableModel modelo, boolean conDorsal)
			throws ClassNotFoundException, SQLException, ParseException {
		Object[] filas = new Object[3];
		if (!conDorsal) {
			for (int i = 0; i < corredoresSinDorsal.size(); i++) {
				filas[0] = corredoresSinDorsal.get(i).getCorredor().getDni();
				filas[1] = corredoresSinDorsal.get(i).getCorredor().getNombre();
				if (corredoresSinDorsal.get(i).getDorsal() == 0) {
					filas[2] = "";
				} else {
					filas[2] = corredoresSinDorsal.get(i).getDorsal();
				}
				modelo.addRow(filas);
			}

		} else {
			if (!carrera.getEstado().equals("ABIERTA")) {
				corredoresConDorsal = BaseDatos.corredoresConDorsalesFin(carrera.getNombre(),
						carrera.getFecha_celebracion());
			}
			for (int i = 0; i < corredoresConDorsal.size(); i++) {
				filas[0] = corredoresConDorsal.get(i).getCorredor().getDni();
				filas[1] = corredoresConDorsal.get(i).getCorredor().getNombre();
				filas[2] = corredoresConDorsal.get(i).getDorsal();
				modelo.addRow(filas);
			}
		}
	}

	private JButton getBtnListaDorsales() {
		if (btnListaDorsales == null) {
			btnListaDorsales = new JButton("Dorsales fijos");
			btnListaDorsales.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getListaCorredoresDorsal();
				}
			});
		}
		return btnListaDorsales;
	}

	private void getListaCorredoresDorsal() {
		String[] nombreColumnas = { "DNI", "Nombre", "Dorsal" };
		ModeloNoEditable modelo = new ModeloNoEditable(nombreColumnas, 0);
		getBtDorsales().setEnabled(false);
		try {
			ordenarDorsales();
			getTablaDorsales().setModel(modelo);
			añadirFilas(modelo, true);
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	private void ordenarDorsales() {
		List<Participante> aux = new ArrayList<Participante>();
		boolean añadido = false;
		for (int i = 0; i < corredoresConDorsal.size(); i++) {
			for (int j = 0; j < aux.size(); j++) {
				if (aux.get(j).getDorsal() > corredoresConDorsal.get(i).getDorsal()) {
					aux.add(j, corredoresConDorsal.get(i));
					añadido = true;
					break;
				}
			}
			if (!añadido)
				aux.add(corredoresConDorsal.get(i));
			añadido = false;
		}
		corredoresConDorsal = aux;
	}

	private JButton getBtnListaPendiente() {
		if (btnListaPendiente == null) {
			btnListaPendiente = new JButton("Modificador de dorsales");
			btnListaPendiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String[] nombreColumnas = { "DNI", "Nombre", "Dorsal" };
					ModeloTablaDorsales modelo = new ModeloTablaDorsales(nombreColumnas, 0);
					if (!corredoresSinDorsal.isEmpty())
						getBtDorsales().setEnabled(true);
					try {
						getTablaDorsales().setModel(modelo);
						añadirFilas(modelo, false);
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return btnListaPendiente;
	}
}
