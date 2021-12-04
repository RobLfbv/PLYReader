package utils;

import exceptions.PLYSynthaxPointFaceException;

/**
 * Cette classe contient les méthode nécessaire à la vérification de la
 * synthaxe d'une ligne correspondant à un point.
 */
public class VerifSynthaxPoint {

	/**
	 * Méthode principale, celle à utiliser, pour vérifier la synthaxe d'une
	 * ligne correspondant à un point
	 * 
	 * @param line
	 * @param numLine
	 * @throws PLYSynthaxPointFaceException
	 */
	public static void verifSynthaxPoint(String line, int numLine) throws PLYSynthaxPointFaceException {
		if (!decimalSynthaxPoint(line)) {
			throw new PLYSynthaxPointFaceException("Synthax point",
					"The synthax is incorrect\n (" + line + "), at line ", numLine);
		}
		String[] element = line.split(" +");
		if (!couleurSynthaxPoint(element)) {
			throw new PLYSynthaxPointFaceException("Color point",
					"Incoherent color (more than 255 or less than 0) \n(" + line + "), at line ", numLine);
		}
	}

	/**
	 * Vérifie que la ligne contient des coordonnées de points cohérents avec ou
	 * sans couleur
	 * 
	 * @param line
	 * @return
	 */
	public static boolean decimalSynthaxPoint(String line) {
		if (!line.matches(
				"([+-]?\\d+(\\.\\d+)?([Ee]-?\\d+)? ){2}([+-]?\\d+(\\.\\d+)?([Ee]-?\\d+)?)(( [+-]?\\d+){3})? *")) {
			return false;
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
	public static boolean couleurSynthaxPoint(String[] element) {
		for (int idx = 3; element.length == 6 && idx < element.length; idx++) {
			if (Integer.parseInt(element[idx]) < 0 || Integer.parseInt(element[idx]) > 255)
				return false;
		}
		return true;
	}
}
