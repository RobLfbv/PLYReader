package sides;

import compare.XYZFacesComp;
import model.PLYData;
import model.Point;
import utils.Matrice;

/**
 * Class qui représente les différents cotés des quels ont peut voir un modèle
 * 3D
 */
public abstract class Side {
	/**
	 * 0=x 1=y 2=z
	 */
	protected int i;

	/**
	 * Crée un coté
	 * 
	 * @param i 0=x 1=y 2=z
	 */
	protected Side(int i) {
		this.i = i;
	}

	/**
	 *
	 * Récupère le comparator correspondant au coté
	 *
	 * @param data la data correspondante
	 * @return le comparator
	 */
	public XYZFacesComp getComparator(PLYData data) {
		return new XYZFacesComp(data, i);
	}

	/**
	 * Retourne la coordonnée X du point s'il été dessiné sur un canvas
	 */

	public abstract double getXOfPoint(Point point);

	/**
	 * Retourne la coordonnée Y du point s'il été dessiné sur un canvas
	 */
	public abstract double getYOfPoint(Point point);

	/**
	 * Retourne la matrice de translation a appliquer sur le modèle qui correspond à
	 * un drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	public abstract Matrice getTranslationFromDrag(double xDelta, double yDelta);

	/**
	 * Retourne la matrice de rotation a appliquer sur le modèle qui correspond à un
	 * drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	public abstract Matrice getRotationFromDrag(double xDelta, double yDelta);

	/**
	 * retourne le nombre correspondant au coté (0=x 1=y 2=z)
	 * 
	 * @return
	 */
	public int getI() {
		return i;
	}

	/**
	 * Méthode qui vérifie l'égalité de deux cotés
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
