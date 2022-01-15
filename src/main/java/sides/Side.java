package sides;

import java.util.List;
import java.util.Set;

import compare.XYZFacesComp;
import model.PLYData;
import model.Point;
import model.Repere;
import utils.transformations.Transformation;
import utils.transformations.TransformationFabric;
import utils.transformations.TransformationType;

/**
 * Class qui represente les differents cotes des quels ont peut voir un modele
 * 3D
 */
public abstract class Side {
	/**
	 * 0=x 1=y 2=z
	 */
	protected int i;

	/**
	 * Cree un cote
	 * 
	 * @param i 0=x 1=y 2=z
	 */
	protected Side(int i) {
		this.i = i;
	}

	/**
	 *
	 * Recupere le comparator correspondant au cote
	 *
	 * @param data la data correspondante
	 * @return le comparator
	 */
	public XYZFacesComp getComparator(PLYData data) {
		return new XYZFacesComp(data, i);
	}

	/**
	 * Retourne la coordonnee X du point s'il ete dessine sur un canvas
	 * 
	 * @param point - le point
	 * @return x du point
	 */

	public abstract double getXOfPoint(Point point);

	/**
	 * Retourne la coordonnee Y du point s'il ete dessine sur un canvas
	 * 
	 * @param point - le point
	 * @return y du point
	 */
	public abstract double getYOfPoint(Point point);

	/**
	 * Retourne la matrice de translation a appliquer sur le modele qui correspond a
	 * un drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 * 
	 * @param xDelta le delta en x du mouvement de la souris
	 * @param yDelta le delta en y du mouvement de la souris
	 * @return la liste des transformations
	 */
	public abstract List<Transformation> getTranslationFromDrag(double xDelta, double yDelta);

	/**
	 * Retourne la matrice de rotation a appliquer sur le modele qui correspond a un
	 * drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 * 
	 * @param xDelta le delta en x du mouvement de la souris
	 * @param yDelta le delta en y du mouvement de la souris
	 * @return la liste des transformations
	 */
	public abstract List<Transformation> getRotationFromDrag(double xDelta, double yDelta);

	/**
	 * Cree une liste de transformation qui permettent de faire la rotation centree
	 * sur le canvas en fonction du drag de la souris sur ce dernier
	 * 
	 * @param repere le repere du model
	 * @param xDelta le delta en x du mouvement de la souris
	 * @param yDelta le delta en y du mouvement de la souris
	 * @return la liste des transformations
	 */
	public List<Transformation> getCenteredRotationFromDrag(Repere repere, double xDelta, double yDelta) {
		return TransformationFabric.getCentered(repere, this.getRotationFromDrag(xDelta, yDelta));
	}

	/**
	 * retourne le nombre correspondant au cote (0=x 1=y 2=z)
	 * 
	 * @return le nombre correspondant au cote
	 */
	public int getI() {
		return i;
	}

	/**
	 * Retourne vrai s'il faut refaire un rendu pour le type de transformation recu
	 * 
	 * @param types - le type de transformation recu
	 * @return true s'il faut refaireun render false si ce n'est pas le cas
	 */
	public abstract boolean hasToRender(Set<TransformationType> types);

	/**
	 * Retourne vrai s'il faut refaire le tri des faces pour le type de
	 * transformation recu
	 * 
	 * @param types - le type de transformation recu
	 * @return true s'il faut retrier false s'il ne faut pas
	 */
	public boolean hasToSort(Set<TransformationType> types) {
		if (types == null)
			return true;
		return types.contains(TransformationType.XROTATION) || types.contains(TransformationType.YROTATION)
				|| types.contains(TransformationType.ZROTATION) || types.contains(TransformationType.LOADMODEL)
				|| types.contains(TransformationType.CHANGESIDE)
				|| types.contains(TransformationType.UPDATEEVERYPARAMETER);
	}

	/**
	 * Retourne vrai s'il faut refaire le calcul de la lumiere pour le type de
	 * transformation recu
	 * 
	 * @param types le type de transformation recu
	 * @return true s'il faut recalculer la lumiere false s'il ne faut pas
	 */
	public boolean hasToCalculateLight(Set<TransformationType> types) {
		if (types == null)
			return true;
		return types.contains(TransformationType.XROTATION) || types.contains(TransformationType.YROTATION)
				|| types.contains(TransformationType.ZROTATION) || types.contains(TransformationType.LOADMODEL)
				|| types.contains(TransformationType.CHANGESIDE) || types.contains(TransformationType.CHANGELIGHT)
				|| types.contains(TransformationType.CHANGEONLYRENDERVISIBLEFACES)
				|| types.contains(TransformationType.CHANGEAPPEARANCE)
				|| types.contains(TransformationType.UPDATEEVERYPARAMETER);
	}

	/**
	 * Methode qui verifie l'egalite de deux cotes
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Side other = (Side) obj;
		return i == other.i;
	}

}
