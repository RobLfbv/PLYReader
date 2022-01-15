package utils.transformations;

import utils.Matrice;

/**
 * 
 * translation sur l'axe Z
 *
 */
public class TransformationZTranslation extends Transformation {
	/**
	 * Cree une transformation de translation sur l'axe Z
	 * 
	 * @param value la valeur
	 */
	public TransformationZTranslation(double value) {
		super(value, TransformationType.ZTRANSLATION);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, getValue() }, { 0, 0, 0, 1 } });
	}

}
