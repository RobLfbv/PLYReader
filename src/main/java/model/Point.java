package model;

import utils.Matrice;

/**
 * Cette classe permet de stocker les coordonnées du point dans une matrice +
 * la couleur de celui-ci si elle est fournis (sinon il sera blanc)
 */
public class Point {
	/**
	 * matrix - Matrice du point
	 */
	private Matrice matrix;
	/**
	 * color - Couleur du point
	 */
	private Color color;

	/**
	 * Constructeur entièrement paramétré
	 * 
	 * @param x     - élément x de la matrice
	 * @param y     - élément y de la matrice
	 * @param z     - élément z de la matrice
	 * @param color - couleur du point
	 */
	public Point(double x, double y, double z, Color color) {
		this.matrix = new Matrice(new double[][] { { x }, { y }, { z }, { 1 } });
		this.color = color;
	}

	/**
	 * Constructeur sans couleur
	 * 
	 * @param x - élément x de la matrice
	 * @param y - élément y de la matrice
	 * @param z - élément z de la matrice
	 */
	public Point(double x, double y, double z) {
		this(x, y, z, new Color(255, 255, 255));
	}

	/**
	 * Constructeur de point avec point
	 * 
	 * @param point - le point utilisé pour en créer un nouveau
	 */
	public Point(Point point) {
		this(point.get(0), point.get(1), point.get(2), new Color(point.getColor()));
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Accesseur de l'élément i dans la matrice
	 * 
	 * @param i- L'éléemnt souhaité, 0 si X, 1 si Y, 2 si Z
	 * @return la valeur de l'élément demandé
	 */
	public double get(int i) {
		return matrix.get(i, 0);
	}

	/**
	 * Définis X de la matrice du point
	 * 
	 * @param x - nouvelle valeur de X
	 */
	public void setX(double x) {
		matrix.set(0, 0, x);
	}

	/**
	 * Définis Y de la matrice du point
	 * 
	 * @param y - nouvelle valeur de Y
	 */
	public void setY(double y) {
		matrix.set(1, 0, y);
	}

	/**
	 * Définis Z de la matrice du point
	 * 
	 * @param z - nouvelle valeur de Z
	 */
	public void setZ(double z) {
		matrix.set(2, 0, z);
	}

	public Matrice getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrice matrix) {
		this.matrix = matrix;
	}

	@Override
	public String toString() {
		return "Point [x=" + get(0) + ", y=" + get(1) + ", z=" + get(2) + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point p = (Point) obj;
		if (getMatrix().equals(p.getMatrix()) && getColor().equals(p.getColor())) {
			return true;
		}
		return false;
	}

}
