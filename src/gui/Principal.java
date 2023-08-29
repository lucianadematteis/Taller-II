package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

import comunicacion.FachadaLogica;
import comunicacion.IFachadaLogica;

public class Principal {

	IFachadaLogica logica= new FachadaLogica();
	
	int aciertos = 0;

	public List<String> comandosNivel1 = Arrays.asList("SHOW", "CREATE", "USE", "INSERT", "DELETE", "UPDATE", "NOTNULL",
			"SELECT", "COUNT", "AVG", "PRIMARYKEY", "DESCRIBE", "HELP", "JOINNATURAL", "MAX", "MIN");

	public List<String> comandosNivel2 = Arrays.asList("SET", "FROM", "WHERE", "TABLES", "DATABASE", "TABLE", "VALUES");

	public List<String> operadoresLogicos = Arrays.asList("AND", "OR");
	
	public List<String> tiposDatos = Arrays.asList("CADENA", "ENTERO");
	
	public List<String> operadores = Arrays.asList("=");

	public ArrayList<String[]> administraSentencia(String sentencia) {

		// Se pretende generar un arreglo de arreglos. Donde en cada posici�n del
		// arreglo principal
		// se almacene una linea de la sentencia, de forma tal que cada palabra
		// corresponda a una posicion de los arreglos secundarios

		// Separa el contenido en lineas
		String[] lineas = sentencia.split("\n");

		// Crea un arreglo para cada linea
		ArrayList<String[]> arregloLinea = new ArrayList<>();

		// Recorre cada linea
		for (String unaLinea : lineas) {

			ArrayList<String> palabras = new ArrayList<>();

			Matcher matcher = Pattern.compile("\"([^\"]*)\"|\\S+").matcher(unaLinea);

			// Encuentra las palabras entre comillas o las palabras separadas por espacios
			while (matcher.find()) {

				String palabra = matcher.group();

				// Si la palabra tiene comillas, se eliminan las comillas
				if (palabra.startsWith("\"") && palabra.endsWith("\"")) {

					palabra = palabra.substring(1, palabra.length() - 1);

				}

				palabras.add(palabra);

			}

			arregloLinea.add(palabras.toArray(new String[0]));
		}

		return arregloLinea;

	}

	public void insertarDepuracion(String mensaje1, String mensaje2) {

		DefaultTableModel model = (DefaultTableModel) VentanaPrincipal.depuracion.getModel();

		Object[] nuevaFila = { mensaje1, mensaje2 };
		model.insertRow(0, nuevaFila);

	}

	public boolean validaCantidadArgumentos(ArrayList<String[]> sentencia, int posInicial, int posFinal, int cantArgumentos) {

		if (posInicial == posFinal) {

			return (sentencia.get(posInicial).length == cantArgumentos);

		} else {

			for (int i = posInicial; i < posFinal; i++) {

				if (sentencia.get(i).length != cantArgumentos) {

					return false;

				}

			}
		}

		return true;

	}

	public boolean validaTipoDato(String tipo) {

		return ((tipo.toUpperCase().equals(tiposDatos.get(0))) || (tipo.toUpperCase().equals(tiposDatos.get(1))));

	}

	public boolean validaTiposAtributos(ArrayList<String[]> sentencia, int posInicial, int posFinal) {

		for (int j = posInicial; j < posFinal; j++) {

			if (!(validaTipoDato(sentencia.get(j)[1]))) {

				return false;

			}

		}

		return true;

	}

	public boolean validaCantidadLineas(ArrayList<String[]> sentencia, int min, int max) {

		if (min == max) {

			return (sentencia.size() == min);

		} else {

			return ((sentencia.size() >= min) && (sentencia.size() <= max));

		}

	}

	public boolean validaSentenciasUnaLinea(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 1, 1))) {

			insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, 0, 2))) {

				insertarDepuracion("Error #03", "Cantidad de argumentos no valida");

			} else {

				return true;

			}

		}

		return false;

	}

	public boolean validaSentenciasDosLineas(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 2, 2))) {

			insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (!(validaCantidadArgumentos(sentencia, 0, sentencia.size(), 2))) {

				insertarDepuracion("Error #03", "Cantidad de argumentos no valida");

			} else {

				if (!(sentencia.get(1)[0].toUpperCase().equals("FROM"))) {

					insertarDepuracion("Error #01",
							"El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");

				} else {

					return true;

				}

			}

		}

		return false;

	}

	public boolean validaSentenciasWhereComun(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 2, 2, 4))) {

			insertarDepuracion("Error #03", "Cantidad de argumentos no valida en linea 3");

		} else {

			if (!(sentencia.get(2)[0].toUpperCase().equals(comandosNivel2.get(2)))) {

				insertarDepuracion("Error #01", "El comando: " + sentencia.get(2)[0].toUpperCase() + " no es valido");

			} else {

				if (!(sentencia.get(2)[2].toUpperCase().equals(operadores.get(0)))) {

					insertarDepuracion("Error #05", "El operador: " + sentencia.get(2)[2] + " no es valido");

				} else {

					return true;

				}
			}

		}

		return false;

	}

	public boolean validaSentenciasFrom(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 1, 1, 2))) {

			insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 2");

		} else {

			if (!(sentencia.get(1)[0].toUpperCase().equals(comandosNivel2.get(1)))) {

				insertarDepuracion("Error #01", "El comando: " + sentencia.get(1)[0].toUpperCase() + " no es valido");

			} else {

				return true;

			}
		}

		return false;

	}

	public boolean validaSentenciasFromWhere(ArrayList<String[]> sentencia) {

		if (!(validaCantidadLineas(sentencia, 3, 3))) {

			insertarDepuracion("Error #02", "La cantidad de lineas ingresada es incorrecta");

		} else {

			if (validaSentenciasFrom(sentencia)) {

				if (validaSentenciasWhereComun(sentencia)) {

					return true;

				}

			}

		}

		return false;

	}

	public boolean validaOperadoresLogicos(ArrayList<String[]> sentencia) {

		if (!(validaCantidadArgumentos(sentencia, 2, 2, 8))) {

			insertarDepuracion("Error #03", "Cantidad de argumentos no valida en la linea 3");

		} else {

			if (!(sentencia.get(2)[2].toUpperCase().equals("=")) || !(sentencia.get(2)[6].toUpperCase().equals("="))) {

				insertarDepuracion("Error #05", "Operador/es de igualdad no valido en la linea 3");

			} else {

				return true;

			}

		}

		return false;

	}

	public static void main(String[] args) {
		
		login frame = new login();
		frame.setVisible(true);

	}

}
