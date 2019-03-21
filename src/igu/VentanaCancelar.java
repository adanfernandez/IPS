package igu;

import java.awt.BorderLayout;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Carrera;
import logica.Participante;
import renders.ModeloTablaCancelar;
import renders.RendererDorsales;
import src.BaseDatos;
import utilidades.Util;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JScrollPane;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.Toolkit;

public class VentanaCancelar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<Participante> corredores;
	private List<Participante> corredoresCancelados;
	private JLabel lblListaDeCorredores;
	private JPanel pnTab;
	private JScrollPane scrollPane;
	private JPanel pnBotones;
	private JLabel lbVacia;
	private JButton btnListaCorredoresInscritos;
	private JButton btCancelar;
	private JButton btnLista;
	private JTable tablaCancelar;
	private String nombre;
	private Date fecha;
	private String estado;
	private ModeloTablaCancelar modeloTabla;
	private boolean[] pagados;
	private VentanaCorredor vO;
	private Carrera carrera;

	/**
	 * Create the frame.
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @wbp.parser.constructor
	 */
	public VentanaCancelar(Carrera car) throws ClassNotFoundException, SQLException, ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCancelar.class.getResource("/img/logo.jpeg")));
		this.carrera=car;
		this.nombre = car.getNombre();
		this.estado = car.getEstado();
		this.fecha = car.getFecha_celebracion();
		if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA")) {
			corredores = BaseDatos.devuelveParticipantesSinCancelar(nombre, fecha);
			corredoresCancelados = BaseDatos.corredoresCancelados(nombre, fecha);
		}
		
		else {
			corredoresCancelados = BaseDatos.corredoresCanceladosPendientesPago(nombre, fecha);
		}
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLblListaDeCorredores(), BorderLayout.NORTH);
		contentPane.add(getPnTab(), BorderLayout.CENTER);
		contentPane.add(getPnBotones(), BorderLayout.SOUTH);
		if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA")) {
			getLblListaDeCorredores().setText("Lista de corredores de la carrera para cancelar su participaci\u00F3n");
		}
		
		else {
			getLblListaDeCorredores().setText("Lista de corredores que han cancelado su participación");
		}
	}
	
	
	public VentanaCancelar(Carrera car, VentanaCorredor vO) throws ClassNotFoundException, SQLException, ParseException {
		this(car);
		this.vO = vO;
	}

	private JLabel getLblListaDeCorredores() {
		if (lblListaDeCorredores == null) {
			lblListaDeCorredores = new JLabel("");
			lblListaDeCorredores.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblListaDeCorredores.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblListaDeCorredores;
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
			scrollPane.add(getTablaCancelar());
			scrollPane.setViewportView(getTablaCancelar());
		}
		return scrollPane;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA"))
				pnBotones.setLayout(new GridLayout(0, 5, 0, 0));
			else
				pnBotones.setLayout(new GridLayout(0, 3, 0, 0));
			pnBotones.add(getLbVacia());
			if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA")) {
				pnBotones.add(getBtnListaCorredoresInscritos());
				pnBotones.add(getBtnLista());
			}		
			pnBotones.add(getBtCancelar());		
		}
		return pnBotones;
	}
	private JLabel getLbVacia() {
		if (lbVacia == null) {
			lbVacia = new JLabel("");
		}
		return lbVacia;
	}
	private JButton getBtnListaCorredoresInscritos() {
		if (btnListaCorredoresInscritos == null) {
			btnListaCorredoresInscritos = new JButton("Lista inscritos");
			btnListaCorredoresInscritos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getLblListaDeCorredores().setText("Lista de corredores de la carrera para cancelar su participaci\u00F3n");
					getBtCancelar().setEnabled(true);
					if (!estado.equals("FINALIZADA") && !carrera.getEstado().equals("CANCELADA")) {
						String[] nombreColumnas = {"DNI","Nombre","Pago realizado", "Dinero a devolver", "Cancelar"};
						modeloTabla = new ModeloTablaCancelar(nombreColumnas,0);
					}
					else {
						String[] nombreColumnas = {"DNI","Nombre","Pago realizado", "Dinero a devolver"};
						modeloTabla = new ModeloTablaCancelar(nombreColumnas,0);
					}
					getTablaCancelar().setModel(modeloTabla);
					añadirFilas();
				}
			});
		}
		return btnListaCorredoresInscritos;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA")){
				btCancelar = new JButton("Cancelar participaci\u00F3n");
			} else {
				btCancelar = new JButton("Devolver dinero");
			}
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					List<Participante> aux = new ArrayList<Participante>();
					if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA")) {
						pagados = new boolean[corredores.size()];
						for (int i=0; i<corredores.size();i++) {
							pagados[i] = (Boolean) modeloTabla.getValueAt(i, 4);
							if (pagados[i]) {
								if (corredores.get(i).getEstado().equals(("PRE_INSCRITO"))) {
									corredores.get(i).setEstado("CANCELADO");
								}
								else {
									corredores.get(i).setEstado("CANCELADO-PENDIENTE");
								}
								corredoresCancelados.add(corredores.get(i));
							}
						}
						for (int i=0; i< vO.carrera.getparticipantes().size();i++) {
							for (int j=0; j<corredores.size();j++) {
								if (vO.carrera.getparticipantes().get(i).getCorredor().getDni()==corredores.get(j).getCorredor().getDni()) {
									vO.carrera.getparticipantes().get(i).setEstado(corredores.get(j).getEstado());
								}
							}
						}
						try {
							vO.añadirFilasEstado();
						} catch (IOException | ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						for (int i=corredores.size()-1;i>=0;i--) {
							if(pagados[i])
								modeloTabla.removeRow(i);
							else
								aux.add(corredores.get(i));	
						}
						try {
							cambiarEstadoCorredor();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						corredores = aux;
						pagados = new boolean[corredores.size()];
					}
					else {
						for (int i=corredoresCancelados.size()-1;i>=0;i--) {
							modeloTabla.removeRow(i);
							try {
								 
								Util.generaJustificanteDevolucion(corredoresCancelados.get(i), carrera);
								BaseDatos.cambiarEstadoParticipanteCanceladoPagar(corredoresCancelados.get(i), fecha, nombre);
							} catch (ClassNotFoundException | SQLException e) {
								e.printStackTrace();
							}
						}	
						for (int i=0; i< vO.carrera.getparticipantes().size();i++) {
							for (int j=0; j<corredoresCancelados.size();j++) {
								if (vO.carrera.getparticipantes().get(i).getCorredor().getDni()==corredoresCancelados.get(j).getCorredor().getDni()) {
									vO.carrera.getparticipantes().get(i).setEstado("CANCELADO-DEVUELTO");
								}
							}
						}
						try {
							vO.añadirFilasEstado();
							corredoresCancelados.clear();
						} catch (IOException | ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				
			});
		}
		return btCancelar;
	}
	
	private void cambiarEstadoCorredor() throws ClassNotFoundException, SQLException {
		for (int i=0; i<corredores.size();i++) {
			if(pagados[i]) {
				BaseDatos.cambiarEstadoParticipanteCancelado(corredores.get(i), fecha, nombre);
			}
		}
	}
	
	private JButton getBtnLista() {
		if (btnLista == null) {
			btnLista = new JButton("Lista cancelados");
			btnLista.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getLblListaDeCorredores().setText("Lista de corredores que han cancelado su participación");
					if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA")) {
						getBtCancelar().setEnabled(false);
						String[] nombreColumnas = {"DNI","Nombre","Pago realizado", "Dinero a devolver"};
						modeloTabla = new ModeloTablaCancelar(nombreColumnas, 0);
						getTablaCancelar().setModel(modeloTabla);
						añadirFilasCancelados();					
					}
				}
			});
		}
		return btnLista;
	}
	private JTable getTablaCancelar() {
		if (tablaCancelar == null) {
			if (!estado.equals("FINALIZADA")&& !carrera.getEstado().equals("CANCELADA")) {
				String[] nombreColumnas = {"DNI","Nombre","Pago realizado", "Dinero a devolver", "Cancelar"};
				modeloTabla = new ModeloTablaCancelar(nombreColumnas,0);
			}
			else {
				String[] nombreColumnas = {"DNI","Nombre","Pago realizado", "Dinero a devolver"};
				modeloTabla = new ModeloTablaCancelar(nombreColumnas,0);
			}
			tablaCancelar = new JTable(modeloTabla);
			tablaCancelar.setDefaultRenderer(Object.class, new RendererDorsales());
			tablaCancelar.getTableHeader().setReorderingAllowed(false);
			tablaCancelar.setRowHeight(30);
			añadirFilas();
		}
		return tablaCancelar;
	}

	private void añadirFilas() {
		Object[] filas;
		if (!estado.equals("FINALIZADA")&&!carrera.getEstado().equals("CANCELADA")) {
			filas = new Object[5];
			for (int i=0;i<corredores.size();i++) {
				if (corredores.get(i).getEstado().equals("PRE_INSCRITO") || corredores.get(i).getEstado().equals("PAGADO-SIN-RECOGER")) {
					filas[0] = corredores.get(i).getCorredor().getDni();
					filas[1] = corredores.get(i).getCorredor().getNombre();
					if (corredores.get(i).getEstado().equals("PRE_INSCRITO")) {
						filas[2] = "No";
						filas[3] = 0;
					}
					else {
						filas[2] = "Si";
						filas[3] = carrera.getDineroDevuelto(corredores.get(i).getFechaRegistro());
					}
					filas[4] = false;
					modeloTabla.addRow(filas);
				}
			}
		}
		else {
			filas = new Object[4];
			for (int i=0;i<corredoresCancelados.size();i++) {
				if (corredoresCancelados.get(i).getEstado().equals("CANCELADO-PENDIENTE")) {
					filas[0] = corredoresCancelados.get(i).getCorredor().getDni();
					filas[1] = corredoresCancelados.get(i).getCorredor().getNombre();
					filas[2] = "Si";
					filas[3] = carrera.getDineroDevuelto(corredoresCancelados.get(i).getFechaRegistro());
					modeloTabla.addRow(filas);
				}
			}
		}
	}
	

	private void añadirFilasCancelados() {
		Object[] filas = new Object[4];
		for (int i=0;i<corredoresCancelados.size();i++) {
			if (corredoresCancelados.get(i).getEstado().equals("CANCELADO") || corredoresCancelados.get(i).getEstado().equals("CANCELADO-PENDIENTE")) {
				filas[0] = corredoresCancelados.get(i).getCorredor().getDni();
				filas[1] = corredoresCancelados.get(i).getCorredor().getNombre();
				if (corredoresCancelados.get(i).getEstado().equals("CANCELADO")) {
					filas[2] = "No";
					filas[3] = 0;
				}
				else {
					filas[2] = "Si";
					filas[3] = carrera.getDineroDevuelto(corredoresCancelados.get(i).getFechaRegistro());
				}
				modeloTabla.addRow(filas);
			}
		}
	}
}
