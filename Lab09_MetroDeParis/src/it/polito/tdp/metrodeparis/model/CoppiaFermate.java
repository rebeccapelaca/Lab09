package it.polito.tdp.metrodeparis.model;

public class CoppiaFermate {
	
	private int idConnessione;
	private Fermata fermata1;
	private Fermata fermata2;
	private Linea linea;
	
	public CoppiaFermate(Fermata fermata1, Fermata fermata2, Linea linea, int idConnessione) {
		super();
		this.fermata1 = fermata1;
		this.fermata2 = fermata2;
		this.linea = linea;
		this.idConnessione = idConnessione;
	}

	public Fermata getFermata1() {
		return fermata1;
	}

	public void setFermata1(Fermata fermata1) {
		this.fermata1 = fermata1;
	}

	public Fermata getFermata2() {
		return fermata2;
	}

	public void setFermata2(Fermata fermata2) {
		this.fermata2 = fermata2;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public int getIdConnessione() {
		return idConnessione;
	}

	public void setIdConnessione(int idConnessione) {
		this.idConnessione = idConnessione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idConnessione;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppiaFermate other = (CoppiaFermate) obj;
		if (idConnessione != other.idConnessione)
			return false;
		return true;
	}
}
