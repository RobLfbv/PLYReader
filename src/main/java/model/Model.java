package model;

import java.io.File;

import utils.Matrice;
import utils.Subject;
import utils.TransformationMatrice;
import utils.TransformationType;

/**
 * Cette classe sert à instancier et interragir avec un fichier PLY
 */
public class Model extends Subject {
	/**
	 * data - Objet de type PLYData qui contient toutes les informations contenues
	 * dans le fichier PLY
	 */
	private PLYData data;
	/**
	 * repere - Objet de type Repere qui represente le repere utilise pour les
	 * transformations
	 */
	private Repere repere;
	/**
	 * transformedData - Objet de type PLYData qui contient toutes les informations
	 * contenues dans le fichier PLY une fois transformé
	 */
	private PLYData transformedData;

	/**
	 * Constructeur du Model
	 */
	public Model() {
		this.data = new PLYData();
		this.transformedData = new PLYData();
		this.repere = new Repere();
	}

	/**
	 * Chargement du fichier avec la modification matriciel par défaut
	 * 
	 * @param file
	 */
	public void loadFromFile(File file) {
		this.loadFromFile(file, true);
	}

	/**
	 * Chargement du fichier avec la possibilités de faire ou non la modificiation
	 * matriciel -> Utile de la désactivé pour les tests
	 * 
	 * @param file
	 * @param doTransfo
	 */
	public void loadFromFile(File file, boolean doTransfo) {
		this.data = new PLYData();
		LoadingFilePLY.readFilePLY(this, file);

		if (doTransfo)
			this.transformationToCenter();
		this.repere.reset();
		this.transformedData = new PLYData(this.data);
		this.notifyObservers();
	}

	/**
	 * Applique une transformation matriciel à la data afin de centrer l'objet à
	 * son chargement
	 */
	public void transformationToCenter() {
		this.getData().applyTransformationWithMatrix(
				new TransformationMatrice(TransformationType.SCALE).get(1600 / this.getData().getAverageAbsoluteXYZ()));

		this.getData()
				.applyTransformationWithMatrix(new TransformationMatrice(TransformationType.TRANSLATION).get(
						(this.getData().getMax(0) + this.getData().getMin(0)) / -2,
						(this.getData().getMax(1) + this.getData().getMin(1)) / -2,
						(this.getData().getMax(2) + this.getData().getMin(2)) / -2));
	}

	public PLYData getData() {
		return this.data;
	}

	public void setData(PLYData data) {
		this.data = data;
	}

	public PLYData getTransformedData() {
		return transformedData;
	}

	@Override
	public String toString() {
		return "Model [data=" + data + "]";
	}

	/**
	 * Remet à zéro la data transformé, toutes les transformations et rotations
	 * sont annulées
	 */
	public void resetTransformation() {
		this.repere.reset();
		this.transformedData = new PLYData(this.data);
		this.notifyObservers();
	}

	/**
	 * Applique la transformation de la matrice
	 * 
	 * @param transformationMatrix - Transformation à appliquer
	 */
	public void applyTransformation(Matrice transformationMatrix) {
		this.repere.applyTransformationWithMatrix(transformationMatrix);
		this.transformedData = new PLYData(this.data);
		this.transformedData.applyTransformationWithMatrix(this.repere.getMatrix());
		this.notifyObservers();
	}

}
