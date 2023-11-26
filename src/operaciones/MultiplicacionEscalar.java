/*
 * Jonathan Eduardo Ibarra Martinez
 * 
 * Practica 16, 17: Calculadora de matrices.
 * 
 * Versión: 11/06/2022
 */
package operaciones;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import excepcion.MatrizExcepcion;
import gui.VentanaPrincipal;
import libreria.Matriz;
import libreria.MiFocusTraversalPolicy;
import libreria.Racional;

public class MultiplicacionEscalar extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propiedades que se visualizan en pantalla.
	private JLabel etiqueta;
	private JTextField cajaDeTexto;
	private JComboBox<String> comboMatriz;
	private JButton botonAceptar;
	private JButton botonCancelar;
	// Arreglo de abecedario para nombrar las matrices.
	private String[] abecedario = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	VentanaPrincipal principal;

	public MultiplicacionEscalar(VentanaPrincipal principal) {
		this.principal = principal;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panelAux = new JPanel();

		cajaDeTexto = new JTextField();
		cajaDeTexto.setPreferredSize(new Dimension(100, 25));
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(cajaDeTexto);
		panel1.add(panelAux);

		etiqueta = new JLabel("x");
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(etiqueta);
		panel1.add(panelAux);

		comboMatriz = new JComboBox<String>(abecedario);
		comboMatriz.setToolTipText("Seleccione el nombre de la matriz que desea multiplicar.");
		comboMatriz.setSelectedIndex(0);
		comboMatriz.setPreferredSize(new Dimension(100, 25));
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(comboMatriz);
		panel1.add(panelAux);

		this.add(panel1, BorderLayout.NORTH);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setToolTipText("Seleccione aceptar si desea multiplicar la matriz y el escalar.");
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(botonAceptar);
		panel2.add(panelAux);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.setToolTipText("Seleccione cancelar si desea salir de la operación.");
		botonCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		botonCancelar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(botonCancelar);
		panel2.add(panelAux);

		this.add(panel2, BorderLayout.SOUTH);

		this.setTitle("Operación multiplicación escalar");
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

	// Metodo que consiste en validar los posibles escenarios posibles antes de multiplicar
	// la matriz seleccionada por el escalar.
	private void metodoAceptar() throws MatrizExcepcion {
		// TODO Auto-generated method stub
		if (principal.arregloMatriz[comboMatriz.getSelectedIndex()] == null) {
			JOptionPane.showMessageDialog(this,
					"No se pudo realizar la operación porque la matriz " + abecedario[comboMatriz.getSelectedIndex()]
							+ " esta vacía." + "\nFavor de usar una matriz con datos almacenados o capture la matriz "
							+ abecedario[comboMatriz.getSelectedIndex()] + ".",
					"Matriz sin datos", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				Racional r;
				String campo = cajaDeTexto.getText();
				if (campo.isEmpty()) {
					throw new MatrizExcepcion(MatrizExcepcion.ESCALAR_OBLIGATORIO);
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

				Matriz temp = null;
				temp = Matriz.multiplicacionEscalar(principal.arregloMatriz[comboMatriz.getSelectedIndex()], r);
				new Resultado(principal, temp);
				this.dispose();

			} catch (MatrizExcepcion err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error al capturar el escalar.",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Método que cierra la pantalla.
	private void metodoCancelar() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	/*
	 * 
	 * Método para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(cajaDeTexto);
		componentes.add(comboMatriz);
		componentes.add(botonAceptar);
		componentes.add(botonCancelar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}
}
