/*
 * Jonathan Eduardo Ibarra Martinez
 * 
 * Práctica 16, 17: Calculadora de matrices.
 * 
 * Versión: 11/06/2022
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import libreria.Matriz;
import libreria.MiFocusTraversalPolicy;
import libreria.Racional;
import matrices.*;
import operaciones.*;

public class VentanaPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	// Opciones de menu e items que se muestran en la ventana principal
	private JMenu archivoMenu;
	private JMenuItem abrir;
	private JMenuItem guardar;
	private JMenuItem salirMenu;
	private JMenu matricesMenu;
	private JMenuItem capturarMatriz;
	private JMenuItem copiarMatriz;
	private JMenuItem verMatriz;
	private JMenu formato;
	private JRadioButtonMenuItem fraccion;
	private JRadioButtonMenuItem decimal;
	private JMenu operacionesMenu;
	private JMenuItem operacionesSuma;
	private JMenuItem operacionesResta;
	private JMenuItem operacionesMultiplicacionEscalar;
	private JMenuItem operacionesMultiplicacion;
	private JMenu ayudaMenu;
	private JMenuItem acercaDeMenu;
	private JMenuBar barraMenu;
	// Arreglo de matrices.
	public Matriz[] arregloMatriz = new Matriz[26];

	// Get para obtener la matriz guardada en el arreglo.
	public Matriz[] getArregloMatriz() {
		return arregloMatriz;
	}

	public VentanaPrincipal() {
		super("Calculadora con matrices");
		archivoMenu = new JMenu("Archivo");
		archivoMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/archivo.png")));
		archivoMenu.setMnemonic(KeyEvent.VK_R);
		archivoMenu.setToolTipText("Contiene la opción para cerrar el sistema.");

		salirMenu = new JMenuItem("Salir");
		salirMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		salirMenu.setMnemonic(KeyEvent.VK_I);
		salirMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0));
		salirMenu.addActionListener(this);
		salirMenu.setToolTipText("Cierra el sistema.");

		abrir = new JMenuItem("Abrir");
		abrir.setToolTipText("Abre los datos guardados.");
		abrir.setIcon(new ImageIcon(getClass().getResource("/imagenes/abrir.png")));
		abrir.addActionListener(this);

		guardar = new JMenuItem("Guardar");
		guardar.setToolTipText("Guarda los datos.");
		guardar.setIcon(new ImageIcon(getClass().getResource("/imagenes/guardar.png")));
		guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		guardar.addActionListener(this);

		archivoMenu.add(abrir);
		archivoMenu.add(guardar);
		archivoMenu.add(salirMenu);

		matricesMenu = new JMenu("Matrices");
		matricesMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/matrices.png")));
		matricesMenu.setMnemonic(KeyEvent.VK_M);
		matricesMenu.setToolTipText(
				"Contiene las opciones para capturar, copiar, visualizar y cambiar el formato de las matrices.");
		capturarMatriz = new JMenuItem("Capturar matriz");
		capturarMatriz.setIcon(new ImageIcon(getClass().getResource("/imagenes/capturar.png")));
		capturarMatriz.setMnemonic(KeyEvent.VK_T);
		capturarMatriz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		capturarMatriz.addActionListener(this);
		capturarMatriz.setToolTipText("Captura o modifica una matriz y la guarda.");
		copiarMatriz = new JMenuItem("Copiar matriz");
		copiarMatriz.setIcon(new ImageIcon(getClass().getResource("/imagenes/copiar.png")));
		copiarMatriz.setMnemonic(KeyEvent.VK_P);
		copiarMatriz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		copiarMatriz.addActionListener(this);
		copiarMatriz.setToolTipText("Copia una matriz existente y las guarda o remplaza.");
		verMatriz = new JMenuItem("Ver matriz");
		verMatriz.setIcon(new ImageIcon(getClass().getResource("/imagenes/ver.png")));
		verMatriz.setMnemonic(KeyEvent.VK_V);
		verMatriz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
		verMatriz.addActionListener(this);
		verMatriz.setToolTipText("Visualiza una matriz existente.");
		formato = new JMenu("Formato");
		formato.setIcon(new ImageIcon(getClass().getResource("/imagenes/formato.png")));
		formato.setMnemonic(KeyEvent.VK_F);
		formato.addActionListener(this);
		formato.setToolTipText("Cambia el formato de las matrices");
		fraccion = new JRadioButtonMenuItem("Fracción");
		fraccion.setIcon(new ImageIcon(getClass().getResource("/imagenes/radioFraccion.png")));
		fraccion.setMnemonic(KeyEvent.VK_N);
		fraccion.setSelected(true);
		fraccion.addActionListener(this);
		fraccion.setToolTipText("Formato de tipo fracción.");
		;
		decimal = new JRadioButtonMenuItem("Decimal");
		decimal.setIcon(new ImageIcon(getClass().getResource("/imagenes/radioDecimal.png")));
		decimal.setMnemonic(KeyEvent.VK_L);
		decimal.addActionListener(this);
		decimal.setToolTipText("Formato de tipo decimal.");
		ButtonGroup grupoDeRadios = new ButtonGroup();
		grupoDeRadios.add(fraccion);
		grupoDeRadios.add(decimal);
		formato.add(fraccion);
		formato.add(decimal);

		matricesMenu.add(capturarMatriz);
		matricesMenu.add(copiarMatriz);
		matricesMenu.addSeparator();
		matricesMenu.add(verMatriz);
		matricesMenu.add(formato);

		operacionesMenu = new JMenu("Operaciones");
		operacionesMenu
				.setToolTipText("Contiene las opciones de sumar, restar, multiplicar y multiplicar por un escalar.");
		operacionesMenu.setMnemonic(KeyEvent.VK_O);
		operacionesMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/operaciones.png")));
		operacionesSuma = new JMenuItem("Suma");
		operacionesSuma.setToolTipText("Suma dos matrices existentes de dimensiones similares.");
		operacionesSuma.setMnemonic(KeyEvent.VK_S);
		operacionesSuma.setIcon(new ImageIcon(getClass().getResource("/imagenes/suma.png")));
		operacionesSuma.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		operacionesSuma.addActionListener(this);
		operacionesResta = new JMenuItem("Resta");
		operacionesResta.setToolTipText("Resta dos matrices existentes de dimensiones similares.");
		operacionesResta.setMnemonic(KeyEvent.VK_E);
		operacionesResta.setIcon(new ImageIcon(getClass().getResource("/imagenes/resta.png")));
		operacionesResta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		operacionesResta.addActionListener(this);
		operacionesMultiplicacionEscalar = new JMenuItem("Multiplicación por escalar");
		operacionesMultiplicacionEscalar.setToolTipText("Multiplica una matriz existente por un escalar.");
		operacionesMultiplicacionEscalar.setMnemonic(KeyEvent.VK_L);
		operacionesMultiplicacionEscalar
				.setIcon(new ImageIcon(getClass().getResource("/imagenes/multiplicacionEscalar.png")));
		operacionesMultiplicacionEscalar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		operacionesMultiplicacionEscalar.addActionListener(this);
		operacionesMultiplicacion = new JMenuItem("Multiplicación");
		operacionesMultiplicacion.setToolTipText("Multiplica dos matrizes existentes de dimensiones similares.");
		operacionesMultiplicacion.setMnemonic(KeyEvent.VK_U);
		operacionesMultiplicacion.setIcon(new ImageIcon(getClass().getResource("/imagenes/multiplicacion.png")));
		operacionesMultiplicacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
		operacionesMultiplicacion.addActionListener(this);
		operacionesMenu.add(operacionesSuma);
		operacionesMenu.add(operacionesResta);
		operacionesMenu.add(operacionesMultiplicacion);
		operacionesMenu.add(operacionesMultiplicacionEscalar);

		ayudaMenu = new JMenu("Ayuda");
		ayudaMenu.setToolTipText("Contiene la opción de acerca de...");
		ayudaMenu.setMnemonic(KeyEvent.VK_Y);
		ayudaMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/ayuda.png")));
		acercaDeMenu = new JMenuItem("Acerca de...");
		acercaDeMenu.setToolTipText("Contiene la información acerca de la aplicación.");
		acercaDeMenu.setMnemonic(KeyEvent.VK_C);
		acercaDeMenu.setIcon(new ImageIcon(getClass().getResource("/imagenes/informacion.png")));
		acercaDeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
		acercaDeMenu.addActionListener(this);

		ayudaMenu.add(acercaDeMenu);
		barraMenu = new JMenuBar();
		barraMenu.add(archivoMenu);
		barraMenu.add(matricesMenu);
		barraMenu.add(operacionesMenu);
		barraMenu.add(ayudaMenu);
		this.setJMenuBar(barraMenu);

		establecerPoliticaFoco();

		this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/calculadora.png")).getImage());
		/*this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		*/
		this.setSize(1200, 780);
		this.getContentPane().setLayout(new FlowLayout());
		JLabel fondo = new JLabel();
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagenes/fondo-calc.png"));
		Image imagenEscalada = imagenFondo.getImage().getScaledInstance(-1, getSize().width - 510, Image.SCALE_DEFAULT);
		fondo.setIcon(new ImageIcon(imagenEscalada));
		this.getContentPane().add(fondo);
		//this.getContentPane().setBackground(Color.CYAN);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/*
	 * Método actionPerformed que sirve para validar los eventos de actionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(acercaDeMenu)) {
			mostrarCreditos();
		} else if (e.getSource().equals(salirMenu)) {
			metodoSalir();
		} else if (e.getSource().equals(capturarMatriz)) {
			new CapturarMatriz(this);
		} else if (e.getSource().equals(copiarMatriz)) {
			new CopiarMatriz(this);
		} else if (e.getSource().equals(verMatriz)) {
			new VerMatriz(this);
		} else if (e.getSource().equals(operacionesSuma)) {
			new Suma(this);
		} else if (e.getSource().equals(operacionesResta)) {
			new Resta(this);
		} else if (e.getSource().equals(operacionesMultiplicacion)) {
			new Multiplicacion(this);
		} else if (e.getSource().equals(operacionesMultiplicacionEscalar)) {
			new MultiplicacionEscalar(this);
		} else if (e.getSource().equals(fraccion)) {
			metodoRadioFraccion();
		} else if (e.getSource().equals(decimal)) {
			metodoRadioDecimal();
		} else if (e.getSource().equals(abrir)) {
			abrirArchivo();
		} else if (e.getSource().equals(guardar)) {
			guardarArchivo();
		}
	}

	// Método que consiste en mostrar una ventana para elegir donde y como se va a
	// gardar el archivo .MAT
	private void guardarArchivo() {
		JFileChooser dialogo = new JFileChooser();
		dialogo.setDialogTitle("Seleccionar imagen de maquillaje");
		FileFilter filtro1 = new FileNameExtensionFilter("Archivo MAT", "MAT");
		dialogo.setFileFilter(filtro1);
		dialogo.setAcceptAllFileFilterUsed(false);
		dialogo.setMultiSelectionEnabled(false);
		if (dialogo.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File archivoAux = new File("");
			// Archivo que se obtiene del SaveDialog
			File archivo = dialogo.getSelectedFile();
			// Se valida si el archivo termina en .MAT
			if (archivo.getName().endsWith(".MAT")) {
				// En caso de que tenga tenga más de un .MAT
				StringTokenizer tokens = new StringTokenizer(archivo.getAbsolutePath(), ".MAT");
				String ruta = tokens.nextToken();
				// Se añade la extensión .MAT
				archivoAux = new File(ruta + ".MAT");
			} else {
				// Se añade la extensión .MAT
				archivoAux = new File(archivo.getAbsolutePath() + ".MAT");
			}
			// El archivo existe
			if (archivo.exists() || archivoAux.exists()) {
				if (JOptionPane.showConfirmDialog(null,
						"¡El nombre de este archivo ya existe!" + "\n¿Desea sobreescribir en el archivo?",
						"El archivo ya existe.", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					// Sobrescribe en el archivo
					escribirArchivo(archivoAux);
					JOptionPane.showMessageDialog(this,
							"El archivo ha sido sobreescrito con éxito.",
							"Archivo sobreescrito.", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				// En caso de que tenga otra extensión
				StringTokenizer token = new StringTokenizer(archivoAux.getAbsolutePath(), ".");
				token.nextToken();
				String extension = token.nextToken();
				// Valida si la extensión es diferente a .MAT
				if (!extension.equals("MAT")) {
					JOptionPane.showMessageDialog(null,
							"¡La extensión del archivo no es correcta!\n" + "Favor de utilizar la extensión .MAT",
							"Archivo incorrecto", JOptionPane.ERROR_MESSAGE);
				} else {
					escribirArchivo(archivoAux);
					JOptionPane.showMessageDialog(this,
							"El archivo ha sido guardado con éxito.",
							"Archivo guardado.", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	// Método que consiste en escribir la coleccién de las matrices en un archivo
	// .MAT
	private void escribirArchivo(File archivoAux) {
		try (ObjectOutputStream lectura = new ObjectOutputStream(new FileOutputStream(archivoAux.getAbsolutePath()))) {
			lectura.writeObject(arregloMatriz);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error al guardar los datos", "Error al guardar",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método que consiste en mostrar una ventana para elegir el archivo.
	private void abrirArchivo() {
		JFileChooser dialogo = new JFileChooser();
		dialogo.setDialogTitle("Seleccionar imagen de maquillaje");
		FileFilter filtro1 = new FileNameExtensionFilter("Archivo MAT", "MAT");
		dialogo.setFileFilter(filtro1);
		dialogo.setAcceptAllFileFilterUsed(false);
		dialogo.setMultiSelectionEnabled(false);
		int valor = dialogo.showOpenDialog(null);
		if (valor == JFileChooser.APPROVE_OPTION) {
			// Se guarda la ruta temporal de la imagen.
			File archivo = dialogo.getSelectedFile();
			if (archivo.exists()) {
				arregloMatriz = leerArchivo(archivo);
				JOptionPane.showMessageDialog(this,
						"El archivo ha sido abierto con éxito.",
						"Archivo abierto.", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,
						"No se pudo encontrar el archivo, favor de intentar con otro archivo.",
						"Error al seleccionar archivo.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Método que consiste en leer la colección de matrices desde un archivo .MAT
	@SuppressWarnings("finally")
	private Matriz[] leerArchivo(File archivo) {
		Matriz[] lista = null;
		try (ObjectInputStream lectura = new ObjectInputStream(
				new FileInputStream(new File(archivo.getAbsolutePath())));) {
			Object obj = lectura.readObject();
			if (obj instanceof Matriz[]) {
				lista = (Matriz[]) obj;
			}
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error al leer el archivo", "Error de lectura",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			return lista;
		}
	}

	/*
	 * Método que consiste en seleccionar el formato de fracción para las matrices.
	 */
	private void metodoRadioFraccion() {
		// TODO Auto-generated method stub
		Racional.setFormatoRacional(-1);
	}

	/*
	 * Método que consiste en validar la cantidad de decimales para el formato de
	 * las matrices.
	 */
	private void metodoRadioDecimal() {
		// TODO Auto-generated method stub

		String decimales = (String) JOptionPane.showInputDialog(this,
				"Ingrese la cantidad de decimales que desea utilizar:", "Cantidad de decimales",
				JOptionPane.DEFAULT_OPTION, new ImageIcon(getClass().getResource("/imagenes/radioDecimal.png")), null,
				"3");
		if (decimales == null) {
			metodoRadioFraccion();
			fraccion.setSelected(true);
		} else {
			int numDecimales = Integer.parseInt(decimales);
			Racional.setFormatoRacional(numDecimales);
		}
	}

	/*
	 * Método que sirve para mostrar los créditos del sistema.
	 */
	private void mostrarCreditos() {
		JOptionPane.showMessageDialog(this,
				"Calculadora con matrices v.1.0" + "\n\n" + "Desarrollado por:" + "\nJonathan Eduardo Ibarra Martínez" + "\n\n"
						+ "Derechos reservados UMAR " + '\u00A9' + " 2022",
				"Acerca de... Calculadora con matrices", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(getClass().getResource("/Imagenes/calculadoraAyuda.png")));
	}

	/*
	 * Método que sirve para salir de la aplciación.
	 */
	private void metodoSalir() {
		System.exit(0);
	}

	/*
	 * Método que sirve para establecer la politica de foco.
	 */
	private void establecerPoliticaFoco() {
		Vector<Component> componentes = new Vector<Component>();
		componentes.add(archivoMenu);
		componentes.add(operacionesMenu);
		componentes.add(ayudaMenu);
		MiFocusTraversalPolicy politicaFoco = new MiFocusTraversalPolicy(componentes);
		this.setFocusTraversalPolicy(politicaFoco);
	}
}