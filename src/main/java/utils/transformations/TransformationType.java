package utils.transformations;

/**
 * Enumeration de chaque transformation possible
 */
public enum TransformationType {
	XTRANSLATION(0), YTRANSLATION(1), ZTRANSLATION(2), XROTATION(3), YROTATION(4), ZROTATION(5), SCALE(6), YSCALE(-1),
	LOADMODEL(-1), CHANGEAPPEARANCE(-1), CHANGESIDE(-1), CHANGEONLYRENDERVISIBLEFACES(-1), CHANGELIGHT(-1),
	UPDATEEVERYPARAMETER(-1), CHANGECANVAS(-1);

	/**
	 * L'index dans le repere
	 */
	private int repereIndex;

	/**
	 * constructeur prive de l'enum
	 * 
	 * @param repereIndex L'index dans le repere
	 */
	private TransformationType(int repereIndex) {
		this.repereIndex = repereIndex;
	}

	public int getRepereIndex() {
		return repereIndex;
	}
}
