package utils.transformations;

import utils.Matrice;

/**
 * 
 * Rotation sur l'axe Y
 *
 */
public class TransformationYRotation extends Transformation {
	/**
	 * Cree une transformation de rotation sur l'axe y
	 * 
	 * @param value la valeur
	 */
	public TransformationYRotation(double value) {
		super(value, TransformationType.YROTATION);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { Math.cos(getValue()), 0, -Math.sin(getValue()), 0 }, { 0, 1, 0, 0 },
				{ Math.sin(getValue()), 0, Math.cos(getValue()), 0 }, { 0, 0, 0, 1 } });
	}

}
