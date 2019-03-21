package igu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

public class ConfigurarCarreraUnSoloSexo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbTitulo;
	private JPanel panel;
	private JRadioButton rbMasculino;
	private JRadioButton rbFemenino;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnSiguiente;
	private JFrame marco;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ConfigurarCarreraUnSoloSexo frame = new ConfigurarCarreraUnSoloSexo();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @param marco 
	 */
	public ConfigurarCarreraUnSoloSexo(JFrame marco) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfigurarCarreraUnSoloSexo.class.getResource("/img/logo.jpeg")));
		this.marco=marco;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 204);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getLbTitulo(), BorderLayout.NORTH);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		contentPane.add(getPnBotones(), BorderLayout.SOUTH);
	}

	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("\u00BFDe qu\u00E9 sexo quieres que sea la carrera?");
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbTitulo;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(2, 0, 0, 0));
			panel.add(getRbMasculino());
			panel.add(getRbFemenino());
		}
		return panel;
	}
	private JRadioButton getRbMasculino() {
		if (rbMasculino == null) {
			rbMasculino = new JRadioButton("Masculino");
			buttonGroup.add(rbMasculino);
		}
		return rbMasculino;
	}
	private JRadioButton getRbFemenino() {
		if (rbFemenino == null) {
			rbFemenino = new JRadioButton("Femenino");
			buttonGroup.add(rbFemenino);
		}
		return rbFemenino;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getBtnVolver());
			pnBotones.add(getBtnSiguiente());
		}
		return pnBotones;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					dispose();
					
				}
			});
		}
		return btnVolver;
	}
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(rbMasculino.isSelected())
					{
						ConfigurarCategoriaEdades v = new ConfigurarCategoriaEdades(marco, "Masculino", false, null);
						v.setVisible(true);
						v.setLocationRelativeTo(null);
						dispose();
					}
					else if(rbFemenino.isSelected())
					{
						ConfigurarCategoriaEdades v = new ConfigurarCategoriaEdades(marco, "Femenino", false, null);
						v.setVisible(true);
						v.setLocationRelativeTo(null);
						dispose();
					}
				}
			});
		}
		return btnSiguiente;
	}
}
