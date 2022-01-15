package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import utils.Matrice;
import view.utils.ColorConstrain;

/**
 * Cette classe permet de stocker les coordonnees du point dans une matrice + la
 * couleur de celui-ci si elle est fournis (sinon il sera blanc)
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
	 * La couleur initial du point
	 */
	private Color colorInit;

	/**
	 * Liste des faces dans lesquelles le point est present
	 */
	private List<Face> includedInFaces;

	/**
	 * Constructeur entierement parametre
	 * 
	 * @param x     - element x de la matrice
	 * @param y     - element y de la matrice
	 * @param z     - element z de la matrice
	 * @param color - couleur du point
	 */
	public Point(double x, double y, double z, Color color) {
		this.matrix = new Matrice(new double[][] { { x }, { y }, { z }, { 1 } });
		this.color = color;
		this.colorInit = color;
		includedInFaces = new ArrayList<>();
	}

	/**
	 * Constructeur sans couleur
	 * 
	 * @param x - element x de la matrice
	 * @param y - element y de la matrice
	 * @param z - element z de la matrice
	 */
	public Point(double x, double y, double z) {
		this(x, y, z, null);
	}

	/**
	 * Constructeur de point avec point
	 * 
	 * @param point - le point utilise pour en creer un nouveau
	 */
	public Point(Point point) {
		this(point.get(0), point.get(1), point.get(2),
				point.getColor() == null ? null
						: ColorConstrain.color(point.getColor().getRed(), point.getColor().getGreen(),
								point.getColor().getBlue(), point.getColor().getOpacity()));
	}

	public Color getColor() {
		return this.color;
	}

	/**
	 * Retourne la valeur rouge de la couleur
	 * 
	 * @return la valeur rouge
	 */
	public double getColorRed() {
		return this.color.getRed();
	}

	/**
	 * Retourne la valeur verte de la couleur
	 * 
	 * @return la valeur verte
	 */
	public double getColorGreen() {
		return this.color.getGreen();
	}

	/**
	 * Retourne la valeur bleu de la couleur
	 * 
	 * @return la valeur bleu
	 */
	public double getColorBlue() {
		return this.color.getBlue();
	}

	/**
	 * Retourne la valeur opacitee de la couleur
	 * 
	 * @return la valeur opacitee
	 */
	public double getColorOpacity() {
		return this.color.getOpacity();
	}

	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the listFace
	 */
	public List<Face> getIncludedInFaces() {
		return includedInFaces;
	}

	/**
	 * Ajoute les faces dans lesquelles le point est present
	 * 
	 * @param f - la face a rajouter
	 */
	public void addIncludedInFaces(Face f) {
		includedInFaces.add(f);
	}

	/**
	 * Accesseur de l'element i dans la matrice
	 * 
	 * @param i- L'element souhaite, 0 si X, 1 si Y, 2 si Z
	 * @return la valeur de l'element demande
	 */
	public double get(int i) {
		return matrix.get(i, 0);
	}

	/**
	 * Definis X de la matrice du point
	 * 
	 * @param x - nouvelle valeur de X
	 */
	public void setX(double x) {
		matrix.set(0, 0, x);
	}

	/**
	 * Definis Y de la matrice du point
	 * 
	 * @param y - nouvelle valeur de Y
	 */
	public void setY(double y) {
		matrix.set(1, 0, y);
	}

	/**
	 * Definis Z de la matrice du point
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
		return "Point [matrix=" + matrix + ", color=" + color + ", colorInit=" + colorInit + ", includedInFaces="
				+ includedInFaces + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point p = new Point((Point) obj);
		if (this.matrix.equals(p.getMatrix()) && this.color.equals(p.getColor())) {

			return true;
		}
		return false;
	}

}
