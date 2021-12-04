package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compare.Sort;
import compare.XYZFacesComp;
import utils.Matrice;

/**
 * Cette classe sert Ã  stocker tous les points dans une map (avec donc un
 * indice) + toutes les faces dans une liste
 */
public class PLYData {
	/**
	 * Liste de points (id , Point)
	 */
	private Map<Integer, Point> points;
	/**
	 * Liste des faces
	 */
	private List<Face> faces;

	/**
	 * Instantie un objet data
	 */
	public PLYData() {
		points = new HashMap<Integer, Point>();
		faces = new ArrayList<>();
	}

	/**
	 * Créer un objet data a partir d'une liste de points et d'une liste de faces
	 * 
	 * @param points liste de points
	 * @param faces  listes de face
	 */
	public PLYData(Map<Integer, Point> points, List<Face> faces) {
		this.points = points;
		this.faces = faces;
	}

	/**
	 * Instantie une copie de la data passï¿½e en parametre
	 * 
	 * @param data la data à copier
	 */
	public PLYData(PLYData data) {
		this();

		for (Face face : data.getFaces()) {
			this.addFace(new Face(face));
		}

		for (Map.Entry<Integer, Point> entry : data.getPoints().entrySet()) {
			this.putPoint(entry.getKey(), new Point(entry.getValue()));
		}
	}

	/**
	 * Retourne le point correspondant à l'id
	 * 
	 * @param id l'id
	 * @return le point correspondant
	 */
	public Point getPoint(int id) {
		return this.points.get(id);
	}

	/**
	 * ajouter un point a la liste des points
	 * 
	 * @param id    l'id du point
	 * @param point le point
	 * @return
	 */
	public Point putPoint(int id, Point point) {
		return points.put(id, point);
	}

	/**
	 * Ajoute une face à la liste des faces
	 * 
	 * @param face la face
	 */
	public void addFace(Face face) {
		this.faces.add(face);
	}

	/**
	 * Applique une transformation de matrice "transformationMatrix" à tous les
	 * points de la data
	 * 
	 * @param transformationMatrix la matrice de transformation
	 */
	public void applyTransformationWithMatrix(Matrice transformationMatrix) {
		for (Map.Entry<Integer, Point> entry : points.entrySet()) {
			Point point = entry.getValue();
			point.setMatrix(Matrice.multiply(transformationMatrix, point.getMatrix()));
		}
	}

	/**
	 * tri toutes les faces selon l'algorithme du quicksort
	 * 
	 * @param comparator le comparator
	 * @param beginning  le debut de la liste
	 * @param end        la fin de la liste
	 * @param faces      la liste des faces
	 */
	public static void quickSortFace(XYZFacesComp comparator, int beginning, int end, List<Face> faces) {
		Sort.quickSortFace(comparator, beginning, end, faces);
	}

	/**
	 * tri toutes les faces selon l'algorithm du heapSort
	 * 
	 * @param comparator le comparator
	 * @param faces      la liste des faces
	 */
	public static void heapSortFace(XYZFacesComp comparator, List<Face> faces) {
		Sort.heapSortFace(comparator, faces);
	}

	@Override
	public String toString() {
		return "PLYData [points=" + points + ", faces=" + faces + "]";
	}

	/**
	 * Retourne la map de tous les points de la data
	 * 
	 * @return la map
	 */
	public Map<Integer, Point> getPoints() {
		return points;
	}

	/**
	 * Remplace la map de tous les points par une nouvelle
	 * 
	 * @param points la nouvelle map
	 */
	public void setPoints(Map<Integer, Point> points) {
		this.points = points;
	}

	/**
	 * récupère la liste des faces
	 * 
	 * @return la liste
	 */
	public List<Face> getFaces() {
		return faces;
	}

	/**
	 * change la liste des faces
	 * 
	 * @param faces la nouvelle liste
	 */
	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	/**
	 * Recupère la coordonée minimale de tous les points
	 * 
	 * @param i (x=0 y=0 z=0)
	 * @return la coordonnées
	 */
	public double getMin(int i) {
		double min = Double.MAX_VALUE;
		for (Map.Entry<Integer, Point> entry : points.entrySet()) {
			if (entry.getValue().get(i) < min) {
				min = entry.getValue().get(i);
			}
		}
		return min;
	}

	/**
	 * Recupère la coordonée maximale de tous les points
	 * 
	 * @param i (x=0 y=0 z=0)
	 * @return la coordonnées
	 */
	public double getMax(int i) {
		double max = Double.MIN_VALUE;
		for (Map.Entry<Integer, Point> entry : points.entrySet()) {
			if (entry.getValue().get(i) > max) {
				max = entry.getValue().get(i);
			}
		}
		return max;
	}

	/**
	 * Retourne la moyenne des coordonée de tous les points Utilisé pour avoir une
	 * approximation de l'echelle du modèle à son chargement
	 * 
	 * @return la moyenne
	 */
	public double getAverageAbsoluteXYZ() {
		double sum = 0;

		for (Map.Entry<Integer, Point> entry : points.entrySet()) {
			sum += Math.abs(entry.getValue().get(0));
			sum += Math.abs(entry.getValue().get(1));
			sum += Math.abs(entry.getValue().get(2));
		}

		return sum / points.entrySet().size() * 3;
	}

}
