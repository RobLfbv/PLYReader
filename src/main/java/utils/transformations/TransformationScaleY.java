package utils.transformations;

import utils.Matrice;

/**
 * Homothetie sur l'axe y
 *
 */
public class TransformationScaleY extends Transformation {

	/**
	 * Cree une transformation d'homothetie sur l'axe y
	 * 
	 * @param value la valeur
	 */
	public TransformationScaleY(double value) {
		super(value, TransformationType.YSCALE);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, getValue(), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
	}

}
