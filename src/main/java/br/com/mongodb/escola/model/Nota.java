package br.com.mongodb.escola.model;

public class Nota {

	private Double valor;

	public Nota() {
	}

	public Nota(Double valor) {
		this.valor = valor;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
