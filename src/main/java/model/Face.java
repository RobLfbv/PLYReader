package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Cette classe sert à répertorier les indices (dans la Map de PLYData) des
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
	 * Creer une nouvelle faace
	 * 
	 * @param points = indice des points qui constitue la face
	 * @param color  = couleur de la face fournis dans le PLY
	 */
	public Face(int[] points, Color color) {
		this.points = points;
		this.color = color;
	}

	/**
	 * Creer une nouvelle faace
	 * 
	 * @param points = indice des points qui constitue la face
	 */
	public Face(int[] points) {
		this(points, null);
		// this(points, new Color((int)(Math.random()*255),
		// (int)(Math.random()*255),(int)(Math.random()*255)));
	}

	/**
	 * Instantie une copie de la face pass�e en param�tre
	 * 
	 * @param face
	 */
	public Face(Face face) {
		this.points = new int[face.getNumberOfPoints()];

		for (int i = 0; i < face.getNumberOfPoints(); i++) {
			this.points[i] = face.getPoints()[i];
		}

		if (face.color == null) {
			this.color = null;
		} else {
			this.color = new Color(face.getColor());
		}
	}

	/**
	 * Retourne un tableau d'indices de points qui constituent la face
	 * 
	 * @return tableau d'indices
	 */
	public int[] getPointsIds() {
		return points;
	}

	/**
	 * Récupère tous les points depuis data
	 *
	 * @param data - Objet de type PLYData dans lequel on récupère les points
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
	 * Calcul la moyenne des couleurs associès au points de la face
	 * 
	 * @param listPoint  - Map des points associés à leur id
	 * @param listPoints - Tableau des id de points
	 */
	public void faceColoringAverage(Map<Integer, Point> listPoint, int[] listPoints) {
		int totalAlpha = 0;
		int totalRed = 0;
		int totalGreen = 0;
		int totalBlue = 0;
		for (int point : listPoints) {
			totalAlpha += listPoint.get(point).getColor().getA();
			totalRed += listPoint.get(point).getColor().getR();
			totalGreen += listPoint.get(point).getColor().getG();
			totalBlue += listPoint.get(point).getColor().getB();
		}
		int n = listPoints.length;
		this.color = new Color(totalRed / n, totalGreen / n, totalBlue / n, totalAlpha / n);
	}

	/**
	 * Change l'attribut couleur depuis le fichier PLY
	 * 
	 * @param parameters     - liste des couleurs
	 * @param listPointModel - Map des points associés à leur id
	 * @param listPointsFace - Tableau des id de points
	 */
	public void setFaceColorFromPLY(String[] parameters, Map<Integer, Point> listPointModel, int[] listPointsFace) {
		if (Integer.parseInt(parameters[0]) + 4 == parameters.length) {
			color = new Color(Integer.parseInt(parameters[Integer.parseInt(parameters[0]) + 1]),
					Integer.parseInt(parameters[Integer.parseInt(parameters[0]) + 2]),
					Integer.parseInt(parameters[Integer.parseInt(parameters[0]) + 3]));
			this.setColor(color);
		} else {
			this.faceColoringAverage(listPointModel, listPointsFace);
		}
	}

	/**
	 * Attribue une nouvelle liste d'indice de point à la face
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

	private Point getMinMax(Comparator<Point> compare, PLYData data) {
		List<Point> stk = this.getPointsFromData(data);
		return Collections.min(stk, compare);
	}

	/**
	 * Cherche le point avec la valeur en i la plus faible dans une face
	 * 
	 * @param i 0 si X, 1 si Y, 2 si Z
	 * @return retourne le point trouve
	 */
	public Point getMin(PLYData data, int i) {
		Comparator<Point> compare = (arg0, arg1) -> Double.compare(arg0.get(i), arg1.get(i));
		return getMinMax(compare, data);
	}

	/**
	 * Récupère la composante idu point donné en paramètre
	 * 
	 * @param p - le point
	 * @param i - la composante du point, 0 si X, 1 si Y, 2 si Z
	 * @return un double qui correspond à X, Y ou Z
	 */
	public static double get(Point p, int i) {
		return p.get(i);
	}

	@Override
	public String toString() {
		return "Face [points=" + Arrays.toString(points) + ", color=" + color + "]";
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

}
