package model;

/**
 * Cette classe sert à stocker les valeurs RGB d'une couleur
 */
public class Color {

	/**
	 * Composant rouge de la couleur
	 */
	private int r;
	/**
	 * composante verte de la couleur
	 */
	private int g;
	/**
	 * composante bleue de la couleur
	 */
	private int b;
	/**
	 * composant alpha de la couleur
	 */
	private int a;

	/**
	 * Cree une nouvelle couleur
	 * 
	 * @param r composante en rouge entre 0 et 255
	 * @param g composante en vert entre 0 et 255
	 * @param b composante en bleu entre 0 et 255
	 * @param a composante en alpha entre 0 et 255
	 */
	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Cree une nouvelle couleur
	 * 
	 * @param r composante en rouge entre 0 et 255
	 * @param g composante en vert entre 0 et 255
	 * @param b composante en bleu entre 0 et 255
	 */
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}

	/**
	 * Instantie une copie de la couleur pass�e en param�tre
	 * 
	 * @param color
	 */
	public Color(Color color) {
		this(color.getR(), color.getG(), color.getB(), color.getA());
	}

	/**
	 * Retourne la compasante en rouge de la couleur
	 * 
	 * @return niveau de rouge
	 */
	public int getR() {
		return r;
	}

	/**
	 * Retourne la compasante en vert de la couleur
	 * 
	 * @return niveau de vert
	 */
	public int getG() {
		return g;
	}

	/**
	 * Retourne la compasante en bleu de la couleur
	 * 
	 * @return niveau de bleu
	 */
	public int getB() {
		return b;
	}

	/**
	 * Retourne la compasante en alpha de la couleur
	 * 
	 * @return niveau de alpha
	 */
	public int getA() {
		return a;
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (b != other.b)
			return false;
		if (g != other.g)
			return false;
		if (r != other.r)
			return false;
		return true;
	}

}
