package utils.transformations;

import java.util.ArrayList;
import java.util.List;

import model.Repere;

/**
 * 
 * Permet de creer des transformation ou des listes de transformations
 *
 */
public final class TransformationFabric {
	/**
	 * Cree une matrice de transformation a partie d'un type et d'une valeur
	 * 
	 * @param type  le type
	 * @param value la valeur
	 * @return la transformation correspondant
	 */
	public static Transformation get(TransformationType type, double value) {
		switch (type) {
		case XTRANSLATION:
			return new TransformationXTranslation(value);
		case YTRANSLATION:
			return new TransformationYTranslation(value);
		case ZTRANSLATION:
			return new TransformationZTranslation(value);
		case XROTATION:
			return new TransformationXRotation(value);
		case YROTATION:
			return new TransformationYRotation(value);
		case ZROTATION:
			return new TransformationZRotation(value);
		case SCALE:
			return new TransformationScale(value);
		default:
			return null;
		}
	}

	/**
	 * Cree une transformation centree sur l'objet
	 * 
	 * @param repere          le repere du model
	 * @param transformations la transformation a centrer
	 * @return la liste des transformations correspondante
	 */
	public static List<Transformation> getCentered(Repere repere, List<Transformation> transformations) {
		List<Transformation> res = new ArrayList<>();

		res.add(new TransformationXTranslation(-repere.getParameter(TransformationType.XTRANSLATION)));
		res.add(new TransformationYTranslation(-repere.getParameter(TransformationType.YTRANSLATION)));
		res.add(new TransformationZTranslation(-repere.getParameter(TransformationType.ZTRANSLATION)));

		res.addAll(transformations);

		res.add(new TransformationXTranslation(repere.getParameter(TransformationType.XTRANSLATION)));
		res.add(new TransformationYTranslation(repere.getParameter(TransformationType.YTRANSLATION)));
		res.add(new TransformationZTranslation(repere.getParameter(TransformationType.ZTRANSLATION)));

		return res;
	}

	/**
	 * Cree une transformation centree sur l'objet
	 * 
	 * @param repere le repere du model
	 * @param type   le type
	 * @param value  la valeur
	 * @return la liste des transformations correspondante
	 */
	public static List<Transformation> getCentered(Repere repere, TransformationType type, double value) {
		List<Transformation> res = new ArrayList<>();
		res.add(get(type, value));
		return getCentered(repere, res);
	}
}
