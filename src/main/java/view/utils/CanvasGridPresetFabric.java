package view.utils;

import sides.individualsides.PositiveXSide;
import sides.individualsides.PositiveYSide;
import sides.individualsides.PositiveZSide;

/**
 * Classe qui fabrique des presets de grille a partir d'un string
 */
public final class CanvasGridPresetFabric {

	/**
	 * fabrique un preset de grille a partir d'un string
	 * 
	 * @param string le string
	 * @return le preset
	 */
	public static CanvasGridPreset get(String string) {
		CanvasGridPreset res;
		switch (string) {
		case "Fullscreen":

			return new CanvasGridPreset(1, 1,
					new Parameters(new PositiveZSide(), RenderMode.FACESONLY, ColorConstrain.color(0.4, 0.4, 0.4),
							ColorConstrain.color(0, 0, 0), ColorConstrain.color(0.8, 0.8, 0.8), true, true, 1.1, 0.5,
							false, SmoothingMode.SMOOTH));

		case "3 Sides":
			res = new CanvasGridPreset(2, 2);
			res.addParameters(new Parameters(new PositiveXSide(), RenderMode.FACESONLY,
					ColorConstrain.color(0.4, 0.4, 0.4), ColorConstrain.color(0, 0, 0),
					ColorConstrain.color(0.8, 0.8, 0.8), true, true, 1.1, 0.5, false, SmoothingMode.SMOOTH));
			res.addParameters(new Parameters(new PositiveYSide(), RenderMode.FACESONLY,
					ColorConstrain.color(0.4, 0.4, 0.4), ColorConstrain.color(0, 0, 0),
					ColorConstrain.color(0.8, 0.8, 0.8), true, true, 1.1, 0.5, false, SmoothingMode.SMOOTH));
			res.addParameters(new Parameters(new PositiveZSide(), RenderMode.FACESONLY,
					ColorConstrain.color(0.4, 0.4, 0.4), ColorConstrain.color(0, 0, 0),
					ColorConstrain.color(0.8, 0.8, 0.8), true, true, 1.1, 0.5, false, SmoothingMode.SMOOTH));
			return res;

		case "Checkerboard":
			res = new CanvasGridPreset(5, 5);
			for (int i = 0; i < 25; i++) {
				if (i % 2 == 0) {
					res.addParameters(new Parameters(new PositiveZSide(), RenderMode.FACESONLY,
							ColorConstrain.color(0.4, 0.4, 0.4), ColorConstrain.color(0, 0, 0),
							ColorConstrain.color(0.8, 0.8, 0.8), true, true, 1.1, 0.5, false, SmoothingMode.SMOOTH));
				} else {
					res.addParameters(new Parameters(new PositiveZSide(), RenderMode.EDGESONLY,
							ColorConstrain.color(0.4, 0.4, 0.4), ColorConstrain.color(0, 1, 1),
							ColorConstrain.color(0, 0, 0), true, true, 1.1, 0.5, false, SmoothingMode.SMOOTH));
				}
			}
			return res;

		default:
			return null;
		}

	}
}
