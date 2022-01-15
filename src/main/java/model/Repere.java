package model;

import java.util.List;

import utils.transformations.Transformation;
import utils.transformations.TransformationFabric;
import utils.transformations.TransformationType;
import view.windows.MainWindow;

/**
 * Classe qui definit un repere
 */
public class Repere {
	/**
	 * le model auquel le repere appartient
	 */
	private Model model;
	/**
	 * les proprietes du reperes
	 */
	private Double[] properties;

	/**
	 * Constructeur du repere
	 * 
	 * @param model - le model a partir duquelle le repere est cree
	 */
	public Repere(Model model) {
		this.model = model;
		this.reset();
	}

	/**
	 * Application de la transformation a partir de la matrice donnee
	 * 
	 * @param transformationMatrices - Matrice de transformation
	 */
	public void applyTransformation(List<Transformation> transformationMatrices) {
		for (Transformation transformationMatrice : transformationMatrices) {
			if (transformationMatrice.getType().getRepereIndex() != -1) {
				if (transformationMatrice.getType() == TransformationType.SCALE) {
					double oldValue = this.getParameter(TransformationType.SCALE);
					properties[TransformationType.SCALE.getRepereIndex()] = oldValue * transformationMatrice.getValue();
				} else {
					double oldValue = this.getParameter(transformationMatrice.getType());
					properties[transformationMatrice.getType().getRepereIndex()] = oldValue
							+ transformationMatrice.getValue();
				}
			}
		}
		MainWindow.clearConsole();
		MainWindow.printToConsole("TRANSFORMATION EFFECTUEE :\n" + this.toString(), -1);
	}

	/**
	 * modifier un des parametres du repere
	 * 
	 * @param type  le type de parametre modifie
	 * @param value la nouvelle valeur
	 */
	public void setParameter(TransformationType type, double value) {
		final int sizeParameterIndex = 6;
		int i = type.getRepereIndex();
		if (i >= 0 && i < 3) {
			model.applyTransformation(TransformationFabric.get(type, value - this.getParameter(type)));
		} else if (i >= 3 && i < sizeParameterIndex) {
			model.applyTransformation(TransformationFabric.getCentered(this, type, value - this.getParameter(type)));
		} else if (i == sizeParameterIndex) {
			if (value > 0)
				model.applyTransformation(
						TransformationFabric.getCentered(this, type, value / this.getParameter(type)));
		}
	}

	/**
	 * 
	 * @param type - le type du parametre
	 * @return la propriete du repere demande
	 */
	public Double getParameter(TransformationType type) {
		return properties[type.getRepereIndex()];
	}

	/**
	 * Remet a zero le repere
	 */
	public void reset() {
		properties = new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
	}

	@Override
	public String toString() {
		String res = "";
		res += "Translations : " + getParameter(TransformationType.XTRANSLATION) + ","
				+ getParameter(TransformationType.YTRANSLATION) + "," + getParameter(TransformationType.ZTRANSLATION)
				+ "\n";
		res += "Rotations : " + getParameter(TransformationType.XROTATION) + ","
				+ getParameter(TransformationType.YROTATION) + "," + getParameter(TransformationType.ZROTATION) + "\n";
		res += "Scalling : " + getParameter(TransformationType.SCALE);
		return res;

	}
}
