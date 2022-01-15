package utils.transformations;

import utils.Matrice;

/**
 * Classe abstraite qui represente les transforamtion applicables au model
 */
public abstract class Transformation {
	/**
	 * le type de transformation
	 */
	private TransformationType type;
	/**
	 * la valeur de la transformation
	 */
	private double value;

	/**
	 * Cree une nouvelle transformation
	 * 
	 * @param value le type de transformation
	 * @param type  la valeur de la transformation
	 */
	public Transformation(double value, TransformationType type) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Retourne la matrice de transformation correspondant
	 * 
	 * @return la matrice
	 */
	public abstract Matrice get();

	public double getValue() {
		return value;
	}

	public TransformationType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Transformation [type=" + type + ", value=" + value + "]";
	}
}
