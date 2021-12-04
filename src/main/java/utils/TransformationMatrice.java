package utils;

/**
 * Contient toutes les transformations possible et permet de calculer le
 * résultat d'une transformation spécifique sur une matrice
 */
public class TransformationMatrice {
	/**
	 * élément de l'énumération transformation pour choisir la transformation à
	 * appliquer
	 */
	TransformationType type;

	/**
	 * Constructeur TransformationMatrice
	 * 
	 * @param type
	 */
	public TransformationMatrice(TransformationType type) {
		this.type = type;
	}

	/**
	 * retourne la matrice correspondant au type de matrice de l'object
	 * 
	 * @param args parametres eventuels de la matrice
	 * @return la matrice
	 */
	public Matrice get(double... args) {
		if (this.type == TransformationType.XROTATION && args.length == 1) {
			return getRotateXMatrix(args[0]);
		} else if (this.type == TransformationType.YROTATION && args.length == 1) {
			return getRotateYMatrix(args[0]);
		} else if (this.type == TransformationType.ZROTATION && args.length == 1) {
			return getRotateZMatrix(args[0]);
		}
		if (this.type == TransformationType.XTRANSLATION && args.length == 1) {
			return getTranslationMatrix(args[0], 0, 0);
		} else if (this.type == TransformationType.YTRANSLATION && args.length == 1) {
			return getTranslationMatrix(0, args[0], 0);
		} else if (this.type == TransformationType.ZTRANSLATION && args.length == 1) {
			return getTranslationMatrix(0, 0, args[0]);
		} else if (this.type == TransformationType.TRANSLATION && args.length == 3) {
			return getTranslationMatrix(args[0], args[1], args[2]);
		} else if (this.type == TransformationType.SCALE && args.length == 1) {
			return getScaleMatrix(args[0]);
		}
		return null;
	}

	public TransformationType getType() {
		return type;
	}

	/**
	 * Retourne la matrice de translation x,y,z
	 * 
	 * @param x x
	 * @param y y
	 * @param z z
	 * @return la matrice
	 */

	private static Matrice getTranslationMatrix(double x, double y, double z) {
		return new Matrice(new double[][] { { 1, 0, 0, x }, { 0, 1, 0, y }, { 0, 0, 1, z }, { 0, 0, 0, 1 } });
	}

	private static Matrice getScaleMatrix(double x, double y, double z) {
		return new Matrice(new double[][] { { x, 0, 0, 0 }, { 0, y, 0, 0 }, { 0, 0, z, 0 }, { 0, 0, 0, 1 } });
	}

	private static Matrice getScaleMatrix(double size) {
		return getScaleMatrix(size, size, size);
	}

	/**
	 * 
	 * @param angle prévu en radian
	 * @return matrice prévue pour une rotation en X
	 */
	private static Matrice getRotateXMatrix(double angle) {
		return new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, Math.cos(angle), -Math.sin(angle), 0 },
				{ 0, Math.sin(angle), Math.cos(angle), 0 }, { 0, 0, 0, 1 } });
	}

	/**
	 * 
	 * @param angle prévu en radian
	 * @return matrice prévue pour une rotation en Y
	 */
	private static Matrice getRotateYMatrix(double angle) {
		return new Matrice(new double[][] { { Math.cos(angle), 0, -Math.sin(angle), 0 }, { 0, 1, 0, 0 },
				{ Math.sin(angle), 0, Math.cos(angle), 0 }, { 0, 0, 0, 1 } });
	}

	/**
	 * 
	 * @param angle prévu en radian
	 * @return matrice prévue pour une rotation en Z
	 */
	private static Matrice getRotateZMatrix(double angle) {
		return new Matrice(new double[][] { { Math.cos(angle), -Math.sin(angle), 0, 0 },
				{ Math.sin(angle), Math.cos(angle), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
	}
}
