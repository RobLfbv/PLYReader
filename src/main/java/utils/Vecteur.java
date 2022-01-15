package utils;

import java.util.Objects;

import model.Point;

/**
 * Classe qui permet de creer et de manipuler des vecteurs
 */
public class Vecteur {
	/**
	 * Matrice correspondant au vecteur
	 */
	private Matrice m;

	/**
	 * Cree un vecteur a partir de ses composantes x,y et z
	 * 
	 * @param x composante en x
	 * @param y composante en y
	 * @param z composante en z
	 */
	public Vecteur(double x, double y, double z) {
		m = new Matrice(new double[][] { { x }, { y }, { z }, { 1 } });
	}

	/**
	 * Cree un vecteur d'un point a un autre
	 * 
	 * @param p1 premier point
	 * @param p2 deuxieme point
	 */
	public Vecteur(Point p1, Point p2) {
		this(p2.get(0) - p1.get(0), p2.get(1) - p1.get(1), p2.get(2) - p1.get(2));
	}

	/**
	 * Realise le produit vectoriel de deux vecteurs
	 * 
	 * @param v1 premier vecteur
	 * @param v2 deuxieme vecteur
	 * @return le resultat
	 */
	static public Vecteur produitVectoriel(Vecteur v1, Vecteur v2) {
		Vecteur res = new Vecteur(0, 0, 0);

		double tmp;
		int idx1 = 1;
		int idx2 = 2;
		for (int i = 0; i < 3; i++) {
			tmp = (v1.get(idx1) * v2.get(idx2)) - (v1.get(idx2) * v2.get(idx1));
			idx1 = (idx1 + 1) % 3;
			idx2 = (idx2 + 1) % 3;
			res.set(i, tmp);
		}
		return res;
	}

	/**
	 * Realise le produit scalaire de deux vecteurs
	 * 
	 * @param v1 premier vecteur
	 * @param v2 deuxieme vecteur
	 * @return le resultat
	 */
	static public double produitScalaire(Vecteur v1, Vecteur v2) {
		return v1.get(0) * v2.get(0) + v1.get(1) * v2.get(1) + v1.get(2) * v2.get(2);
	}

	/**
	 * Calciu la norme d'un vecteur
	 * 
	 * @param v1 le vecteur
	 * @return le resultat
	 */
	static public double getNorme(Vecteur v1) {
		double x = produitScalaire(v1, v1);
		return Math.sqrt(x);
	}

	/**
	 * Retourne la matrice correspondante au vecteur
	 * 
	 * @return la matrice
	 */
	public Matrice getMatrice() {
		return this.m;
	}

	/**
	 * Unitarise le vecteur passe en parametre
	 * 
	 * @param v1 le vecteur
	 */
	public static void unitariser(Vecteur v1) {
		double x = getNorme(v1);
		v1.set(0, v1.get(0) / x);
		v1.set(1, v1.get(1) / x);
		v1.set(2, v1.get(2) / x);
	}

	/**
	 * Unitarise le vecteur passe en parametre
	 * 
	 * @param v1 le vecteur
	 * @return le vecteur unitarise
	 */
	public static Vecteur unitariserRetour(Vecteur v1) {
		Vecteur res = new Vecteur(0, 0, 0);
		double x = getNorme(v1);
		res.set(0, v1.get(0) / x);
		res.set(1, v1.get(1) / x);
		res.set(2, v1.get(2) / x);
		return res;
	}

	/**
	 * 
	 * @param i x=0 y=1 z=2
	 * @return le double correspondant a l'indice du vecteur
	 */
	public double get(int i) {
		return m.get(i, 0);
	}

	/**
	 * modifie une des composantes du vecteur
	 * 
	 * @param i     indice de la composant -> x:0 y:1 z:2
	 * @param value la nouvelle valeur
	 */
	public void set(int i, double value) {
		m.set(i, 0, value);
	}

	/**
	 * Applique une transformation au vecteur
	 * 
	 * @param transformationMatrix la matrice de transformation
	 */
	public void applyTransformationWithMatrix(Matrice transformationMatrix) {
		this.m = Matrice.multiply(transformationMatrix, this.m);
	}

	@Override
	public String toString() {
		return m.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		return Objects.equals(m, other.m);
	}

}
