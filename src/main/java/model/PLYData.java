package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compare.Sort;
import compare.XYZFacesComp;
import utils.Matrice;
import view.utils.ColorConstrain;

/**
 * Cette classe sert a stocker tous les points dans une map (avec donc un
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
		this(new HashMap<Integer, Point>(), new ArrayList<>());
	}

	/**
	 * Creer un objet data a partir d'une liste de points et d'une liste de faces
	 * 
	 * @param points liste de points
	 * @param faces  listes de face
	 */
	public PLYData(Map<Integer, Point> points, List<Face> faces) {
		this.points = points;
		this.faces = faces;
		this.setIncludedInFacesOfPoints();
	}

	/**
	 * Instantie une copie de la data passee en parametre
	 * 
	 * @param data la data a copier
	 */
	public PLYData(PLYData data) {
		this();

		for (Face face : data.getFaces()) {
			this.addFace(new Face(face));
		}

		for (Map.Entry<Integer, Point> entry : data.getPoints().entrySet()) {
			this.putPoint(entry.getKey(), new Point(entry.getValue()));
		}

		this.setIncludedInFacesOfPoints();
	}

	private void setIncludedInFacesOfPoints() {
		for (Face face : faces) {
			for (int id : face.getPoints()) {
				points.get(id).addIncludedInFaces(face);
			}
		}
	}

	/**
	 * Calcule les couleurs des faces a partir de celles des points
	 * 
	 * @param applyToInitColor vrai s'il faut appliquer ce changement aux couleurs
	 *                         initiales des faces ou non
	 */
	public void computeFacesColorsFromPointsColors(boolean applyToInitColor) {
		for (Face face : faces) {
			double sumR = 0, sumG = 0, sumB = 0, sumA = 0;

			if (points.get(face.getPoints()[0]).getColor() == null) {
				face.setColor(null);
				if (applyToInitColor)
					face.setInitColor(null);
			} else {

				for (int idPoint : face.getPoints()) {
					Point point = points.get(idPoint);

					sumR += point.getColor().getRed();
					sumG += point.getColor().getGreen();
					sumB += point.getColor().getBlue();
					sumA += point.getColor().getOpacity();
				}
				int nbPoints = face.getPoints().length;
				if (applyToInitColor)
					face.setInitColor(
							ColorConstrain.color(sumR / nbPoints, sumG / nbPoints, sumB / nbPoints, sumA / nbPoints));
				face.setColor(ColorConstrain.color(sumR / nbPoints, sumG / nbPoints, sumB / nbPoints, sumA / nbPoints));
			}

		}
	}

	/**
	 * Calcule la couleur des points a partir de celles des faces auxquelles il
	 * appartient
	 */
	public void computePointsColorsFromFacesColors() {
		for (Map.Entry<Integer, Point> entry : points.entrySet()) {
			Point point = entry.getValue();

			if (point.getIncludedInFaces().size() == 0 || point.getIncludedInFaces().get(0).getColor() == null) {
				point.setColor(null);
			} else {
				double rSum = 0, gSum = 0, bSum = 0, aSum = 0;
				for (Face face : point.getIncludedInFaces()) {
					if (face == null || face.getColor() == null)
						continue;
					rSum += face.getColor().getRed();
					gSum += face.getColor().getGreen();
					bSum += face.getColor().getBlue();
					aSum += face.getColor().getOpacity();
				}
				int nbFaces = point.getIncludedInFaces().size();
				point.setColor(ColorConstrain.color(rSum / nbFaces, gSum / nbFaces, bSum / nbFaces, aSum / nbFaces));
			}
		}
	}

	/**
	 * Retourne le point correspondant a l'id
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
	 * @return le point ajouter
	 */
	public Point putPoint(int id, Point point) {
		return points.put(id, point);
	}

	/**
	 * Ajoute une face a la liste des faces
	 * 
	 * @param face la face
	 */
	public void addFace(Face face) {
		this.faces.add(face);
	}

	/**
	 * Applique une transformation de matrice "transformationMatrix" a tous les
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
	 * recupere la liste des faces
	 * 
	 * @return la liste
	 */
	public List<Face> getFaces() {
		return faces;
	}

	/**
	 * Permet d'avoir le nombre de faces
	 * 
	 * @return le nombre de faces
	 */
	public int getNbFaces() {
		return faces.size();
	}

	/**
	 * recupere la face d'indice i
	 * 
	 * @param i - indice de la face
	 * @return laface
	 */
	public Face getFace(int i) {
		return faces.get(i);
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
	 * Recupere la coordonee minimale de tous les points
	 * 
	 * @param i (x=0 y=1 z=2)
	 * @return la coordonnees
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
	 * Recupere la coordonee maximale de tous les points
	 * 
	 * @param i (x=0 y=1 z=2)
	 * @return la coordonnees
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
	 * Retourne la moyenne des coordonee de tous les points Utilise pour avoir une
	 * approximation de l'echelle du modele a son chargement
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
