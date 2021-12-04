package sides;

import model.Point;
import utils.Matrice;
import utils.TransformationMatrice;
import utils.TransformationType;

/**
 * 
 * Classe qui fournit les methodes � un canvas qui est vu du cot� x positif
 * (cam�ra en x=infini)
 *
 */
public class PositiveXSide extends Side {

	/**
	 * Instantie le cot�
	 */
	public PositiveXSide() {
		super(0);
	}

	/**
	 * Retourne la coordonn�e X du point s'il �t� dessin� sur un canvas
	 */
	@Override
	public double getXOfPoint(Point point) {
		return -point.get(2);
	}

	/**
	 * Retourne la coordonn�e Y du point s'il �t� dessin� sur un canvas
	 */
	@Override
	public double getYOfPoint(Point point) {
		return point.get(1);
	}

	/**
	 * Retourne la matrice de translation a appliquer sur le mod�le qui correspond �
	 * un drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	@Override
	public Matrice getTranslationFromDrag(double xDelta, double yDelta) {
		return new TransformationMatrice(TransformationType.TRANSLATION).get(0, -yDelta, -xDelta);
	}

	/**
	 * Retourne la matrice de rotation a appliquer sur le mod�le qui correspond � un
	 * drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	@Override
	public Matrice getRotationFromDrag(double xDelta, double yDelta) {
		Matrice fromX = new TransformationMatrice(TransformationType.YROTATION).get(-xDelta);
		Matrice fromY = new TransformationMatrice(TransformationType.ZROTATION).get(-yDelta);

		return Matrice.multiply(fromX, fromY);
	}

	/**
	 * Retourne le nom du cot�
	 */
	@Override
	public String toString() {
		return "Positive X";
	}

}
