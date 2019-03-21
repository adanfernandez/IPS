package igu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import logica.Carrera;
import logica.Club;
import logica.Corredor;
import logica.Participante;
import persistencia.FileSystem;
import src.BaseDatos;
import utilidades.Util;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class VentanaRegistroClub extends JFrame {

	private JPanel contentPane;
	private JButton btnRegistrarClub;
	private JFileChooser jChooser;
	private Club club;
	private JTextArea txtrInfoclub;
	private JButton btnInscribirse;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel panel;
	private JButton btnVolver;
	
	private VentanaInscripcion ventanaInscripcion;
	private JLabel lbdes;
	private JTextField textField;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaRegistroClub frame = new VentanaRegistroClub();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VentanaRegistroClub(VentanaInscripcion vI) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaRegistroClub.class.getResource("/img/logo.jpeg")));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaInscripcion=vI;
		setBounds(100, 100, 584, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.NORTH);
//		contentPane.add(getTxtrInfoclub(), BorderLayout.CENTER);
		contentPane.add(getBtnInscribirse(), BorderLayout.SOUTH);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
	}

	public JFileChooser getSelector() {
		if (jChooser == null) {
			jChooser = new JFileChooser();
			jChooser.setMultiSelectionEnabled(true);
			jChooser.setFileFilter(new FileNameExtensionFilter("Archivos texto", "dat"));
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
	
	private JButton getBtnRegistrarClub() {
		if (btnRegistrarClub == null) {
			btnRegistrarClub = new JButton("Registrar Club");
			
			
			
			btnRegistrarClub.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int respuesta = getSelector().showOpenDialog(null);
					File file = jChooser.getSelectedFile();
					if (respuesta == JFileChooser.APPROVE_OPTION) {
						if (!file.getName().endsWith("dat")) {
							JOptionPane.showMessageDialog(null, "Please select only txt file.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							FileSystem fs = new FileSystem();
							try {
								List<Corredor> c = fs.loadTextFile("files/"+file.getName());
								String nC = fs.getDataCarrera()[0];
								String dC = fs.getDataCarrera()[1];
								Date fecha = Util.convierteStringToDate(dC);
								club = new Club(nC,(java.sql.Date) fecha,file.getName().split(".dat")[0],c,0);
								
								StringBuffer sb = new StringBuffer();
								sb.append("Nombre del club: "+club.getNombreClub());
								sb.append("\n");
								sb.append("Carrera en la que participan: "+club.getCarreraEnLaQueParticipa());
								sb.append("\n");
								sb.append("Fecha de la carrera: "+club.getFechaEnLaQueParticipa());
								sb.append("\n");
								sb.append("Integrantes del club: ");
								sb.append("\n");
								for(Corredor co : c){
									sb.append(co.getNombre()+" con DNI: "+co.getDni()+"\n");
								}
								getTxtrInfoclub().setText(sb.toString());
								btnInscribirse.setEnabled(true);
							} catch (Exception e1) {
//								JOptionPane.showMessageDialog(null,"error de formato ");
							}
							
							
						}
					}
				}
			});
		}
		return btnRegistrarClub;
	}
	private JTextArea getTxtrInfoclub() {
		if (txtrInfoclub == null) {
			txtrInfoclub = new JTextArea();
			txtrInfoclub.setBackground(SystemColor.controlHighlight);
			txtrInfoclub.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
			txtrInfoclub.setEditable(false);
		}
		return txtrInfoclub;
	}
	private JButton getBtnInscribirse() {
		if (btnInscribirse == null) {
			btnInscribirse = new JButton("Inscribirse");
			btnInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(!comprobarDescuento())
						return;
					String descuento=getTextField().getText();
					ArrayList<Carrera> carreras=null;
					try {
						carreras = BaseDatos.carrerasTotal();
						int dto = Integer.parseInt(descuento);
						club.setDescuento(dto);
						
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Carrera car = null;
					
					
					
					for(Carrera car1:carreras){
						if(car1.getNombre().equals(club.getCarreraEnLaQueParticipa())&& 
								car1.getFecha_celebracion().equals(club.getFechaEnLaQueParticipa())){
							car=car1;
							if(!car.getEstado().equals("ABIERTA")) {
								System.out.println(car.getEstado());
								JOptionPane.showMessageDialog(null, "La carrera NO esta abierta");
								return;
							}
							else {
								try {
									car.setCategorias(BaseDatos.devolverCategorias(car));
									car.setCuotas(BaseDatos.devolverCuotas(car));
									if(!BaseDatos.existeClub(club.getNombreClub()))
										BaseDatos.añadirClub(club);
									BaseDatos.VincularClubCarrera(club);
								}catch(SQLIntegrityConstraintViolationException e) {
									JOptionPane.showMessageDialog(null, "Ya se ha introducido este club en esta carrera");
									return;
								}
								catch (ClassNotFoundException | SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						}
					}
					if(car!=null) {
							//new Carrera(Util.convierteStringToDate(club.getFechaEnLaQueParticipa()),club.getCarreraEnLaQueParticipa(),"ABIERTA");
					for(Corredor c: club.getClub()){
						if(car.puedeInscribirse(c)) {
							if(car.puedeInscribirseFecha()) {
						try {
							if(!BaseDatos.existeCorredor(c.getDni()))
								Util.añadirCorredor(c);
						
						int id = Integer.parseInt(c.getDni().substring(0, c.getDni().length()-1));
						
						Participante par = new Participante(c, new java.sql.Date (new Date().getTime()),"PRE_INSCRITO",0.0, id, 0,0,club.getNombreClub());
						
						Util.registraParticipante(car,par);
						} catch(SQLIntegrityConstraintViolationException e) {
							
							JOptionPane.showMessageDialog(null, "El participante"+c.getNombre()
									+" ya esta registrado ");
						}catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}else {
							JOptionPane.showMessageDialog(null, "La carrera "+car.getNombre()+
									"No tiene abierta ninguna cuota ");
						}
							}else
						JOptionPane.showMessageDialog(null, "El corredor "+c.getNombre()+
								"No pertenece a ninguna categoria de la carrera "+ car.getNombre());
				}
					}else {
				JOptionPane.showMessageDialog(null, "La carrera no existe");
				}getThis().dispose();
					Inicio.inicio.setVisible(true);
			}

				private boolean comprobarDescuento() {
					String descuento=getTextField().getText();
					for (int i=0; i<descuento.length();i++) {
						if (!Character.isDigit(descuento.charAt(i))) {
							JOptionPane.showMessageDialog(null, "El descuento no es valido");
							return false;
						}				
					}
					if (Integer.parseInt(descuento)>100 || Integer.parseInt(descuento)<0) {
						JOptionPane.showMessageDialog(null, "El descuento no es valido");
						return false;
					}
					return true;	
					
				}});
			btnInscribirse.setEnabled(false);
		}
		return btnInscribirse;
	}
	private JFrame getThis(){
		return this;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTxtrInfoclub());
		}
		return scrollPane;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnVolver());
			panel.add(getBtnRegistrarClub());
			panel.add(getLbdes());
			panel.add(getTextField());
		}
		return panel;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarVentanaAnterior();
				}

			
			});
		}
		return btnVolver;
	}
	private void mostrarVentanaAnterior() {
		ventanaInscripcion.setVisible(true);
		this.dispose();
		
	}
	private JLabel getLbdes() {
		if (lbdes == null) {
			lbdes = new JLabel("Descuento para clubs");
		}
		return lbdes;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setText("0");
			textField.setFont(new Font("Tahoma", Font.BOLD, 11));
			textField.setHorizontalAlignment(SwingConstants.CENTER);
			textField.setColumns(10);
		}
		return textField;
	}
}
