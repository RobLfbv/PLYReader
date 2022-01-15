package utils;

/**
 * 
 * Classe qui defini les classes observees selon le patron MVC
 *
 */
public interface Observer {
	/**
	 * methode appele pour mettre a jours tous les observers
	 * 
	 * @param subj le sujet qui est observe
	 */
	public void update(Subject subj);

	/**
	 * methode appele pour mettre a jours tous les observers
	 * 
	 * @param subj le sujet qui est observe
	 * @param data possibilite de passer des parametres sur le sujet aux observers
	 */
	public void update(Subject subj, Object data);
}
