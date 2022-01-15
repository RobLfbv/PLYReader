package exceptions;

/**
 * Excpetion qui permet d'avertir lorsque le fichier ne commence pas pas ply
 */
@SuppressWarnings("serial")
public class NotStartPlyException extends CustomException {
	/**
	 * Constructeur de l'exception qui avertit l'utilisateur s'il manque le mot
	 * "ply" au debut du fichier
	 */
	public NotStartPlyException() {
		super("Invalid ply file", "The ply file must start with \"ply\"");
	}
}
