package igu;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

public class ConfigurarCategoriaInicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtcmoPrefieresOrganizar;
	private JPanel pnBotones;
	private JPanel pnCb;
	private JRadioButton rbMixta;
	private JRadioButton rbSexos;
	private JButton btnVolver;
	private JButton btnSiguiente;
	private JRadioButton rbUnSoloSexo;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JFrame marco;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ConfigurarCategoriaInicio frame = new ConfigurarCategoriaInicio();
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
	public ConfigurarCategoriaInicio(JFrame marco) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfigurarCategoriaInicio.class.getResource("/img/logo.jpeg")));
		this.marco = marco;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 166);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getTxtcmoPrefieresOrganizar(), BorderLayout.NORTH);
		contentPane.add(getPanel_5(), BorderLayout.SOUTH);
		contentPane.add(getPanel_6(), BorderLayout.CENTER);
	}
	private JTextField getTxtcmoPrefieresOrganizar() {
		if (txtcmoPrefieresOrganizar == null) {
			txtcmoPrefieresOrganizar = new JTextField();
			txtcmoPrefieresOrganizar.setEditable(false);
			txtcmoPrefieresOrganizar.setFont(new Font("Tahoma", Font.PLAIN, 25));
			txtcmoPrefieresOrganizar.setHorizontalAlignment(SwingConstants.CENTER);
			txtcmoPrefieresOrganizar.setText("\u00BFC\u00F3mo prefieres organizar la carrera?");
			txtcmoPrefieresOrganizar.setColumns(10);
		}
		return txtcmoPrefieresOrganizar;
	}
	private JPanel getPanel_5() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getBtnVolver());
			pnBotones.add(getBtnSiguiente());
		}
		return pnBotones;
	}
	private JPanel getPanel_6() {
		if (pnCb == null) {
			pnCb = new JPanel();
			pnCb.setLayout(new GridLayout(3, 0, 0, 0));
			pnCb.add(getRbSexos());
			pnCb.add(getRbMixta());
			pnCb.add(getRbUnSoloSexo());
		}
		return pnCb;
	}
	private JRadioButton getRbMixta() {
		if (rbMixta == null) {
			rbMixta = new JRadioButton("Carrera mixta");
			buttonGroup_2.add(rbMixta);
		}
		return rbMixta;
	}
	private JRadioButton getRbSexos() {
		if (rbSexos == null) {
			rbSexos = new JRadioButton("Carrera por sexos");
			buttonGroup_2.add(rbSexos);
			rbSexos.setSelected(true);
		}
		return rbSexos;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
					if(rbMixta.isSelected())
					{
						ConfigurarCategoriaEdades v = new ConfigurarCategoriaEdades(marco, "Mixto", false, null);
						v.setVisible(true);
						v.setLocationRelativeTo(null);
						dispose();
					}
					else if(rbSexos.isSelected())
					{
						ConfigurarCategoriaEdades v = new ConfigurarCategoriaEdades(marco, "Femenino", true, null);
						v.setVisible(true);
						v.setLocationRelativeTo(null);
						dispose();
					}
					else if(rbUnSoloSexo.isSelected())
					{
						ConfigurarCarreraUnSoloSexo v = new ConfigurarCarreraUnSoloSexo(marco);
						v.setVisible(true);
						v.setLocationRelativeTo(null);
						dispose();
					}
				}
			});
		}
		return btnSiguiente;
	}
	private JRadioButton getRbUnSoloSexo() {
		if (rbUnSoloSexo == null) {
			rbUnSoloSexo = new JRadioButton("Carrera de un solo sexo");
			buttonGroup_2.add(rbUnSoloSexo);
		}
		return rbUnSoloSexo;
	}
}
