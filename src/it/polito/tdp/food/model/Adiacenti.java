package it.polito.tdp.food.model;

public class Adiacenti {
	
	private int ing1;
	private int ing2;
	private int peso;
	
	public Adiacenti(int ing1, int ing2, int peso) {
		this.ing1 = ing1;
		this.ing2 = ing2;
		this.peso = peso;
	}
	
	public int getIng1() {
		return ing1;
	}

	public int getIng2() {
		return ing2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ing1;
		result = prime * result + ing2;
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
		Adiacenti other = (Adiacenti) obj;
		if (ing1 != other.ing1)
			return false;
		if (ing2 != other.ing2)
			return false;
		return true;
	}	
	
}