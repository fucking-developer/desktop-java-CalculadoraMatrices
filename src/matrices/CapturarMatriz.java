/*
 * Jonathan Eduardo Ibarra Martínez
 * 
 * Practica 16, 17: Calculadora de matrices.
 * 
 * Versión: 11/06/2022
 */
package matrices;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import gui.VentanaPrincipal;
import libreria.Matriz;
import libreria.MiFocusTraversalPolicy;

public class CapturarMatriz extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propiedades que se visualizan en pantalla.
	private JLabel etiquetas;
	private JComboBox<String> comboMatriz;
	private JSpinner spinnerFilas;
	private JSpinner spinnerColumnas;
	private JButton botonAceptar;
	private JButton botonCancelar;
	// Arreglo de abecedario para nombrar las matrices.
	private String[] abecedario = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private boolean bandera = false;
	private VentanaPrincipal principal;

	public CapturarMatriz(VentanaPrincipal principal) {
		this.principal = principal;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panelAux = new JPanel();

		etiquetas = new JLabel("Matriz");
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(etiquetas);
		panel1.add(panelAux);

		comboMatriz = new JComboBox<String>(abecedario);
		comboMatriz.setToolTipText("Seleccione el nombre de la matriz.");
		comboMatriz.setSelectedIndex(0);
		comboMatriz.setPreferredSize(new Dimension(100, 25));
		comboMatriz.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(comboMatriz);
		panel1.add(panelAux);

		this.add(panel1, BorderLayout.NORTH);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));

		etiquetas = new JLabel("Filas");
		panelAux = new JPanel();
		panelAux.add(etiquetas);
		panel2.add(panelAux);

		spinnerFilas = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
		spinnerFilas.setPreferredSize(new Dimension(50, 25));
		spinnerFilas.setToolTipText("Seleccione o ingrese el número de filas de la matriz.");
		panelAux = new JPanel();
		panelAux.add(spinnerFilas);
		panel2.add(panelAux);

		etiquetas = new JLabel("Columnas");
		panelAux = new JPanel();
		panelAux.add(etiquetas);
		panel2.add(panelAux);

		spinnerColumnas = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
		spinnerColumnas.setPreferredSize(new Dimension(50, 25));
		spinnerColumnas.setToolTipText("Seleccione o ingrese el número de columnas de la matriz.");
		panelAux = new JPanel();
		panelAux.add(spinnerColumnas);
		panel2.add(panelAux);

		this.add(panel2, BorderLayout.CENTER);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.setToolTipText("Seleccione aceptar para poder ingresar los valores.");
		botonAceptar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(botonAceptar);
		panel3.add(panelAux);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.setToolTipText("Seleccione cancelar si desea cerrar la ventana.");
		botonCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		botonCancelar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(botonCancelar);
		panel3.add(panelAux);

		this.add(panel3, BorderLayout.SOUTH);

		this.setTitle("Capturar matriz");
		this.pack();
		this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/calculadora.png")).getImage());
		this.setLocationRelativeTo(null);
		metodoComboMatriz();
		establecerPoliticaFoco();
		this.setVisible(true);
	}

	/*
	 * Método actionPerformed que sirve para validar los eventos de actionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(botonAceptar)) {
			metodoBotonAceptar();
		} else if (e.getSource().equals(botonCancelar)) {
			metodoSalir();
		} else if (e.getSource().equals(comboMatriz)) {
			metodoComboMatriz();
		}
	}

	/*
	 * Metodo para validar cuando se selecciona un elemento del combo.
	 */
	private void metodoComboMatriz() {
		// TODO Auto-generated method stub
		if (principal.arregloMatriz[comboMatriz.getSelectedIndex()] == null) {
			spinnerFilas.setValue(2);
			spinnerColumnas.setValue(2);
		} else {
			spinnerFilas.setValue(principal.arregloMatriz[comboMatriz.getSelectedIndex()].getRenglones());
			spinnerColumnas.setValue(principal.arregloMatriz[comboMatriz.getSelectedIndex()].getColumnas());
		}
	}

	/*
	 * Método para cerrar el dialogo.
	 */
	private void metodoSalir() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	/*
	 * Método con la funcionalidad de validar si se debe usar una matriz nueva, usar
	 * una matriz modificada o usar una matriz existente que no ha sido modificada.
	 */
	private void metodoBotonAceptar() {
		// TODO Auto-generated method stub
		bandera = false;
		int filas = (int) spinnerFilas.getValue();
		int columnas = (int) spinnerColumnas.getValue();
		Matriz aux = null;
		if (principal.getArregloMatriz()[comboMatriz.getSelectedIndex()] == null) {
			aux = new Matriz(filas, columnas);
			bandera = true;
		} else if (filas == principal.getArregloMatriz()[comboMatriz.getSelectedIndex()].getRenglones()
				&& columnas == principal.getArregloMatriz()[comboMatriz.getSelectedIndex()].getColumnas()) {
			aux = new Matriz(principal.getArregloMatriz()[comboMatriz.getSelectedIndex()].getRenglones(),
					principal.getArregloMatriz()[comboMatriz.getSelectedIndex()].getColumnas());
			bandera = false;
		} else {
			int opcion = JOptionPane.showConfirmDialog(this,
					"La matriz ya existe. ¿Desea crear una nueva matriz perdiendo la matriz existente?", "Nueva matriz",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (opcion == JOptionPane.YES_OPTION) {
				aux = new Matriz(filas, columnas);
				bandera = true;
			} else {
				return;
			}
		}
		new MatrizX(principal, aux, comboMatriz.getSelectedIndex(), bandera);
		this.dispose();
	}
	
	/*
	 * 
	 * Método para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(comboMatriz);
		componentes.add(botonAceptar);
		componentes.add(botonCancelar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}

}
