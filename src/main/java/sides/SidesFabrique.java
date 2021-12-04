package sides;

/**
 * Classe qui cr�e des classes side a partir d'un string (leur nom) Patron de
 * conception : Fabrique
 */
public class SidesFabrique {
	/**
	 * cr�e une classes side a partir d'un string
	 * 
	 * @param string le nom
	 * @return un cot�
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
