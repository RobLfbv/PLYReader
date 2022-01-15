package utils.transformations;

import utils.Matrice;

/**
 * 
 * translation sur l'axe X
 *
 */
public class TransformationYTranslation extends Transformation {
	/**
	 * Cree une transformation de translation sur l'axe X
	 * 
	 * @param value la valeur
	 */
	public TransformationYTranslation(double value) {
		super(value, TransformationType.YTRANSLATION);
	}

	@Override
	public Matrice get() {
		return new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, getValue() }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
	}

}
