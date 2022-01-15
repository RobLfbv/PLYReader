package view.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

/**
 * Classe permettant le rendu de polygone sur un canvas java fx mais avec des
 * degrades (utilise pour le smoothing)
 * 
 * @author moa20
 *
 */
public class SmoothPolygonRenderer {

	/**
	 * Le canvas sur lequel dessiner
	 */
	private Canvas canvas;
	/**
	 * le pixelwriter de ce canvas
	 */
	private PixelWriter pixelWriter;

	/**
	 * mode de lissage
	 */
	private SmoothingMode smoothingMode;

	/**
	 * cree une instance pour pouvoir dessiner
	 * 
	 * @param canvas        le canvas sur lequel dessiner
	 * @param smoothingMode le mode de lissage
	 */
	public SmoothPolygonRenderer(Canvas canvas, SmoothingMode smoothingMode) {
		this.canvas = canvas;
		this.pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
		this.smoothingMode = smoothingMode;
	}

	/**
	 * Dessine un polygone a partir des coordonees et des couleurs de ses points
	 * 
	 * @param xs les coordonnees en x des points
	 * @param ys les coordonnees en y des points
	 * @param cs les couleurs des points
	 */
	public void fillPolygonWithGradient(double[] xs, double[] ys, Color[] cs) {
		double minX = xs[0], maxX = xs[0], minY = ys[0], maxY = ys[0];

		for (int i = 1; i < xs.length; i++) {
			if (xs[i] < minX)
				minX = xs[i];
			if (xs[i] > maxX)
				maxX = xs[i];
			if (ys[i] < minY)
				minY = ys[i];
			if (ys[i] > maxY)
				maxY = ys[i];
		}

		for (int y = (int) Math.max(0, Math.floor(minY)); y < (int) Math.min(canvas.getHeight(),
				Math.ceil(maxY)); y++) {
			for (int x = (int) Math.max(0, Math.floor(minX)); x < (int) Math.min(canvas.getWidth(),
					Math.ceil(maxX)); x++) {
				Color pixelColor = Color.BLACK;
				if (smoothingMode == SmoothingMode.OLDSCHOOL) {
					pixelColor = getColorOfPixelRandom(x, y, xs, ys, cs);
				} else if (smoothingMode == SmoothingMode.SMOOTH) {
					pixelColor = getColorOfPixel(x, y, xs, ys, cs);
				}

				if (pixelColor != null) {
					this.pixelWriter.setColor(x, y, pixelColor);
				}

			}
		}

	}

	/**
	 * Calcule les poids des distances du pixel par rapport aux points du polygone a
	 * partir de ses coordonnees barycentriques, ne marche que pour les triangles
	 * 
	 * @param x  coordonnee x du pixel
	 * @param y  coordonnee y du pixel
	 * @param xs coordonnees x des points du triangle
	 * @param ys coordonnees y des points du triangle
	 * @param cs couleurs des points du triangle
	 * @return la liste des poids
	 */
	private double[] getWeightsOfPixelInTriangle(int x, int y, double[] xs, double[] ys) {
		double div = (ys[1] - ys[2]) * (xs[0] - xs[2]) + (xs[2] - xs[1]) * (ys[0] - ys[2]);
		double weight1 = ((ys[1] - ys[2]) * (x - xs[2]) + (xs[2] - xs[1]) * (y - ys[2])) / div;
		double weight2 = ((ys[2] - ys[0]) * (x - xs[2]) + (xs[0] - xs[2]) * (y - ys[2])) / div;
		double weight3 = 1 - weight1 - weight2;

		return new double[] { weight1, weight2, weight3 };
	}

	/**
	 * Calcule les poids des distances du pixel par rapport aux points du polygone a
	 * partir de sa distance moyenne avec les point du polygone, methode naive mais
	 * qui marche pour tout type de polygones
	 * 
	 * @param x  coordonnee x du pixel
	 * @param y  coordonnee y du pixel
	 * @param xs coordonnees x des points du polygone
	 * @param ys coordonnees y des points du polygone
	 * @param cs couleurs des points du polygone
	 * @return la liste des poids
	 */
	private double[] getWeightsOfPixelInPolygon(int x, int y, double[] xs, double[] ys) {
		double[] dists = new double[xs.length];
		for (int i = 0; i < xs.length; i++) {
			double dist = Math.sqrt(Math.pow((xs[i] - x), 2) + Math.pow((ys[i] - y), 2));
			dists[i] = dist;
		}
		double[] percents = new double[dists.length];
		double totalPercent = 0;
		for (int i = 0; i < dists.length; i++) {
			percents[i] = 1 / dists[i];
			totalPercent += percents[i];
		}

		double[] res = new double[xs.length];

		for (int i = 0; i < percents.length; i++) {
			res[i] = percents[i] / totalPercent;
		}

		return res;
	}

	/**
	 * Retourne la couleur du pixel en fonction des poids des distances entre ce
	 * pixel et les points du polygone
	 * 
	 * @param x  coordonnee x du pixel
	 * @param y  coordonnee y du pixel
	 * @param xs coordonnees x des points du polygone
	 * @param ys coordonnees y des points du polygone
	 * @param cs couleurs des points du polygone
	 * @return null si le pixel n'est pas dans le polygone
	 */
	private Color getColorOfPixel(int x, int y, double[] xs, double[] ys, Color[] cs) {
		double[] weights;
		final int numberOfSidesInTriangle = 3;
		if (xs.length == numberOfSidesInTriangle) {
			weights = getWeightsOfPixelInTriangle(x, y, xs, ys);
			if (weights[0] < 0 || weights[1] < 0 || weights[2] < 0)
				return null;
		} else {
			if (!isPointInPolygon(x, y, xs, ys))
				return null;
			weights = getWeightsOfPixelInPolygon(x, y, xs, ys);
		}

		double sumR = 0, sumG = 0, sumB = 0, sumA = 0;
		for (int i = 0; i < xs.length; i++) {
			Color c = cs[i];
			sumR += c.getRed() * weights[i];
			sumG += c.getGreen() * weights[i];
			sumB += c.getBlue() * weights[i];
			sumA += c.getOpacity() * weights[i];
		}

		return Color.color(Math.max(0, Math.min(sumR, 1)), Math.max(0, Math.min(sumG, 1)),
				Math.max(0, Math.min(sumB, 1)), Math.max(0, Math.min(sumA, 1)));

	}

	/**
	 * Retourne la couleur du pixel en fonction des poids des distances entre ce
	 * pixel et les points du polygone, choisi aleatoirement la couleur
	 * proportionnelement aux poids des distances
	 * 
	 * @param x  coordonnee x du pixel
	 * @param y  coordonnee y du pixel
	 * @param xs coordonnees x des points du polygone
	 * @param ys coordonnees y des points du polygone
	 * @param cs couleurs des points du polygone
	 * @return null si le pixel n'est pas dans le polygone
	 */
	private Color getColorOfPixelRandom(int x, int y, double[] xs, double[] ys, Color[] cs) {
		double[] weights;
		final int numberOfSidesInTriangle = 3;
		if (xs.length == numberOfSidesInTriangle) {
			weights = getWeightsOfPixelInTriangle(x, y, xs, ys);
			if (weights[0] < 0 || weights[1] < 0 || weights[2] < 0)
				return null;
		} else {
			if (!isPointInPolygon(x, y, xs, ys))
				return null;
			weights = getWeightsOfPixelInPolygon(x, y, xs, ys);
		}

		double randomChoice = Math.random();
		double sum = 0;
		for (int i = 0; i < cs.length; i++) {
			sum += weights[i];
			if (sum >= randomChoice) {
				return cs[i];
			}
		}

		return cs[cs.length - 1];
	}

	/**
	 * Permet de savoir si un point est inclu ou non dans le polygone
	 * 
	 * @param x  la coordonnee en x du point
	 * @param y  la coordonnee en y du point
	 * @param xs coordonnees x des points du polygone
	 * @param ys coordonnees y des points du polygone
	 * @return true si le point est dans le polygone, false sinon
	 */
	private boolean isPointInPolygon(int x, int y, double[] xs, double[] ys) {
		int i, j;
		boolean isInside = false;

		for (i = 0, j = xs.length - 1; i < xs.length; j = i++) {
			if ((ys[i] >= y) != (ys[j] >= y) && x <= (xs[j] - xs[i]) * (y - ys[i]) / (ys[j] - ys[i]) + xs[i]) {
				isInside = !isInside;
			}
		}
		return isInside;
	}
}
