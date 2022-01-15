package exceptions;

/**
 * Excepetion qui permet d'avertir lorsqu'il y a une erreur dans les parametres
 * (avant le "header") + donne l'indice de la ligne d'erreur
 */
@SuppressWarnings("serial")
public class PropertyException extends CustomException {
	/**
	 * Constructeur de l'exception qui avertit l'utilisateur s'il y a une erreur
	 * dans le header du fichier PLY
	 */
	public PropertyException() {
		super("Invalid property", "Property lines are wrong !");
	}
}
