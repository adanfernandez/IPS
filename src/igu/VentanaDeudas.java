package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import logica.Carrera;
import logica.Participante;
import renders.MyTableModel;
import renders.Renderer;
import src.BaseDatos;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import java.awt.Toolkit;

public class VentanaDeudas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaDeudas;
	private MyTableModel modeloTabla;
	private static List<Participante> corredores;
	private static List<Participante> corredoresDeClubParaCambiarEstado;
	private static HashMap<String, Integer> club;
	private static HashMap<String, Double> clubDineros;
	private static boolean[] pagados;
	private static boolean[] pagadosClub;
	private static String nombreCarrera;
	private static java.sql.Date fechaCarrera;
	private JLabel lbTituloCarrera;
	private JButton btnRegistrarPago;
	private JPanel pnBoton;
	private JLabel lbVacia0;
	private JLabel lbVacia1;
	private JPanel pnTab;
	private JScrollPane scrollPane;
	private VentanaCorredor vO;
	private JTabbedPane tabbedPane;
	private JPanel panelClub;
	private JScrollPane scrollPane_1;
	private JTable table;
	private MyTableModel modeloTabla2;
	private Carrera car;

	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @wbp.parser.constructor
	 */

	public VentanaDeudas(Carrera carrera, VentanaCorredor vO)
			throws ClassNotFoundException, SQLException, ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDeudas.class.getResource("/img/logo.jpeg")));
		this.vO = vO;
		setTitle("Deudas");
		nombreCarrera = carrera.getNombre();
		fechaCarrera = carrera.getFecha_celebracion();
		car = carrera;
		corredoresDeClubParaCambiarEstado = new ArrayList<Participante>();
		obtenerDatos();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1302, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getTabbedPane(), BorderLayout.CENTER);
		contentPane.add(getLbTituloCarrera(), BorderLayout.NORTH);
		contentPane.add(getPnBoton(), BorderLayout.SOUTH);
		this.setLocationRelativeTo(this);
	}

	private JTable getTablaDeudas() {
		if (tablaDeudas == null) {
			String[] nombreColumnas = { "DNI", "Inscripción", "Cuota (€)", "Pago" };
			modeloTabla = new MyTableModel(nombreColumnas, 0);
			tablaDeudas = new JTable(modeloTabla);
			tablaDeudas.setDefaultRenderer(Object.class, new Renderer());
			tablaDeudas.setSelectionForeground(Color.BLACK);
			tablaDeudas.setSelectionBackground(Color.WHITE);
			tablaDeudas.getTableHeader().setReorderingAllowed(false);
			tablaDeudas.setRowHeight(30);
			añadirFilas();
		}
		return tablaDeudas;
	}

	private void añadirFilas() {
		Object[] filas = new Object[4];
		for (int i = 0; i < corredores.size(); i++) {
			filas[0] = corredores.get(i).getCorredor().getDni();
			filas[1] = horasRegistro(corredores.get(i).getFechaRegistro()) + " horas";
			filas[2] = car.getCoste(corredores.get(i).getFechaRegistro());
			filas[3] = false;
			modeloTabla.addRow(filas);
		}
	}

	private int horasRegistro(Date fechaRegistro) {
		java.util.Date fecha = new java.util.Date();
		long ms = (fecha.getTime() - fechaRegistro.getTime()) / 3600000;
		return (int) ms;
	}

	private ArrayList<Participante> numeroCorredoresTotales;

	private void obtenerDatos() throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Participante> a = BaseDatos.devuelveParticipantes(nombreCarrera, fechaCarrera);
		numeroCorredoresTotales = a;
		System.out.println("numero de corredores totales =" + a.size());
		corredores = new ArrayList<Participante>();
		for (Participante p : a) {
			if (p.getEstado().equals("PRE_INSCRITO") && p.getClub().getNombreClub().equals(""))
				corredores.add(p);
		}
		System.out.println("numero de corredores sin club =" + corredores.size());
		club = new HashMap<String, Integer>();
		clubDineros = new HashMap<String, Double>();

		for (Participante p : a) {
			if (!p.getEstado().equals("PAGADO-SIN-RECOGER") && !p.getEstado().equals("CANCELADO")
					&& !p.getEstado().equals("CANCELADO-PENDIENTE")) {
				if (!club.containsKey(p.getClub().getNombreClub()) && !p.getClub().getNombreClub().equals("")) {
					club.put(p.getClub().getNombreClub(), 1);
					double dto = BaseDatos.descuentoClub(p.getClub().getNombreClub(), nombreCarrera, fechaCarrera)
							/ 1.0;
					clubDineros.put(p.getClub().getNombreClub(),
							car.getCoste(p.getFechaRegistro()) * (1 - dto / 100.0));
					System.out.println(dto);
				} else if (!p.getClub().getNombreClub().equals("")) {// COMO SE EL NUMER DE PARTICIPANTES DE UN CLUB?
																		// club.size();
					double dto = BaseDatos.descuentoClub(p.getClub().getNombreClub(), nombreCarrera, fechaCarrera)
							/ 1.0;
					int n = dameNumeroParticipantesDelClubAlQuePerteneceP(p);
					double cuota = car.getCoste(p.getFechaRegistro());
					club.replace(p.getClub().getNombreClub(), club.get(p.getClub().getNombreClub()) + 1);
					double cuotaClub = cuota * n;
					double t = 0;
					if (dto != 0)

						t = (cuotaClub * (1 - (dto / 100.0)));

					else
						t = cuotaClub;
					clubDineros.replace(p.getClub().getNombreClub(), t);// cuota*n*(1-dto/100)
				}

			}
		}
		pagados = new boolean[corredores.size()];
		pagadosClub = new boolean[club.size()];
	}

	private int dameNumeroParticipantesDelClubAlQuePerteneceP(Participante p) {
		int cont = 0;
		for (Participante q : numeroCorredoresTotales) {
			if (q.getClub().getNombreClub().equals(p.getClub().getNombreClub())
					&& q.getEstado().equals("PRE_INSCRITO")) {
				cont++;
			}
		}

		return cont;
	}

	private void cambiarEstadoCorredor() throws ClassNotFoundException, SQLException {
		boolean pagado = false;
		for (int i = 0; i < corredores.size(); i++) {
			if (pagados[i]) {
				BaseDatos.cambiarEstadoParticipante(corredores.get(i), fechaCarrera, nombreCarrera);
				vO.getBtnCerrarCarrera().setEnabled(false);
				pagado = true;
			}
		}
		if (pagado)
			JOptionPane.showMessageDialog(null, "Se ha confirmado el pago de los corredores");

	}

	protected void cambiarEstadoClub() throws ClassNotFoundException, SQLException {
		boolean pagado = false;
		for (int i = 0; i < corredoresDeClubParaCambiarEstado.size(); i++) {
			BaseDatos.cambiarEstadoParticipante(corredoresDeClubParaCambiarEstado.get(i), fechaCarrera, nombreCarrera);
			vO.getBtnCerrarCarrera().setEnabled(false);
			pagado = true;
		}
		if (pagado)
			JOptionPane.showMessageDialog(null, "Se ha confirmado el pago de los clubs");
	}

	private JLabel getLbTituloCarrera() {
		if (lbTituloCarrera == null) {
			lbTituloCarrera = new JLabel("Lista de corredores de la carrera " + nombreCarrera
					+ " que aun no han pagado la inscripción a la carrera");
			lbTituloCarrera.setFont(new Font("Tahoma", Font.BOLD, 18));
			lbTituloCarrera.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTituloCarrera;
	}

	private void cambiarEstadoPAGADOclub(String nombreClub) {
		try {
			for (int i = 0; i < vO.carrera.getparticipantes().size(); i++) {
				if (vO.carrera.getparticipantes().get(i).getClub().getNombreClub().equals(nombreClub)
						&& vO.carrera.getparticipantes().get(i).getEstado().equals("PRE_INSCRITO")) {
					vO.carrera.getparticipantes().get(i).setEstado("PAGADO-SIN-RECOGER");
					corredoresDeClubParaCambiarEstado.add(vO.carrera.getparticipantes().get(i));
				}
			}
			vO.añadirFilasEstado();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

	}

	private JButton getBtnRegistrarPago() {
		if (btnRegistrarPago == null) {
			btnRegistrarPago = new JButton("Registrar pago");
			btnRegistrarPago.setIgnoreRepaint(true);
			btnRegistrarPago.setHorizontalTextPosition(SwingConstants.CENTER);
			btnRegistrarPago.setFocusable(false);
			btnRegistrarPago.setFocusTraversalKeysEnabled(false);
			btnRegistrarPago.setFocusPainted(false);
			btnRegistrarPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ArrayList<Participante> aux = new ArrayList<Participante>();

					for (int i = 0; i < table.getRowCount(); i++) {
						pagadosClub[i] = (Boolean) modeloTabla2.getValueAt(i, 3);
						if (pagadosClub[i]) {
							cambiarEstadoPAGADOclub(modeloTabla2.getValueAt(i, 0).toString());
							System.out.println("todos los corredores del club "
									+ modeloTabla2.getValueAt(i, 0).toString() + " a PAGADO");
						} else
							System.out.println("todos los corredores del club "
									+ modeloTabla2.getValueAt(i, 0).toString() + " a PRE_INSCRITO");
					}

					for (int i = 0; i < corredores.size(); i++) {
						pagados[i] = (Boolean) modeloTabla.getValueAt(i, 3);

						if (pagados[i])
							corredores.get(i).setEstado("PAGADO-SIN-RECOGER");
						else
							corredores.get(i).setEstado("PRE_INSCRITO");
					}
					try {
						for (int i = 0; i < vO.carrera.getparticipantes().size(); i++) {
							for (int j = 0; j < corredores.size(); j++) {
								if (vO.carrera.getparticipantes().get(i).getCorredor().getDni() == corredores.get(j)
										.getCorredor().getDni()) {
									vO.carrera.getparticipantes().get(i).setEstado(corredores.get(j).getEstado());
								}
							}
						}
						vO.añadirFilasEstado();
					} catch (IOException | ParseException e) {
						e.printStackTrace();
					}
					for (int i = corredores.size() - 1; i >= 0; i--) {
						if (pagados[i]) {
							modeloTabla.removeRow(i);
						} else
							aux.add(corredores.get(i));

					}
					for (int i = table.getRowCount() - 1; i >= 0; i--) {
						if (pagadosClub[i]) {
							modeloTabla2.removeRow(i);
						}
					}

					try {
						cambiarEstadoCorredor();
						cambiarEstadoClub();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					corredores = aux;
					pagados = new boolean[corredores.size()];
				}

			});
		}
		return btnRegistrarPago;
	}

	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setLayout(new GridLayout(0, 5, 0, 0));
			pnBoton.add(getLbVacia0());
			pnBoton.add(getLbVacia1());
			pnBoton.add(getBtnRegistrarPago());
		}
		return pnBoton;
	}

	private JLabel getLbVacia0() {
		if (lbVacia0 == null) {
			lbVacia0 = new JLabel("");
		}
		return lbVacia0;
	}

	private JLabel getLbVacia1() {
		if (lbVacia1 == null) {
			lbVacia1 = new JLabel("");
		}
		return lbVacia1;
	}

	private JPanel getPnTab() {
		if (pnTab == null) {
			pnTab = new JPanel();
			pnTab.setLayout(new BorderLayout(0, 0));
			pnTab.add(getScrollPane());
		}
		return pnTab;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTablaDeudas());
		}
		return scrollPane;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Corredores", null, getPnTab(), null);
			tabbedPane.addTab("Clubs", null, getPanelClub(), null);
		}
		return tabbedPane;
	}

	private JPanel getPanelClub() {
		if (panelClub == null) {
			panelClub = new JPanel();
			panelClub.setLayout(new BorderLayout(0, 0));
			panelClub.add(getScrollPane_1(), BorderLayout.NORTH);
		}
		return panelClub;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTable());
		}
		return scrollPane_1;
	}

	private JTable getTable() {
		if (table == null) {
			String[] nombreColumnas = { "Club", "nº integrantes", "Cuota club (€)", "Pago" };
			modeloTabla2 = new MyTableModel(nombreColumnas, 0);
			table = new JTable(modeloTabla2);
			table.setDefaultRenderer(Object.class, new Renderer());
			table.setSelectionForeground(Color.BLACK);
			table.setSelectionBackground(Color.WHITE);
			table.getTableHeader().setReorderingAllowed(false);
			table.setRowHeight(30);
			añadirFilasClub();
		}
		return table;
	}

	private void añadirFilasClub() {
		System.out.print(club.size());
		Object[] filas = new Object[4];
		club.forEach((k, v) -> {
			filas[0] = k;
			filas[1] = v;
			filas[2] = clubDineros.get(k);
			filas[3] = false;
			modeloTabla2.addRow(filas);
		});
	}
}
