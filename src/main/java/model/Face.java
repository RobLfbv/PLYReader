package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.paint.Color;
import view.utils.ColorConstrain;

/**
 * Cette classe sert a repertorier les indices (dans la Map de PLYData) des
 * points et la couleur qui constituent la face
 */
public class Face {
	/**
	 * Indices des points dans la Map de PLYData qui constituent la face
	 */
	private int[] points;
	/**
	 * Couleur de la face
	 */
	private Color color;
	/**
	 * Couleur initial de la face si il n'y a pas de couleur
	 */
	private Color colorInit;
	/**
	 * Boolean pour savoir si la face est a afficher
	 */
	private boolean visible;

	/**
	 * Creer une nouvelle faace
	 * 
	 * @param points = indice des points qui constitue la face
	 * @param color  = couleur de la face fournis dans le PLY
	 */
	private Face(int[] points, Color color) {
		this.points = points;
		this.color = color;
		this.colorInit = color;
		this.visible = true;
	}

	/**
	 * Creer une nouvelle faace
	 * 
	 * @param points = indice des points qui constitue la face
	 */
	public Face(int[] points) {
		this(points, null);
	}

	/**
	 * Instantie une copie de la face passee en parametre
	 * 
	 * @param face - une face a copier
	 */
	public Face(Face face) {
		this.points = new int[face.getNumberOfPoints()];

		for (int i = 0; i < face.getNumberOfPoints(); i++) {
			this.points[i] = face.getPoints()[i];
		}

		if (face.getColorInit() == null) {
			this.colorInit = null;
		} else {
			Color prev = face.getColorInit();
			this.colorInit = ColorConstrain.color(prev.getRed(), prev.getGreen(), prev.getBlue(), prev.getOpacity());
		}
		this.color = null;
	}

	/**
	 * Recupere tous les points depuis data
	 *
	 * @param data - Objet de type PLYData dans lequel on recupere les points
	 * @return une liste de points depuis data
	 */
	public List<Point> getPointsFromData(PLYData data) {
		List<Point> res = new ArrayList<>();
		for (int id : points) {
			res.add(data.getPoint(id));
		}
		return res;
	}

	/**
	 * Attribue une nouvelle liste d'indice de point a la face
	 * 
	 * @param points = la nouvelle liste d'indice de point
	 */
	public void setFace(int[] points) {
		System.arraycopy(points, 0, this.points, 0, points.length);
	}

	/**
	 * Retourne le nombre de points qui constitue la face
	 * 
	 * @return nombre de points de la face
	 */
	public int getNumberOfPoints() {
		return this.points.length;
	}

	/**
	 * Retourne la couleur de la face
	 * 
	 * @return la couleur de la face
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Retourne la valeur rouge de la face
	 * 
	 * @return la valeur rouge de la face
	 */
	public double getColorRed() {
		return this.color.getRed();
	}

	/**
	 * Retourne la valeur verte de la face
	 * 
	 * @return la valeur verte de la face
	 */
	public double getColorGreen() {
		return this.color.getGreen();
	}

	/**
	 * Retourne la valeur bleue de la face
	 * 
	 * @return la valeur bleue de la face
	 */
	public double getColorBlue() {
		return this.color.getBlue();
	}

	/**
	 * Retourne la valeur de l'opacite de la face
	 * 
	 * @return la valeur de l'opacite de la face
	 */
	public double getColorOpacity() {
		return this.color.getOpacity();
	}

	public Color getColorInit() {
		return colorInit;
	}

	private double getMinMax(Comparator<Point> compare, PLYData data, int i) {
		List<Point> stk = this.getPointsFromData(data);
		Point p = new Point(Collections.min(stk, compare));
		return p.get(i);
	}

	/**
	 * Cherche le point avec la valeur en i la plus faible dans une face
	 * 
	 * @param i 0 si X, 1 si Y, 2 si Z
	 * @return retourne le point trouve
	 */

	public double getMin(PLYData data, int i, int y) {
		Comparator<Point> compare = (arg0, arg1) -> Double.compare(arg0.get(i), arg1.get(i));
		return getMinMax(compare, data, y);
	}

	@Override
	public String toString() {
		return "Face [points=" + Arrays.toString(points) + ", color=" + color + ", colorInit=" + colorInit + "]";
	}

	public int[] getPoints() {
		return points;
	}

	public void setPoints(int[] points) {
		this.points = points;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Change la couleur initial
	 * 
	 * @param color - la nouvelle couleur initial
	 */
	public void setInitColor(Color color) {
		this.colorInit = color;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Renvoie le nombre de points qui composent la face
	 * 
	 * @return le nombre de point
	 */
	public int getNbPoints() {
		return this.points.length;
	}

}
