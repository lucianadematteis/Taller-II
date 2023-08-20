package logica;

class Entero extends Atributo {
	private int valor;

	public Entero(String nombreAtributo, boolean nulo, boolean clave, int valor) {
		super(nombreAtributo, nulo, clave);
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
