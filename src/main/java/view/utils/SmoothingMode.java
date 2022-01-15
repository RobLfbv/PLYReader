package view.utils;

/**
 * Enum correspondant aux differents modes de lissage lors du rendu
 */
public enum SmoothingMode {
	DISABLED("Disabled"), OLDSCHOOL("Old School"), SMOOTH("Smooth");

	/**
	 * Le nom du mode de lissage
	 */
	private String name;

	/**
	 * Cree un nouveau mode de lissage
	 * 
	 * @param name son nom
	 */
	private SmoothingMode(String name) {
		this.name = name;
	}

	/**
	 * Cree un mode de lissage a partir d'un nom donne
	 * 
	 * @param string - le nom
	 * @return le mode de lissage
	 */
	public static SmoothingMode getFromString(String string) {
		for (SmoothingMode renderMode : SmoothingMode.values()) {
			if (renderMode.toString().equals(string)) {
				return renderMode;
			}
		}
		return null;
	}

	/**
	 * retourne le nom du mode de lissage
	 * 
	 * @return le nom
	 */
	public String getName() {
		return name;
	}

	/**
	 * retourne le nom du mode de lissage
	 * 
	 * @return le nom
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}
