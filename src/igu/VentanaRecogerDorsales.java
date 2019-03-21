package igu;

import java.awt.BorderLayout;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import logica.Carrera;
import logica.Participante;
import renders.MyTableModel;
import renders.Renderer;
import renders.RendererDorsales;
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

import java.awt.Toolkit;

public class VentanaRecogerDorsales extends JFrame {
	public VentanaRecogerDorsales() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaDorsales;
	private MyTableModel modeloTabla;
	private static List<Participante> corredores;
	private static boolean[] recogidos;
	private static String nombreCarrera;
	private static java.sql.Date fechaCarrera;
	private JLabel lbTituloCarrera;
	private JButton btnRegistrarRecogidaDorsales;
	private JPanel pnBoton;
	private JLabel lbVacia0;
	private JLabel lbVacia1;
	private JPanel pnTab;
	private JScrollPane scrollPane;
	private VentanaCorredor vO;
	private Carrera car;

	/**
	 * Create the frame.
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @wbp.parser.constructor
	 */
	
	public VentanaRecogerDorsales(Carrera carrera, VentanaCorredor vO) throws ClassNotFoundException, SQLException, ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDeudas.class.getResource("/img/logo.jpeg")));
		this.vO = vO;
		setTitle("Deudas");
		nombreCarrera = carrera.getNombre();
		fechaCarrera = carrera.getFecha_celebracion();
		car = carrera;
		obtenerDatos();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1302, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLbTituloCarrera(), BorderLayout.NORTH);
		contentPane.add(getPnBoton(), BorderLayout.SOUTH);
		contentPane.add(getPnTab(), BorderLayout.CENTER);
		this.setLocationRelativeTo(this);
	}

	private JTable getTablaRecogerDorsales() {
		if (tablaDorsales == null) {
			String[] nombreColumnas = {"DNI","Nombre","Dorsal", "Recogido"};
			modeloTabla = new MyTableModel(nombreColumnas,0);
			tablaDorsales = new JTable(modeloTabla);
			tablaDorsales.setDefaultRenderer(Object.class, new RendererDorsales());
			tablaDorsales.getTableHeader().setReorderingAllowed(false);
			tablaDorsales.setRowHeight(30);
			añadirFilas();
		}
		return tablaDorsales;
	}
	
	private void añadirFilas() {
		Object[] filas = new Object[4];
		for (int i=0; i<corredores.size();i++) {
			filas[0] = corredores.get(i).getCorredor().getDni();
			filas[1] = corredores.get(i).getCorredor().getNombre();
			filas[2] = corredores.get(i).getDorsal();
			filas[3] = false;
			modeloTabla.addRow(filas);
		}
	}
	
	private void obtenerDatos() throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Participante> a = BaseDatos.devuelveParticipantes(nombreCarrera, fechaCarrera);
		corredores=new ArrayList<Participante>();
		for(Participante p:a){
			if(p.getEstado().equals("PAGADO-SIN-RECOGER") && p.getDorsal()>0)
				corredores.add(p);
		}
	}
	
	private void cambiarEstadoCorredor() throws ClassNotFoundException, SQLException {
		boolean recogido = false;
		for (int i=0; i<corredores.size();i++) {
			if(recogidos[i]) {
				BaseDatos.participanteRecogeDorsal(corredores.get(i), fechaCarrera, nombreCarrera);
				recogido = true;
			}
		}
			if (recogido)
				JOptionPane.showMessageDialog(null, "Se ha confirmado la recogida de dorsales de los corredores");
		
	}

	private JLabel getLbTituloCarrera() {
		if (lbTituloCarrera == null) {
			lbTituloCarrera = new JLabel("Lista de corredores de la carrera " + nombreCarrera + " que tienen que recoger su dorsal");
			lbTituloCarrera.setFont(new Font("Tahoma", Font.BOLD, 18));
			lbTituloCarrera.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTituloCarrera;
	}

	private JButton getBtnRegistrarRecogidaDorsales() {
		if (btnRegistrarRecogidaDorsales == null) {
			btnRegistrarRecogidaDorsales = new JButton("Registrar recogida");
			btnRegistrarRecogidaDorsales.setIgnoreRepaint(true);
			btnRegistrarRecogidaDorsales.setHorizontalTextPosition(SwingConstants.CENTER);
			btnRegistrarRecogidaDorsales.setFocusable(false);
			btnRegistrarRecogidaDorsales.setFocusTraversalKeysEnabled(false);
			btnRegistrarRecogidaDorsales.setFocusPainted(false);
			btnRegistrarRecogidaDorsales.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ArrayList<Participante> aux = new ArrayList<Participante>();
					recogidos = new boolean[corredores.size()];
									
					for (int i=0; i<corredores.size();i++) {
						recogidos[i] = (Boolean) modeloTabla.getValueAt(i, 3);
						
						if(recogidos[i])
							corredores.get(i).setEstado("PAGADO");
						else
							corredores.get(i).setEstado("PAGADO-SIN-RECOGER");
					}
					try {
						for (int i=0; i< vO.carrera.getparticipantes().size();i++) {
							for (int j=0; j<corredores.size();j++) {
								if (vO.carrera.getparticipantes().get(i).getCorredor().getDni()==corredores.get(j).getCorredor().getDni()) {
									vO.carrera.getparticipantes().get(i).setEstado(corredores.get(j).getEstado());
								}
							}
						}
						vO.añadirFilasEstado();
					} catch (IOException | ParseException e) {
						e.printStackTrace();
					}
					for (int i=corredores.size()-1;i>=0;i--) {
						if(recogidos[i]) {
							modeloTabla.removeRow(i);
						} else
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
					recogidos = new boolean[corredores.size()];
				}

			});
		}
		return btnRegistrarRecogidaDorsales;
	}

	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setLayout(new GridLayout(0, 5, 0, 0));
			pnBoton.add(getLbVacia0());
			pnBoton.add(getLbVacia1());
			pnBoton.add(getBtnRegistrarRecogidaDorsales());
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
			scrollPane.setViewportView(getTablaRecogerDorsales());
		}
		return scrollPane;
	}

}
