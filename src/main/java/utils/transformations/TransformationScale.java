package utils.transformations;

import utils.Matrice;

/**
 * Homothetie
 */
public class TransformationScale extends Transformation {

	/**
	 * Cree une transformation d'homothetie
	 * 
	 * @param value la valeur
	 */
	public TransformationScale(double value) {
		super(value, TransformationType.SCALE);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { getValue(), 0, 0, 0 }, { 0, getValue(), 0, 0 }, { 0, 0, getValue(), 0 },
				{ 0, 0, 0, 1 } });
	}

}
