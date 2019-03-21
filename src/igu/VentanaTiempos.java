package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Carrera;
import logica.Participante;
import renders.ModeloNoEditable;
import renders.MyModeloTiempos;
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
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Toolkit;

public class VentanaTiempos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaTiempos;
//	private MyTableModel modeloTabla;
	private static List<Participante> corredores;
	private static boolean[] pagados;
	private static String nombreCarrera;
	private static java.sql.Date fechaCarrera;
	private JPanel pnTabla;
	private JLabel lbTituloCarrera;
	private JButton btnRegistrarTiempo;
	private JPanel pnBoton;
	private JPanel pnTab;
	private JScrollPane scrollPane;
	private Carrera carrera;
	/**
	 * Launch the application.
	 */
	private MyModeloTiempos modeloTabla;
	private JButton btnCargar;
	private JButton btnVolver;
	VentanaCorredor ventanaCorredor;

	/**
	 * Create the frame.
	 * @param ventanaCorredor 
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public VentanaTiempos(Carrera carrera, VentanaCorredor ventanaCorredor) throws ClassNotFoundException, SQLException, ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaTiempos.class.getResource("/img/logo.jpeg")));
		setTitle("Tiempos");
		this.setCarrera(carrera);
		this.ventanaCorredor=ventanaCorredor;
		setCorredores(getCarrera().getparticipantes());
		//obtenerCorredores(carrera);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 643, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnTabla(), BorderLayout.CENTER);
		this.setLocationRelativeTo(this);
	}
	
	private JTable getTablaTiempos() {
		if (tablaTiempos == null) {
			String[] nombreColumnas = {"DNI","Nombre","Tiempo"};
			modeloTabla =  new MyModeloTiempos(nombreColumnas,0);
			tablaTiempos = new JTable(modeloTabla);
			tablaTiempos.setDefaultRenderer(Object.class, new RendererDorsales());
			tablaTiempos.getTableHeader().setReorderingAllowed(false);
			tablaTiempos.setRowHeight(30);
			añadirFilas();
		}
		return tablaTiempos;
	}
	
	private void añadirFilas() {
		Object[] filas = new Object[3];
		for (int i=0; i<corredores.size();i++) {
			filas[0] = corredores.get(i).getCorredor().getDni();
			filas[1] = corredores.get(i).getCorredor().getNombre();
			filas[2] = corredores.get(i).getTiempo();
			modeloTabla.addRow(filas);
		}
	}

	private int horasRegistro(Date fechaRegistro) {
		java.util.Date fecha = new java.util.Date();
		long ms = (fecha.getTime() - fechaRegistro.getTime())/3600000;
		return (int) ms;
	}

	private void obtenerCorredores(Carrera car) throws ClassNotFoundException, SQLException, ParseException {
		
		setCorredores(BaseDatos.devuelveParticipantes(car.getNombre(),car.getFecha_celebracion()));
	
	}
	
	
	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setLayout(new BorderLayout(0, 0));
			pnTabla.add(getLbTituloCarrera(), BorderLayout.NORTH);
			pnTabla.add(getPnBoton(), BorderLayout.SOUTH);
			pnTabla.add(getPnTab(), BorderLayout.CENTER);
		}
		return pnTabla;
	}
	private JLabel getLbTituloCarrera() {
		if (lbTituloCarrera == null) {
			lbTituloCarrera = new JLabel("Lista de corredores de la carrera "+carrera.getNombre()+" con sus tiempos");
			lbTituloCarrera.setFont(new Font("Tahoma", Font.BOLD, 18));
			lbTituloCarrera.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		return lbTituloCarrera;
	}
	private JButton getBtnRegistrarTiempo() {
		if (btnRegistrarTiempo == null) {
			btnRegistrarTiempo = new JButton("Registrar Tiempo");
			//btnRegistrarTiempo.setIgnoreRepaint(true);
			btnRegistrarTiempo.setHorizontalTextPosition(SwingConstants.CENTER);
			btnRegistrarTiempo.setFocusable(false);
			btnRegistrarTiempo.setFocusTraversalKeysEnabled(false);
			btnRegistrarTiempo.setFocusPainted(false);
			btnRegistrarTiempo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//ArrayList<Participante> aux = new ArrayList<Participante>();
					
					int opcion = JOptionPane.showOptionDialog(null, "Con esta opción cargaras los tiempos y finalizaras la carrera.Estas seguro?", 
							"Finalizacion de carrera", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"SI", "NO"},(Object)"SI");
				 if(opcion == 0) {	
					for (int i=0; i<corredores.size();i++) {
						String tiempo = modeloTabla.getValueAt(i, 2).toString();
						char[] charTiempo=tiempo.toCharArray();
						boolean valido = true;
						for(int j=0; j<charTiempo.length;j++) {
							if(Character.isDigit(charTiempo[j])||charTiempo[j]=='.') {
								valido = true;
							}
							else valido = false;
						}
					double t = 0;
					if(valido) {
						t =Double.parseDouble(tiempo);		}
					
					corredores.get(i).setTiempo(t);
					try {
						BaseDatos.añadirTiempo(corredores.get(i),carrera.getNombre(),carrera.getFecha_celebracion());
				
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					}
					try {
						carrera.setEstado("FINALIZADA");
						BaseDatos.cambiarEstadoCarrera(carrera,"FINALIZADA");
						ventanaCorredor.getTextField_Estado().setText("FINALIZADA");
						contentPane.setVisible(false);
						salir();
						
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				 else {;}
					
					
				}
			});
		}
		return btnRegistrarTiempo;
	}
	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnBoton.add(getBtnVolver());
			pnBoton.add(getBtnCargar());
			pnBoton.add(getBtnRegistrarTiempo());
		}
		return pnBoton;
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
			scrollPane.setViewportView(getTablaTiempos());
		}
		return scrollPane;
	}

	public Carrera getCarrera() {
		return carrera;
	}
	
	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}
	private JButton getBtnCargar() {
		if (btnCargar == null) {
			btnCargar = new JButton("cargar tiempo desde fichero ");
			btnCargar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				try {				
					cambiarVentana();
				} catch (ClassNotFoundException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				}
			});
		}
		return btnCargar;
	}
	protected void cambiarVentana() throws ClassNotFoundException, SQLException, ParseException {
		//BaseDatos.AsignaIdentificadores(carrera);
		VentanaCargaCronometraje Vcc = new VentanaCargaCronometraje((this) );
		Vcc.setVisible(true);
		
	}

	
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					salir();
				}
			});
		}
		return btnVolver;
	}
	private void setCorredores( List<Participante> corredores) {
		 List<Participante> aux= new ArrayList<Participante>();
		for(Participante p:corredores) {
				if(p.getEstado().equals("PAGADO")) {
					aux.add(p);
				}
			}
			this.corredores=aux;
	}

	public void salir() {
		this.ventanaCorredor.carrera=this.carrera;
		
		this.dispose();
		
	}
}
