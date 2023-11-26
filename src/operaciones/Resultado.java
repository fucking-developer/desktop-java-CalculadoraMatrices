/*
 * Jonathan Eduardo Ibarra Martínez
 * 
 * Practica 16, 17: Calculadora de matrices.
 * 
 * Versión: 11/06/2022
 */
package operaciones;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import gui.VentanaPrincipal;
import libreria.Matriz;
import libreria.MiFocusTraversalPolicy;

public class Resultado extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propiedades que se visualizan en pantalla.
	private JLabel etiqueta;
	private JComboBox<String> comboGuardar;
	private JButton botonAceptar;
	private JButton botonCancelar;
	private JTextField[][] cajasDeTexto;
	// Arreglo de abecedario para nombrar las matrices.
	private String[] abecedario = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z", "No guardar" };
	private Matriz temp;
	private VentanaPrincipal principal;

	public Resultado(VentanaPrincipal principal, Matriz temp) {
		this.principal = principal;
		this.temp = temp;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panelAux = new JPanel();

		cajasDeTexto = new JTextField[temp.getRenglones()][temp.getColumnas()];
		panel1.setLayout(new GridLayout(temp.getRenglones(), temp.getColumnas()));

		for (int i = 0; i < cajasDeTexto.length; i++) {
			for (int j = 0; j < cajasDeTexto[i].length; j++) {
				cajasDeTexto[i][j] = new JTextField();
				cajasDeTexto[i][j].setPreferredSize(new Dimension(80, 30));
				cajasDeTexto[i][j].setText(temp.getDato(i, j).toString());
				cajasDeTexto[i][j].setEditable(false);
				cajasDeTexto[i][j].setToolTipText("Resultado de la operación.");
				panelAux = new JPanel();
				panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelAux.add(cajasDeTexto[i][j]);
				panel1.add(panelAux);
			}
		}

		this.add(panel1, BorderLayout.CENTER);

		etiqueta = new JLabel("Matriz");
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelAux.add(etiqueta);
		panel2.add(panelAux);

		comboGuardar = new JComboBox<String>(abecedario);
		comboGuardar.setToolTipText("Seleccione donde desea guardar el resultado de la operación.");
		comboGuardar.setSelectedIndex(26);
		comboGuardar.setPreferredSize(new Dimension(100, 25));
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelAux.add(comboGuardar);
		panel2.add(panelAux);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setToolTipText("Seleccione aceptar si desea terminar la operación.");
		botonAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/aceptar.png")));
		botonAceptar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelAux.add(botonAceptar);
		panel2.add(panelAux);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.setToolTipText("Seleccione cancelar si desea cerrar la pantalla.");
		botonCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/cancelar.png")));
		botonCancelar.addActionListener(this);
		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelAux.add(botonCancelar);
		panel2.add(panelAux);

		this.add(panel2, BorderLayout.SOUTH);

		this.setTitle("Resultado");
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

	// Método que consiste en cerrar la pantalla.
	private void metodoCancelar() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	// Método que consiste en validar si el usuario decidio guardar el resultado, y
	// validar la opción de remplazar una matriz en caso de elegir un matriz que
	// exista en el arreglo.
	private void metodoAceptar() {
		// TODO Auto-generated method stub
		if (comboGuardar.getSelectedIndex() == 26) {
			this.dispose();
		} else {
			if (principal.arregloMatriz[comboGuardar.getSelectedIndex()] == null) {
				principal.arregloMatriz[comboGuardar.getSelectedIndex()] = new Matriz(temp);
			} else {
				int opcion = JOptionPane.showConfirmDialog(this,
						"La matriz ya existe. ¿Desea reemplazar matriz \n en la matriz anterior?", "Reemplazar matriz",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					principal.arregloMatriz[comboGuardar.getSelectedIndex()] = new Matriz(temp);
				} else {
					return;
				}
			}
			JOptionPane.showMessageDialog(this,
					"El resultado se guardo \n" + "correctamente en la matriz "
							+ abecedario[comboGuardar.getSelectedIndex()],
					"Resultado guardado", JOptionPane.INFORMATION_MESSAGE);
		}
		this.dispose();
	}

	/*
	 * 
	 * Método para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(comboGuardar);
		componentes.add(botonAceptar);
		componentes.add(botonCancelar);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}
}