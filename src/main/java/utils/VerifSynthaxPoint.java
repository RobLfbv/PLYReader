package utils;

import exceptions.PLYSynthaxPointFaceException;
import exceptions.PropertyException;
import model.Model;
import model.plyreader.Header;

/**
 * Classe de verification de la syntaxe d'un point
 */
public class VerifSynthaxPoint {

	/**
	 * Verifie la synthax d'une ligne correspondant a un point
	 * 
	 * @param line    - la ligne a verifier
	 * @param header  - le header
	 * @param numLine - numero de la ligne
	 * @param model   - le modele
	 * @return true si la synthax de la ligne correspondante est correct, false si
	 *         ce n'est pas le cas
	 * @throws PLYSynthaxPointFaceException - Excpetion qui permet d'avertir
	 *                                      lorsqu'il y a une erreur avec un point
	 *                                      ou une face (erreur de syntaxe notament)
	 *                                      + donne l'indice de la ligne d'erreur
	 * @throws PropertyException            - Excpetion qui permet d'avertir
	 *                                      lorsqu'il y a une erreur dans les
	 *                                      parametres
	 */
	public static boolean verifSynthaxPoint(String line, Header header, int numLine, Model model)
			throws PLYSynthaxPointFaceException, PropertyException {
		String[] sline = line.split(" ");
		if (sline.length != header.getSizePropertiesPoint()) {
			throw new PLYSynthaxPointFaceException("Synthax point",
					"There are not enough elements\n (" + line + "), at line ", numLine);
		}
		for (int idx = 0; idx < header.getSizePropertiesPoint(); idx++) {
			if (!verifNumber(sline[idx], header.getPointProperty(idx, 1))) {
				throw new PLYSynthaxPointFaceException("Synthax point",
						"The synthax is incorrect\n (" + line + "), at line ", numLine);
			}
		}
		return true;
	}

	/**
	 * Verifie que le format du nombre est correct par rapport a celui de la
	 * propriete du header
	 * 
	 * @param value     - la string a verifier
	 * @param numFormat -
	 * @return true si le format du nombre est correct par rapport a celui de la
	 *         propriete du header, false si ce n'est pas le cas
	 * @throws PropertyException - Excpetion qui permet d'avertir lorsqu'il y a une
	 *                           erreur dans les parametres
	 */
	public static boolean verifNumber(String value, String numFormat) throws PropertyException {
		boolean result = true;
		if (numFormat.contains("float")) {
			if (!value.matches("[+-]?\\d+(\\.\\d+)?([Ee]-?\\d+)?")) {
				result = false;
			}
		} else if (numFormat.contains("char")) {
			if (!value.matches("\\d+")) {
				result = false;
			} else {
				int nb = Integer.parseInt(value);
				if (nb < 0 || nb > 255) {
					result = false;
				}
			}
		} else {
			throw new PropertyException();
		}
		return result;
	}

}
