package sides;

import compare.XYZFacesComp;
import model.PLYData;
import model.Point;
import utils.Matrice;

/**
 * Class qui repr�sente les diff�rents cot�s des quels ont peut voir un mod�le
 * 3D
 */
public abstract class Side {
	/**
	 * 0=x 1=y 2=z
	 */
	protected int i;

	/**
	 * Cr�e un cot�
	 * 
	 * @param i 0=x 1=y 2=z
	 */
	protected Side(int i) {
		this.i = i;
	}

	/**
	 *
	 * R�cup�re le comparator correspondant au cot�
	 *
	 * @param data la data correspondante
	 * @return le comparator
	 */
	public XYZFacesComp getComparator(PLYData data) {
		return new XYZFacesComp(data, i);
	}

	/**
	 * Retourne la coordonn�e X du point s'il �t� dessin� sur un canvas
	 */

	public abstract double getXOfPoint(Point point);

	/**
	 * Retourne la coordonn�e Y du point s'il �t� dessin� sur un canvas
	 */
	public abstract double getYOfPoint(Point point);

	/**
	 * Retourne la matrice de translation a appliquer sur le mod�le qui correspond �
	 * un drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	public abstract Matrice getTranslationFromDrag(double xDelta, double yDelta);

	/**
	 * Retourne la matrice de rotation a appliquer sur le mod�le qui correspond � un
	 * drag sur un canvas de xDelta pixel sur l'axe x et yDelta pixel sur l'axe y
	 */
	public abstract Matrice getRotationFromDrag(double xDelta, double yDelta);

	/**
	 * retourne le nombre correspondant au cot� (0=x 1=y 2=z)
	 * 
	 * @return
	 */
	public int getI() {
		return i;
	}

	/**
	 * M�thode qui v�rifie l'�galit� de deux cot�s
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
