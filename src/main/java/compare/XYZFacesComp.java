package compare;

import model.Face;
import model.PLYData;

/**
 * Cette interface permet de faire des tris entre 2 faces dans n'importe quelle
 * classe qui l'implemente
 */
public class XYZFacesComp {
	/**
	 * p- Le PLYData utilisé
	 */
	protected PLYData p;
	/**
	 * i- Le sens du tri souhaité, 0 si X, 1 si Y, 2 si Z
	 */
	protected int i;

	/**
	 * Constructeur qui crée un objet qui permet de comparer avec 2 attributs
	 * 
	 * @param p- Le PLYData utilisé
	 * @param i- Le sens du tri souhaité, 0 si X, 1 si Y, 2 si Z
	 */

	public XYZFacesComp(PLYData p, int i) {
		this.p = p;
		this.i = i;
	}

	/**
	 * Permets de comparer deux points
	 * 
	 * @param o1 - le premier face à comparer
	 * @param o2 - le deuxième face à comparer
	 * @return renvois 0 si égaux, un nombre supérieur à 0 si o1 est supérieur à o2
	 *         et un nombre inférieur à 0 si o1 est inférieur à o2
	 */
	public int compare(Face o1, Face o2) {
		return Double.compare(o1.getMin(p, i).get(i), o2.getMin(p, i).get(i));
	}
}
