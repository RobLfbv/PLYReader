package utils;

/**
 * 
 * Classe qui d�fini les classes observ�es selon le patron MVC
 *
 */
public interface Observer {
	/**
	 * methode appel� pour mettre � jours tous les observers
	 * 
	 * @param subj le sujet qui est observ�
	 */
	public void update(Subject subj);

	/**
	 * methode appel� pour mettre � jours tous les observers
	 * 
	 * @param subj le sujet qui est observ�
	 * @param data possibilit� de passer des parametres sur le sujet aux observers
	 */
	public void update(Subject subj, Object data);
}
