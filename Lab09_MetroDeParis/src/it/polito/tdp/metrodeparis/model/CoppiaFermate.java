package it.polito.tdp.metrodeparis.model;

public class CoppiaFermate {
	
	private Fermata fermata1;
	private Fermata fermata2;
	private int id_linea;
	
	public CoppiaFermate(Fermata fermata1, Fermata fermata2, int id_linea) {
		super();
		this.fermata1 = fermata1;
		this.fermata2 = fermata2;
		this.id_linea = id_linea;
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

	public int getId_linea() {
		return id_linea;
	}

	public void setId_linea(int id_linea) {
		this.id_linea = id_linea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fermata1 == null) ? 0 : fermata1.hashCode());
		result = prime * result + ((fermata2 == null) ? 0 : fermata2.hashCode());
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
		if (fermata1 == null) {
			if (other.fermata1 != null)
				return false;
		} else if (!fermata1.equals(other.fermata1))
			return false;
		if (fermata2 == null) {
			if (other.fermata2 != null)
				return false;
		} else if (!fermata2.equals(other.fermata2))
			return false;
		return true;
	}
}
