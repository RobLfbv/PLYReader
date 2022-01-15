package utils;

import exceptions.PLYSynthaxPointFaceException;
import model.Model;

/**
 * Cette classe contient les methode necessaire a la verification de la synthaxe
 * d'une ligne correspondant a une face.
 */
public class VerifSynthaxFace {

	/**
	 * Methode principale, celle a utiliser, pour verifier la synthaxe d'une ligne
	 * correspondant a une face
	 * 
	 * @param line     - la ligne a verifier
	 * @param nbPoints - le nombre de points
	 * @param numLine  - le numero de la ligne
	 * @param model    - le modele
	 * @return true si la ligne est correcte, false si la ligne n'est pas correcte
	 * @throws PLYSynthaxPointFaceException - Excpetion qui permet d'avertir
	 *                                      lorsqu'il y a une erreur avec un point
	 *                                      ou une face (erreur de syntaxe notament)
	 *                                      + donne l'indice de la ligne d'erreur
	 */
	public static boolean verifSynthaxFace(String line, int nbPoints, int numLine, Model model)
			throws PLYSynthaxPointFaceException {
		if (!decimalSynthaxFace(line)) {
			throw new PLYSynthaxPointFaceException("Synthax face", "The synthax is incorrect,\n at line ", numLine);
		}
		String[] element = line.split(" +");
		if (!nbElementSynthaxFace(element)) {
			throw new PLYSynthaxPointFaceException("Number of elements",
					"The Number of elements is incoherent \n(too many or not enough point or color)\n, at line ",
					numLine);
		}
		if (!idCoherentSynthax(element, nbPoints)) {
			throw new PLYSynthaxPointFaceException("Incoherent ID",
					"The ID of the points is incoherent \n(less than 0 or more than the number of points),\n at line ",
					numLine);
		}
		if (!couleurValideFace(element)) {
			throw new PLYSynthaxPointFaceException("Color face",
					"Incoherent color \n(more than 255 or less than 0),\n at line ", numLine);
		}
		return true;
	}

	/**
	 * Verifie que la ligne n'est composee que de nombres decimaux, suivi ou non de
	 * trois nombres correspondants aux couleurs
	 * 
	 * @param line - la ligne a verifier
	 * @return true si la ligne respecte les conditions, false si ce n'est pas le
	 *         cas
	 */
	public static boolean decimalSynthaxFace(String line) {
		if (!line.matches("(\\d+ )*(\\d+) *(( [+-]?\\d+){3})? *"))
			return false;
		return true;
	}

	/**
	 * Verifie que le nombre de points qui forme la face est coherent avec le
	 * premier nombre indiquant le nombre de point composant la face
	 * 
	 * @param element - les elements a verifier
	 * @return true si c'est coherent, false si ce n'est pas le cas
	 */
	public static boolean nbElementSynthaxFace(String[] element) {
		if (Integer.parseInt(element[0]) != element.length - 1
				&& Integer.parseInt(element[0]) + 3 != element.length - 1) {
			return false;
		}
		return true;
	}

	/**
	 * Verifie que l'ID des points qui composent la face est coherent
	 * 
	 * @param element  - les elements a verifier
	 * @param nbPoints - le nombre de points
	 * @return true si c'est coherent, false si ça ne l'est pas
	 */
	public static boolean idCoherentSynthax(String[] element, int nbPoints) {
		for (int idx = 1; idx < Integer.parseInt(element[0]); idx++) {
			if (Integer.parseInt(element[idx]) > nbPoints || Integer.parseInt(element[idx]) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifie que les couleurs renseignees ont une valeur comprise entre 0 et 255
	 * incluts
	 * 
	 * @param element - les elements a verifier
	 * @return true si les valeurs sont bien comprisent entre 0 et 255, false si ce
	 *         n'est pas le cas
	 */
	public static boolean couleurValideFace(String[] element) {
		for (int idx = Integer.parseInt(element[0]) + 1; element.length == Integer.parseInt(element[0]) + 4
				&& idx < element.length; idx++) {
			if (Integer.parseInt(element[idx]) < 0 || Integer.parseInt(element[idx]) > 255) {
				return false;
			}
		}
		return true;
	}
}
