package view.utils;

import javafx.scene.paint.Color;
import utils.MathUtils;

/**
 * Classe permettant d'instantier des couleur sans risque de depasser les bornes
 * 0 et 1 des valeurs rgba
 *
 */
public final class ColorConstrain {

	/**
	 * Cree une couleur a partir de ses valeurs rgb
	 * 
	 * @param red   - la valeur rouge
	 * @param green - la valeur verte
	 * @param blue  - la valeur bleue
	 * @return la couleur
	 */
	public static Color color(double red, double green, double blue) {
		return Color.color(MathUtils.constrain(red, 0, 1), MathUtils.constrain(green, 0, 1),
				MathUtils.constrain(blue, 0, 1));
	}

	/**
	 * Cree une couleur a partir de ses valeurs rgb et son alpha
	 * 
	 * @param red   - la valeur rouge
	 * @param green - la valeur verte
	 * @param blue  - la valeur bleue
	 * @param alpha - la valeur d'alpha
	 * @return la couleur
	 */
	public static Color color(double red, double green, double blue, double alpha) {
		return Color.color(MathUtils.constrain(red, 0, 1), MathUtils.constrain(green, 0, 1),
				MathUtils.constrain(blue, 0, 1), MathUtils.constrain(alpha, 0, 1));
	}
}
