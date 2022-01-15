package compare;

import java.util.Comparator;

import model.Face;
import model.PLYData;

/**
 * Cette interface permet de faire des tris entre 2 faces dans n'importe quelle
 * classe qui l'implemente
 */
public class XYZFacesComp implements Comparator<Face>{
	/**
	 * p- Le PLYData utilise
	 */
	private PLYData p;
	/**
	 * i- Le sens du tri souhaite, 0 si X, 1 si Y, 2 si Z
	 */
	private int i;

	/**
	 * Constructeur qui cree un objet qui permet de comparer avec 2 attributs
	 * 
	 * @param p- Le PLYData utilise
	 * @param i- Le sens du tri souhaite, 0 si X, 1 si Y, 2 si Z
	 */

	public XYZFacesComp(PLYData p, int i) {
		this.p = p;
		this.i = i;
	}

	/**
	 * Permets de comparer deux points
	 * 
	 * @param o1 - le premier face a comparer
	 * @param o2 - le deuxieme face a comparer
	 * @return renvois 0 si egaux, un nombre superieur a 0 si o1 est superieur a o2
	 *         et un nombre inferieur a 0 si o1 est inferieur a o2
	 */
	public int compare(Face o1, Face o2) {
		//return Double.compare(o1.getMin(p, i).get(i), o2.getMin(p, i).get(i));
		return Double.compare(o1.getMin(p, i,i), o2.getMin(p, i,i));
	}
}
