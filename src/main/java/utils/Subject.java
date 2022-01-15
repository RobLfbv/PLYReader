package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui definit les classes sujets dans le patron MVC
 */
public abstract class Subject {
	/**
	 * List des observers qui observent ce sujet
	 */
	protected List<Observer> attached;

	/**
	 * Instantie un sujet
	 */
	public Subject() {
		attached = new ArrayList<>();
	}

	/**
	 * Attache un observer au sujet
	 * 
	 * @param obs l'observer
	 */
	public void attach(Observer obs) {
		if (!attached.contains(obs)) {
			attached.add(obs);
		}
	}

	/**
	 * Detache un observer de son sujet
	 * 
	 * @param obs l'observer
	 */
	public void detach(Observer obs) {
		attached.remove(obs);
	}

	/**
	 * Notifie tous les obsevers d'un changement du sujet
	 */
	public void notifyObservers() {
		for (Observer o : attached) {
			o.update(this);
		}
	}

	/**
	 * Notifie tous les obsevers d'un changement du sujet
	 * 
	 * @param data peut leur envoyer de la data
	 */
	public void notifyObservers(Object data) {
		for (Observer o : attached) {
			o.update(this, data);
		}
	}

}
