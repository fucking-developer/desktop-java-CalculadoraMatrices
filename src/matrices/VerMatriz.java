/*
 * 
 * Jonathan Eduardo Ibarra Martínez
 * 
 * Practica 16, 17: Calculadora de matrices.
 * 
 * Versión: 11/06/2022
 * 
 */

package matrices;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import gui.VentanaPrincipal;
import libreria.MiFocusTraversalPolicy;

public class VerMatriz extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Propiedades que se visualizan en pantalla.
	private JLabel etiqueta;
	private JComboBox<String> comboMatriz;
	private JButton botonAceptar;
	private JTextField[][] cajasDeTexto;
	// Arreglo de abecedario para nombrar las matrices.
	private String[] abecedario = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };	
	private JPanel panelCentral;
	private VentanaPrincipal principal;
	public VerMatriz(VentanaPrincipal principal) {
		this.principal = principal;
		JPanel panel1 = new JPanel();
		panelCentral = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panelAux = new JPanel();

		etiqueta = new JLabel("Matriz");
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(etiqueta);
		panel1.add(panelAux);

		comboMatriz = new JComboBox<String>(abecedario);
		comboMatriz.setToolTipText("Seleccione la matriz que desea consultar.");
		comboMatriz.setSelectedIndex(0);
		comboMatriz.setPreferredSize(new Dimension(100, 25));
		comboMatriz.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(comboMatriz);
		panel1.add(panelAux);

		this.add(panel1, BorderLayout.NORTH);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setToolTipText("Seleccione aceptar si desea cerrar la pantalla.");
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(botonAceptar);
		panel3.add(panelAux);

		
		this.add(panel3, BorderLayout.SOUTH);

		this.setTitle("Ver matriz");
		this.pack();
		this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/calculadora.png")).getImage());
		this.setLocationRelativeTo(null);
		metodoCombo();
		establecerPoliticaFoco();
		this.setVisible(true);
	}

	/*
	 * Método actionPerformed que sirve para validar los eventos de actionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(comboMatriz)) {
			metodoCombo();
		}else if (e.getSource().equals(botonAceptar)) {
			metodoAceptar();
		}
	}

	/*
	 * Método que consiste en cerrar la pantalla. 
	 */
	private void metodoAceptar() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	/*
	 * Método que consiste en consultar la matriz guardada utilizando el combo del abecedario.
	 */
	private void metodoCombo() {
		// TODO Auto-generated method stub
		this.remove(panelCentral);
		panelCentral = new JPanel(); 
		if (principal.arregloMatriz[comboMatriz.getSelectedIndex()] == null) {
		} else { 
			panelCentral.setLayout(
					new GridLayout(principal.arregloMatriz[comboMatriz.getSelectedIndex()].getRenglones(),
							principal.arregloMatriz[comboMatriz.getSelectedIndex()].getColumnas()));
			cajasDeTexto = new JTextField[principal.arregloMatriz[comboMatriz.getSelectedIndex()]
					.getRenglones()][principal.arregloMatriz[comboMatriz.getSelectedIndex()].getColumnas()];
			for (int i = 0; i < cajasDeTexto.length; i++) {
				for (int j = 0; j < cajasDeTexto[i].length; j++) {
					JPanel panelAux = new JPanel(); 
					cajasDeTexto[i][j] = new JTextField();
					cajasDeTexto[i][j].setText(
							//principal.arregloMatriz[comboMatriz.getSelectedIndex()].getDato(i, j).toString());
							principal.getArregloMatriz()[comboMatriz.getSelectedIndex()].getDato(i, j).toString());
							cajasDeTexto[i][j].setPreferredSize(new Dimension(80, 30));
					cajasDeTexto[i][j].setEditable(false); 
					cajasDeTexto[i][j].setToolTipText("Valor almacenado de la matriz.");
					panelAux.setLayout(new FlowLayout(FlowLayout.CENTER)); 
					panelAux.add(cajasDeTexto[i][j]); 
					panelCentral.add(panelAux); 
				}
			}
		}
		this.add(panelCentral, BorderLayout.CENTER); 
		pack(); 
		this.setLocationRelativeTo(principal); 
	}

	/*
	 * 
	 * Método para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(comboMatriz);
		componentes.add(botonAceptar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}
}
