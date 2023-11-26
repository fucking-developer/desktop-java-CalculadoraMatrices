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

import excepcion.MatrizExcepcion;
import gui.VentanaPrincipal;
import libreria.Matriz;
import libreria.MiFocusTraversalPolicy;
import libreria.Racional;

public class MatrizX extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propiedades que se visualizan en pantalla.
	private JButton botonAceptar;
	private JButton botonCancelar;
	// Arreglo de abecedario para nombrar las matrices.
	private String[] abecedario = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private JTextField[][] cajasDeTexto;
	private VentanaPrincipal principal;
	private Matriz arregloMatriz;
	private int posicion;
	private boolean bandera;

	public MatrizX(VentanaPrincipal principal, Matriz arregloMatriz, int posicion, boolean bandera) {
		this.principal = principal;
		this.arregloMatriz = arregloMatriz;
		this.posicion = posicion;
		this.bandera = bandera;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panelAux = new JPanel();

		cajasDeTexto = new JTextField[arregloMatriz.getRenglones()][arregloMatriz.getColumnas()];
		panel1.setLayout(new GridLayout(arregloMatriz.getRenglones(), arregloMatriz.getColumnas()));
		if (bandera) {
			for (int i = 0; i < arregloMatriz.getRenglones(); i++) {
				for (int j = 0; j < arregloMatriz.getColumnas(); j++) {
					cajasDeTexto[i][j] = new JTextField();
					cajasDeTexto[i][j].setPreferredSize(new Dimension(80, 30));
					cajasDeTexto[i][j].setToolTipText("Ingrese el valor del elemento de la matriz");
					panelAux = new JPanel();
					panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
					panelAux.add(cajasDeTexto[i][j]);
					panel1.add(panelAux);
				}
			}
		} else {
			for (int i = 0; i < arregloMatriz.getRenglones(); i++) {
				for (int j = 0; j < arregloMatriz.getColumnas(); j++) {
					cajasDeTexto[i][j] = new JTextField();
					cajasDeTexto[i][j].setPreferredSize(new Dimension(80, 30));
					cajasDeTexto[i][j].setText(principal.arregloMatriz[posicion].getDato(i, j).toString());
					cajasDeTexto[i][j].setToolTipText("Ingrese el valor del elemento de la matriz");
					panelAux = new JPanel();
					panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
					panelAux.add(cajasDeTexto[i][j]);
					panel1.add(panelAux);
				}
			}
		}

		this.add(panel1, BorderLayout.CENTER);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setToolTipText("Seleccione aceptar si desea guardar los datos ingresados.");
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(botonAceptar);
		panel2.add(panelAux);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.setToolTipText("Seleccione cancelar si desea revertir los datos ingresados.");
		botonCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		botonCancelar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(botonCancelar);
		panel2.add(panelAux);

		this.add(panel2, BorderLayout.SOUTH);

		this.setTitle("Matriz " + abecedario[posicion]);
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
			botonAceptar();
		} else if (e.getSource().equals(botonCancelar)) {
			botonCancelar();
		}
	}

	/*
	 * Método que consiste en guardar una matriz nueva o existente, también consiste
	 * en validar los valores ingresados de la matriz.
	 */
	private void botonAceptar() throws MatrizExcepcion {
		// TODO Auto-generated method stub
		Racional r = null;
		String campo = null;
		if (bandera == true) {
			try {
				principal.getArregloMatriz()[posicion] = new Matriz(arregloMatriz.getRenglones(),
						arregloMatriz.getColumnas());
				for (int i = 0; i < arregloMatriz.getRenglones(); i++) {
					for (int j = 0; j < arregloMatriz.getColumnas(); j++) {

						campo = cajasDeTexto[i][j].getText();
						if (campo.isEmpty()) {
							throw new MatrizExcepcion(MatrizExcepcion.MATRIZ_OBLIGATORIA);
						} else {
							try {
								r = new Racional(Integer.parseInt(campo));
							} catch (NumberFormatException e1) {
								try {
									r = new Racional(Double.parseDouble(campo));
								} catch (IllegalArgumentException e2) {
									r = new Racional(campo);
								}
							}
						}
						principal.arregloMatriz[posicion].setDato(i, j, r);
					}
				}
				JOptionPane.showMessageDialog(this, "La matriz " + abecedario[posicion] + " fue creada con éxito",
						"Matriz creada", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			} catch (MatrizExcepcion err) {
				principal.getArregloMatriz()[posicion] = null;
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error al capturar la matriz.",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			try {
				for (int i = 0; i < arregloMatriz.getRenglones(); i++) {
					for (int j = 0; j < arregloMatriz.getColumnas(); j++) {
						campo = cajasDeTexto[i][j].getText();
						if (campo.isEmpty()) {
							// r = new Racional();
							throw new MatrizExcepcion(MatrizExcepcion.MATRIZ_OBLIGATORIA);
						} else {
							try {
								r = new Racional(Integer.parseInt(campo));
							} catch (NumberFormatException e1) {
								try {
									r = new Racional(Double.parseDouble(campo));
								} catch (IllegalArgumentException e2) {
									r = new Racional(campo);
								}
							}
						}
						principal.arregloMatriz[posicion].setDato(i, j, r);
					}
				}
				JOptionPane.showMessageDialog(this,
						"La matriz  " + abecedario[posicion] + " fue guardada correctamente", "Matriz guardada",
						JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			} catch (MatrizExcepcion err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error al capturar la matriz.",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	// Metodo que consiste en cerrar el dialogo.
	private void botonCancelar() {
		this.dispose();
	}

	/*
	 * 
	 * Método para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(botonAceptar);
		componentes.add(botonCancelar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}
}
