package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logica.Categoria;
import renders.ModeloUltimaFilaNoEditable;
import renders.Tabla;

import java.awt.Toolkit;

public class ConfigurarCategoriaFinal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblConfigurarCategora;
	private JPanel pnBotones;
	private JButton btVolver;
	private JButton btnAceptar;

	private int intervalo;
	private String nombre;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tm;

	private JFrame marco;

	private ArrayList<Categoria> categorias;
	private boolean seguir;

	// /**
	// * Launch the application.
	// */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ConfigurarCategoriaFinal frame = new ConfigurarCategoriaFinal(
	// 10, 100, 5, "a");
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public ConfigurarCategoriaFinal(JFrame marco, int edadMaxima,int edadMinima, int intervalos, String nombre, boolean seguir, 
			ArrayList<Categoria> categorias)throws IOException {
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfigurarCategoriaFinal.class.getResource("/img/logo.jpeg")));

		this.marco = marco;
		this.intervalo = intervalos;
		this.nombre = nombre;
		this.seguir = seguir;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 551, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getLblConfigurarCategora(), BorderLayout.NORTH);
		contentPane.add(getPnBotones(), BorderLayout.SOUTH);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);

		datos_inicales(edadMaxima, edadMinima);
		this.categorias=categorias;
	}

	private void datos_inicales(int edadMaxima, int edadMinima) {
		table.setValueAt(edadMinima, 0, 1);
		
		if(edadMaxima==Integer.MAX_VALUE)
			table.setValueAt("INDEFINIDO", table.getRowCount() - 1, 2);
		else
			table.setValueAt(edadMaxima, table.getRowCount() - 1, 2);
	}

	private JLabel getLblConfigurarCategora() {
		if (lblConfigurarCategora == null) {
			lblConfigurarCategora = new JLabel("Configurar categoria " + nombre);
			lblConfigurarCategora.setFont(new Font("Tahoma", Font.BOLD, 25));
			lblConfigurarCategora.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblConfigurarCategora;
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnBotones.add(getBtVolver());
			pnBotones.add(getBtnAceptar());
		}
		return pnBotones;
	}

	private JButton getBtVolver() {
		if (btVolver == null) {
			btVolver = new JButton("Volver");
			btVolver.setMnemonic('V');
			btVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btVolver;
	}

	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (!comprobarCamposVacios())
						JOptionPane.showMessageDialog(null,
								"Complete todos los campos");
					else if (!comprobarNumeros())
						JOptionPane.showMessageDialog(null,
								"Los datos introducidos son incorrectos");
					else if (!comprobarCampos())
						JOptionPane
								.showMessageDialog(
										null,
										"Los datos introducidos no concuerdan.\nPor favor, revíselos y pruebe de nuevo.");
					else {

						int selectedOption = JOptionPane
								.showConfirmDialog(
										null,
										"¿Quieres añadir las siguientes categorías en el apartado?\nSi quiere sobreescribir los datos, tendrá que repetir todo el proceso anterior.",
										"Choose", JOptionPane.YES_NO_OPTION);

						if (selectedOption == JOptionPane.YES_OPTION) {
							
							if(seguir)
							{
								cargarCategorias();
								JOptionPane.showMessageDialog(null,
										"Se han guardado sus cambios.\nDeberá completar los datos pertenecientes al género masculino.");
								ConfigurarCategoriaEdades v = new ConfigurarCategoriaEdades(marco, "Masculino", false, categorias);
								v.setVisible(true);
								v.setLocationRelativeTo(null);
								dispose();
							}
							else if(!seguir)
							{
								añadirCategorias();
								JOptionPane.showMessageDialog(null,
										"Se han guardado sus cambios.");
								volverACarrera();
							}
						}

					}
				}
			});
			btnAceptar.setMnemonic('a');
		}
		return btnAceptar;
	}

	private JScrollPane getScrollPane() throws IOException {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setFocusTraversalPolicyProvider(true);
			scrollPane.setBorder(new LineBorder(Color.BLACK, 3));
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
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
		String[] titulos = { "Nombre de la categoría", "Edad inicial",
				"Edad final" };
		tm = new ModeloUltimaFilaNoEditable(null, titulos);
		table.setModel(tm);
		table.setEnabled(true);
		
		
		añadirFilas();

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

	private void añadirFilas() {
		Object[] añadir = null;
		for (int i = 0; i < intervalo; i++) {

			añadir = new Object[] { "" };

			tm.addRow(añadir);
			añadir = null;
		}
	}


	private boolean comprobarNumeros() {

		for (int i = 0; i < table.getRowCount(); i++) {
			try {
				if(table.getValueAt(i, 2).toString().equals("INDEFINIDO"))
					return true;
				Integer.parseInt(table.getValueAt(i, 1).toString());
				Integer.parseInt((String) table.getValueAt(i, 2).toString());

			} catch (NumberFormatException nfe) {
				return false;
			}
		}
		return true;
	}

	private boolean comprobarCamposVacios() {
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < 3; j++) {
				if (table.getValueAt(i, j) == null)
					return false;
				if (table.getValueAt(i, j).toString().length() == 0)
					return false;
			}
		}
		return true;
	}

	protected void volverACarrera() {

		marco.setEnabled(true);

		((CreacionCarreras) marco).setCategorias(categorias);
		((CreacionCarreras) marco).añadirFilasCat();
		this.setVisible(false);
	}

	private void cargarCategorias() {
		categorias = new ArrayList<Categoria>();
		Categoria categoria;
		for (int i = 0; i < table.getRowCount(); i++) {
			String edadMinima = table.getValueAt(i, 1).toString();
			String edadMaxima = (String) table.getValueAt(i, 2).toString();
			if(edadMaxima.equals("INDEFINIDO"))
				edadMaxima = String.valueOf(Integer.MAX_VALUE);
			categoria = new Categoria((String) table.getValueAt(i, 0),
					Integer.parseInt(edadMinima), Integer.parseInt(edadMaxima),
					nombre);
			categorias.add(categoria);
		}
	}
	
	/**
	 * Mete las categorias en la carrera.
	 */
	private void añadirCategorias() {
		if(categorias==null)
			categorias = new ArrayList<Categoria>();
		
		Categoria categoria;
		for (int i = 0; i < table.getRowCount(); i++) {
			String edadMinima = table.getValueAt(i, 1).toString();
			String edadMaxima = (String) table.getValueAt(i, 2).toString();

			if(edadMaxima.equals("INDEFINIDO"))
				edadMaxima = String.valueOf(Integer.MAX_VALUE);
			
			categoria = new Categoria((String) table.getValueAt(i, 0),
					Integer.parseInt(edadMinima), Integer.parseInt(edadMaxima),
					nombre);
			categorias.add(categoria);
		}
	}
	
	

	private boolean comprobarCampos() {
		for (int i = 1; i < table.getRowCount(); i++) {
			
			if(table.getValueAt(i, 2).equals("INDEFINIDO"))
			{
				int anterior = Integer.parseInt(table
						.getValueAt(i - 1, 2).toString());
				int minimaActual = Integer.parseInt(table.getValueAt(i, 1)
						.toString());

				if (anterior >= minimaActual)
					return false;

				else
					return true;
			}
			
			int edadMaximaAnterior = Integer.parseInt(table
					.getValueAt(i - 1, 2).toString());
			int edadMinimaActual = Integer.parseInt(table.getValueAt(i, 1)
					.toString());
			int edadMaximaActual = Integer.parseInt(table.getValueAt(i, 2)
					.toString());

			if (edadMaximaAnterior >= edadMinimaActual)
				return false;

			else if (edadMinimaActual >= edadMaximaActual)
				return false;
		}
		return true;
	}

}