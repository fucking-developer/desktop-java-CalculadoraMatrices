package excepcion;

public class MatrizExcepcion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// Constantes obligatorias para validar en caso de no ingresar ningun valor.
	public static final String MATRIZ_OBLIGATORIA = "Los campos son obligatorios.\nFavor de ingresar un valor.";
	public static final String ESCALAR_OBLIGATORIO = "El valor del escalar es obligatorio.\nFavor de ingresar un valor.";
	// Constante invalida para validar en caso de ingresar un valor invalido.
	public static final String CAMPO_INVALIDO = "El valor ingresados es invalido.\nFavor de ingresar un valor valido.";
	
	// Constructor el cual regresa el mensaje establecido en la clase Racional, MatrizX y MultiplicacionEscalar.
	public MatrizExcepcion(String mensaje) {
		super(mensaje);
	}
}
