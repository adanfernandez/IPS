package igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

import javax.swing.JTextField;

import java.awt.Insets;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private JPanel panel_Registro;
	private JPanel panel_Titulo;
	private JLabel lblRegistro;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private VentanaRegistroCorredores ventRegCorr;
	private CreacionCarreras ventCreacion;


	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel_Registro(), BorderLayout.CENTER);
		ventRegCorr = new VentanaRegistroCorredores();
	}


	private JPanel getPanel_Registro() {
		if (panel_Registro == null) {
			panel_Registro = new JPanel();
			panel_Registro.setLayout(new BorderLayout(0, 0));
			panel_Registro.add(getPanel_Titulo(), BorderLayout.NORTH);
			panel_Registro.add(getTabbedPane(), BorderLayout.CENTER);
			
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
	private JLabel getLblRegistro() {
		if (lblRegistro == null) {
			lblRegistro = new JLabel("Gestor de Carreras");
			lblRegistro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lblRegistro;
	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("Registro Corredores", null, getVentanaRegistroCorredores().getContentPane(), null);
			tabbedPane.addTab("Nueva Carrera", null, getCreacionCarreras().getContentPane(), null);
		}
		return tabbedPane;
	}
	private CreacionCarreras getCreacionCarreras() {
		if (ventCreacion == null) {
	
			try {
				ventCreacion = new CreacionCarreras();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ventCreacion;
	}
	private VentanaRegistroCorredores getVentanaRegistroCorredores() {
		if (ventRegCorr == null) {
			ventRegCorr = new VentanaRegistroCorredores();
		}
		return ventRegCorr;
	}
}
