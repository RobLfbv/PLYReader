package exceptions;

/**
 * Classe abstraite pour les nouvelles exceptions créé
 *
 */
@SuppressWarnings("serial")
public abstract class CustomException extends Exception {
	/**
	 * name - le titre/nom de la nouvelle exception
	 */
	private String name;

	public String getName() {
		return name;
	}

	/**
	 * Constructeur de l'exception CustomException
	 * 
	 * @param name        - le titre/nom de la nouvelle exception
	 * @param description - la description de l'exception
	 */
	public CustomException(String name, String description) {
		super(description);
		this.name = name;
	}

}
