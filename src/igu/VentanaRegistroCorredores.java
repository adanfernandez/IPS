package igu;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utilidades.Util;
import logica.Carrera;
import logica.Categoria;
import logica.Corredor;
import logica.Cuota;
import logica.Participante;
import src.BaseDatos;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class VentanaRegistroCorredores extends JFrame {

	private JPanel contentPane;
	private JPanel panel_Registro;
	private JPanel panel_Titulo;
	private JPanel panel_contenido;
	private JLabel lblRegistro;
	private JLabel lblIntroduceElDni;
	private JPanel panel_Datos1;
	private JTextField textFieldDNI;
	private JLabel lblSeleccionaCarrera;
	private JComboBox comboBox_Carreras;
	private JButton btnRegistrar;
	private JPanel panel_Datos2;
	private JLabel lblNombre;
	private JLabel lblDni;
	private JTextField textField_Nombre;
	private JTextField textField_DNI;
	private JLabel lblSexo;
	private JRadioButton rdbtnHombre;
	private JRadioButton rdbtnMujer;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblFechaDe;
	private JComboBox comboBox_Dias;
	private JComboBox comboBox_Mes;
	private JComboBox comboBox_Years;
	private JLabel lblDia;
	private JLabel lblMes;
	private JLabel lblAo;
	private JButton btnRegistrar_1;

	private JButton btnComprobar;
	private JButton btnValor;
	private JButton btnVolver;
	private JLabel lblTotalAPagar;
	private JLabel lblNewLabel;
	private JLabel lblNombre_1;
	private JLabel lblNombreDinamico;
	private JLabel lblEdad;
	private JLabel lblEdadDinamica;
	private JLabel lblSexo_1;
	private JLabel lblSexoDinamico;
	private JLabel lblCategoria;
	private JLabel lblCategoriadinamica;
	private JLabel lblPlazasDisponibles;
	
	private JLabel lblNewLabel_1;
	private JLabel lblInicioCuotaDinamico;
	private JLabel lblFinCuota;
	private JLabel lblTotalAPagarDinamico;
	private JLabel lblPlazasdinamico;
	private JLabel lblFinCuotaDinamico;
	private Corredor corredor;
	private JLabel lblDevolucionDinamico;


	/**
	 * Create the frame.
	 */
	public VentanaRegistroCorredores() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				VentanaRegistroCorredores.class.getResource("/img/logo.jpeg")));
		setTitle("Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel_Registro(), BorderLayout.CENTER);

	}

	private JPanel getPanel_Registro() {
		if (panel_Registro == null) {
			panel_Registro = new JPanel();
			panel_Registro.setLayout(new BorderLayout(0, 0));
			panel_Registro.add(getPanel_Titulo(), BorderLayout.NORTH);
			panel_Registro.add(getPanel_contenido());
		}
		return panel_Registro;
	}

	private JPanel getPanel_Titulo() {
		if (panel_Titulo == null) {
			panel_Titulo = new JPanel();
			panel_Titulo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_Titulo.add(getLblRegistro());
		}
		return panel_Titulo;
	}

	private JPanel getPanel_contenido() {
		if (panel_contenido == null) {
			panel_contenido = new JPanel();
			panel_contenido.setLayout(new CardLayout(0, 0));
			panel_contenido.add(getPanel_Datos1(), "name_RegistroEnCarrera");
			panel_contenido.add(getPanel_Datos2(), "name_RegistroUsuario");
		}
		return panel_contenido;
	}

	private JLabel getLblRegistro() {
		if (lblRegistro == null) {
			lblRegistro = new JLabel("REGISTRO");
			lblRegistro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lblRegistro;
	}

	private JLabel getLblIntroduceElDni() {
		if (lblIntroduceElDni == null) {
			lblIntroduceElDni = new JLabel("Introduce el DNI");
			lblIntroduceElDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblIntroduceElDni;
	}

	private JPanel getPanel_Datos1() {
		if (panel_Datos1 == null) {
			panel_Datos1 = new JPanel();
			GridBagLayout gbl_panel_Datos1 = new GridBagLayout();
			gbl_panel_Datos1.columnWidths = new int[] { 18, 28, 68, 71, 79, 0,
					0, 0 };
			gbl_panel_Datos1.rowHeights = new int[] { 35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel_Datos1.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0,
					0.0, 1.0, 0.0, Double.MIN_VALUE };
			gbl_panel_Datos1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			panel_Datos1.setLayout(gbl_panel_Datos1);
			GridBagConstraints gbc_lblIntroduceElDni = new GridBagConstraints();
			gbc_lblIntroduceElDni.fill = GridBagConstraints.VERTICAL;
			gbc_lblIntroduceElDni.gridwidth = 4;
			gbc_lblIntroduceElDni.insets = new Insets(0, 0, 5, 5);
			gbc_lblIntroduceElDni.gridx = 1;
			gbc_lblIntroduceElDni.gridy = 1;
			panel_Datos1.add(getLblIntroduceElDni(), gbc_lblIntroduceElDni);
			GridBagConstraints gbc_textFieldDNI = new GridBagConstraints();
			gbc_textFieldDNI.gridwidth = 3;
			gbc_textFieldDNI.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldDNI.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldDNI.gridx = 1;
			gbc_textFieldDNI.gridy = 2;
			panel_Datos1.add(getTextFieldDNI(), gbc_textFieldDNI);
			GridBagConstraints gbc_btnComprobar = new GridBagConstraints();
			gbc_btnComprobar.insets = new Insets(0, 0, 5, 5);
			gbc_btnComprobar.gridx = 4;
			gbc_btnComprobar.gridy = 2;
			panel_Datos1.add(getBtnComprobar(), gbc_btnComprobar);
			GridBagConstraints gbc_lblNombre_1 = new GridBagConstraints();
			gbc_lblNombre_1.anchor = GridBagConstraints.EAST;
			gbc_lblNombre_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNombre_1.gridx = 1;
			gbc_lblNombre_1.gridy = 3;
			panel_Datos1.add(getLblNombre_1(), gbc_lblNombre_1);
			GridBagConstraints gbc_lblNombreDinamico = new GridBagConstraints();
			gbc_lblNombreDinamico.insets = new Insets(0, 0, 5, 5);
			gbc_lblNombreDinamico.gridx = 2;
			gbc_lblNombreDinamico.gridy = 3;
			panel_Datos1.add(getLblNombreDinamico(), gbc_lblNombreDinamico);
			GridBagConstraints gbc_lblEdad = new GridBagConstraints();
			gbc_lblEdad.anchor = GridBagConstraints.EAST;
			gbc_lblEdad.insets = new Insets(0, 0, 5, 5);
			gbc_lblEdad.gridx = 3;
			gbc_lblEdad.gridy = 3;
			panel_Datos1.add(getLblEdad(), gbc_lblEdad);
			GridBagConstraints gbc_lblEdadDinamica = new GridBagConstraints();
			gbc_lblEdadDinamica.insets = new Insets(0, 0, 5, 5);
			gbc_lblEdadDinamica.gridx = 4;
			gbc_lblEdadDinamica.gridy = 3;
			panel_Datos1.add(getLblEdadDinamica(), gbc_lblEdadDinamica);
			GridBagConstraints gbc_lblSexo_1 = new GridBagConstraints();
			gbc_lblSexo_1.anchor = GridBagConstraints.EAST;
			gbc_lblSexo_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblSexo_1.gridx = 1;
			gbc_lblSexo_1.gridy = 4;
			panel_Datos1.add(getLblSexo_1(), gbc_lblSexo_1);
			GridBagConstraints gbc_lblSexoDinamico = new GridBagConstraints();
			gbc_lblSexoDinamico.insets = new Insets(0, 0, 5, 5);
			gbc_lblSexoDinamico.gridx = 2;
			gbc_lblSexoDinamico.gridy = 4;
			panel_Datos1.add(getLblSexoDinamico(), gbc_lblSexoDinamico);
			GridBagConstraints gbc_lblCategoria = new GridBagConstraints();
			gbc_lblCategoria.anchor = GridBagConstraints.EAST;
			gbc_lblCategoria.insets = new Insets(0, 0, 5, 5);
			gbc_lblCategoria.gridx = 3;
			gbc_lblCategoria.gridy = 4;
			panel_Datos1.add(getLblCategoria(), gbc_lblCategoria);
			GridBagConstraints gbc_lblCategoriadinamica = new GridBagConstraints();
			gbc_lblCategoriadinamica.insets = new Insets(0, 0, 5, 5);
			gbc_lblCategoriadinamica.gridx = 4;
			gbc_lblCategoriadinamica.gridy = 4;
			panel_Datos1.add(getLblCategoriadinamica(), gbc_lblCategoriadinamica);
			GridBagConstraints gbc_lblSeleccionaCarrera = new GridBagConstraints();
			gbc_lblSeleccionaCarrera.gridwidth = 4;
			gbc_lblSeleccionaCarrera.insets = new Insets(0, 0, 5, 5);
			gbc_lblSeleccionaCarrera.gridx = 1;
			gbc_lblSeleccionaCarrera.gridy = 5;
			panel_Datos1.add(getLblSeleccionaCarrera(),
					gbc_lblSeleccionaCarrera);
			GridBagConstraints gbc_comboBox_Carreras = new GridBagConstraints();
			gbc_comboBox_Carreras.gridwidth = 3;
			gbc_comboBox_Carreras.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_Carreras.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_Carreras.gridx = 1;
			gbc_comboBox_Carreras.gridy = 6;
			panel_Datos1.add(getComboBox_Carreras(), gbc_comboBox_Carreras);
			GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
			gbc_btnRegistrar.insets = new Insets(0, 0, 5, 5);
			gbc_btnRegistrar.gridx = 4;
			gbc_btnRegistrar.gridy = 6;
			panel_Datos1.add(getBtnRegistrar(), gbc_btnRegistrar);
			GridBagConstraints gbc_lblPlazasDisponibles = new GridBagConstraints();
			gbc_lblPlazasDisponibles.anchor = GridBagConstraints.EAST;
			gbc_lblPlazasDisponibles.insets = new Insets(0, 0, 5, 5);
			gbc_lblPlazasDisponibles.gridx = 1;
			gbc_lblPlazasDisponibles.gridy = 7;
			panel_Datos1.add(getLblPlazasDisponibles(), gbc_lblPlazasDisponibles);
			GridBagConstraints gbc_lblPlazasdinamico = new GridBagConstraints();
			gbc_lblPlazasdinamico.insets = new Insets(0, 0, 5, 5);
			gbc_lblPlazasdinamico.gridx = 2;
			gbc_lblPlazasdinamico.gridy = 7;
			panel_Datos1.add(getLblPlazasdinamico(), gbc_lblPlazasdinamico);
			GridBagConstraints gbc_lblTotalAPagar = new GridBagConstraints();
			gbc_lblTotalAPagar.anchor = GridBagConstraints.EAST;
			gbc_lblTotalAPagar.insets = new Insets(0, 0, 5, 5);
			gbc_lblTotalAPagar.gridx = 3;
			gbc_lblTotalAPagar.gridy = 7;
			panel_Datos1.add(getLblTotalAPagar(), gbc_lblTotalAPagar);
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 4;
			gbc_lblNewLabel_2.gridy = 7;
			panel_Datos1.add(getLblNewLabel_2(), gbc_lblNewLabel_2);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 8;
			panel_Datos1.add(getLblInicioCuota(), gbc_lblNewLabel_1);
			GridBagConstraints gbc_lblInicioCuotaDinamico = new GridBagConstraints();
			gbc_lblInicioCuotaDinamico.fill = GridBagConstraints.VERTICAL;
			gbc_lblInicioCuotaDinamico.insets = new Insets(0, 0, 5, 5);
			gbc_lblInicioCuotaDinamico.gridx = 2;
			gbc_lblInicioCuotaDinamico.gridy = 8;
			panel_Datos1.add(getLblInicioCuotaDinamico(), gbc_lblInicioCuotaDinamico);
			GridBagConstraints gbc_lblFinCuota = new GridBagConstraints();
			gbc_lblFinCuota.anchor = GridBagConstraints.EAST;
			gbc_lblFinCuota.insets = new Insets(0, 0, 5, 5);
			gbc_lblFinCuota.gridx = 3;
			gbc_lblFinCuota.gridy = 8;
			panel_Datos1.add(getLblFinCuota(), gbc_lblFinCuota);
			GridBagConstraints gbc_lblFinCuotaDinamico = new GridBagConstraints();
			gbc_lblFinCuotaDinamico.insets = new Insets(0, 0, 5, 5);
			gbc_lblFinCuotaDinamico.gridx = 4;
			gbc_lblFinCuotaDinamico.gridy = 8;
			panel_Datos1.add(getLblFinCuotaDinamico(), gbc_lblFinCuotaDinamico);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 9;
			panel_Datos1.add(getLblNewLabel(), gbc_lblNewLabel);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 2;
			gbc_label.gridy = 9;
			panel_Datos1.add(getLabel(), gbc_label);
			GridBagConstraints gbc_btnVolver = new GridBagConstraints();
			gbc_btnVolver.gridwidth = 5;
			gbc_btnVolver.insets = new Insets(0, 0, 0, 5);
			gbc_btnVolver.gridx = 1;
			gbc_btnVolver.gridy = 10;
			panel_Datos1.add(getBtnVolver(), gbc_btnVolver);
		}
		return panel_Datos1;
	}

	private JTextField getTextFieldDNI() {
		if (textFieldDNI == null) {
			textFieldDNI = new JTextField();
			textFieldDNI.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					comboBox_Carreras.setEnabled(false);
					btnRegistrar.setEnabled(false);
					lblDevolucionDinamico.setText(" ");
					lblTotalAPagarDinamico.setText(" ");
					lblInicioCuotaDinamico.setText("");
					lblFinCuotaDinamico.setText("");				
					lblCategoriadinamica.setText("");
					lblPlazasdinamico.setText("");
				}
			});
			textFieldDNI.setColumns(10);
		}
		return textFieldDNI;
	}

	protected void comprobarDNI() throws ClassNotFoundException, SQLException, ParseException {
		String DNI = textFieldDNI.getText();
		if (validarDNI(DNI)) {

			Boolean b = Util.existeCorredor(DNI);

			if (b) {
				String genero = "";
				this.corredor = BaseDatos.buscarCorredor(DNI);
				if(corredor.getGenero()) {
					genero = "Masculino";
				}else {
					genero = "Femenino";
				}
				lblSexoDinamico.setText(genero);
				lblEdadDinamica.setText(corredor.getEdad()+"");
				lblNombreDinamico.setText(corredor.getNombre());
				comboBox_Carreras.setEnabled(true);
				btnRegistrar.setEnabled(true);
				actualizarCombo(DNI);

			} else {

				Object[] options = { "Registrar", "Cancelar" };

				int opcion = JOptionPane.showOptionDialog(contentPane,
						"Usuario no registrado en la basa de Datos",
						"Registro de Corredores", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						"Registrar");
				if (opcion == 0) {
					CardLayout cl = (CardLayout) (panel_contenido.getLayout());
					textField_DNI.setText(DNI);
					cl.show(panel_contenido, "name_RegistroUsuario");
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "DNI incorrecto");
	}

	private boolean validarDNI(String DNI) {

		return DNI.length() == 9;

	}

	private JLabel getLblSeleccionaCarrera() {
		if (lblSeleccionaCarrera == null) {
			lblSeleccionaCarrera = new JLabel("Selecciona Carrera");
			lblSeleccionaCarrera.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblSeleccionaCarrera;
	}

	private JComboBox getComboBox_Carreras() {
		if (comboBox_Carreras == null) {
			comboBox_Carreras = new JComboBox();
			comboBox_Carreras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					try {
						carreraSeleccionada();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			comboBox_Carreras.setEnabled(false);
		}
		return comboBox_Carreras;
	}

	protected void carreraSeleccionada() throws ClassNotFoundException, SQLException, ParseException {
		Corredor corredor = BaseDatos.buscarCorredor(textFieldDNI.getText());
		Carrera car = (Carrera) comboBox_Carreras.getSelectedItem();
		double coste = car.getCoste(new java.sql.Date(new Date().getTime()));
		int devolucion = car.getDevolucion();
		Cuota cuo = car.getCuotaActual();
		String FechaFin = "No hay Cuota";
		String FechaInicio = "No hay Cuota";
		String costeDef = "No hay Cuota";
		if(cuo!=null) {
			FechaInicio = cuo.getFecha_inicio()+"";
			FechaFin = cuo.getFecha_fin()+"";
			costeDef= coste+"";
		}
		String categoria = "No tinene categoria";
		int num = BaseDatos.numeroCorredores(car);
		String plazas = (car.getNumeroParticipantes()-num)+"";
		for(Categoria cat :car.getCategorias()) {
			if(cat.perteneceAcategoria(new Participante(corredor,null,"",0,0,0,0,""))) {
				categoria=cat.getNombre();}
		}
		lblInicioCuotaDinamico.setText(FechaInicio);
		lblFinCuotaDinamico.setText(FechaFin);
		lblDevolucionDinamico.setText(devolucion + " %");		
		lblTotalAPagarDinamico.setText(costeDef + " Euros");
		lblCategoriadinamica.setText(categoria);
		lblPlazasdinamico.setText(plazas);
			}

	private JButton getBtnRegistrar() {
		if (btnRegistrar == null) {
			btnRegistrar = new JButton("Registrar");
			btnRegistrar.setMnemonic('R');
			btnRegistrar.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						registrarParticipante();
					}
				}
			});
			btnRegistrar.setEnabled(false);
			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					registrarParticipante();
				}
			});
		}
		return btnRegistrar;
	}

	protected void registrarParticipante() {

		String DNI = textFieldDNI.getText();
		Corredor c = Util.devuelveCorredor(DNI);
		Carrera car = (Carrera) comboBox_Carreras.getSelectedItem();
		if (car.puedeInscribirse(c))
		{

			if (car.puedeInscribirseFecha())
			{
				Participante par = new Participante(
						c,(java.sql.Date) new java.sql.Date(new Date().getTime()),
						"PRE_INSCRITO", 0.0,  Integer.parseInt(DNI.substring(0,
								DNI.length() - 1)),0, 0,"");

				int corredores = 0;
				try {
					corredores = BaseDatos.numeroCorredores(car);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (car.getNumeroParticipantes() > corredores) {
					Util.registraParticipante(car, par);
					actualizarCombo(textFieldDNI.getText());
					Util.generaJustificante(par, car);
					JOptionPane.showMessageDialog(
							null,
							"El participante " + par.getCorredor().getNombre()
									+ " se ha registrado en la carrera "
									+ car.getNombre());
				}
				else
				{
					JOptionPane.showMessageDialog(null, "La carrera esta llena");
				}
			
			}
			else
				{
				JOptionPane.showMessageDialog(null,
						"Todavía no está abierta ninguna cuota de inscripción");
				}
		}
		else{
				JOptionPane.showMessageDialog(null,
						"El corredor no pertenece a ninguna Categoria");
			}

		}
		
	

	private JPanel getPanel_Datos2() {
		if (panel_Datos2 == null) {
			panel_Datos2 = new JPanel();
			GridBagLayout gbl_panel_Datos2 = new GridBagLayout();
			gbl_panel_Datos2.columnWidths = new int[] { 0, 0, 0, 0, 59, 0, 0 };
			gbl_panel_Datos2.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel_Datos2.columnWeights = new double[] { 1.0, 0.0, 1.0, 1.0,
					1.0, 1.0, Double.MIN_VALUE };
			gbl_panel_Datos2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
			panel_Datos2.setLayout(gbl_panel_Datos2);
			GridBagConstraints gbc_lblNombre = new GridBagConstraints();
			gbc_lblNombre.anchor = GridBagConstraints.EAST;
			gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
			gbc_lblNombre.gridx = 1;
			gbc_lblNombre.gridy = 1;
			panel_Datos2.add(getLblNombre(), gbc_lblNombre);
			GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
			gbc_textField_Nombre.gridwidth = 3;
			gbc_textField_Nombre.insets = new Insets(0, 0, 5, 5);
			gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Nombre.gridx = 2;
			gbc_textField_Nombre.gridy = 1;
			panel_Datos2.add(getTextField_Nombre(), gbc_textField_Nombre);
			GridBagConstraints gbc_lblDni = new GridBagConstraints();
			gbc_lblDni.anchor = GridBagConstraints.EAST;
			gbc_lblDni.insets = new Insets(0, 0, 5, 5);
			gbc_lblDni.gridx = 1;
			gbc_lblDni.gridy = 2;
			panel_Datos2.add(getLblDni(), gbc_lblDni);
			GridBagConstraints gbc_textField_DNI = new GridBagConstraints();
			gbc_textField_DNI.gridwidth = 3;
			gbc_textField_DNI.insets = new Insets(0, 0, 5, 5);
			gbc_textField_DNI.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_DNI.gridx = 2;
			gbc_textField_DNI.gridy = 2;
			panel_Datos2.add(getTextField_DNI(), gbc_textField_DNI);
			GridBagConstraints gbc_lblSexo = new GridBagConstraints();
			gbc_lblSexo.anchor = GridBagConstraints.EAST;
			gbc_lblSexo.insets = new Insets(0, 0, 5, 5);
			gbc_lblSexo.gridx = 1;
			gbc_lblSexo.gridy = 3;
			panel_Datos2.add(getLblSexo(), gbc_lblSexo);
			GridBagConstraints gbc_rdbtnMujer = new GridBagConstraints();
			gbc_rdbtnMujer.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnMujer.gridx = 3;
			gbc_rdbtnMujer.gridy = 3;
			panel_Datos2.add(getRdbtnMujer(), gbc_rdbtnMujer);
			GridBagConstraints gbc_rdbtnHombre = new GridBagConstraints();
			gbc_rdbtnHombre.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnHombre.gridx = 4;
			gbc_rdbtnHombre.gridy = 3;
			panel_Datos2.add(getRdbtnHombre(), gbc_rdbtnHombre);
			GridBagConstraints gbc_lblFechaDe = new GridBagConstraints();
			gbc_lblFechaDe.gridheight = 2;
			gbc_lblFechaDe.insets = new Insets(0, 0, 5, 5);
			gbc_lblFechaDe.gridx = 1;
			gbc_lblFechaDe.gridy = 4;
			panel_Datos2.add(getLblFechaDe(), gbc_lblFechaDe);
			GridBagConstraints gbc_lblDia = new GridBagConstraints();
			gbc_lblDia.insets = new Insets(0, 0, 5, 5);
			gbc_lblDia.gridx = 2;
			gbc_lblDia.gridy = 4;
			panel_Datos2.add(getLblDia(), gbc_lblDia);
			GridBagConstraints gbc_lblMes = new GridBagConstraints();
			gbc_lblMes.insets = new Insets(0, 0, 5, 5);
			gbc_lblMes.gridx = 3;
			gbc_lblMes.gridy = 4;
			panel_Datos2.add(getLblMes(), gbc_lblMes);
			GridBagConstraints gbc_lblAo = new GridBagConstraints();
			gbc_lblAo.insets = new Insets(0, 0, 5, 5);
			gbc_lblAo.gridx = 4;
			gbc_lblAo.gridy = 4;
			panel_Datos2.add(getLblAo(), gbc_lblAo);
			GridBagConstraints gbc_comboBox_Dias = new GridBagConstraints();
			gbc_comboBox_Dias.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_Dias.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_Dias.gridx = 2;
			gbc_comboBox_Dias.gridy = 5;
			panel_Datos2.add(getComboBox_Dias(), gbc_comboBox_Dias);
			GridBagConstraints gbc_comboBox_Mes = new GridBagConstraints();
			gbc_comboBox_Mes.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_Mes.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_Mes.gridx = 3;
			gbc_comboBox_Mes.gridy = 5;
			panel_Datos2.add(getComboBox_Mes(), gbc_comboBox_Mes);
			GridBagConstraints gbc_comboBox_Years = new GridBagConstraints();
			gbc_comboBox_Years.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_Years.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_Years.gridx = 4;
			gbc_comboBox_Years.gridy = 5;
			panel_Datos2.add(getComboBox_Years(), gbc_comboBox_Years);
			GridBagConstraints gbc_btnValor = new GridBagConstraints();
			gbc_btnValor.gridwidth = 2;
			gbc_btnValor.insets = new Insets(0, 0, 5, 5);
			gbc_btnValor.gridx = 1;
			gbc_btnValor.gridy = 6;
			panel_Datos2.add(getBtnValor(), gbc_btnValor);
			GridBagConstraints gbc_btnRegistrar_1 = new GridBagConstraints();
			gbc_btnRegistrar_1.gridwidth = 2;
			gbc_btnRegistrar_1.insets = new Insets(0, 0, 5, 5);
			gbc_btnRegistrar_1.gridx = 3;
			gbc_btnRegistrar_1.gridy = 6;
			panel_Datos2.add(getBtnRegistrar_1(), gbc_btnRegistrar_1);
		}
		return panel_Datos2;
	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setDisplayedMnemonic('N');
			lblNombre.setLabelFor(getTextField_Nombre());
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNombre;
	}

	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
			lblDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDni;
	}

	private JTextField getTextField_Nombre() {
		if (textField_Nombre == null) {
			textField_Nombre = new JTextField();
			textField_Nombre.setColumns(10);
		}
		return textField_Nombre;
	}

	private JTextField getTextField_DNI() {
		if (textField_DNI == null) {
			textField_DNI = new JTextField();
			textField_DNI.setEditable(false);
			textField_DNI.setColumns(10);
		}
		return textField_DNI;
	}

	private JLabel getLblSexo() {
		if (lblSexo == null) {
			lblSexo = new JLabel("Sexo:");
			lblSexo.setLabelFor(getRdbtnMujer());
			lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblSexo;
	}

	private JRadioButton getRdbtnHombre() {
		if (rdbtnHombre == null) {
			rdbtnHombre = new JRadioButton("Hombre");
			rdbtnHombre.setMnemonic('H');
			rdbtnHombre.setSelected(true);
			rdbtnHombre.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						rdbtnHombre.setSelected(true);
					}
				}
			});
			buttonGroup.add(rdbtnHombre);
		}
		return rdbtnHombre;
	}

	private JRadioButton getRdbtnMujer() {
		if (rdbtnMujer == null) {
			rdbtnMujer = new JRadioButton("Mujer");
			rdbtnMujer.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						rdbtnMujer.setSelected(true);
					}
				}
			});
			rdbtnMujer.setMnemonic('M');
			buttonGroup.add(rdbtnMujer);
		}
		return rdbtnMujer;
	}

	private JLabel getLblFechaDe() {
		if (lblFechaDe == null) {
			lblFechaDe = new JLabel(
					"<html><body> Fecha de <br> Nacimiento </body></html>");
			lblFechaDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblFechaDe;
	}

	private JComboBox getComboBox_Dias() {
		if (comboBox_Dias == null) {
			comboBox_Dias = new JComboBox();
			for (int i = 1; i < 31; i++) {
				comboBox_Dias.addItem(i);
			}
		}
		return comboBox_Dias;
	}

	private JComboBox getComboBox_Mes() {
		if (comboBox_Mes == null) {
			comboBox_Mes = new JComboBox();
			String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
					"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
					"Noviembre", "Diciembre" };
			DefaultComboBoxModel model = new DefaultComboBoxModel(meses);
			comboBox_Mes.setModel(model);
		}
		return comboBox_Mes;
	}

	private JComboBox getComboBox_Years() {
		if (comboBox_Years == null) {
			comboBox_Years = new JComboBox();

			for (int i = 1910; i < 2017; i++) {
				comboBox_Years.addItem(i);
			}
		}
		return comboBox_Years;
	}

	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Dia");
			lblDia.setDisplayedMnemonic('D');
			lblDia.setLabelFor(getComboBox_Dias());
			lblDia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDia;
	}

	private JLabel getLblMes() {
		if (lblMes == null) {
			lblMes = new JLabel("Mes");
			lblMes.setDisplayedMnemonic('e');
			lblMes.setLabelFor(getComboBox_Mes());
			lblMes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblMes;
	}

	private JLabel getLblAo() {
		if (lblAo == null) {
			lblAo = new JLabel("A\u00F1o");
			lblAo.setDisplayedMnemonic('A');
			lblAo.setLabelFor(getComboBox_Years());
			lblAo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblAo;
	}

	private JButton getBtnRegistrar_1() {
		if (btnRegistrar_1 == null) {
			btnRegistrar_1 = new JButton("Registrar");
			btnRegistrar_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						RegistarUsuario();
						CardLayout cl = (CardLayout) (panel_contenido
								.getLayout());
						cl.show(panel_contenido, "name_RegistroEnCarrera");
					}
				}
			});
			btnRegistrar_1.setMnemonic('R');
			btnRegistrar_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					RegistarUsuario();

				}
			});
			btnRegistrar_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return btnRegistrar_1;
	}

	/*
	 * zabrozongo
	 */
	protected void RegistarUsuario()  {
		String DNI = textField_DNI.getText();
		String Nombre = textField_Nombre.getText();
		int year = comboBox_Years.getSelectedIndex() + 1910;
		int dia = comboBox_Dias.getSelectedIndex() + 1;
		int mes = comboBox_Mes.getSelectedIndex() + 1;
		// java.sql.Date FechaNac = new java.sql.Date(year,mes,dia);
		String FechaNac = year + "-" + mes + "-" + dia;
		java.sql.Date fechaNacimiento=null;;
		try {
			fechaNacimiento = Util.convierteStringToDate(FechaNac);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fechaNacimiento.before(new Date())) {
			if (fechaNacimiento.before(new java.sql.Date(2012 - 1900, 10 + 1,
					24))) {
				if (fechaNacimiento.after(new java.sql.Date(1917 - 1900,
						10 + 1, 24))) {
					registrar(DNI, Nombre, FechaNac);
				} else {

					if (JOptionPane
							.showOptionDialog(
									contentPane,
									"Seguro que quieres registrar a un participante de mas de 100 años",
									"Registro de Corredores",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.INFORMATION_MESSAGE, null,
									new Object[] { "SI", "NO" }, "SI") == 0) {
						registrar(DNI, Nombre, FechaNac);
					} else {
						;
					}
				}
			} else {
				if (JOptionPane
						.showOptionDialog(
								contentPane,
								"Seguro que quieres registrar a un participante de menos de 5 años",
								"Registro de Corredores",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null,
								new Object[] { "SI", "NO" }, "SI") == 0) {
					registrar(DNI, Nombre, FechaNac);
				} else {
					;
				}
			}
		}

		else {
			JOptionPane.showMessageDialog(contentPane,
					"La fecha no puede ser en el futuro");
		}
	}

	private void registrar(String DNI, String Nombre, String FechaNac) {
		boolean sexo = rdbtnHombre.isSelected();
		try {

			Corredor c = new Corredor(Nombre, DNI, FechaNac, sexo);
			Util.añadirCorredor(c);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		comboBox_Carreras.setEnabled(true);
		btnRegistrar.setEnabled(true);
		actualizarCombo(DNI);
		CardLayout cl = (CardLayout) (panel_contenido.getLayout());
		cl.show(panel_contenido, "name_RegistroEnCarrera");
	}

	private void actualizarCombo(String DNI) {
		Carrera[] carreras;

		carreras = Util.carrerasPosibles(DNI);
		if (carreras.length == 0) {
			Object[] obj = new Object[] { "NO SE PUDE REGISTRAR EN NINGUNA" };
			DefaultComboBoxModel model = new DefaultComboBoxModel(obj);
			comboBox_Carreras.setModel(model);
		} else {
			DefaultComboBoxModel model = new DefaultComboBoxModel(carreras);
			comboBox_Carreras.setModel(model);
			comboBox_Carreras.setSelectedIndex(1);
		}
	}

	private JButton getBtnComprobar() {
		if (btnComprobar == null) {
			btnComprobar = new JButton("Comprobar");
			btnComprobar.setMnemonic('C');
			btnComprobar.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						try {
							comprobarDNI();
						} catch (ClassNotFoundException | SQLException | ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			btnComprobar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						comprobarDNI();
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return btnComprobar;
	}

	private JButton getBtnValor() {
		if (btnValor == null) {
			btnValor = new JButton("Volver");
			btnValor.setMnemonic('V');
			btnValor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					salir();
				}
			});
			btnValor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return btnValor;
	}

	private void salir() {
		VentanaInscripcion.ventanaInscripcion.setVisible(true);
		this.dispose();
	}

	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setMnemonic('V');
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					salir();
				}
			});
			btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return btnVolver;
	}

	private JLabel getLblTotalAPagar() {
		if (lblTotalAPagar == null) {
			lblTotalAPagar = new JLabel("Precio de la Cuota:");
			lblTotalAPagar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblTotalAPagar;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("% Devolucion:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNewLabel;
	}
	private JLabel getLblNombre_1() {
		if (lblNombre_1 == null) {
			lblNombre_1 = new JLabel("Nombre:");
			lblNombre_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNombre_1;
	}
	private JLabel getLblNombreDinamico() {
		if (lblNombreDinamico == null) {
			lblNombreDinamico = new JLabel("");
			lblNombreDinamico.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNombreDinamico;
	}
	private JLabel getLblEdad() {
		if (lblEdad == null) {
			lblEdad = new JLabel("Edad:");
			lblEdad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblEdad;
	}
	private JLabel getLblEdadDinamica() {
		if (lblEdadDinamica == null) {
			lblEdadDinamica = new JLabel("");
			lblEdadDinamica.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblEdadDinamica;
	}
	private JLabel getLblSexo_1() {
		if (lblSexo_1 == null) {
			lblSexo_1 = new JLabel("Sexo:");
			lblSexo_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblSexo_1;
	}
	private JLabel getLblSexoDinamico() {
		if (lblSexoDinamico == null) {
			lblSexoDinamico = new JLabel("");
			lblSexoDinamico.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblSexoDinamico;
	}
	private JLabel getLblCategoria() {
		if (lblCategoria == null) {
			lblCategoria = new JLabel("Categoria:");
			lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblCategoria;
	}
	private JLabel getLblCategoriadinamica() {
		if (lblCategoriadinamica == null) {
			lblCategoriadinamica = new JLabel("");
			lblCategoriadinamica.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblCategoriadinamica;
	}
	private JLabel getLblPlazasDisponibles() {
		if (lblPlazasDisponibles == null) {
			lblPlazasDisponibles = new JLabel("Plazas Disponibles:");
			lblPlazasDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblPlazasDisponibles;
	}
	private JLabel getLabel() {
		if (lblDevolucionDinamico == null) {
			lblDevolucionDinamico = new JLabel("");
			lblDevolucionDinamico.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDevolucionDinamico;
	}
	private JLabel getLblInicioCuota() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Inicio Cuota:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNewLabel_1;
	}
	private JLabel getLblInicioCuotaDinamico() {
		if (lblInicioCuotaDinamico == null) {
			lblInicioCuotaDinamico = new JLabel("");
		}
		return lblInicioCuotaDinamico;
	}
	private JLabel getLblFinCuota() {
		if (lblFinCuota == null) {
			lblFinCuota = new JLabel("Fin Cuota:");
			lblFinCuota.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblFinCuota;
	}
	private JLabel getLblPlazasdinamico() {
		if (lblPlazasdinamico == null) {
			lblPlazasdinamico = new JLabel("");
		}
		return lblPlazasdinamico;
	}
	private JLabel getLblFinCuotaDinamico() {
		if (lblFinCuotaDinamico == null) {
			lblFinCuotaDinamico = new JLabel("");
		}
		return lblFinCuotaDinamico;
	}
	private JLabel getLblNewLabel_2() {
		if (lblTotalAPagarDinamico == null) {
			lblTotalAPagarDinamico = new JLabel("");
		}
		return lblTotalAPagarDinamico;
	}
}
