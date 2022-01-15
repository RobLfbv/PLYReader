package exceptions;

/**
 * Excpetion qui permet d'avertir lorsqu'il y a une erreur dans les parametres
 * (avant le "header") + donne l'indice de la ligne d'erreur
 */
@SuppressWarnings("serial")
public class PLYFileInvalidException extends CustomException {
	/**
	 * Constructeur de l'exception qui avertit l'utilisateur s'il y a une erreur
	 * dans le header du fichier PLY
	 * 
	 * @param line - ligne a laquelle l'erreur se situe
	 */
	public PLYFileInvalidException(int line) {
		super("Invalid ply file", "The ply file you tried to load was invalid (error line " + line + ")");
	}
}
