package view.mainviewcomponents;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import view.windows.AboutWindow;
import view.windows.HelpWindow;
import view.windows.MainWindow;

/**
 * 
 * La barre de menu de l'application
 *
 */
public class MenuBarView extends MenuBar {

	/**
	 * Cree la bar de menu
	 * 
	 * @param parent la fenetre a laquelle la barre de menu appartient
	 */
	public MenuBarView(MainWindow parent) {
		Menu fileMenu = new Menu("_File");
		MenuItem openWorkspace = new MenuItem("Open Workspace");
		openWorkspace.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		openWorkspace.setOnAction(e -> {
			parent.openWorkspace();
		});
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> {
			parent.closeApplication();
		});

		fileMenu.getItems().addAll(openWorkspace, exit);

		MenuItem helpWindow = new MenuItem("Help");
		helpWindow.setOnAction(e -> {
			new HelpWindow();
		});

		Menu helpMenu = new Menu("_Help");
		MenuItem aboutWindow = new MenuItem("About...");
		aboutWindow.setOnAction(e -> {
			new AboutWindow();
		});

		helpMenu.getItems().addAll(helpWindow, aboutWindow);

		this.getMenus().addAll(fileMenu, helpMenu);
	}
}
