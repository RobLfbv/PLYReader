package view.utils;

/**
 * Enumeration des informations du fichier ply, cette classe permet d'optimiser
 * et de rendre le code plus lisible
 */
public enum PlyCommentEnum {
	NOM("nom"), DESCRIPTION("description"), DATECREATION("datecreation"), AUTEUR("auteur");

	/**
	 * le mot clee lie au type de commentaire
	 */
	private String keyWord;

	/**
	 * constucteur de l'enum
	 * 
	 * @param keyWord le mot cle
	 */
	private PlyCommentEnum(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * recupere le mot cle lie au type de commentaire
	 * 
	 * @return le mot cle
	 */
	public String getKeyWord() {
		return this.keyWord;
	}

}
