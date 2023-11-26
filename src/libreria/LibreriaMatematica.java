package libreria;

/*
* Jonathan Eduardo Ibarra Martínez
* 
* Clase LibreriaMatematica que contiene los siguientes métodos:
* 
* 	- Factorial.
* 
* 	- Máximo común divisor (MCD). 
* 
* 	- Exponencial  utilizando series de Maclaurin. (Práctica 11). 
* 
* 	- Función trigonométrica seno (sin x) utilizando series de Maclaurin. 
* 
* 	- Función trigonométrica cos(x). (Examen). 
* 
* version 13/05/2022
*/
public class LibreriaMatematica {
	// Método para calcular el factorial de un número de forma recursiva.
	public static long factorial(long num) {
		if (num >= 0) {
			if (num == 0) {
				return 1;
			}
			return num * factorial(num - 1);
		} else {
			throw new IllegalArgumentException();
		}
	}

	// Método para calcular el máximo común divisor (MCD), aplicando el
	// algoritmo de Euclides de forma recursiva.

	public static int mcd(int num1, int num2) {
		num1 = Math.abs(num1);
		num2 = Math.abs(num2);
		if (num2 == 0){
			return num1;
		} else {
			return mcd(num2, num1 % num2);
		}
	}

	// Método para calcular la función exponencial utilizando series de
	// Maclaurin
	public static double exp(double valorX, int cantidadTerminos) {
		if (cantidadTerminos <= 0) {
			if (cantidadTerminos < 0) {
				throw new IllegalArgumentException();
			}
			return 0;
		} else {
			double sumatoria = 0;
			for (int i = 0; i < cantidadTerminos; i++) {
				sumatoria += Math.pow(valorX, i) / factorial(i);
			}
			return sumatoria;
		}
	}

	// Método para calcular la función trigonométrica seno (sin x) utilizando
	// series de Maclaurin.
	public static double sen(double valorX, int cantidadTerminos) {
		if (cantidadTerminos <= 0) {
			if (cantidadTerminos < 0) {
				throw new IllegalArgumentException();
			}
			return 0;
		} else {
			double sumatoria = 0;
			for (int i = 0; i < cantidadTerminos; i++) {
				sumatoria = sumatoria + (Math.pow(-1, i)) * (Math.pow(valorX, 2 * i + 1) / factorial(2 * i + 1));
			}
			return sumatoria;
		}
	}

	// Función examen.
	// Función para calcular la función trigonométrica coseno.
	public static double coseno(double x, int terminos) {
		if (terminos <= 0) {
			if (terminos < 0) {
				throw new IllegalArgumentException();
			}
			return 0;
		} else{
		double sumatoria = 0;
		for (int i = 0; i < terminos; i++) {
			sumatoria = sumatoria + (Math.pow(-1, i)) * (Math.pow(x, 2 * 1) / factorial(2 * i));
		}
		return sumatoria;
	}
	}

}
