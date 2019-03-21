package igu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logica.Categoria;
import java.awt.Toolkit;
import javax.swing.JRadioButton;

public class ConfigurarCategoriaEdades extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnTitulo;
	private JLabel lbTitulo;
	private JPanel pnBotones;
	private JButton btnVolver;
	private JButton btnSiguiente;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lblEdadMnima;
	private JPanel panel_2;
	private JLabel lblEdadMxima;
	private JPanel panel_3;
	private JLabel lblNmeroDeIntervalos;
	private JTextField txtmin;
	private JTextField txtMax;
	private JTextField txtIntervalos;
	private JFrame marco;
	private boolean seguir;
	private ArrayList<Categoria> categorias;
	private JRadioButton rbEdadMaxima;

	/**
	 * Create the frame.
	 * @param marco 
	 */
	public ConfigurarCategoriaEdades(JFrame marco, String titulo, boolean seguir, ArrayList<Categoria> categorias) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfigurarCategoriaEdades.class.getResource("/img/logo.jpeg")));
		this.marco=marco;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnTitulo(), BorderLayout.NORTH);
		contentPane.add(getPnBotones(), BorderLayout.SOUTH);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		lbTitulo.setText(titulo);
		this.seguir=seguir;
		this.categorias=categorias;
	}

	private JPanel getPnTitulo() {
		if (pnTitulo == null) {
			pnTitulo = new JPanel();
			pnTitulo.add(getLbTitulo());
		}
		return pnTitulo;
	}

	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("T\u00EDtulo");
		}
		return lbTitulo;
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

					if(rbEdadMaxima.isSelected() && txtMax.getText().length()>0)
						JOptionPane.showMessageDialog(null, "Tiene seleccionada una edad máxima a la vez que la opción de que no hay edad máxima."
								+ "\nPor favor, revise sus datos y pruebe de nuevo");
					
					else if(!rbEdadMaxima.isSelected()){
							
						if (!comprobarCamposVacios1())
							JOptionPane.showMessageDialog(null,
									"Por favor, complete todos los campos.");
	
						else if (!isNumber(txtmin.getText())
								|| !isNumber(txtMax.getText()) || !isNumber(txtIntervalos.getText()))
							JOptionPane
									.showMessageDialog(
											null,
											"Algún dato no se corresponde conun número.\nPor favor, revíselos  y pruebe otra vez.");
	
						else if (Integer.parseInt(txtIntervalos.getText()) < 0)
							JOptionPane
									.showMessageDialog(
											null,
											"El número de intervalos no puede ser menor que 0.\nPor favor, revíselo y pruebe otra vez.");
	
						else {
							ConfigurarCategoriaFinal v;
							try {
								v = new ConfigurarCategoriaFinal(marco, Integer
										.parseInt(txtMax.getText()), Integer
										.parseInt(txtmin.getText()), Integer
										.parseInt(txtIntervalos.getText()),
										lbTitulo.getText().toString(), seguir, categorias);
								v.setVisible(true);
								v.setLocationRelativeTo(null);
								dispose();
							} catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					else
					{
						if (!comprobarCamposVacios2())
							JOptionPane.showMessageDialog(null,
									"Por favor, complete todos los campos.");
						else if (!isNumber(txtmin.getText()) || !isNumber(txtIntervalos.getText()))
							JOptionPane.showMessageDialog(null,"Algún dato no se corresponde conun número.\nPor favor, revíselos  y pruebe otra vez.");
						else if (Integer.parseInt(txtIntervalos.getText()) < 0)
							JOptionPane.showMessageDialog(null, "El número de intervalos no puede ser menor que 0.\nPor favor, revíselo y pruebe otra vez.");
						else {
							ConfigurarCategoriaFinal v;
							try {
								v = new ConfigurarCategoriaFinal(marco, Integer.MAX_VALUE,
										Integer.parseInt(txtmin.getText()),
										Integer.parseInt(txtIntervalos.getText()),
										lbTitulo.getText().toString(), seguir, categorias);
								v.setVisible(true);
								v.setLocationRelativeTo(null);
								dispose();
							} catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						
					}
				}
			});
			
		}
		return btnSiguiente;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(3, 0, 0, 0));
			panel.add(getPanel_1());
			panel.add(getPanel_2());
			panel.add(getPanel_3());
		}
		return panel;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getLblEdadMnima());
			panel_1.add(getTxtmin());
		}
		return panel_1;
	}

	private JLabel getLblEdadMnima() {
		if (lblEdadMnima == null) {
			lblEdadMnima = new JLabel("Edad m\u00EDnima: ");
		}
		return lblEdadMnima;
	}

	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.add(getLblEdadMxima());
			panel_2.add(getTxtMax());
			panel_2.add(getRbEdadMaxima());
		}
		return panel_2;
	}

	private JLabel getLblEdadMxima() {
		if (lblEdadMxima == null) {
			lblEdadMxima = new JLabel("Edad m\u00E1xima: ");
		}
		return lblEdadMxima;
	}

	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.add(getLblNmeroDeIntervalos());
			panel_3.add(getTxtIntervalos());
		}
		return panel_3;
	}

	private JLabel getLblNmeroDeIntervalos() {
		if (lblNmeroDeIntervalos == null) {
			lblNmeroDeIntervalos = new JLabel("N\u00FAmero de intervalos:");
		}
		return lblNmeroDeIntervalos;
	}

	private JTextField getTxtmin() {
		if (txtmin == null) {
			txtmin = new JTextField();
			txtmin.setColumns(10);
		}
		return txtmin;
	}

	private JTextField getTxtMax() {
		if (txtMax == null) {
			txtMax = new JTextField();
			txtMax.setColumns(10);
		}
		return txtMax;
	}

	private JTextField getTxtIntervalos() {
		if (txtIntervalos == null) {
			txtIntervalos = new JTextField();
			txtIntervalos.setColumns(10);
		}
		return txtIntervalos;
	}

	private boolean isNumber(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private boolean comprobarCamposVacios1() {
		if (txtmin.getText().length()==0 || txtMax.getText().length()==0
				|| txtIntervalos.getText().length()==0)
			return false;

		return true;
	}
	
	private boolean comprobarCamposVacios2() {
		if (txtmin.getText().length()==0)
			return false;

		return true;
	}
	
	private JRadioButton getRbEdadMaxima() {
		if (rbEdadMaxima == null) {
			rbEdadMaxima = new JRadioButton("Sin edad m\u00E1xima");
		}
		return rbEdadMaxima;
	}
}
