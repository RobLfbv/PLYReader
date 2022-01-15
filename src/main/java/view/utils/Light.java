package view.utils;

import java.util.List;

import javafx.scene.paint.Color;
import model.Face;
import model.PLYData;
import utils.MathUtils;
import utils.Vecteur;
import utils.transformations.Transformation;

/**
 * Classe qui permet de faire tous les calculs de la lumiere
 */
public class Light {
	/**
	 * Le vecteur qui represente la lumiere
	 */
	public static Vecteur lightSource = Vecteur.unitariserRetour(new Vecteur(1, 1, 1));
	/**
	 * Instance de l'objet lumiere utilisee pour le design patern singleton
	 */
	private static Light instance;

	/**
	 * constructeur inutilisable car singleton
	 */
	private Light() {
	}

	/**
	 * recupere l'instance de l'objet lumiere
	 * 
	 * @return l'instance
	 */
	static public Light getInstance() {
		if (instance == null) {
			instance = new Light();
		}
		return instance;
	}

	/**
	 * La methode qui applique a la couleur les changements lie a la lumiere
	 * 
	 * @param j                      - Le sens du tri souhaite, 0 si X, 1 si Y, 2 si
	 *                               Z
	 * @param def                    - la couleur par defaut
	 * @param p                      - Le PLYData utilise
	 * @param shadowStrength         - la puissance de l'ombre
	 * @param lightStrength          - la puissance de la lumiere
	 * @param computeLight           - true si on doit effectuer les changements sur
	 *                               les faces, false si ce n'est pas le cas
	 * @param onlyRenderVisibleFaces - true si on doit effectuer le changement
	 *                               uniquement sur les faces visibles, false si ce
	 *                               n'est pas le cas
	 */
	public void shadeOnColor(int j, Color def, PLYData p, double shadowStrength, double lightStrength,
			boolean computeLight, boolean onlyRenderVisibleFaces) {
		List<Face> listFaces = p.getFaces();
		for (int i = 0; i < listFaces.size(); i++) {
			// loi de dem

			Vecteur v1 = new Vecteur(p.getPoint(listFaces.get(i).getPoints()[0]),
					p.getPoint((listFaces.get(i).getPoints()[1])));
			Vecteur v2 = new Vecteur(p.getPoint(listFaces.get(i).getPoints()[0]),
					p.getPoint(listFaces.get(i).getPoints()[2]));

			Vecteur normal = Vecteur.produitVectoriel(v1, v2);
			Vecteur.unitariser(normal);

			if (normal.get(j) < 0) {
				if (onlyRenderVisibleFaces) {
					p.getFace(i).setVisible(false);
				}
				normal.set(0, normal.get(0) * -1);
				normal.set(1, normal.get(1) * -1);
				normal.set(2, normal.get(2) * -1);

			}

			if (computeLight) {
				double x = Vecteur.produitScalaire(normal, lightSource);

				Color c = p.getFaces().get(i).getColorInit();
				if (c == null) {
					c = def;
				}

				double coeff = MathUtils.map(x, 0, 1, shadowStrength, lightStrength);

				// loi de dem
				p.getFace(i)
						.setColor(ColorConstrain.color(c.getRed() * coeff, c.getGreen() * coeff, c.getBlue() * coeff));
			}
		}
	}

	/**
	 * Reinistialise les faces avant un calcul de lumiere
	 * 
	 * @param defaultColor la couleur par defaut des faces
	 * @param data         la data
	 */
	public void resetColorsOfFaceIfNoLight(Color defaultColor, PLYData data) {
		for (int i = 0; i < data.getFaces().size(); i++) {
			data.getFace(i).setColor(data.getFace(i).getColorInit());
			data.getFace(i).setVisible(true);
		}
	}

	/**
	 * Applique la transformation au vecteur de lumiere
	 * 
	 * @param transformationMatrices - la matrice de transformation
	 */
	public static void applyTranformations(List<Transformation> transformationMatrices) {
		for (Transformation transformationMatrice : transformationMatrices) {
			lightSource.applyTransformationWithMatrix(transformationMatrice.get());
		}
	}
}
