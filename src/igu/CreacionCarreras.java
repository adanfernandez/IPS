package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import logica.Carrera;
import logica.Categoria;
import logica.Cuota;
import renders.ModeloNoEditable;
import renders.Tabla;
import src.BaseDatos;
import utilidades.Util;
/**
 * @author fernando
 *
 */
public class CreacionCarreras extends JFrame {

	private JDateChooser dateChooserCelebracion;
	private JPanel contentPane;
	private JPanel panel_Contenido;
	private JLabel label_Nombre;
	private JTextField textField_NombreCarrera;
	private JLabel lblNumParticipantes;
	private JTextField textField_NumParticipantes;
	private JLabel lblFechaDeCelebracion;
	private JButton button_Crear;
	private JPanel panel_titulo;
	private JLabel label_5;
	private java.util.Date FechaDeHoy= new java.util.Date();
	private java.util.Date FechaCelebracion;
	
	//FECHA POR DEFECTO//
	private Date fechaPorDefecto = new Date(2400,31,12); 
	
	//private JLabel lblTarifa;
	//private JTextField textTarifa;
	private JButton btnVolver;
	private ArrayList<Cuota> cuotas ;
	private JButton btnConfigurarCategoras;
	
	private ArrayList<Categoria> categorias;
	private JLabel lblDevolucion;
	private JTextField textFieldDevolucion;
	private JButton button;
	private JButton button_1;
	private JLabel lblNewLabel;
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	private JLabel lblDescuento;
	private JLabel lblCuotas;
	private JTable tableCuotas;
	private ModeloNoEditable modeloTabla;
	private JTextField textFieldDescuento;
	private JButton btnNuevaCuota;
	private JButton btnModificar;
	DecimalFormat df = new DecimalFormat("#.00");
	private JDateChooser dateChooserFechaInicio;
	private JDateChooser dateChooserFechaFin;
	private Carrera carreraAntigua;
	private JPanel panel;
	private JTable table;
	private DefaultTableModel tm;
	
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JLabel lblCategoras;
	private JSeparator separator;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public CreacionCarreras() throws IOException {
		setTitle("Nueva carrera");
		categoriasPorDefecto();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 722, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel_Contenido(), BorderLayout.CENTER);
		contentPane.add(getPanel_titulo(), BorderLayout.NORTH);
		
		cuotas= new ArrayList<Cuota>();
		 java.util.Date fecha = new java.util.Date();
		fecha.setTime(fechaPorDefecto.getTime());
		FechaCelebracion=fecha;
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDorsales.class.getResource("/img/logo.jpeg")));

		añadirFilasCat();
	}

	private JPanel getPanel_Contenido() throws IOException {
		if (panel_Contenido == null) {
			panel_Contenido = new JPanel();
			GridBagLayout gbl_panel_Contenido = new GridBagLayout();
			gbl_panel_Contenido.columnWidths = new int[]{82, 102, 57, 0, 39, 0, 0, 45, 0, 0, 0, 36, 0, 0};
			gbl_panel_Contenido.rowHeights = new int[]{0, 0, 19, 0, 17, -9, 17, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panel_Contenido.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0};
			gbl_panel_Contenido.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			panel_Contenido.setLayout(gbl_panel_Contenido);
			GridBagConstraints gbc_label_Nombre = new GridBagConstraints();
			gbc_label_Nombre.gridwidth = 2;
			gbc_label_Nombre.fill = GridBagConstraints.VERTICAL;
			gbc_label_Nombre.anchor = GridBagConstraints.EAST;
			gbc_label_Nombre.insets = new Insets(0, 0, 5, 5);
			gbc_label_Nombre.gridx = 0;
			gbc_label_Nombre.gridy = 0;
			panel_Contenido.add(getLabel_Nombre(), gbc_label_Nombre);
			GridBagConstraints gbc_textField_NombreCarrera = new GridBagConstraints();
			gbc_textField_NombreCarrera.gridwidth = 3;
			gbc_textField_NombreCarrera.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_NombreCarrera.insets = new Insets(0, 0, 5, 5);
			gbc_textField_NombreCarrera.gridx = 2;
			gbc_textField_NombreCarrera.gridy = 0;
			panel_Contenido.add(getTextField_NombreCarrera(), gbc_textField_NombreCarrera);
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.gridheight = 14;
			gbc_separator.insets = new Insets(0, 0, 5, 5);
			gbc_separator.gridx = 6;
			gbc_separator.gridy = 0;
			panel_Contenido.add(getSeparator(), gbc_separator);
			GridBagConstraints gbc_lblCategoras = new GridBagConstraints();
			gbc_lblCategoras.gridwidth = 2;
			gbc_lblCategoras.insets = new Insets(0, 0, 5, 5);
			gbc_lblCategoras.gridx = 10;
			gbc_lblCategoras.gridy = 0;
			panel_Contenido.add(getLblCategoras(), gbc_lblCategoras);
			GridBagConstraints gbc_lblFechaDeCelebracion = new GridBagConstraints();
			gbc_lblFechaDeCelebracion.anchor = GridBagConstraints.EAST;
			gbc_lblFechaDeCelebracion.gridwidth = 2;
			gbc_lblFechaDeCelebracion.fill = GridBagConstraints.VERTICAL;
			gbc_lblFechaDeCelebracion.insets = new Insets(0, 0, 5, 5);
			gbc_lblFechaDeCelebracion.gridx = 0;
			gbc_lblFechaDeCelebracion.gridy = 1;
			panel_Contenido.add(getLblFechaDeCelebracion(), gbc_lblFechaDeCelebracion);
			GridBagConstraints gbc_dateChooserCelebracion = new GridBagConstraints();
			gbc_dateChooserCelebracion.fill = GridBagConstraints.HORIZONTAL;
			gbc_dateChooserCelebracion.gridwidth = 3;
			gbc_dateChooserCelebracion.insets = new Insets(0, 0, 5, 5);
			gbc_dateChooserCelebracion.gridx = 2;
			gbc_dateChooserCelebracion.gridy = 1;
			panel_Contenido.add(getJDateChooser(), gbc_dateChooserCelebracion);
			GridBagConstraints gbc_lblDevolucion = new GridBagConstraints();
			gbc_lblDevolucion.anchor = GridBagConstraints.EAST;
			gbc_lblDevolucion.gridwidth = 2;
			gbc_lblDevolucion.insets = new Insets(0, 0, 5, 5);
			gbc_lblDevolucion.gridx = 0;
			gbc_lblDevolucion.gridy = 2;
			panel_Contenido.add(getLblDevolucion(), gbc_lblDevolucion);
			GridBagConstraints gbc_textFieldDevolucion = new GridBagConstraints();
			gbc_textFieldDevolucion.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldDevolucion.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldDevolucion.gridx = 2;
			gbc_textFieldDevolucion.gridy = 2;
			panel_Contenido.add(getTextFieldDevolucion(), gbc_textFieldDevolucion);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridx = 4;
			gbc_button.gridy = 2;
			panel_Contenido.add(getButton(), gbc_button);
			GridBagConstraints gbc_button_1 = new GridBagConstraints();
			gbc_button_1.insets = new Insets(0, 0, 5, 5);
			gbc_button_1.gridx = 5;
			gbc_button_1.gridy = 2;
			panel_Contenido.add(getButton_1(), gbc_button_1);
			GridBagConstraints gbc_lblNumParticipantes = new GridBagConstraints();
			gbc_lblNumParticipantes.gridwidth = 2;
			gbc_lblNumParticipantes.fill = GridBagConstraints.VERTICAL;
			gbc_lblNumParticipantes.anchor = GridBagConstraints.EAST;
			gbc_lblNumParticipantes.insets = new Insets(0, 0, 5, 5);
			gbc_lblNumParticipantes.gridx = 0;
			gbc_lblNumParticipantes.gridy = 3;
			panel_Contenido.add(getLblNumParticipantes(), gbc_lblNumParticipantes);
			GridBagConstraints gbc_textField_NumParticipantes = new GridBagConstraints();
			gbc_textField_NumParticipantes.gridwidth = 3;
			gbc_textField_NumParticipantes.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_NumParticipantes.insets = new Insets(0, 0, 5, 5);
			gbc_textField_NumParticipantes.gridx = 2;
			gbc_textField_NumParticipantes.gridy = 3;
			panel_Contenido.add(getTextField_NumParticipantes(), gbc_textField_NumParticipantes);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridheight = 4;
			gbc_panel.gridwidth = 10;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 7;
			gbc_panel.gridy = 1;
			panel_Contenido.add(getPanel(), gbc_panel);
			GridBagConstraints gbc_lblTarifa = new GridBagConstraints();
			gbc_lblTarifa.anchor = GridBagConstraints.EAST;
			gbc_lblTarifa.gridwidth = 2;
			gbc_lblTarifa.insets = new Insets(0, 0, 5, 5);
			gbc_lblTarifa.gridx = 0;
			gbc_lblTarifa.gridy = 4;
			//panel_Contenido.add(getLblTarifa(), gbc_lblTarifa);
			//GridBagConstraints gbc_textTarifa = new GridBagConstraints();
//			gbc_textTarifa.gridwidth = 3;
//			gbc_textTarifa.insets = new Insets(0, 0, 5, 5);
//			gbc_textTarifa.fill = GridBagConstraints.HORIZONTAL;
//			gbc_textTarifa.gridx = 2;
//			gbc_textTarifa.gridy = 4;
//			panel_Contenido.add(getTextTarifa(), gbc_textTarifa);
			GridBagConstraints gbc_btnConfigurarCategoras = new GridBagConstraints();
			gbc_btnConfigurarCategoras.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnConfigurarCategoras.gridwidth = 10;
			gbc_btnConfigurarCategoras.insets = new Insets(0, 0, 5, 0);
			gbc_btnConfigurarCategoras.gridx = 7;
			gbc_btnConfigurarCategoras.gridy = 5;
			panel_Contenido.add(getBtnConfigurarCategoras(), gbc_btnConfigurarCategoras);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridwidth = 2;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 7;
			panel_Contenido.add(getLblNewLabel(), gbc_lblNewLabel);
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridheight = 5;
			gbc_scrollPane.gridwidth = 10;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 7;
			gbc_scrollPane.gridy = 7;
			panel_Contenido.add(getScrollPane(), gbc_scrollPane);
			GridBagConstraints gbc_lblFechaInicio = new GridBagConstraints();
			gbc_lblFechaInicio.anchor = GridBagConstraints.EAST;
			gbc_lblFechaInicio.insets = new Insets(0, 0, 5, 5);
			gbc_lblFechaInicio.gridx = 1;
			gbc_lblFechaInicio.gridy = 8;
			panel_Contenido.add(getLblFechaInicio(), gbc_lblFechaInicio);
			GridBagConstraints gbc_dateChooserFechaInicio = new GridBagConstraints();
			gbc_dateChooserFechaInicio.gridwidth = 3;
			gbc_dateChooserFechaInicio.insets = new Insets(0, 0, 5, 5);
			gbc_dateChooserFechaInicio.fill = GridBagConstraints.HORIZONTAL;
			gbc_dateChooserFechaInicio.gridx = 2;
			gbc_dateChooserFechaInicio.gridy = 8;
			panel_Contenido.add(getDateChooserFechaInicio(), gbc_dateChooserFechaInicio);
			GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
			gbc_lblFechaFin.anchor = GridBagConstraints.EAST;
			gbc_lblFechaFin.insets = new Insets(0, 0, 5, 5);
			gbc_lblFechaFin.gridx = 1;
			gbc_lblFechaFin.gridy = 9;
			panel_Contenido.add(getLblFechaFin(), gbc_lblFechaFin);
			GridBagConstraints gbc_dateChooserFechaFin = new GridBagConstraints();
			gbc_dateChooserFechaFin.gridwidth = 3;
			gbc_dateChooserFechaFin.insets = new Insets(0, 0, 5, 5);
			gbc_dateChooserFechaFin.fill = GridBagConstraints.HORIZONTAL;
			gbc_dateChooserFechaFin.gridx = 2;
			gbc_dateChooserFechaFin.gridy = 9;
			panel_Contenido.add(getDateChooserFechaFin(), gbc_dateChooserFechaFin);
			GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
			gbc_lblDescuento.anchor = GridBagConstraints.EAST;
			gbc_lblDescuento.insets = new Insets(0, 0, 5, 5);
			gbc_lblDescuento.gridx = 1;
			gbc_lblDescuento.gridy = 10;
			panel_Contenido.add(getLblDescuento(), gbc_lblDescuento);
			GridBagConstraints gbc_textFieldDescuento = new GridBagConstraints();
			gbc_textFieldDescuento.gridwidth = 3;
			gbc_textFieldDescuento.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldDescuento.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldDescuento.gridx = 2;
			gbc_textFieldDescuento.gridy = 10;
			panel_Contenido.add(getTextFieldDescuento(), gbc_textFieldDescuento);
			GridBagConstraints gbc_btnNuevaCuota = new GridBagConstraints();
			gbc_btnNuevaCuota.insets = new Insets(0, 0, 5, 5);
			gbc_btnNuevaCuota.gridx = 1;
			gbc_btnNuevaCuota.gridy = 11;
			panel_Contenido.add(getBtnNuevaCuota(), gbc_btnNuevaCuota);
			GridBagConstraints gbc_btnModificar = new GridBagConstraints();
			gbc_btnModificar.gridwidth = 2;
			gbc_btnModificar.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnModificar.insets = new Insets(0, 0, 5, 5);
			gbc_btnModificar.gridx = 2;
			gbc_btnModificar.gridy = 11;
			panel_Contenido.add(getBtnModificar(), gbc_btnModificar);
			GridBagConstraints gbc_btnVolver = new GridBagConstraints();
			gbc_btnVolver.gridwidth = 2;
			gbc_btnVolver.insets = new Insets(0, 0, 0, 5);
			gbc_btnVolver.gridx = 11;
			gbc_btnVolver.gridy = 13;
			panel_Contenido.add(getBtnVolver(), gbc_btnVolver);
			GridBagConstraints gbc_button_Crear = new GridBagConstraints();
			gbc_button_Crear.gridwidth = 4;
			gbc_button_Crear.gridx = 13;
			gbc_button_Crear.gridy = 13;
			panel_Contenido.add(getButton_Crear(), gbc_button_Crear);
		}
		return panel_Contenido;
	}
	
	private JLabel getLabel_Nombre() {
		if (label_Nombre == null) {
			label_Nombre = new JLabel("Nombre de la Carrera:");
			label_Nombre.setLabelFor(getTextField_NombreCarrera());
			label_Nombre.setDisplayedMnemonic('N');
			label_Nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return label_Nombre;
	}
	private JTextField getTextField_NombreCarrera() {
		if (textField_NombreCarrera == null) {
			textField_NombreCarrera = new JTextField();
			
			textField_NombreCarrera.setColumns(10);
		}
		return textField_NombreCarrera;
	}
	private JLabel getLblNumParticipantes() {
		if (lblNumParticipantes == null) {
			lblNumParticipantes = new JLabel("Num Participantes:");
			lblNumParticipantes.setLabelFor(getTextField_NumParticipantes());
			lblNumParticipantes.setDisplayedMnemonic('u');
			lblNumParticipantes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNumParticipantes;
	}
	private JTextField getTextField_NumParticipantes() {
		if (textField_NumParticipantes == null) {
			textField_NumParticipantes = new JTextField();
			
			textField_NumParticipantes.setColumns(10);
		}
		return textField_NumParticipantes;
	}
	private JLabel getLblFechaDeCelebracion() {
		if (lblFechaDeCelebracion == null) {
			lblFechaDeCelebracion = new JLabel("Fecha de Celebracion:\r\n");
			lblFechaDeCelebracion.setDisplayedMnemonic('F');
			lblFechaDeCelebracion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblFechaDeCelebracion;
	}
	
	
	//Comprueba que los datos de las carreras son correctos 
	protected void ComprobarCarrera(){
		
		
		
		String num =textField_NumParticipantes.getText();
		//String tarifa = textTarifa.getText();
		String nombre =textField_NombreCarrera.getText();
		//Miramos si el nombre es correcto
		if(validar_TextoNombre(nombre)) {
			//Estan Todos los Datos?
				
				Date d1= fechaPorDefecto;
				int numParticipantes=0;
				int precio=0;
				String estado="PENDIENTE";
				boolean valido=true;
				
				if(dateChooserCelebracion.getDate()!=null) {
					d1 = new Date(dateChooserCelebracion.getDate().getTime());
				}
				if(!num.isEmpty()){
					if(validar_NumCorredores(num)) {
						numParticipantes=Integer.parseInt(num);
					}else valido=false;
				}
				
				
				if(valido) {
				if(cuotas.size()<=0 ||num.isEmpty() || dateChooserCelebracion.getDate()==null) {
					JOptionPane.showMessageDialog(contentPane, "La carrera no esta completa se guardara como PENDIENTE ");
					 estado = "PENDIENTE";}
				
				else {
					Object[] opciones = {"PENDIENTE","ABIERTA"};
			    	int opcion = JOptionPane.showOptionDialog(contentPane, "Quieres guardar la carrera como pendiente o como abierta", "Nueva Carrera",		    			
			    			JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null,opciones, "PENDIENTE");
					
			    	if(opcion==0) {
						estado = "PENDIENTE";
					}else
						estado = "ABIERTA";
					}
				if(button_Crear.getText().equals("GUARDAR")) {
					try {
						BaseDatos.borrarCarrera(carreraAntigua);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				int dto = Integer.parseInt(textFieldDevolucion.getText());
				Carrera car = new Carrera(d1,nombre,numParticipantes,estado,categorias,dto);
				
				try {
					utilidades.Util.registraCarrera(car);
				
				
				for(Cuota c:cuotas) {
					try {
						BaseDatos.AñadirCuotas(car, c);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				for(Categoria cat:categorias) {
					try {
						if(BaseDatos.existeCategoria(cat.getNombre())) {
							BaseDatos.VinculaCategoriaCarrera(car, cat);
						}
						else {
							BaseDatos.NuevaCategoria(cat);
							BaseDatos.VinculaCategoriaCarrera(car, cat);
							}
					}catch(SQLIntegrityConstraintViolationException a) {
						JOptionPane.showMessageDialog(contentPane, "La categoria ya existe en la base de datos");

					}
					catch (ClassNotFoundException | SQLException  e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
					
					//JOptionPane.showMessageDialog(null, "Se han guardado sus datos");
					
					Inicio inicio = new Inicio();
					inicio.setVisible(true);
					inicio.setLocationRelativeTo(null);
					
					dispose();
					} 
				catch(SQLIntegrityConstraintViolationException e) {
						JOptionPane.showMessageDialog(contentPane, "La carrera ya existe en la base de datos");

					}
				catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
						
			}
				}
				
		else {
			//JOptionPane.showMessageDialog(contentPane, "La carrera tiene un nombre incorrecto");
			}
	    }
			

	
	
	private boolean validar_Tarifa(String num) {    
	    boolean valido = !num.isEmpty();
	    char[] numero = num.toCharArray();
	 
	     for(char r:numero) {
	    	if(!Character.isDigit(r)) {
				
	    		valido = false;
	    	}
	    	}if(valido) {
	    
	    int valor = Integer.parseInt(num);
	    if(valor<0) {
	    	JOptionPane.showMessageDialog(contentPane, "La tarifa es inferior a 0");
	    	textFieldDescuento.setText("");
	    	textFieldDescuento.grabFocus();
	    	valido =  false;
	    	
	    }
	    else if(valor>100) {
	    	Object[] opciones = {"SI","NO"};
	    	int opcion = JOptionPane.showOptionDialog(contentPane, "Seguro que quieres aplicar una tarifa mayor de 100 euros", "Creacion de tarifa",
	    			
	    			JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null,opciones, "SI");
			if(opcion==1) {
				textFieldDescuento.setText("");
				textFieldDescuento.grabFocus();
				valido = false;}
				else {
					valido = true;}
			}
		else if(valor==0) {
		    	Object[] opciones = {"SI","NO"};
		    	int opcion = JOptionPane.showOptionDialog(contentPane, "Seguro que quieres hacer la carrera gratuita?", "Creacion de tarifa",
		    			
		    			JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null,opciones, "SI");
				if(opcion==1) {
					//textTarifa.setText("");
					//textTarifa.grabFocus();
					valido = false;}
					else {
						valido = true;}
				}
			
	    }
	    	else {
	    		textFieldDescuento.setText("");
		    	JOptionPane.showMessageDialog(contentPane, "La tarifa es incorrecta");
		    	
	    	}
	    return valido;
	}

	private boolean validar_NumCorredores(String num) {
		char[] numero = num.toCharArray();
	    boolean valido = !num.isEmpty();
	   
	 
	     for(char r:numero) {
	    	if(!Character.isDigit(r)) {
				
	    		valido = false;
	    	}
	    	}
	     if(valido) {
	    int valor = Integer.parseInt(num);
	    if(valor<10) {
	    	JOptionPane.showMessageDialog(contentPane, "La carrera tiene menos de 10 corredores");
	    	textField_NumParticipantes.setText("");
	    	valido =  false;
	    	
	    }
	    else if(valor>500) {
	    	Object[] opciones = {"SI","NO"};
	    	int opcion = JOptionPane.showOptionDialog(contentPane, "Seguro que quieres celebrar una carrera con mas de 500 corredores", "Registro de Corredores",
					JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null,opciones, "SI");
	    			
	    	if(opcion==1) {
	    	textField_NumParticipantes.setText("");
	    	textField_NumParticipantes.grabFocus();
	    	valido = false;
			}
	    	else
			valido = true;
	    }}else {
	    	textField_NumParticipantes.setText("");
	    	JOptionPane.showMessageDialog(contentPane, "La carrera tiene un numero de corredores incorrecto");
	    	
	    }
	    return valido;
	}

	private boolean validar_TextoNombre(String nom) {
		
	  if(nom.isEmpty()) {
		  	JOptionPane.showMessageDialog(contentPane, "La carrera tiene que tener un nombre");
	    	return false;
		  
	  }
	  return true;
	}
	private JButton getButton_Crear() {
		if (button_Crear == null) {
			button_Crear = new JButton("CREAR CARRERA");
			button_Crear.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						
						ComprobarCarrera();
					}
				}
			});
			button_Crear.setMnemonic('C');
			button_Crear.setMnemonic(KeyEvent.VK_ENTER);
			button_Crear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ComprobarCarrera();
				}
			});
		}
		return button_Crear;
	}
	private JPanel getPanel_titulo() {
		if (panel_titulo == null) {
			panel_titulo = new JPanel();
			panel_titulo.add(getLabel_5());
		}
		return panel_titulo;
	}
	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel(" NUEVA CARRERA");
			label_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return label_5;
	}
	
	public void cambiarFecha(JTextField txt,Date fecha) {
	//	FechaCarrera = fecha;
		this.setVisible(true);
		txt.setText(fecha.toString());
		
	}
	
	
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("VOLVER");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					salida();}
			});
		}
		return btnVolver;}
	

	protected void salida() {
		this.dispose();
		Inicio.inicio.setVisible(true);
	}	
	
	
	private JButton getBtnConfigurarCategoras() {
		if (btnConfigurarCategoras == null) {
			btnConfigurarCategoras = new JButton("Configurar categor\u00EDas");
			btnConfigurarCategoras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					crearCategorias();
					System.out.println("Las categorÃ­as son: \n");
				}
			});
		}
		return btnConfigurarCategoras;
	}
	
	public void setCategorias(ArrayList<Categoria> categorias)
	{
		this.categorias=categorias;
	}
	
	protected void crearCategorias() {
		ConfigurarCategoriaInicio v = new ConfigurarCategoriaInicio(this);
		v.setVisible(true);
		v.setLocationRelativeTo(null);
	}
	
	private void categoriasPorDefecto()
	{
		Categoria categoria1m = new Categoria("SM", 18, 34, "Masculino");
		Categoria categoria2m = new Categoria("M-35", 35, 39, "Masculino");
		Categoria categoria3m = new Categoria("M-40", 39, 44, "Masculino");
		Categoria categoria4m = new Categoria("M-45", 45, 49, "Masculino");
		Categoria categoria5m = new Categoria("M-50", 50, 54, "Masculino");
		Categoria categoria6m = new Categoria("M-55", 55, 59, "Masculino");
		Categoria categoria7m = new Categoria("M-60", 60, 65, "Masculino");
		Categoria categoria8m = new Categoria("M-65", 66, 70, "Masculino");
		Categoria categoria9m = new Categoria("M-70", 71, Integer.MAX_VALUE, "Masculino");

		
		Categoria categoria1f = new Categoria("SF", 18, 39, "Femenino");
		Categoria categoria2f = new Categoria("F-40", 40, 44, "Femenino");
		Categoria categoria3f = new Categoria("F-45", 45, 49, "Femenino");
		Categoria categoria4f = new Categoria("F-50", 50, 54, "Femenino");
		Categoria categoria5f = new Categoria("F-55", 55, 59, "Femenino");
		Categoria categoria6f = new Categoria("F-60", 60, 70, "Femenino");
		Categoria categoria7f = new Categoria("F-70", 71, Integer.MAX_VALUE, "Femenino");

		categorias = new ArrayList<Categoria>();
		categorias.add(categoria1m);
		categorias.add(categoria2m);
		categorias.add(categoria3m);
		categorias.add(categoria4m);
		categorias.add(categoria5m);
		categorias.add(categoria6m);
		categorias.add(categoria7m);
		categorias.add(categoria8m);
		categorias.add(categoria9m);

		
		categorias.add(categoria1f);
		categorias.add(categoria2f);
		categorias.add(categoria3f);
		categorias.add(categoria4f);
		categorias.add(categoria5f);
		categorias.add(categoria6f);
		categorias.add(categoria7f);







	}
	private JLabel getLblDevolucion() {
		if (lblDevolucion == null) {
			lblDevolucion = new JLabel("% Devolucion:");
			lblDevolucion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDevolucion;
	}
	private JTextField getTextFieldDevolucion() {
		if (textFieldDevolucion == null) {
			textFieldDevolucion = new JTextField();
			textFieldDevolucion.setText("20");
			textFieldDevolucion.setEditable(false);
			textFieldDevolucion.setColumns(10);
		}
		return textFieldDevolucion;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton("+");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int dev = Integer.parseInt(textFieldDevolucion.getText());
					if(dev<100) {				
					textFieldDevolucion.setText(dev+5+"");
					}

				}
			});
		}
		return button;
	}
	private JButton getButton_1() {
		if (button_1 == null) {
			button_1 = new JButton("-");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int dev = Integer.parseInt(textFieldDevolucion.getText());
					if(dev>0) {				
					textFieldDevolucion.setText(dev-5+"");
					}

				}
			});
		}
		return button_1;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Cuotas");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lblNewLabel;
	}
	private JLabel getLblFechaInicio() {
		if (lblFechaInicio == null) {
			lblFechaInicio = new JLabel("Fecha Inicio");
			lblFechaInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblFechaInicio;
	}
	private JLabel getLblFechaFin() {
		if (lblFechaFin == null) {
			lblFechaFin = new JLabel("Fecha Fin");
			lblFechaFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblFechaFin;
	}
	private JLabel getLblDescuento() {
		if (lblDescuento == null) {
			lblDescuento = new JLabel("Tarifa");
			lblDescuento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblDescuento;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setColumnHeaderView(getLblCuotas());
			scrollPane.setViewportView(getTableCuotas());
		}
		return scrollPane;
	}
	private JLabel getLblCuotas() {
		if (lblCuotas == null) {
			lblCuotas = new JLabel("CUOTAS");
			lblCuotas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return lblCuotas;
	}
	
	private JTable getTableCuotas() {
		if (tableCuotas == null) {
			tableCuotas = new JTable();
			String[] nombreColumnas = {"Inicio de la cuota","Fin de la cuota","Tarifa"};

			modeloTabla = new ModeloNoEditable(nombreColumnas,0);
			tableCuotas = new JTable(modeloTabla);
			tableCuotas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
				//	CambiarAModificado();

					//arg0.getClickCount()==2
					
				}
			});
			
		}
		return tableCuotas;
	}
	protected void eliminarCuota() {
		int i = tableCuotas.getSelectedRow();
		Cuota c = cuotas.remove(i);
		dateChooserFechaInicio.setDate(c.getFecha_inicio());
		dateChooserFechaFin.setDate(c.getFecha_fin());
		textFieldDescuento.setText(c.getDescuento()+"");
		btnModificar.setVisible(true);
		btnNuevaCuota.setVisible(false);
    	JOptionPane.showMessageDialog(contentPane, "Cuota eliminada");

	}


	private void añadirFilas() {


		Object[] filas = new Object[3];
		for (int i=0; i<modeloTabla.getRowCount();i++) {
			modeloTabla.removeRow(i);
		}
		for (int i=0; i<cuotas.size();i++) {
			filas[0] = cuotas.get(i).getFecha_inicio();
			filas[1] = cuotas.get(i).getFecha_fin();
			filas[2] = cuotas.get(i).getDescuento();
			modeloTabla.addRow(filas);
		}
	}
	
	protected void crearCuota()
	{
		if(dateChooserFechaInicio.getDate()!=null &&dateChooserFechaFin.getDate()!=null && !textFieldDescuento.getText().isEmpty()) {
			String etiqueta = textFieldDescuento.getText();
			char[]et = etiqueta.toCharArray();
			boolean res = true;
			res = validar_Tarifa(etiqueta);
			
		if(res) {
		int descuento = Integer.parseInt(etiqueta);
		Date date1 = new Date(dateChooserFechaInicio.getDate().getTime());
		Date date2 =  new Date(dateChooserFechaFin.getDate().getTime());

		
		if(!estaYaEnCuota(date1) && !estaYaEnCuota(date1) && !incluyeCuota(date1,date2)) {
			
			
			
			
			Cuota c= new Cuota(date1,date2,descuento); 
			cuotas.add(c);
			añadirFilas();
			scrollPane.setViewportView(getTableCuotas());
			dateChooserFechaInicio.setDate(null);
			dateChooserFechaFin.setDate(null);
			textFieldDescuento.setText("");
	    	JOptionPane.showMessageDialog(contentPane, "Nueva cuota añadida");}
			
			

		}
	    else {
	    	dateChooserFechaInicio.setDate(null);
			dateChooserFechaFin.setDate(null);
			textFieldDescuento.setText("");
	    	JOptionPane.showMessageDialog(contentPane, "Esta fechas ya pertenecen a una cuota");
	    }
		
		
		
		}
		else {
	    	JOptionPane.showMessageDialog(contentPane, "faltan campos por rellenar");
	    	
	    }

		
		
	}
	private boolean incluyeCuota(Date date1, Date date2) {
		for(Cuota c:cuotas) {
			if(c.incluyeLaCuota( date1,date2)) {
				return true;
				}
			}return false;
	}

	private JTextField getTextFieldDescuento() {
		if (textFieldDescuento == null) {
			textFieldDescuento = new JTextField();
			textFieldDescuento.setColumns(10);
		}
		return textFieldDescuento;
	}
	private JButton getBtnNuevaCuota() {
		if (btnNuevaCuota == null) {
			btnNuevaCuota = new JButton("Nueva Cuota");
			btnNuevaCuota.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					crearCuota();
				
				
				}
			});
		}
		
		return btnNuevaCuota;
	}
	private JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar = new JButton("Modificar");
			btnModificar.setVisible(false);
		}
		return btnModificar;
	}
	
	
	private JDateChooser getJDateChooser() {
		if (dateChooserCelebracion == null) {
			dateChooserCelebracion = new JDateChooser();
			dateChooserCelebracion.setDateFormatString("dd-M-yyyy");
			dateChooserCelebracion.setSelectableDateRange(FechaDeHoy, FechaCelebracion);
			dateChooserCelebracion.getDateEditor().addPropertyChangeListener(new java.beans.PropertyChangeListener() {

						@Override
						public void propertyChange(java.beans.PropertyChangeEvent evt) {
							java.util.Date d = dateChooserCelebracion.getDate();
							 System.out.println(""+d);
							FechaCelebracion = d;
							dateChooserFechaInicio.setSelectableDateRange(FechaDeHoy, dateChooserCelebracion.getDate());
						}

					});
		}

		return dateChooserCelebracion;
	}
	
	private JDateChooser getDateChooserFechaInicio() {
		if (dateChooserFechaInicio == null) {
			dateChooserFechaInicio = new JDateChooser();
			dateChooserFechaInicio.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			dateChooserFechaInicio.setDateFormatString("dd-M-yyyy");
			dateChooserFechaInicio.getDateEditor().addPropertyChangeListener(new java.beans.PropertyChangeListener() {

				@Override
				public void propertyChange(java.beans.PropertyChangeEvent evt) {
				
			    if(dateChooserFechaInicio.getDate()!=null) {
			    
			    dateChooserFechaFin.setSelectableDateRange(dateChooserFechaInicio.getDate(), FechaCelebracion);
			    }}
			    	
			});	
		}
		return dateChooserFechaInicio;
	}

	private JDateChooser getDateChooserFechaFin() {
		if (dateChooserFechaFin == null) {
			dateChooserFechaFin = new JDateChooser();
			dateChooserFechaFin.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			dateChooserFechaFin.setDateFormatString("dd-M-yyyy");
				
		}
		return dateChooserFechaFin;
	}
	
	private boolean estaYaEnCuota(Date fecha) {
		
		for(Cuota c:cuotas) {
			if(c.entraEnTarifa(fecha)) {
				return true;
			}
		}return false;
	}
	public void rellenarDatos(Carrera car) {
		textField_NumParticipantes.setText(car.getNumeroParticipantes()+"");
		//textTarifa.setText(car.getPrecio()+"");
		textField_NombreCarrera.setText(car.getNombre());
		textFieldDevolucion.setText(car.getDevolucion()+"");
		cuotas=(ArrayList<Cuota>) car.getTarifas();
		categorias = car.getCategorias();
		añadirFilasCat();
		if(!car.getFecha_celebracion().equals(fechaPorDefecto)) {
			dateChooserCelebracion.setDate(car.getFecha_celebracion());}
		else {
			dateChooserCelebracion.setDate(null);}
			
			button_Crear.setText("GUARDAR");
			carreraAntigua=car;
			añadirFilas();
			scrollPane.setViewportView(getTableCuotas());
			
	}
	
	private JPanel getPanel() throws IOException {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getScrollPane_1(), BorderLayout.CENTER);
		}
		return panel;
	}
	
	private JTable getTable() throws IOException {
		if (table == null) {
			table = new JTable();
			table.setDragEnabled(true);
			table.setFont(new Font("Tahoma", Font.PLAIN, 11));
			// table.setFocusTraversalPolicyProvider(true);
			table.setEnabled(true);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// table.setRowSelectionAllowed(false);
			// table.setFillsViewportHeight(true);
			// table.setFocusCycleRoot(true);
			// table.setRowHeight(150);

			propiedadesTabla();
		}
		return getTabla();
	}
	
	private void propiedadesTabla() throws IOException {
		table.setDefaultRenderer(Object.class, new Tabla());
		String[] titulos = { "Género","Nombre de la categoría", "Edad inicial", "Edad final"};
		tm = new DefaultTableModel(null, titulos);
		table.setModel(tm);
		table.setEnabled(false);

	}

	private JTable getTabla() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int column = table.getColumnModel().getColumnIndexAtX(
						arg0.getX());
				int row = arg0.getY() / table.getRowHeight();

				if (row < table.getRowCount() && row >= 0
						&& column < table.getColumnCount() && column >= 0) {
					Object value = table.getValueAt(row, column);
					if (value instanceof JButton) {
						((JButton) value).doClick();

					}
				}
			}

		});
		return table;
	}
	
	public void añadirFilasCat() {
		
		limpiar();

		
		for(int i = 0; i < categorias.size(); i++)
		{	
			
			String genero = categorias.get(i).getGenero();
			String nombre = categorias.get(i).getNombre();
			int edadi = categorias.get(i).getEdadi();
			String edadf= String.valueOf(categorias.get(i).getEdadf());
			if(Integer.parseInt(edadf)==Integer.MAX_VALUE)
				edadf = "Mayor que " + edadi;
				
			
			Object[] añadir = new Object[]{genero, nombre, edadi, edadf};
			tm.addRow(añadir);
		}	
		

}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			try {
				scrollPane_1.setViewportView(getTable());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return scrollPane_1;
	}
	
	private void limpiar(){
		
		 try {
	            DefaultTableModel modelo=(DefaultTableModel) table.getModel();
	            int filas=table.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);          
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
	   
	private JLabel getLblCategoras() {
		if (lblCategoras == null) {
			lblCategoras = new JLabel("Categor\u00EDas");
			lblCategoras.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lblCategoras;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
}