package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class VentanaInscripcion extends JFrame {

	private JPanel contentPane;
	private JButton btnInscribirUnCorredor;
	private JButton btnNewButton;
	private JButton btnVolver;

	public static VentanaInscripcion ventanaInscripcion;
	/**
	 * Create the frame.
	 */
	public VentanaInscripcion() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInscripcion.class.getResource("/img/logo.jpeg")));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnInscribirUnCorredor());
		contentPane.add(getBtnNewButton());
		contentPane.add(getBtnVolver());
		ventanaInscripcion= this;
	}
	private JButton getBtnInscribirUnCorredor() {
		if (btnInscribirUnCorredor == null) {
			btnInscribirUnCorredor = new JButton("Inscribir un corredor");
			btnInscribirUnCorredor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaRegistroUnCorredor();
				}

			});
			btnInscribirUnCorredor.setBounds(111, 94, 206, 33);
		}
		return btnInscribirUnCorredor;
	}
	private void mostrarVentanaRegistroUnCorredor() {
		VentanaRegistroCorredores vR = new VentanaRegistroCorredores(); 
		vR.setVisible(true);
		this.setVisible(false);
		vR.setLocationRelativeTo(null);
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Inscribir un club");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarVentanaInscripcionClub();
				}
			});
			btnNewButton.setBounds(111, 150, 206, 33);
		}
		return btnNewButton;
	}
	private void mostrarVentanaInscripcionClub() {
		this.setVisible(false);
		VentanaRegistroClub vRc = new VentanaRegistroClub(this);
		vRc.setVisible(true);
		
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Inicio.inicio.setVisible(true);
					dispose();
				}
			});
			btnVolver.setBounds(10, 213, 89, 23);
		}
		return btnVolver;
	}
}
