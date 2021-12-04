package view;

/**
 * 
 * Enum pour les différents modes de rendus d'un canvas
 *
 */
public enum RenderMode {
	EDGESONLY("Edges Only"), FACESONLY("Faces Only"), EDGESFACES("Both Edges and Faces");

	/**
	 * Le nom du mode de rendu
	 */
	private String name;

	/**
	 * Crée un nouveau mode de rendu
	 * 
	 * @param name son nom
	 */
	private RenderMode(String name) {
		this.name = name;
	}

	/**
	 * Crée un mode de rendu a partir d'un nom donnée
	 * 
	 * @param string
	 * @return
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
