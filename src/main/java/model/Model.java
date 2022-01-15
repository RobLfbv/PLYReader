package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.plyreader.PLYReader;
import utils.Subject;
import utils.transformations.Transformation;
import utils.transformations.TransformationScale;
import utils.transformations.TransformationType;
import utils.transformations.TransformationXTranslation;
import utils.transformations.TransformationYTranslation;
import utils.transformations.TransformationZTranslation;
import view.utils.Parameters;
import view.utils.Shadow;

/**
 * Cette classe sert a instancier et interragir avec un fichier PLY
 */
public class Model extends Subject {
	/**
	 * data - Objet de type PLYData qui contient toutes les informations contenues
	 * dans le fichier PLY
	 */
	private PLYData data;
	/**
	 * data - Objet de type PLYData qui contient toutes les informations contenues
	 * dans le fichier PLY et auxquelles sont appliquees les transformations
	 */
	private PLYData transformedData;
	/**
	 * repere - Objet de type Repere qui represente le repere utilise pour les
	 * transformations
	 */
	private Repere repere;

	/**
	 * Le presse papier pour stocker les parametres du canvas
	 */
	private Parameters parametersClipboard;

	/**
	 * Constructeur du Model
	 */
	public Model() {
		this.data = new PLYData();
		this.transformedData = new PLYData();
		this.repere = new Repere(this);
	}

	/**
	 * Chargement du fichier avec la modification matriciel par defaut
	 * 
	 * @param file - le fichier
	 */
	public void loadFromFile(File file) {
		this.loadFromFile(file, true);
	}

	/**
	 * Chargement du fichier avec la possibilites de faire ou non la modificiation
	 * matriciel -> Utile de la desactive pour les tests
	 * 
	 * @param file
	 * @param doTransfo
	 */
	private void loadFromFile(File file, boolean doTransfo) {
		this.data = new PLYData();
		PLYReader plyReader = new PLYReader();
		if (!plyReader.readFilePLY(this, file))
			return;
		if (doTransfo)
			this.transformationToCenter();
		this.repere.reset();
		Shadow.getInstance(data).setSol(data.getMin(1));

		data.computeFacesColorsFromPointsColors(true);

		transformedData = new PLYData(this.data);
		this.notifyObservers(transformedData);
	}

	/**
	 * Applique une transformation matriciel a la data afin de centrer l'objet a son
	 * chargement
	 */

	public void transformationToCenter() {
		final int screenWidth = 1600;
		this.getData().applyTransformationWithMatrix(
				new TransformationScale(screenWidth / this.getData().getAverageAbsoluteXYZ()).get());
		this.getData().applyTransformationWithMatrix(
				new TransformationXTranslation((this.getData().getMax(0) + this.getData().getMin(0)) / -2).get());
		this.getData().applyTransformationWithMatrix(
				new TransformationYTranslation((this.getData().getMax(1) + this.getData().getMin(1)) / -2).get());
		this.getData().applyTransformationWithMatrix(
				new TransformationZTranslation((this.getData().getMax(2) + this.getData().getMin(2)) / -2).get());
	}

	public PLYData getData() {
		return this.data;
	}

	public PLYData getTransformedData() {
		return transformedData;
	}

	public Repere getRepere() {
		return repere;
	}

	/**
	 * Retourne le parametre du repere correspondant
	 * 
	 * @param type le type du parametre que l'on veut obtenir
	 * @return la valeur
	 */
	public Double getRepereParameter(TransformationType type) {
		return repere.getParameter(type);
	}

	public void setData(PLYData data) {
		this.data = data;
		this.transformedData = data;
	}

	public void setParametersClipboard(Parameters parameters) {
		this.parametersClipboard = new Parameters(parameters);
	}

	public Parameters getParametersClipboard() {
		return parametersClipboard;
	}

	@Override
	public String toString() {
		return "Model [data=" + data + "]";
	}

	/**
	 * Remet a zero la data transforme, toutes les transformations et rotations sont
	 * annulees
	 */
	public void resetTransformation() {
		this.repere.reset();
		transformedData = new PLYData(data);
		this.notifyObservers(transformedData);
	}

	/**
	 * Applique la transformation au model
	 * 
	 * @param transformationMatrice - la matrice de transformation a appliquer
	 */
	public void applyTransformation(Transformation transformationMatrice) {
		List<Transformation> transformationMatrices = new ArrayList<>();
		transformationMatrices.add(transformationMatrice);
		this.applyTransformation(transformationMatrices);
	}

	/**
	 * Applique la transformation de la matrice
	 * 
	 * @param transformationMatrices - Transformation a appliquer
	 */
	public void applyTransformation(List<Transformation> transformationMatrices) {
		this.repere.applyTransformation(transformationMatrices);
		for (Transformation transformationMatrice : transformationMatrices) {
			this.transformedData.applyTransformationWithMatrix(transformationMatrice.get());
		}
		this.notifyObservers(transformationMatrices);
	}

}
