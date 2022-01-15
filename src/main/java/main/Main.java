package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.windows.MainWindow;

/**
 * Classe principale qui lance l'application
 */
public class Main extends Application {

	/**
	 * le main
	 * 
	 * @param args les arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Methode qui lance application (appelee depuis le main)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// System.out.println("================== Running Main ==================");
		Model model = new Model();
		new MainWindow(model);
	}
}
