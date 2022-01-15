package model.plyreader;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe servant a instancier les proprietes contenues dans le header
 */
public class Property {
	/**
	 * Parametress de la propriete
	 */
	private List<String> param = new ArrayList<>();

	/**
	 * @param idx - indice du parametre
	 * @return param a partir d'un indice
	 */
	public String getParam(int idx) {
		return param.get(idx);
	}

	/**
	 * @return le nom du type de la donnee
	 */
	public String getType() {
		return getParam(1);
	}

	/**
	 * @return param
	 */
	public List<String> getParam() {
		return param;
	}

	/**
	 * ajoute un parametre dans la liste de parametres
	 * 
	 * @param chaine - parametre a ajouter
	 */
	public void addParam(String chaine) {
		param.add(chaine);
	}

	@Override
	public String toString() {
		return "Property [param=" + param + "]";
	}

}
