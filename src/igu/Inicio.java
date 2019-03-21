package igu;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.io.IOException;
import java.awt.Font;

public class Inicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JPanel panel;
	private static JPanel panel_1;
	private static JPanel panel_2;
	private static JPanel panel_3;
	private static JPanel panel_4;
	private static JPanel panel_5;
	private static JLabel lblVentanaPrincipal;
	private static JButton btnAadirCarrera;
	private static JButton btnAadirCorredor;
	private static JButton btnVerCarreras;
	public static JFrame inicio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    // If Nimbus is not available, you can set the GUI to another look and feel.
				}
					Inicio frame = new Inicio();
					frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inicio() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("/img/logo.jpeg")));
		setTitle("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(6, 1, 0, 0));
		contentPane.add(getPanel());
		contentPane.add(getPanel_2());
		contentPane.add(getPanel_3());
		contentPane.add(getPanel_4());
		contentPane.add(getPanel_5());
		contentPane.add(getPanel_1());
		setLocationRelativeTo(null);
		
		inicio = this;
	}
	private static JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblVentanaPrincipal());
		}
		return panel;
	}
	private static JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
		}
		return panel_1;
	}
	private static JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
		}
		return panel_2;
	}
	private static JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_3.add(getBtnAadirCarrera());
		}
		return panel_3;
	}
	private static JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.add(getBtnAadirCorredor());
		}
		return panel_4;
	}
	private static JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.add(getBtnVerCarreras());
		}
		return panel_5;
	}
	private static JLabel getLblVentanaPrincipal() {
		if (lblVentanaPrincipal == null) {
			lblVentanaPrincipal = new JLabel("INICIO");
			lblVentanaPrincipal.setFont(new Font("Tahoma", Font.BOLD, 42));
		}
		return lblVentanaPrincipal;
	}
	private static JButton getBtnAadirCarrera() {
		if (btnAadirCarrera == null) {
			btnAadirCarrera = new JButton("A\u00F1adir Carrera");
			btnAadirCarrera.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					MostrarCreacionCarreras();
					
				}
			});
		}
		return btnAadirCarrera;
	}
	private static JButton getBtnAadirCorredor() {
		if (btnAadirCorredor == null) {
			btnAadirCorredor = new JButton("A\u00F1adir Corredor");
			btnAadirCorredor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inicio.setVisible(false);
					VentanaInscripcion vI = new VentanaInscripcion(); 
					vI.setVisible(true);
					vI.setLocationRelativeTo(null);
				}
			});
		}
		return btnAadirCorredor;
	}
	private static JButton getBtnVerCarreras() {
		if (btnVerCarreras == null) {
			btnVerCarreras = new JButton("Ver Carreras");
			btnVerCarreras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						mostrarVentanaCompeticiones();
					} catch (ClassNotFoundException | SQLException | ParseException e1) {
						e1.printStackTrace();
					}

					
				}
			});
		}
		return btnVerCarreras;
	}

	private static void mostrarVentanaCompeticiones() throws ClassNotFoundException, SQLException, ParseException {

		VentanaCompeticiones v = new VentanaCompeticiones();
		inicio.setVisible(false);
		v.setVisible(true);
	}

	private static void MostrarCreacionCarreras() {
		CreacionCarreras vC;
		try {
			vC = new CreacionCarreras();
			inicio.setVisible(false);
			vC.setVisible(true);
			vC.setLocationRelativeTo(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
