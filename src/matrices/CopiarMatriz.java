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

public class CopiarMatriz extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propiedades que se visualizan en pantalla.
	private JLabel etiqueta;
	private JComboBox<String> comboOrigen;
	private JComboBox<String> comboDestino;
	private JButton botonAceptar;
	private JButton botonCancelar;
	// Arreglo de abecedario para nombrar las matrices.
	private String[] abecedario = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private VentanaPrincipal principal;
	public CopiarMatriz(VentanaPrincipal principal) {
		this.principal = principal;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panelAux = new JPanel();

		comboOrigen = new JComboBox<String>(abecedario);
		comboOrigen.setSelectedIndex(0);
		comboOrigen.setPreferredSize(new Dimension(100, 25));
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(comboOrigen);
		panel1.add(panelAux);

		etiqueta = new JLabel("=>");
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(etiqueta);
		panel1.add(panelAux);

		comboDestino = new JComboBox<String>(abecedario);
		comboDestino.setToolTipText("Seleccione el nombre de la matriz donde desea crear una copia.");
		comboDestino.setPreferredSize(new Dimension(100, 25));
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(comboDestino);
		panel1.add(panelAux);

		this.add(panel1, BorderLayout.NORTH);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setToolTipText("Seleccione aceptar si desea guardar los cambios.");
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelAux.add(botonAceptar);
		panel2.add(panelAux);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.setToolTipText("Seleccione cancelar si desea revertir los cambios.");
		botonCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		botonCancelar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelAux.add(botonCancelar);
		panel2.add(panelAux);

		this.add(panel2, BorderLayout.SOUTH);

		this.setTitle("Copiar matriz");
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
			metodoBotonAceptar();
		} else if (e.getSource().equals(botonCancelar)) {
			metodoBotonCancelar();
		}
	}

	/*
	 * Método para cancelar y cerrar el dialogo.
	 */
	private void metodoBotonCancelar() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	/*
	 * Método que consiste en validar los diferentes escenarios, de tal manera
	 * guardar una copia de la matriz.
	 */
	private void metodoBotonAceptar() {
		// TODO Auto-generated method stub
		if (principal.arregloMatriz[comboOrigen.getSelectedIndex()] == null) {
			JOptionPane.showMessageDialog(this, "No hay datos almacenados en la matriz "
					+ abecedario[comboOrigen.getSelectedIndex()] + " seleccionada", "Error al copiar la matriz",
					JOptionPane.ERROR_MESSAGE);
		} else if (principal.arregloMatriz[comboOrigen.getSelectedIndex()] == principal.arregloMatriz[comboDestino
				.getSelectedIndex()]) {
			JOptionPane.showMessageDialog(this, "No se puede copiar datos en la misma matriz",
					"Error al copiar la matriz", JOptionPane.ERROR_MESSAGE);
		} else if (principal.arregloMatriz[comboOrigen.getSelectedIndex()] != null
				&& principal.arregloMatriz[comboDestino.getSelectedIndex()] == null) {
			principal.arregloMatriz[comboDestino.getSelectedIndex()] = new Matriz(
					principal.arregloMatriz[comboOrigen.getSelectedIndex()]);
			JOptionPane.showMessageDialog(this,
					"Se copió la matriz " + abecedario[comboOrigen.getSelectedIndex()] + " en la matriz "
							+ abecedario[comboDestino.getSelectedIndex()],
					"Matriz copiada", JOptionPane.INFORMATION_MESSAGE);
		} else if (principal.arregloMatriz[comboOrigen.getSelectedIndex()] != null
				&& principal.arregloMatriz[comboDestino.getSelectedIndex()] != null) {
			int opcion = JOptionPane.showConfirmDialog(this,
					"La matriz ya existe. ¿Desea reemplazar la matriz \n perdiendo la matriz anterior?",
					"Crear o conservar matriz", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (opcion == JOptionPane.YES_OPTION) {
				principal.arregloMatriz[comboDestino.getSelectedIndex()] = new Matriz(
						principal.arregloMatriz[comboOrigen.getSelectedIndex()]);
				JOptionPane.showMessageDialog(this,
						"Se copió la matriz " + abecedario[comboOrigen.getSelectedIndex()] + " en la matriz "
								+ abecedario[comboDestino.getSelectedIndex()],
						"Matriz copiada", JOptionPane.INFORMATION_MESSAGE);
			} else {
				return;
			}
		}
		this.dispose();
	}

	/*
	 * 
	 * Método para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(comboOrigen);
		componentes.add(comboDestino);
		componentes.add(botonAceptar);
		componentes.add(botonCancelar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}
}
