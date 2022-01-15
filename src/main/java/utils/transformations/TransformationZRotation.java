package utils.transformations;

import utils.Matrice;

/**
 * 
 * Rotation sur l'axe Z
 *
 */
public class TransformationZRotation extends Transformation {
	/**
	 * Cree une transformation de rotation sur l'axe z
	 * 
	 * @param value la valeur
	 */
	public TransformationZRotation(double value) {
		super(value, TransformationType.ZROTATION);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { Math.cos(getValue()), -Math.sin(getValue()), 0, 0 },
				{ Math.sin(getValue()), Math.cos(getValue()), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
	}

}
