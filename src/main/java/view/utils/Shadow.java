package view.utils;

import javafx.scene.paint.Color;
import model.PLYData;
import model.Repere;
import utils.Vecteur;
import utils.transformations.TransformationScaleY;
import utils.transformations.TransformationType;
import utils.transformations.TransformationXTranslation;
import utils.transformations.TransformationYTranslation;
import utils.transformations.TransformationZTranslation;

/**
 * 
 * Classe de calcul de l'ombre portee, suit le design patern singleton
 *
 */
public class Shadow {
	/**
	 * Vecteur normal au sol sur lequel l'ombre est projetee
	 */
	private final Vecteur normalY = new Vecteur(0, 1, 0);
	/**
	 * la hauteur du sol sur l'axe y
	 */
	private double sol;
	/**
	 * l'instance utilisee pour le design patern singleton
	 */
	private static Shadow singleton;

	/**
	 * constructeur en privee pour le design patern singleton
	 * 
	 * @param p
	 */
	private Shadow(PLYData p) {
		sol = p.getMin(1);
	}

	/**
	 * recupère l'instance de l'objet
	 * 
	 * @param p la data a partir de laquelle on fait le calcul de l'ombre
	 * @return l'instance
	 */
	static public Shadow getInstance(PLYData p) {
		if (singleton == null) {
			singleton = new Shadow(p);
		}
		return singleton;
	}

	/**
	 * Cree un nouveau plyData correspondant a l'ombre portee sur le sol de la data
	 * passsee en paramètre
	 * 
	 * @param data   la data
	 * @param repere le repère dans lequel le model est situe
	 * @return l'ombre dont on fait ensuite le rendu
	 */
	public PLYData applyShadow(PLYData data, Repere repere) {
		PLYData res = new PLYData(data);
		res.applyTransformationWithMatrix(new TransformationScaleY(0.01).get());

		res.applyTransformationWithMatrix(
				new TransformationYTranslation((sol) * (repere.getParameter(TransformationType.SCALE))).get());

		double distY = Math.sqrt(Math.pow(sol - repere.getParameter(TransformationType.YTRANSLATION), 2)
				+ Math.pow(repere.getParameter(TransformationType.XTRANSLATION), 2)
				+ Math.pow(repere.getParameter(TransformationType.ZTRANSLATION), 2));
		double cos = Vecteur.produitScalaire(normalY, Light.lightSource);
		double hypoth = distY / cos;
		double dist = Math.sqrt(Math.pow(hypoth, 2) - Math.pow(distY, 2));

		res.applyTransformationWithMatrix(new TransformationXTranslation(
				(dist * Light.lightSource.get(0) * -1) * (repere.getParameter(TransformationType.SCALE))).get());
		res.applyTransformationWithMatrix(new TransformationZTranslation(
				(dist * Light.lightSource.get(2) * -1) * (repere.getParameter(TransformationType.SCALE))).get());

		for (int i = 0; i < res.getFaces().size(); i++) {
			res.getFace(i).setInitColor(Color.BLACK);
			res.getFace(i).setColor(Color.BLACK);
			res.getFace(i).setVisible(true);
		}
		return res;
	}

	/**
	 * Modifie la hauteur du sol (utilise lorqu'on change de niveau de zoom)
	 */
	public void setSol(double sol) {
		this.sol = sol;
	}
}
