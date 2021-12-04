package utils;

import java.util.Arrays;

/**
 * 
 * Classe qui permet de stocker des matrices et de faire des calculs avec
 *
 */
public class Matrice {

	/**
	 * Tableau qui stoque les valeurs contenues dans la matrices
	 */
	private double[][] tab;

	/**
	 * Cree une matrice a partir d'un tableau a deux dimensions de double
	 * 
	 * @param tab le ableau
	 */
	public Matrice(double[][] tab) {
		this.tab = tab;
	}

	/**
	 * cree une matrice de taille rows*cols non initialis�e (ne contenant que des
	 * 0)
	 * 
	 * @param rows nombre de lignes
	 * @param cols nombre de colones
	 */
	public Matrice(int rows, int cols) {
		this(new double[rows][cols]);
	}

	/**
	 * retourne la valeur en position x=col y=row dans la matrice
	 * 
	 * @param row numero de la ligne
	 * @param col numero de la colone
	 * @return la valeur
	 */
	public double get(int row, int col) {
		return this.tab[row][col];
	}

	/**
	 * modifie la valeur en position x=col y=row dans la matrice
	 * 
	 * @param row numero de la ligne
	 * @param col numero de la colone
	 * @value la valeur a mettre a cet emplacement
	 */
	public void set(int row, int col, double value) {
		this.tab[row][col] = value;
	}

	/**
	 * renvoie le nombre de colonnes
	 * 
	 * @return le nombre de colonnes
	 */
	public int getCols() {
		return tab[0].length;
	}

	/**
	 * renvoie le nombre de lignes
	 * 
	 * @return le nombre de lignes
	 */
	public int getRows() {
		return tab.length;
	}

	/**
	 * Permet de savoir si deux matrices ont la meme taille
	 * 
	 * @param m1 premiere matrice
	 * @param m2 seconde matrice
	 * @return vrai si elles ont la meme taille, faux sinon
	 */
	public static boolean sameSize(Matrice m1, Matrice m2) {
		return m1.getRows() == m2.getRows() && m1.getCols() == m2.getCols();
	}

	/**
	 * renvoie une copie de la matrice
	 * 
	 * @param m la matrice a copier
	 * @return une matrice de meme taille, contenant les memes valeurs
	 */
	public static Matrice copy(Matrice m) {
		Matrice res = new Matrice(m.getRows(), m.getCols());
		for (int i = 0; i < m.getRows(); i++) {
			for (int j = 0; j < m.getRows(); j++) {
				res.set(i, j, m.get(i, j));
			}
		}
		return res;
	}

	/**
	 * Renvoie une version texte de la matrice
	 */
	public String toString() {
		int[] tailles = getColsMaxSizes();

		StringBuilder res = new StringBuilder();
		for (int rows = 0; rows < this.getRows(); rows++) {
			for (int cols = 0; cols < this.getCols(); cols++) {
				String str = "" + this.get(rows, cols);
				res.append("[" + str);
				for (int i = 0; i < tailles[cols] - str.length(); i++) {
					res.append(" ");
				}

				res.append("]");
			}
			res.append('\n');
		}
		return res.toString();
	}

	/**
	 * renvoie pour chaque colone le nombre de caract�re max pris par un element
	 * 
	 * @return tableau de taille nbCol contenant toutes ces valeurs
	 */
	private int[] getColsMaxSizes() {
		int[] tailles = new int[this.getCols()];

		for (int col = 0; col < this.getCols(); col++) {
			int tailleMax = 0;
			for (int row = 0; row < this.getRows(); row++) {
				int t = ("" + this.get(row, col)).length();
				if (t > tailleMax)
					tailleMax = t;
			}
			tailles[col] = tailleMax;
		}
		return tailles;
	}

	/**
	 * Renvoie le produit de deux matrices
	 * 
	 * @param m1 premiere matrice
	 * @param m2 seconde matrice
	 * @return le produit des deux, null si la multiplication n'est pas possible
	 */
	public static Matrice multiply(Matrice m1, Matrice m2) {

		if (m1.getCols() != m2.getRows())
			return null;

		Matrice res = new Matrice(m1.getRows(), m2.getCols());

		for (int row = 0; row < res.getRows(); row++) {
			for (int col = 0; col < res.getCols(); col++) {
				double value = 0;
				for (int i = 0; i < m1.getCols(); i++) {
					value += m1.get(row, i) * m2.get(i, col);
				}
				res.set(row, col, value);
			}
		}
		return res;
	}

	/**
	 * renvoie vrai si les deux matrices sont egales (memes taille et meme contenu)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrice other = (Matrice) obj;
		return Arrays.deepEquals(tab, other.tab);
	}

}
