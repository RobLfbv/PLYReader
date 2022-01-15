package exceptions;

/**
 * Exception qui permet d'avertir lorsque le fichier que l'on souhaite ouvrir
 * n'est pas dans le format ASCII
 */
@SuppressWarnings("serial")
public class PlyFormatException extends CustomException {
	/**
	 * Constructeur de l'erreur qui avertit l'utilisateur de l'ouverture d'un
	 * fichier qui n'est pas dans le format ASCII
	 */
	public PlyFormatException() {
		super("Invalid ply file", "The format must be ASCII");
	}
}
