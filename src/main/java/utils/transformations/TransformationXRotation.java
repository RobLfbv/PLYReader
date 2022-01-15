package utils.transformations;

import utils.Matrice;

/**
 * 
 * Rotation sur l'axe X
 *
 */
public class TransformationXRotation extends Transformation {

	/**
	 * Cree une transformation de rotation sur l'axe x
	 * 
	 * @param value la valeur
	 */
	public TransformationXRotation(double value) {
		super(value, TransformationType.XROTATION);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, Math.cos(getValue()), -Math.sin(getValue()), 0 },
				{ 0, Math.sin(getValue()), Math.cos(getValue()), 0 }, { 0, 0, 0, 1 } });
	}

}
