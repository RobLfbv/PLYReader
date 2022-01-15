package sides;

import sides.individualsides.PositiveXSide;
import sides.individualsides.PositiveYSide;
import sides.individualsides.PositiveZSide;

/**
 * Classe qui cree des classes side a partir d'un string (leur nom) Patron de
 * conception : Fabrique
 */
public class SidesFabrique {
	/**
	 * cree une classes side a partir d'un string
	 * 
	 * @param string le nom
	 * @return un cote
	 */
	public Side create(String string) {
		if (string.equals("Positive X")) {
			return new PositiveXSide();
		}
		if (string.equals("Positive Y")) {
			return new PositiveYSide();
		}
		if (string.equals("Positive Z")) {
			return new PositiveZSide();
		}
		return null;
	}
}
