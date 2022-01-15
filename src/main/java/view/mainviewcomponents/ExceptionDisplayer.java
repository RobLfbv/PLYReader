package view.mainviewcomponents;

import exceptions.CustomException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Model;
import utils.Observer;
import utils.Subject;

/**
 * Classe attachee au model qui se charge d'afficher les messages d'erreur que
 * ce dernier envoie
 */
public class ExceptionDisplayer implements Observer {
	/**
	 * Cree le displayer
	 * 
	 * @param model le model auquel il est lie
	 */
	public ExceptionDisplayer(Model model) {
		model.attach(this);
	}

	@Override
	public void update(Subject subj) {
		update(subj, null);
	}

	@Override
	public void update(Subject subj, Object data) {
		if (data instanceof CustomException) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(((CustomException) data).getName());
			errorAlert.setContentText(((CustomException) data).getMessage());
			errorAlert.showAndWait();
		}
	}

}
