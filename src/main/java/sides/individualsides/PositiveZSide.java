package sides.individualsides;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Point;
import sides.Side;
import utils.transformations.Transformation;
import utils.transformations.TransformationType;
import utils.transformations.TransformationXRotation;
import utils.transformations.TransformationXTranslation;
import utils.transformations.TransformationYRotation;
import utils.transformations.TransformationYTranslation;

/**
 * 
 * Classe qui fournit les methodes a un canvas qui est vu du cote z positif
 * (camera en z=infini)
 *
 */
public class PositiveZSide extends Side {

	/**
	 * Instantie le cote
	 */
	public PositiveZSide() {
		super(2);
	}

	/**
	 * Retourne la coordonnee X du point s'il ete dessine sur un canvas
	 */
	@Override
	public double getXOfPoint(Point point) {
		return point.get(0);
	}

	/**
	 * Retourne la coordonnee Y du point s'il ete dessine sur un canvas
	 */
	@Override
	public double getYOfPoint(Point point) {
		return point.get(1);
	}

	/**
	 * Retourne la matrice de translation a appliquer sur le modele qui correspond a
	 * un drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	@Override
	public List<Transformation> getTranslationFromDrag(double xDelta, double yDelta) {
		List<Transformation> transformationMatrices = new ArrayList<>();

		if (xDelta != 0)
			transformationMatrices.add(new TransformationXTranslation(xDelta));
		if (yDelta != 0)
			transformationMatrices.add(new TransformationYTranslation(-yDelta));

		return transformationMatrices;
	}

	/**
	 * Retourne la matrice de rotation a appliquer sur le modele qui correspond a un
	 * drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	@Override
	public List<Transformation> getRotationFromDrag(double xDelta, double yDelta) {
		List<Transformation> transformationMatrices = new ArrayList<>();
		if (xDelta != 0)
			transformationMatrices.add(new TransformationYRotation(-xDelta));
		if (yDelta != 0)
			transformationMatrices.add(new TransformationXRotation(yDelta));

		return transformationMatrices;
	}

	/**
	 * Retourne vrai s'il faut refaire un rendu pour le type de transformation recu
	 */
	public boolean hasToRender(Set<TransformationType> types) {
		if (types == null)
			return true;
		return !(types.size() == 1 && types.contains(TransformationType.ZTRANSLATION));
	}

	/**
	 * Retourne le nom du cote
	 */
	@Override
	public String toString() {
		return "Positive Z";
	}

}
