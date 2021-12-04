package utils;

/**
 * 
 * Classe qui défini les classes observées selon le patron MVC
 *
 */
public interface Observer {
	/**
	 * methode appelé pour mettre à jours tous les observers
	 * 
	 * @param subj le sujet qui est observé
	 */
	public void update(Subject subj);

	/**
	 * methode appelé pour mettre à jours tous les observers
	 * 
	 * @param subj le sujet qui est observé
	 * @param data possibilité de passer des parametres sur le sujet aux observers
	 */
	public void update(Subject subj, Object data);
}
