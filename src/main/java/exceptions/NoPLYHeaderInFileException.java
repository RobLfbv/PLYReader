package exceptions;

/**
 * Excpetion qui permet d'avertir lorsque le fichier PLY ne contient pas de
 * "header"
 */
@SuppressWarnings("serial")
public class NoPLYHeaderInFileException extends CustomException {
	/**
	 * Constructeur de l'erreur qui avertit l'utilisateur quand le fichier PLY ne
	 * contient de "header"
	 * 
	 */
	public NoPLYHeaderInFileException() {
		super("No ply header",
				"The file you've tried to load doesn't not contain any header indicating that it is a ply file (even though it might be one)");
	}
}
