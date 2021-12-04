package model;

import utils.Matrice;

/**
 * Classe qui définit un repère
 */
public class Repere {
	/**
	 * matrix - Matrice du repère
	 */
	private Matrice matrix;

	/**
	 * Constructeur du repère
	 */
	public Repere() {
		this.reset();
	}

	/**
	 * Application de la transformation à partir de la matrice donnée
	 * 
	 * @param transformationMatrix - Matrice de transformation
	 */
	public void applyTransformationWithMatrix(Matrice transformationMatrix) {
		this.matrix = Matrice.multiply(transformationMatrix, matrix);

	}

	public Matrice getMatrix() {
		return matrix;
	}

	/**
	 * Remet à zéro le repère
	 */
	public void reset() {
		this.matrix = new Matrice(new double[][] { { 1.0, 0.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0, 0.0 },
				{ 0.0, 0.0, 1.0, 0.0 }, { 0.0, 0.0, 0.0, 1.0 } });
	}
}
