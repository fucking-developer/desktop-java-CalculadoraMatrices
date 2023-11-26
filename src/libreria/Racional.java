package libreria;

import java.io.Serializable;
import java.util.StringTokenizer;

import excepcion.MatrizExcepcion;

/*
 * 
 * Jonathan Eduardo Ibarra Martínez
 * 
 * Versión: 15/05/2022
 * 
 * Práctica 13. Racional.
 * (Utliza la clase: LibreriaMatematica)
 * 
 */
public class Racional implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int formatoRacional = -1;
	private int numerador;
	private int denominador = 1;

	// Método get para la variable de clase formatoRacional.
	public static int getFormatoRacional() {
		return formatoRacional;
	}

	// Método set para la variable de clase formatoRacional.
	public static void setFormatoRacional(int formatoRacional) {
		Racional.formatoRacional = formatoRacional;
	}

	// Método get para la variable miembro denominador.
	public int getDenominador() {
		return denominador;
	}

	// Método set para la variable miembro denominador.
	public void setDenominador(int denominador) {
		if (denominador != 0) {
			this.denominador = denominador;
		}
	}

	// Método get para la variable miembro numerador.
	public int getNumerador() {
		return numerador;
	}

	// Método set para la variable miembro numerador.
	public void setNumerador(int numerador) {
		this.numerador = numerador;
	}

	// Método para representar en su mínima expresión a los números racionales.
	public void simplificar() {
		/*
		 * int mcd = LibreriaMatematica.mcd(denominador, numerador); numerador =
		 * numerador / mcd; denominador = denominador / mcd; if (denominador < 0) {
		 * denominador *= -1; numerador *= -1; }
		 */
		int mcd = LibreriaMatematica.mcd(denominador, numerador);
		numerador = numerador / mcd;
		denominador = denominador / mcd;
	}

	// Constructor sin parámetros.
	public Racional() {
		// setNumerador(0);
		// setDenominador(1);
		this(0, 1);

	}

	// Constructor con parámetros.
	public Racional(int numerador, int denominador) {
		setNumerador(numerador);
		setDenominador(denominador);
		simplificar();
	}

	// Constructor que recibe solo el numerador.
	public Racional(int numerador) {
		this(numerador, 1);
	}

	// Constructor copia.
	public Racional(Racional racional) {
		this(racional.getNumerador(), racional.getDenominador());
	}

	// Constructor que recibe un número de doble precisión.
	public Racional(Double decimal) {
		String cadenaDecimal = String.valueOf(decimal);
		int cantidad = cadenaDecimal.length() - 1 - cadenaDecimal.indexOf('.');
		denominador = 1;
		for (int i = 0; i < cantidad; i++) {
			decimal *= 10;
			denominador *= 10;
		}
		numerador = (int) Math.round(decimal);
		simplificar();
	}

	// Constructor que recibe una cadena de caracteres.
	public Racional(String cadena) throws MatrizExcepcion {
		if (cadena.matches("(-?[0-9])+/(-?[0-9])+")) {
			StringTokenizer partes = new StringTokenizer(cadena, "/");
			this.setNumerador(Integer.parseInt(partes.nextToken()));
			this.setDenominador(Integer.parseInt(partes.nextToken()));
		} else {
			throw new MatrizExcepcion(MatrizExcepcion.CAMPO_INVALIDO);
		}
	}

	// Método que regresa el número racional en formato de fracción.
	private String mostrarFraccion() {
		String fraccion = "";
		if (this.getDenominador() == 1) {
			fraccion = String.valueOf(this.getNumerador());
		} else {
			fraccion = this.getNumerador() + "/" + this.getDenominador();
		}
		return fraccion;
	}

	// Método que regresa una cadena en formato decimal el parametro recibido.
	private String mostrarDecimal(int decimales) {
		// return String.format("%." + decimales + "f", (float) numerador /
		// denominador);
		String resultado = "";
		double division = (double) this.getNumerador() / this.getDenominador();
		if (decimales <= 0) {
			resultado = String.valueOf((int) division);
		} else {
			resultado = String.format("%." + decimales + "f", division);
		}
		return resultado;
	}

	// Método que regresa el tipo de formato que se desea utilizar.
	public String mostrar(int formato) {
		simplificar();
		if (formato < 0) {
			return mostrarFraccion();
		} else {
			return mostrarDecimal(formato);
		}
	}

	// Método toString() para la representación en cadena de los números
	// racionales.
	public String toString() {
		return mostrar(formatoRacional);
	}

	// Método suma que recibe dos números racionales.
	public static Racional suma(Racional sumando1, Racional sumando2) {
		Racional suma = new Racional();
		suma.setNumerador((sumando1.getNumerador() * sumando2.getDenominador())
				+ (sumando1.getDenominador() * sumando2.getNumerador()));
		suma.setDenominador(sumando1.getDenominador() * sumando2.getDenominador());
		suma.simplificar();
		return suma;
	}

	// Método resta que recibe dos números racionales.
	public static Racional resta(Racional minuendo, Racional sustraendo) {
		Racional resta = new Racional();
		resta.setNumerador((minuendo.getNumerador() * sustraendo.getDenominador())
				- (minuendo.getDenominador() * sustraendo.getNumerador()));
		resta.setDenominador(minuendo.getDenominador() * sustraendo.getDenominador());
		resta.simplificar();
		return resta;
	}

	// Método multiplicacion que recibe dos números racionales.
	public static Racional multiplicacion(Racional factor1, Racional factor2) {
		Racional producto = new Racional();
		producto.setNumerador(factor1.getNumerador() * factor2.getNumerador());
		producto.setDenominador(factor1.getDenominador() * factor2.getDenominador());
		producto.simplificar();
		return producto;
	}

	// Método division que recibe dos números racionales.
	public static Racional division(Racional dividendo, Racional divisor) {
		if (divisor.getDenominador() == 0) {
			return null;
		} else {
			Racional cociente = new Racional();
			cociente.setNumerador(dividendo.getNumerador() * divisor.getDenominador());
			cociente.setDenominador(dividendo.getDenominador() * divisor.getNumerador());
			cociente.simplificar();
			return cociente;
		}
	}
}
