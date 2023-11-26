package libreria;

import java.io.Serializable;

/*
 * 
 * Jonathan Eduardo Ibarra Martinez
 * 
 * Versión: 15/05/2022
 * 
 * RETO 1: Matriz.
 * 
 * (Utliza la clase: LibreriaMatematica y la clase Racioal)
 * 
 */
public class Matriz implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int renglones = 1;
	private int columnas = 1;
	private Racional[][] datos;

	// Método get para la variable miembro columnas.
	public int getColumnas() {
		return columnas;
	}

	// Método set para la variable miembro columnas.
	public void setColumnas(int columnas) {
		if (columnas > 0) {
			this.columnas = columnas;
		}

	}

	// Método get para la variable miembro renglones.
	public int getRenglones() {
		return renglones;
	}

	// Método set para la variable miembro renglones.
	public void setRenglones(int renglones) {
		if (renglones > 0) {
			this.renglones = renglones;
		}
	}

	// Método setDato que establecera un número racional dentro del arreglo
	// datos.
	public void setDato(int ren, int col, Racional racional) {
		if ((ren < renglones && ren >=0) && (col < columnas && col >= 0)) {
			this.datos[ren][col] = racional;
		}
	}

	// Método getDato // Método suma que recibe dos números racionales.
	public Racional getDato(int ren, int col) {
		if ((ren < renglones && ren >= 0) && (col < columnas && col >= 0)) {
			return datos[ren][col];
		} else {
			return null;
		}
	}

	// Constructor de la clase que reciba renglones y columnas.
	public Matriz(int renglones, int columnas) {
		setRenglones(renglones);
		setColumnas(columnas);
		datos = new Racional[renglones][columnas];
		for (int i = 0; i < datos.length; i++) {
			for (int j = 0; j < datos[i].length; j++) {
				datos[i][j] = new Racional();
			}
		}
	}

	// Constructor que reciba un arreglo bidimensional de números racionales.
	public Matriz(Racional[][] matriz) {
		setRenglones(matriz.length);
		setColumnas(matriz[0].length);
		datos = new Racional[renglones][columnas];
		for (int i = 0; i < renglones; i++) {
			for (int j = 0; j < columnas; j++) {
				datos[i][j] = new Racional(matriz[i][j]);
			}
		}
	}

	// Constrcutor copia, se inicializan los renglones y columnas con las
	// deimencisons de la matriz recibida.
	public Matriz(Matriz matriz) {
		this(matriz.datos);
	}

	// Método público simplificarDatos() que realiza las representacionnes en su
	// mínima expresión a los números racionales de la matriz.
	public void simplificarDatos() {
		for (int i = 0; i < datos.length; i++) {
			for (int j = 0; j < datos[i].length; j++) {
				datos[i][j].simplificar();
			}
		}
	}

	// Método de clase público suma que recibe dos matrices, que serán los
	// sumandos.
	public static Matriz suma(Matriz sumando1, Matriz sumando2) {
		if (sumando1.renglones == sumando2.renglones && sumando1.columnas == sumando2.columnas) {
			Matriz suma = new Matriz(sumando1.renglones, sumando1.columnas);
			for (int i = 0; i < suma.renglones; i++) {
				for (int j = 0; j < suma.columnas; j++) {
					suma.setDato(i, j, Racional.suma(sumando1.getDato(i, j), sumando2.getDato(i, j)));
				}
			}
			suma.simplificarDatos();
			return suma;
		} else {
			return null;
		}
	}

	// Método de clase público resta que recibe dos matrices, que significarán
	// el
	// minuendo y el sustraendo
	public static Matriz resta(Matriz minuendo, Matriz sustraendo) {
		if (minuendo.renglones == sustraendo.renglones && minuendo.columnas == sustraendo.columnas) {
			Matriz resta = new Matriz(minuendo.renglones, minuendo.columnas);
			for (int i = 0; i < resta.renglones; i++) {
				for (int j = 0; j < resta.columnas; j++) {
					resta.setDato(i, j, Racional.resta(minuendo.getDato(i, j), sustraendo.getDato(i, j)));
				}
			}
			resta.simplificarDatos();
			return resta;
		} else {
			return null;
		}
	}

	// Método de clase público multicacionEscalar que recibe una matriz y un
	// número racional que serán la matriz y el escalar.
	public static Matriz multiplicacionEscalar(Matriz matriz, Racional escalar) {
		Matriz producto = new Matriz(matriz.renglones, matriz.columnas);
		for (int i = 0; i < matriz.renglones; i++) {
			for (int j = 0; j < matriz.columnas; j++) {
				producto.datos[i][j] = Racional.multiplicacion(escalar, matriz.datos[i][j]); 
			}
		}
		producto.simplificarDatos(); //Se simplifica la matriz.
		return producto;
	}

	// Método de clase público multiplicacion que recibe dos matrices, que
	// significarán los factores.
	public static Matriz multiplicacion(Matriz factor1, Matriz factor2) {
		if (factor1.getColumnas() == factor2.getRenglones()) {
			Matriz multiplicacion = new Matriz(factor1.getRenglones(), factor2.getColumnas());
			for (int i = 0; i < factor1.getRenglones(); i++) {
				for (int j = 0; j < factor2.getColumnas(); j++) {
					for (int k = 0; k < factor2.getRenglones(); k++) {
						multiplicacion.setDato(i, j, Racional.multiplicacion(factor1.getDato(i, k), factor2.getDato(k, j)));
					}
				}
			}
			multiplicacion.simplificarDatos();
			return multiplicacion;
		} else {
			return null;
		}
	}
	
	
	// Método público mostrarMatriz() que regresa un arreglo de cadenas
	// mostrando cada renglón de la matriz.
	public String[] mostrarMatriz() {
		String[] filas = new String[getRenglones()];
		for(int i=0; i<datos.length; i++){
			filas[i] = "[";
			for(int j=0; j<datos[i].length; j++){
				if(j < datos[i].length - 1){
					filas[i] += datos[i][j].toString() + " ";
				}else{
					filas[i] += datos[i][j].toString();
				}
			}
			filas[i] += "]";
		}
		return filas;
	}
}
