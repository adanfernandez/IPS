package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Carrera;
import src.BaseDatos;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;

import java.awt.GridLayout;

import jxl.write.DateTime;

import com.toedter.calendar.JDateChooser;

import java.awt.FlowLayout;

import javax.swing.SwingConstants;

import java.awt.Toolkit;

public class VentanaCompeticiones extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnBusqueda;
	private JPanel pnBotones;
	private JPanel pnAbajo;
	private JButton btnVerTodas;
	private JPanel pnAux;

	private ArrayList<Carrera> carreras;
	private JPanel pnCentro;
	private JPanel pnArriba;
	private JLabel lbBuscar;
	private JPanel pnFecha1;
	private JLabel lbFecha1;
	private JPanel pnFecha2;
	private JPanel pnBtBuscar;
	private JButton btBuscar;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel lbAño1;
	private JPanel panel_7;
	private JPanel panel_8;
	private JPanel panel_6;
	private JPanel panel_10;
	private JPanel panel_11;
	private JPanel panel_12;
	private JPanel panel_13;
	private JLabel lbFecha2;
	private JPanel panel_14;
	private JButton btnNewButton;
	private JPanel panel_9;
	private JDateChooser dateChooserFechaInicio;
	private JDateChooser dateChooserFechaFin;
	private JPanel panel_15;
	private JPanel panel_16;
	private JPanel panel_17;
	private JPanel panel_18;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	public VentanaCompeticiones() throws ClassNotFoundException, SQLException,
			
	ParseException {

		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCompeticiones.class.getResource("/img/logo.jpeg")));
		setTitle("Carreras");
		carreras = BaseDatos.carrerasTotal(); //Devuelve TODAS las carreras
	/*	for (Carrera c : carreras)
			c.setparticipantes(BaseDatos.devuelveParticipantes(c.getNombre(),
					c.getFecha_celebracion()));
		//Les asigna a TODAS las carreras TODOS sus participantes se hace en la ventana corredor
		*/
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 682, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnBusqueda(), BorderLayout.CENTER);
		contentPane.add(getPnBotones(), BorderLayout.SOUTH);
		contentPane.add(getPanel_12(), BorderLayout.NORTH);
		this.setLocationRelativeTo(null);
		if(carreras.size()==0)
		{
			dateChooserFechaInicio.setEnabled(false);
			dateChooserFechaFin.setEnabled(false);
			
			JOptionPane.showMessageDialog(null, "No hay ninguna carrera registrada.");

		}
	}

	private JPanel getPnBusqueda() {
		if (pnBusqueda == null) {
			pnBusqueda = new JPanel();
			pnBusqueda.setLayout(new BorderLayout(0, 0));
			pnBusqueda.add(getPanel_8(), BorderLayout.CENTER);
		}
		return pnBusqueda;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new BorderLayout(0, 0));
		}
		return pnBotones;
	}

	private JPanel getPnAbajo() {
		if (pnAbajo == null) {
			pnAbajo = new JPanel();
			pnAbajo.setLayout(new BorderLayout(0, 0));
			pnAbajo.add(getBtnNewButton(), BorderLayout.WEST);
		}
		return pnAbajo;
	}

	private JButton getBtnVerTodas() {
		if (btnVerTodas == null) {
			btnVerTodas = new JButton("Ver todas las carreras");
			btnVerTodas.setMnemonic('V');
			btnVerTodas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						Carreras ventana = new Carreras(carreras);
						ventana.setVisible(true);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		}
		return btnVerTodas;
	}

	private JPanel getPnAux() {
		if (pnAux == null) {
			pnAux = new JPanel();
			pnAux.setLayout(new BorderLayout(0, 0));
			pnAux.add(getPnAbajo());
			pnAux.add(getBtnVerTodas(), BorderLayout.EAST);
		}
		return pnAux;
	}

	private JPanel getPanel_8() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new GridLayout(5, 1, 0, 0));
			pnCentro.add(getPnFecha1());
			pnCentro.add(getPnFecha2());
			pnCentro.add(getPanel_3());
			pnCentro.add(getPanel_4());
		}
		return pnCentro;
	}

	private JPanel getPanel_12() {
		if (pnArriba == null) {
			pnArriba = new JPanel();
			pnArriba.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnArriba.add(getLbBuscar());
		}
		return pnArriba;
	}

	private JLabel getLbBuscar() {
		if (lbBuscar == null) {
			lbBuscar = new JLabel("Buscar carreras entre:");
			lbBuscar.setHorizontalAlignment(SwingConstants.CENTER);
			lbBuscar.setFont(new Font("Verdana", Font.BOLD, 25));
		}
		return lbBuscar;
	}

	private JPanel getPnFecha1() {
		if (pnFecha1 == null) {
			pnFecha1 = new JPanel();
			pnFecha1.setLayout(new BorderLayout(0, 0));
			pnFecha1.add(getPanel_1_2(), BorderLayout.WEST);
			pnFecha1.add(getPanel_4_1(), BorderLayout.EAST);
			pnFecha1.add(getPanel_5(), BorderLayout.CENTER);
		}
		return pnFecha1;
	}

	private JLabel getLbFecha1() {
		if (lbFecha1 == null) {
			lbFecha1 = new JLabel("Fecha 1:");
			lbFecha1.setLabelFor(getDateChooser());
			lbFecha1.setHorizontalAlignment(SwingConstants.RIGHT);
			lbFecha1.setDisplayedMnemonic('f');
		}
		return lbFecha1;
	}

	private JPanel getPnFecha2() {
		if (pnFecha2 == null) {
			pnFecha2 = new JPanel();
			pnFecha2.setLayout(new BorderLayout(0, 0));
			pnFecha2.add(getPanel_6(), BorderLayout.WEST);
			pnFecha2.add(getPanel_10(), BorderLayout.EAST);
			pnFecha2.add(getPanel_11(), BorderLayout.SOUTH);
			pnFecha2.add(getPanel_12_1(), BorderLayout.NORTH);
			pnFecha2.add(getPanel_13(), BorderLayout.CENTER);
		}
		return pnFecha2;
	}

	private JPanel getPanel_3() {
		if (pnBtBuscar == null) {
			pnBtBuscar = new JPanel();
			pnBtBuscar.add(getBtBuscar());
		}
		return pnBtBuscar;
	}

	private JButton getBtBuscar() {
		if (btBuscar == null) {
			btBuscar = new JButton("Buscar");
			btBuscar.setMnemonic('B');
			btBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (carreras.size()>0) {
						List<Carrera> lista = new ArrayList<Carrera>();

						java.util.Date fecha1 = dateChooserFechaInicio.getDate();
						java.util.Date fecha2 = dateChooserFechaFin.getDate();
						java.util.Date fechaCarrera;
						for (int i = 0; i < carreras.size(); i++) {
							fechaCarrera = carreras.get(i)
									.getFecha_celebracion();
							if (fechaCarrera.compareTo(fecha1) >= 0
									&& fechaCarrera.compareTo(fecha2) <= 0) {
								lista.add(carreras.get(i));
							}
						}
						if (lista.size() > 0) {
							try {
								Carreras ventanaC = new Carreras(lista);
								ventanaC.setVisible(true);
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (ParseException e1) {
								e1.printStackTrace();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"No hay carreras entre esas fechas");
						}
					} else
						JOptionPane.showMessageDialog(null,
								"Fechas incorrectas");
				}
			});
		}
		return btBuscar;
	}

	private JPanel getPanel_4() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPnAux(), BorderLayout.SOUTH);
		}
		return panel;
	}

	private JPanel getPanel_1_2() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(getPanel_2_1(), BorderLayout.NORTH);
			panel_1.add(getPanel_3_1(), BorderLayout.SOUTH);
		}
		return panel_1;
	}

	private JPanel getPanel_2_1() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
		}
		return panel_2;
	}

	private JPanel getPanel_3_1() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
		}
		return panel_3;
	}

	private JPanel getPanel_4_1() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
		}
		return panel_4;
	}

	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.setLayout(new BorderLayout(0, 0));
			panel_5.add(getLbAño1(), BorderLayout.NORTH);
			panel_5.add(getPanel_7(), BorderLayout.SOUTH);
			panel_5.add(getPanel_8_1(), BorderLayout.CENTER);
		}
		return panel_5;
	}

	private JPanel getLbAño1() {
		if (lbAño1 == null) {
			lbAño1 = new JPanel();
		}
		return lbAño1;
	}

	private JPanel getPanel_7() {
		if (panel_7 == null) {
			panel_7 = new JPanel();
		}
		return panel_7;
	}

	private JPanel getPanel_8_1() {
		if (panel_8 == null) {
			panel_8 = new JPanel();
			panel_8.setLayout(new GridLayout(0, 4, 0, 0));
			panel_8.add(getPanel_15());
			panel_8.add(getLbFecha1());
			panel_8.add(getPanel_9_1());
			panel_8.add(getPanel_16());
		}
		return panel_8;
	}

	private JPanel getPanel_6() {
		if (panel_6 == null) {
			panel_6 = new JPanel();
		}
		return panel_6;
	}

	private JPanel getPanel_10() {
		if (panel_10 == null) {
			panel_10 = new JPanel();
		}
		return panel_10;
	}

	private JPanel getPanel_11() {
		if (panel_11 == null) {
			panel_11 = new JPanel();
		}
		return panel_11;
	}

	private JPanel getPanel_12_1() {
		if (panel_12 == null) {
			panel_12 = new JPanel();
		}
		return panel_12;
	}

	private JPanel getPanel_13() {
		if (panel_13 == null) {
			panel_13 = new JPanel();
			panel_13.setLayout(new GridLayout(0, 4, 0, 0));
			panel_13.add(getPanel_17());
			panel_13.add(getLbFecha2());
			panel_13.add(getPanel_14());
			panel_13.add(getPanel_18());
		}
		return panel_13;
	}

	private JLabel getLbFecha2() {
		if (lbFecha2 == null) {
			lbFecha2 = new JLabel("Fecha 2:");
			lbFecha2.setLabelFor(getDateChooser_1());
			lbFecha2.setHorizontalAlignment(SwingConstants.RIGHT);
			lbFecha2.setDisplayedMnemonic('e');
		}
		return lbFecha2;
	}

	private JPanel getPanel_14() {
		if (panel_14 == null) {
			panel_14 = new JPanel();
			panel_14.setLayout(new BorderLayout(0, 0));
			panel_14.add(getDateChooser_1());
		}
		return panel_14;
	}


	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Volver");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Inicio.inicio.setVisible(true);
					dispose();

				}
			});
		}
		return btnNewButton;
	}

	private JPanel getPanel_9_1() {
		if (panel_9 == null) {
			panel_9 = new JPanel();
			panel_9.setLayout(new BorderLayout(0, 0));
			panel_9.add(getDateChooser());
		}
		return panel_9;
	}

	private JDateChooser getDateChooser() {
		if (dateChooserFechaInicio == null) {
			dateChooserFechaInicio = new JDateChooser();
			dateChooserFechaInicio.getCalendarButton().addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
						}
					});
			dateChooserFechaInicio.setDateFormatString("dd-M-yyyy");
			dateChooserFechaInicio.getDateEditor().addPropertyChangeListener(
					new java.beans.PropertyChangeListener() {

						@Override
						public void propertyChange(
								java.beans.PropertyChangeEvent evt) {

							if (dateChooserFechaInicio.getDate() != null) {

								if (carreras.size() > 0)
								dateChooserFechaFin.setSelectableDateRange(dateChooserFechaInicio.getDate(), dateChooserFechaFin.getMaxSelectableDate());
							}

						}

					});
		}
		return dateChooserFechaInicio;
	}

	private JDateChooser getDateChooser_1() {
		if (dateChooserFechaFin == null) {
			dateChooserFechaFin = new JDateChooser();
			dateChooserFechaFin.setDateFormatString("dd-M-yyyy");
		}
		return dateChooserFechaFin;
	}

	private Date obtenerMaximaFecha() {

		Date date = carreras.get(0).getFecha_celebracion();
		for (int i = 0; i < carreras.size(); i++) {
			if (date.compareTo(carreras.get(i).getFecha_celebracion()) > 0)
				date = carreras.get(i).getFecha_celebracion();
		}
		return date;

	}
	private JPanel getPanel_15() {
		if (panel_15 == null) {
			panel_15 = new JPanel();
		}
		return panel_15;
	}
	private JPanel getPanel_16() {
		if (panel_16 == null) {
			panel_16 = new JPanel();
		}
		return panel_16;
	}
	private JPanel getPanel_17() {
		if (panel_17 == null) {
			panel_17 = new JPanel();
		}
		return panel_17;
	}
	private JPanel getPanel_18() {
		if (panel_18 == null) {
			panel_18 = new JPanel();
		}
		return panel_18;
	}
}
