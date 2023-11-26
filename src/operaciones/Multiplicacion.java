/*
 * Jonathan Eduardo Ibarra Martinez
 * 
 * Practica 16, 17: Calculadora de matrices.
 * 
 * Versión: 11/06/2022
 */
package operaciones;

import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

import gui.VentanaPrincipal;
import libreria.Matriz;
import libreria.MiFocusTraversalPolicy;

import java.awt.*;

public class Multiplicacion extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propiedades que se visualizan en pantalla.
	private JLabel etiqueta;
	private JComboBox<String> comboMatriz1;
	private JComboBox<String> comboMatriz2;
	private JButton botonAceptar;
	private JButton botonCancelar;
	// Arreglo de abecedario para nombrar las matrices.
	private String[] abecedario = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	VentanaPrincipal principal;

	public Multiplicacion(VentanaPrincipal principal) {
		this.principal = principal;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panelAux = new JPanel();

		comboMatriz1 = new JComboBox<String>(abecedario);
		comboMatriz1.setToolTipText("Seleccione el nombre de la matriz que desea multiplicar.");
		comboMatriz1.setSelectedIndex(0);
		comboMatriz1.setPreferredSize(new Dimension(100, 25));
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(comboMatriz1);
		panel1.add(panelAux);

		etiqueta = new JLabel("x");
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(etiqueta);
		panel1.add(panelAux);

		comboMatriz2 = new JComboBox<String>(abecedario);
		comboMatriz2.setToolTipText("Seleccione el nombre de la matriz que desea multiplicar.");
		comboMatriz2.setSelectedIndex(0);
		comboMatriz2.setPreferredSize(new Dimension(100, 25));
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(comboMatriz2);
		panel1.add(panelAux);

		this.add(panel1, BorderLayout.NORTH);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setToolTipText("Seleccione aceptar si desea multiplicar las matrices");
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(botonAceptar);
		panel2.add(panelAux);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.setToolTipText("Seleccione cancelar si desea cerrar la pantalla.");
		botonCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		botonCancelar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(botonCancelar);
		panel2.add(panelAux);

		this.add(panel2, BorderLayout.SOUTH);

		this.setTitle("Operación multiplicación");
		this.pack();
		this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/calculadora.png")).getImage());
		this.setLocationRelativeTo(null);
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
			metodoAceptar();
		} else if (e.getSource().equals(botonCancelar)) {
			metodoCancelar();
		}
	}

	/*
	 * Método que consiste en cerrar la pantalla.
	 */
	private void metodoCancelar() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	/*
	 * Método que consiste en validar los escenarios posiles antes de realizar la
	 * multiplicacion de las matrices.
	 */
	private void metodoAceptar() {
		// TODO Auto-generated method stub
		if (principal.arregloMatriz[comboMatriz1.getSelectedIndex()] == null
				|| principal.arregloMatriz[comboMatriz2.getSelectedIndex()] == null) {
			JOptionPane.showMessageDialog(this,
					"No se pudo realizar la operación porque alguna de las matrices esta vacía."
							+ "\nFavor de usar matrices con datos almacenados o capture la matriz.",
					"Matriz sin datos", JOptionPane.ERROR_MESSAGE);
		} else {
			Matriz temp = null;
			temp = Matriz.multiplicacion(principal.arregloMatriz[comboMatriz1.getSelectedIndex()],
					principal.arregloMatriz[comboMatriz2.getSelectedIndex()]);
			if (temp == null) {
				JOptionPane.showMessageDialog(this,
						"Ocurrió un error al realizar la multiplicación.\nFavor de usar solo matrices de tamaño similar",
						"Error al multiplicar", JOptionPane.ERROR_MESSAGE);
			} else {
				this.dispose();
				new Resultado(principal, temp);
			}
		}
	}

	
	/*
	 * 
	 * Método para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(comboMatriz1);
		componentes.add(comboMatriz2);
		componentes.add(botonAceptar);
		componentes.add(botonCancelar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}
}
