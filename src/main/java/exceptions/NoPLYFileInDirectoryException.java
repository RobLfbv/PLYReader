package exceptions;

/**
 * Exception qui permet d'avertir lorsque le fichier que l'on souhaite ouvrir
 * n'est pas un fichier .ply
 */
@SuppressWarnings("serial")
public class NoPLYFileInDirectoryException extends CustomException {
	/**
	 * Constructeur de l'erreur qui avertit l'utilisateur de l'ouverture d'un
	 * fichier qui n'est pas un PLY
	 */
	public NoPLYFileInDirectoryException() {
		super("No ply file in this directory", "There is no ply file in the workspace you've tried to open.");

	}

}
