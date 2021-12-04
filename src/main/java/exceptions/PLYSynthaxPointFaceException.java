package exceptions;

/**
 * Excpetion qui permet d'avertir lorsqu'il y a une erreur avec un point ou une
 * face (erreur de syntaxe notament) + donne l'indice de la ligne d'erreur
 */
@SuppressWarnings("serial")
public class PLYSynthaxPointFaceException extends CustomException {
	/**
	 * Constructeur qui avertit l'utilisateur s'il y a une erreur dans la synthaxe
	 * d'un point ou d'une face
	 * 
	 * @param name        - le titre de l'erreur exemple: "Synthax point"
	 * @param description - description de l'erreur
	 * @param line        - ligne à laquelle se trouve l'erreur
	 */
	public PLYSynthaxPointFaceException(String name, String description, int line) {
		super(name, description + line);
	}

}
