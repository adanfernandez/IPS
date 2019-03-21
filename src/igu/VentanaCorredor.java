package igu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Carrera;
import logica.Categoria;
import logica.Club;
import logica.Cuota;
import logica.Participante;
import renders.RendererCorredores;
import renders.RendererDorsales;
import src.BaseDatos;
import utilidades.Util;

import javax.swing.SwingConstants;

import java.awt.Toolkit;

import javax.swing.JTextField;

public class VentanaCorredor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private DefaultTableModel tm;
	private JScrollPane scrollPane;
	private JTable table;

	ArrayList<Carrera> carreras;
	Carrera carrera;

	private JPanel pnSeleccion;
	private JLabel lbOrdenar;
	private JButton btnFechaDeInscripcin;
	private JButton btnEstadoDePago;
	private JPanel panel;
	private JLabel lblTotalDeCorredores;
	private JLabel lbCorredores;
	private JPanel panel_1;
	private JButton btnGestionar;
	private JButton btnAsignarverDorsales;
	private JButton btnVolver;
	private JLabel lblNewLabel;
	private JButton btnCancelarInscripciones;
	private JButton btnCerrarCarrera;
	private JPanel panel_titulo;
	private JLabel lblEstado;
	private JPanel panel_estado;
	private JTextField textField_Estado;
	private JButton btnCancelarcarrera;

	private String mokjon;

	// /**
	// * Launch the application.
	// */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// VentanaOrdenar frame = new VentanaOrdenar();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public VentanaCorredor(Carrera carrera) throws IOException, ParseException, ClassNotFoundException, SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCorredor.class.getResource("/img/logo.jpeg")));
		this.carrera = carrera;

		// CARGAS LOS CORREDORES POR 1ª VEZ//
		carrera.setparticipantes(BaseDatos.devuelveParticipantes(carrera.getNombre(), carrera.getFecha_celebracion()));
		carrera.setCategorias(BaseDatos.devolverCategorias(carrera));
		setTitle("Corredores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1171, 610);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagConstraints gbc_btActualizar = new GridBagConstraints();
		gbc_btActualizar.gridwidth = 9;
		gbc_btActualizar.insets = new Insets(0, 0, 5, 0);
		gbc_btActualizar.gridx = 0;
		gbc_btActualizar.gridy = 4;
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getScrollPane_1(), BorderLayout.CENTER);
		contentPane.add(getPnSeleccion(), BorderLayout.SOUTH);
		añadirFilas();
		lbCorredores.setText(String.valueOf(table.getRowCount()));
		contentPane.add(getPanel_titulo(), BorderLayout.NORTH);
		getBtnCerrarCarrera().setEnabled(false);
		this.getBtnCerrarCarrera().setEnabled(true);
		for (int i = 0; i < this.carrera.getparticipantes().size(); i++) {
			if (carrera.getparticipantes().get(i).getDorsal() == 0
					&& carrera.getparticipantes().get(i).getEstado().equals("PAGADO")) {
				this.getBtnCerrarCarrera().setEnabled(false);
			}
		}
		textField_Estado.setText(carrera.getEstado());
		if (carrera.getEstado().equals("CANCELADA")) {
			btnCerrarCarrera.setVisible(false);
			btnGestionar.setVisible(false);
			btnAsignarverDorsales.setVisible(false);
			añadirFilas();
		}
	}

	private JScrollPane getScrollPane_1() throws IOException {
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

	public void propiedadesTabla() throws IOException {
		table.setDefaultRenderer(Object.class, new RendererCorredores());

		String[] titulos = { "DNI", "Nombre", "Categoría", "Fecha de inscripción", "Estado de la inscripción", "Club",
				"Dorsales", "Precio a pagar/devolver" };
		tm = new DefaultTableModel(null, titulos);
		table.setModel(tm);
		table.setEnabled(false);
	}

	public void añadirFilas() throws IOException, ParseException {
		limpiar();
		String pagado;
		carrera.ordenarPago();
		for (int i = 0; i < carrera.getVectorOrdenado().size(); i++) {
			pagado = carrera.getVectorOrdenado().get(i).getEstado();

			String[] fechaNac = carrera.getVectorOrdenado().get(i).getFechaRegistro().toString().split("-");
			String año = fechaNac[0];
			String mes = fechaNac[1];
			String dia = fechaNac[2];

			carrera.cargarCategoriasFerni();
			double dineros = 0;
			boolean club = false;
			if (carrera.getVectorOrdenado().get(i).getClub().getNombreClub().equals("")) {
				if (carrera.getVectorOrdenado().get(i).getEstado().equals("PRE_INSCRITO"))
					dineros = carrera.getCoste(carrera.getVectorOrdenado().get(i).getFechaRegistro());
				else if (carrera.getVectorOrdenado().get(i).getEstado().equals("PAGADO-SIN-RECOGER")
						|| carrera.getVectorOrdenado().get(i).getEstado().equals("CANCELADO-PENDIENTE"))
					dineros = -1 * carrera.getDineroDevuelto(carrera.getVectorOrdenado().get(i).getFechaRegistro());
			}
			else {
				club = true;
			}
			
			String dorsales = "";
			if (carrera.getVectorOrdenado().get(i).getDorsal() != 0)
				dorsales = String.valueOf(carrera.getVectorOrdenado().get(i).getDorsal());

			dineros = redondearDecimales(dineros,2);
			
			Object[] añadir = new Object[] { carrera.getVectorOrdenado().get(i).getCorredor().getDni(),
					carrera.getVectorOrdenado().get(i).getCorredor().getNombre(),
					carrera.getVectorOrdenado().get(i).getCategoria().getNombre(), dia + " / " + mes + " / " + año,
					pagado, carrera.getVectorOrdenado().get(i).getClub().getNombreClub(), dorsales, club?"":dineros};
			tm.addRow(añadir);
		}

	}

	private JTable getTabla() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int column = table.getColumnModel().getColumnIndexAtX(arg0.getX());
				int row = arg0.getY() / table.getRowHeight();

				if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
					Object value = table.getValueAt(row, column);
					if (value instanceof JButton) {
						((JButton) value).doClick();
					}
				}
			}

		});
		return table;
	}

	private void limpiar() {

		try {
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			int filas = table.getRowCount();
			for (int i = 0; filas > i; i++) {
				modelo.removeRow(0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
		}
	}

	public JTable getTabl() {
		return table;
	}

	private JPanel getPnSeleccion() {
		if (pnSeleccion == null) {
			pnSeleccion = new JPanel();
			pnSeleccion.setLayout(new BoxLayout(pnSeleccion, BoxLayout.X_AXIS));
			pnSeleccion.add(getPanel());
			pnSeleccion.add(getPanel_1());
			pnSeleccion.add(getLbOrdenar());
			pnSeleccion.add(getBtnFechaDeInscripcin());
			pnSeleccion.add(getBtnEstadoDePago());
		}
		return pnSeleccion;
	}

	private JLabel getLbOrdenar() {
		if (lbOrdenar == null) {
			lbOrdenar = new JLabel("Ordenar por: ");
			lbOrdenar.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 27));
		}
		return lbOrdenar;
	}

	private JButton getBtnFechaDeInscripcin() {
		if (btnFechaDeInscripcin == null) {
			btnFechaDeInscripcin = new JButton("Fecha de inscripci\u00F3n");
			btnFechaDeInscripcin.setMnemonic('F');
			btnFechaDeInscripcin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (!carrera.getEstado().equals("FINALIZADA")) {
						try {
							if (table.getRowCount() > 0)
								añadirFilasFechaInscripcion();
						} catch (IOException | ParseException e) {

						}
					}
				}
			});
		}
		return btnFechaDeInscripcin;
	}

	private JButton getBtnEstadoDePago() {
		if (btnEstadoDePago == null) {
			btnEstadoDePago = new JButton("Estado de pago");
			btnEstadoDePago.setMnemonic('E');
			btnEstadoDePago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (!carrera.getEstado().equals("FINALIZADA")) {
						try {
							if (table.getRowCount() > 0)
								añadirFilasEstado();
						} catch (IOException | ParseException e1) {

						}
					}
				}
			});
		}
		return btnEstadoDePago;
	}

	public void añadirFilasFechaInscripcion() throws IOException, ParseException {
		limpiar();
		carrera.ordenarFechaRegistro();
		String pagado;

		for (int i = 0; i < carrera.getVectorOrdenado().size(); i++) {
			pagado = carrera.getVectorOrdenado().get(i).getEstado();
			carrera.cargarCategorias();

			String[] fechaNac = carrera.getVectorOrdenado().get(i).getFechaRegistro().toString().split("-");
			String año = fechaNac[0];
			String mes = fechaNac[1];
			String dia = fechaNac[2];

			double dineros = 0;
			boolean club = false;
			if (carrera.getVectorOrdenado().get(i).getClub().getNombreClub().equals("")) {
				if (carrera.getVectorOrdenado().get(i).getEstado().equals("PRE_INSCRITO"))
					dineros = carrera.getCoste(carrera.getVectorOrdenado().get(i).getFechaRegistro());
				else if (carrera.getVectorOrdenado().get(i).getEstado().equals("PAGADO-SIN-RECOGER")
						|| carrera.getVectorOrdenado().get(i).getEstado().equals("CANCELADO-PENDIENTE"))
					dineros = -1 * carrera.getDineroDevuelto(carrera.getVectorOrdenado().get(i).getFechaRegistro());
			}
			else {
				club = true;
			}

			String dorsales = "";
			if (carrera.getVectorOrdenado().get(i).getDorsal() != 0)
				dorsales = String.valueOf(carrera.getVectorOrdenado().get(i).getDorsal());
			
			dineros = redondearDecimales(dineros,2);
			
			Object[] añadir = new Object[] { carrera.getVectorOrdenado().get(i).getCorredor().getDni(),
					carrera.getVectorOrdenado().get(i).getCorredor().getNombre(),
					carrera.getVectorOrdenado().get(i).getCategoria().getNombre(), dia + " / " + mes + " / " + año,
					pagado, carrera.getVectorOrdenado().get(i).getClub().getNombreClub(), dorsales, club?"":dineros

			};
			tm.addRow(añadir);
		}

	}

	public void añadirFilasEstado() throws IOException, ParseException {
		limpiar();
		carrera.ordenarPago();
		String pagado;

		for (int i = 0; i < carrera.getVectorOrdenado().size(); i++) {
			pagado = carrera.getVectorOrdenado().get(i).getEstado();

			String[] fechaNac = carrera.getVectorOrdenado().get(i).getFechaRegistro().toString().split("-");
			String año = fechaNac[0];
			String mes = fechaNac[1];
			String dia = fechaNac[2];

			carrera.cargarCategorias();
			double dineros = 0;
			boolean club = false;
			if (carrera.getVectorOrdenado().get(i).getClub().getNombreClub().equals("")) {
				if (carrera.getVectorOrdenado().get(i).getEstado().equals("PRE_INSCRITO"))
					dineros = carrera.getCoste(carrera.getVectorOrdenado().get(i).getFechaRegistro());
				else if (carrera.getVectorOrdenado().get(i).getEstado().equals("PAGADO-SIN-RECOGER")
						|| carrera.getVectorOrdenado().get(i).getEstado().equals("CANCELADO-PENDIENTE"))
					dineros = -1 * carrera.getDineroDevuelto(carrera.getVectorOrdenado().get(i).getFechaRegistro());
			}
			else {
				club = true;
			}
	
			String dorsales = "";
			if (carrera.getVectorOrdenado().get(i).getDorsal() != 0)
				dorsales = String.valueOf(carrera.getVectorOrdenado().get(i).getDorsal());
			
			dineros = redondearDecimales(dineros,2);
			
			Object[] añadir = new Object[] { carrera.getVectorOrdenado().get(i).getCorredor().getDni(),
					carrera.getVectorOrdenado().get(i).getCorredor().getNombre(),
					carrera.getVectorOrdenado().get(i).getCategoria().getNombre(), dia + " / " + mes + " / " + año,
					pagado, carrera.getVectorOrdenado().get(i).getClub().getNombreClub(), dorsales, club?"":dineros

			};
			tm.addRow(añadir);
		}

	}
	
    public double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel.add(getBtnVolver());
			panel.add(getLblTotalDeCorredores());
			panel.add(getLbCorredores());
		}
		return panel;
	}

	private JLabel getLblTotalDeCorredores() {
		if (lblTotalDeCorredores == null) {
			lblTotalDeCorredores = new JLabel("Total de corredores: ");
		}
		return lblTotalDeCorredores;
	}

	private JLabel getLbCorredores() {
		if (lbCorredores == null) {
			lbCorredores = new JLabel("-");
			lbCorredores.setFont(new Font("Tahoma", Font.PLAIN, 24));
		}
		return lbCorredores;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getBtnGestionar());
			panel_1.add(getBtnAsignarverDorsales());
			panel_1.add(getBtnCancelarInscripciones());

			if (carrera.getEstado().equals("ABIERTA"))
				panel_1.add(getBtnCerrarCarrera());
		}
		return panel_1;
	}

	private VentanaDeudas createVentanaDeudas() throws ClassNotFoundException, SQLException, ParseException {
		VentanaDeudas vD = new VentanaDeudas(carrera, this);
		return vD;
	}

	private VentanaCancelar createVentanaCancelar() throws ClassNotFoundException, SQLException, ParseException {
		VentanaCancelar vC = new VentanaCancelar(carrera, this);
		return vC;
	}

	private VentanaTiempos createVentanaTiempos() throws ClassNotFoundException, SQLException, ParseException {
		VentanaTiempos vT = new VentanaTiempos(carrera, this);
		return vT;
	}

	private JButton getBtnGestionar() {
		if (btnGestionar == null) {
			btnGestionar = new JButton("Gestionar carrera");
			btnGestionar.setMnemonic('G');
			btnGestionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (carrera.getEstado().equals("ABIERTA")) {
						try {
							VentanaDeudas vD = createVentanaDeudas();
							vD.setVisible(true);
						} catch (ClassNotFoundException | SQLException | ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else if (carrera.getEstado().equals("CERRADA")) {
						VentanaTiempos vT;
						try {
							vT = createVentanaTiempos();
							vT.setVisible(true);
						} catch (ClassNotFoundException | SQLException | ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else if (carrera.getEstado().equals("FINALIZADA")) {
						// VentanaClasificaciones
						VentanaClasificacion vC = new VentanaClasificacion(carrera);
						vC.setVisible(true);
					}
				}
			});
		}
		return btnGestionar;
	}

	private JButton getBtnAsignarverDorsales() {
		if (btnAsignarverDorsales == null) {
			btnAsignarverDorsales = new JButton("Asignar/Ver Dorsales");
			btnAsignarverDorsales.setMnemonic('a');
			btnAsignarverDorsales.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						crearVentanaDorsales();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return btnAsignarverDorsales;
	}

	private void crearVentanaDorsales() throws ClassNotFoundException, SQLException, ParseException {
		if (carrera.getEstado().equals("CERRADA")) {
			VentanaRecogerDorsales cD = new VentanaRecogerDorsales(carrera, this);
			cD.setVisible(true);
		} else {
			VentanaDorsales cD = new VentanaDorsales(carrera, this);
			cD.setVisible(true);
		}
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setMnemonic('V');
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		return btnVolver;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Corredores");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		}
		return lblNewLabel;
	}

	private JButton getBtnCancelarInscripciones() {
		if (btnCancelarInscripciones == null) {
			btnCancelarInscripciones = new JButton("Cancelar inscripciones");
			btnCancelarInscripciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					VentanaCancelar vD = null;
					try {
						vD = createVentanaCancelar();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}
					vD.setVisible(true);
				}
			});
		}
		return btnCancelarInscripciones;
	}

	protected JButton getBtnCerrarCarrera() {
		if (btnCerrarCarrera == null) {
			btnCerrarCarrera = new JButton("Cerrar carrera");
			btnCerrarCarrera.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					carrera.setEstado("CERRADA");
					try {
						BaseDatos.cambiarEstadoCarrera(carrera, "CERRADA");
						carrera.setEstado("CERRADA");
						textField_Estado.setText("CERRADA");
						JOptionPane.showMessageDialog(null, "La carrera ha sido cerrada");
						btnCerrarCarrera.setEnabled(false);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return btnCerrarCarrera;
	}

	public void activarCerrarCarrera() {
		btnCerrarCarrera.setEnabled(true);
	}

	private JPanel getPanel_titulo() {
		if (panel_titulo == null) {
			panel_titulo = new JPanel();
			panel_titulo.setLayout(new BorderLayout(0, 0));
			panel_titulo.add(getLblNewLabel(), BorderLayout.CENTER);
			panel_titulo.add(getPanel_estado(), BorderLayout.EAST);
		}
		return panel_titulo;
	}

	private JLabel getLblEstado() {
		if (lblEstado == null) {
			lblEstado = new JLabel("Estado: ");
			lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lblEstado;
	}

	private JPanel getPanel_estado() {
		if (panel_estado == null) {
			panel_estado = new JPanel();
			panel_estado.add(getLblEstado());
			panel_estado.add(getTextField_Estado());
		}
		return panel_estado;
	}

	JTextField getTextField_Estado() {
		if (textField_Estado == null) {
			textField_Estado = new JTextField();
			textField_Estado.setEditable(false);
			textField_Estado.setColumns(10);
		}
		return textField_Estado;
	}

	private void cambiarVentanaDorsales() throws ClassNotFoundException, SQLException, ParseException {
		VentanaRecogerDorsales v = new VentanaRecogerDorsales(carrera, this);
		v.setVisible(true);
	}

	private JButton getBtnCancelarcarrera() {
		if (btnCancelarcarrera == null) {
			btnCancelarcarrera = new JButton("Cancelar/Aplazar Carrera");
			btnCancelarcarrera.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						Object[] opciones = { "Aplazar Carrera", "Cancelar Carrera" };
						int op = JOptionPane.showOptionDialog(null, "Quieres Aplazar o cancelar la carrera",
								"Aplazar/Cancelar Carrera", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, opciones, "Aplazar Carrera");

						if (op == 0)
							aplazarCarrera();
						if (op == 1)
							cancelarCarrera();
					} catch (ClassNotFoundException | SQLException | ParseException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return btnCancelarcarrera;
	}

	protected void aplazarCarrera() throws ClassNotFoundException, SQLException, IOException, ParseException {
		if (!carrera.getEstado().equals("FINALIZADA") && !carrera.getEstado().equals("CANCELADA")
				&& !carrera.getEstado().equals("CERRADA")) {
			int op = JOptionPane.showConfirmDialog(this, "Con esta accion aplazaras la carrera,"
					+ " El porcentaje de devolucion para los participantes a partir de ahora sera del 100%");
			if (op < 1) {
				List<Club> clubs = new ArrayList<Club>();
				clubs = BaseDatos.clubsEnCarrera(carrera);
				carrera.setCategorias(BaseDatos.devolverCategorias(carrera));
				carrera.setCuotas(BaseDatos.devolverCuotas(carrera));
				for (Participante p : carrera.getparticipantes()) {
					BaseDatos.borrarParticipante(carrera, p);
				}
				BaseDatos.borrarCarrera(carrera);
				carrera.setDevolucion(100);
				BaseDatos.cambiarDevolucion(carrera, 100);
				Date nuevaFecha = null;
				while (nuevaFecha == null) {
					try {
						String Date = JOptionPane.showInputDialog(this,
								"introduce Nueva fecha con formato  'YYYY-MM-DD'", carrera.getFecha_celebracion());
						nuevaFecha = Util.convierteStringToDate(Date);
						if (nuevaFecha.before(carrera.getFecha_celebracion())) {
							JOptionPane.showMessageDialog(this, "No puedes realizar la carrera antes de tiempo");
							nuevaFecha = null;
						}
					}

					catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(this, "EL formato de la fecha es incorrecto");

					}
				}
				this.enable(false);
				carrera.setFecha_celebracion(nuevaFecha);
				Util.registraCarrera(carrera);

				System.out.println(clubs.size());
				for (Club cl : clubs) {
					cl.setFecha(carrera.getFecha_celebracion());
					try {
						if (!BaseDatos.existeClub(cl.getNombreClub()))
							BaseDatos.añadirClub(cl);
						BaseDatos.VincularClubCarrera(cl);
						;
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				for (Cuota c : carrera.getCuotas()) {
					try {
						BaseDatos.AñadirCuotas(carrera, c);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				for (Categoria cat : carrera.getCategorias()) {
					try {
						if (BaseDatos.existeCategoria(cat.getNombre())) {
							BaseDatos.VinculaCategoriaCarrera(carrera, cat);
						} else {
							BaseDatos.NuevaCategoria(cat);
							BaseDatos.VinculaCategoriaCarrera(carrera, cat);
						}
					} catch (SQLIntegrityConstraintViolationException a) {
						JOptionPane.showMessageDialog(contentPane, "La categoria ya existe en la base de datos");

					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for (Participante p : carrera.getparticipantes()) {
					Util.registraParticipante(carrera, p);
				}
				añadirFilas();
				this.enable(true);
				JOptionPane.showMessageDialog(contentPane,
						"La carrera ha sido aplazada a la fecha " + carrera.getFecha_celebracion());
			} else {

			}
		} else {
			JOptionPane.showMessageDialog(this, "Esta Carrera no se puede apalzar");
		}

	}

	protected void cancelarCarrera() throws ClassNotFoundException, SQLException, ParseException, IOException {
		if (!carrera.getEstado().equals("FINALIZADA") && !carrera.getEstado().equals("CANCELADA")) {
			int op = JOptionPane.showConfirmDialog(this,
					"Con esta accion cancelaras la carrera, todos los participantes"
							+ " pasaran a estado cancelado y el porcentaje de devolucion para ellos subira al 100%");
			if (op < 1) {

				carrera.setEstado("CANCELADA");
				carrera.setDevolucion(100);
				textField_Estado.setText("CANCELADA");
				BaseDatos.cambiarDevolucion(carrera, 100);
				BaseDatos.cambiarEstadoCarrera(carrera, "CANCELADA");

				for (Participante p : carrera.getparticipantes()) {
					if (p.getEstado().contains("PAGADO")) {
						p.setEstado("CANCELADO-PENDIENTE");
						BaseDatos.cambiarEstadoParticipanteCancelado(p, carrera.getFecha_celebracion(),
								carrera.getNombre());
					}
					if (p.getEstado().contains("CANCELADO")) {
						;
					} else {
						p.setEstado("CANCELADO");
						BaseDatos.cambiarEstadoParticipanteCancelado(p, carrera.getFecha_celebracion(),
								carrera.getNombre());
					}
				}
				btnCerrarCarrera.setVisible(false);
				btnGestionar.setVisible(false);
				btnAsignarverDorsales.setVisible(false);
				añadirFilas();
			} else {
			}
		} else
			JOptionPane.showMessageDialog(this, "Esta Carrera no se puede cancelar");
	}

}