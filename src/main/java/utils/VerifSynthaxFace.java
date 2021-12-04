package utils;

import exceptions.PLYSynthaxPointFaceException;

/**
 * Cette classe contient les méthode nécessaire à la vérification de la
 * synthaxe d'une ligne correspondant à une face.
 */
public class VerifSynthaxFace {

	/**
	 * Méthode principale, celle à utiliser, pour vérifier la synthaxe d'une
	 * ligne correspondant à une face
	 * 
	 * @param line
	 * @param nbPoints
	 * @param numLine
	 * @throws PLYSynthaxPointFaceException
	 */
	public static void verifSynthaxFace(String line, int nbPoints, int numLine) throws PLYSynthaxPointFaceException {
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
	}

	/**
	 * Vérifie que la ligne n'est composée que de nombres décimaux, suivi ou non
	 * de trois nombres correspondants aux couleurs
	 * 
	 * @param line
	 * @return
	 */
	public static boolean decimalSynthaxFace(String line) {
		if (!line.matches("(\\d+ )*(\\d+) *(( [+-]?\\d+){3})? *"))
			return false;
		return true;
	}

	/**
	 * Vérifie que le nombre de points qui forme la face est cohérent avec le
	 * premier nombre indiquant le nombre de point composant la face
	 * 
	 * @param element
	 * @return
	 */
	public static boolean nbElementSynthaxFace(String[] element) {
		if (Integer.parseInt(element[0]) != element.length - 1
				&& Integer.parseInt(element[0]) + 3 != element.length - 1) {
			// System.out.println("Mauvais nombre d'éléments ");
			return false;
		}
		return true;
	}

	/**
	 * Vérifie que l'ID des points qui composent la face est cohérent
	 * 
	 * @param element
	 * @param nbPoints
	 * @return
	 */
	public static boolean idCoherentSynthax(String[] element, int nbPoints) {
		for (int idx = 1; idx < Integer.parseInt(element[0]); idx++) {
			if (Integer.parseInt(element[idx]) > nbPoints || Integer.parseInt(element[idx]) < 0) {
				// System.out.println("ID des points incohérent");
				return false;
			}
		}
		return true;
	}

	/**
	 * Vérifie que les couleurs renseignées ont une valeur comprise entre 0 et 255
	 * incluts
	 * 
	 * @param element
	 * @return
	 */
	public static boolean couleurValideFace(String[] element) {
		for (int idx = Integer.parseInt(element[0]) + 1; element.length == Integer.parseInt(element[0]) + 4
				&& idx < element.length; idx++) {
			if (Integer.parseInt(element[idx]) < 0 || Integer.parseInt(element[idx]) > 255) {
				// System.out.println("code des couleurs invalide");
				return false;
			}
		}
		return true;
	}
}
