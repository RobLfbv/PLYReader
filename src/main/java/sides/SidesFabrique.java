package sides;

/**
 * Classe qui crée des classes side a partir d'un string (leur nom) Patron de
 * conception : Fabrique
 */
public class SidesFabrique {
	/**
	 * crée une classes side a partir d'un string
	 * 
	 * @param string le nom
	 * @return un coté
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
