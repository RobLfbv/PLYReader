package view.utils;

/**
 * 
 * Enum pour les differents modes de rendus d'un canvas
 *
 */
public enum RenderMode {
	EDGESONLY("Edges Only"), FACESONLY("Faces Only"), EDGESFACES("Both Edges and Faces");

	/**
	 * Le nom du mode de rendu
	 */
	private String name;

	/**
	 * Cree un nouveau mode de rendu
	 * 
	 * @param name son nom
	 */
	private RenderMode(String name) {
		this.name = name;
	}

	/**
	 * Cree un mode de rendu a partir d'un nom donnee
	 * 
	 * @param string - le nom
	 * @return le mode de rendu
	 */
	public static RenderMode getFromString(String string) {
		for (RenderMode renderMode : RenderMode.values()) {
			if (renderMode.toString().equals(string)) {
				return renderMode;
			}
		}
		return null;
	}

	/**
	 * retourne le nom du mode de rendu
	 * 
	 * @return le nom
	 */
	public String getName() {
		return name;
	}

	/**
	 * retourne le nom du mode de rendu
	 * 
	 * @return le nom
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}
