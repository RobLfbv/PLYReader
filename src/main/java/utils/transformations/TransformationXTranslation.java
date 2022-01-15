package utils.transformations;

import utils.Matrice;

/**
 * 
 * Translation sur l'axe X
 *
 */
public class TransformationXTranslation extends Transformation {
	/**
	 * Cree une transformation de translation sur l'axe X
	 * 
	 * @param value la valeur
	 */
	public TransformationXTranslation(double value) {
		super(value, TransformationType.XTRANSLATION);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { 1, 0, 0, getValue() }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
	}

}
