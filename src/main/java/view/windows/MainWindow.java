
package view.windows;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;
import view.mainviewcomponents.CanvasGrid;
import view.mainviewcomponents.CanvasView;
import view.mainviewcomponents.ExceptionDisplayer;
import view.mainviewcomponents.MenuBarView;
import view.mainviewcomponents.ObjectPositionSection;
import view.mainviewcomponents.TimerSection;
import view.mainviewcomponents.WorkspaceViewer;
import view.utils.Timer;

/**
 * Classe Vue, gere la vue du modele et de l'interface
 */
public class MainWindow extends Stage {
	/**
	 * modele
	 */
	private Model model;

	/**
	 * affichage des fichiers PLY
	 */

	public WorkspaceViewer workspaceViewer;

	/**
	 * Barre de menu de la fenetres
	 */
	private MenuBarView menuBar;

	/**
	 * section des parametres de position d'objet de la fenetre
	 */
	private ObjectPositionSection objectPositionSection;

	/**
	 * section du timer
	 */

	private TimerSection timerSection;

	/**
	 * console de la fenetre
	 */
	private static TextArea console;

	/**
	 * Thread du Controleur Horloge
	 */
	public Timer threadControleur;

	/**
	 * La grille des canvas
	 */
	private static CanvasGrid canvasGrid;

	/**
	 * Racines du borderpane, utilise pour le redimensionnement de la fenetre
	 * 
	 */
	private VBox rootLeft, rootRight;

	/**
	 * Cree un vue simple et lui attache un modele
	 * 
	 * @param model Le modele a attacher a la vue
	 */

	public MainWindow(Model model) {

		this.model = model;

		this.setMaximized(true);
		this.setFullScreen(false);

		BorderPane root = new BorderPane();

		menuBar = new MenuBarView(this);
		root.setTop(menuBar);

		rootLeft = new VBox();

		this.workspaceViewer = new WorkspaceViewer(this, model);
		console = new TextArea();
		console.setEditable(false);
		console.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		rootLeft.getChildren().addAll(workspaceViewer, console);

		root.setLeft(rootLeft);

		canvasGrid = new CanvasGrid(model, this);
		root.setCenter(canvasGrid);

		rootRight = new VBox();

		this.objectPositionSection = new ObjectPositionSection(model);
		this.timerSection = new TimerSection(model);
		rootRight.getChildren().addAll(objectPositionSection, timerSection);

		root.setRight(rootRight);

		Scene scene = new Scene(root, 1000, 600);
		this.setScene(scene);
		this.setTitle("3D Rendering Project");

		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				closeApplication();
			}
		});

		this.widthProperty().addListener((obs, oldVal, newVal) -> {
			resize();
		});

		this.heightProperty().addListener((obs, oldVal, newVal) -> {
			resize();
		});
		resize();

		new ExceptionDisplayer(model);

		this.show();
	}

	/**
	 * Redimensionne les elements de la fenetre
	 */
	public void resize() {
		rootLeft.setPrefWidth(this.getWidth() * 0.3);
		canvasGrid.updateCanvasPositionsAndSizes();
		canvasGrid.setPrefWidth(getCanvasGridWidth());
		rootRight.setPrefWidth(this.getWidth() * 0.15);
	}

	/**
	 * <<<<<<< HEAD Recupère la largeur que doit faire la canvasGrid en fonction de
	 * la taille de la fenetre
	 * 
	 * @return la largeur en fonction de la taille de la fenêtre ======= Recupere la
	 *         largeur que doit faire la canvasGrid en fonction de la taille de la
	 *         fenetre
	 * @return >>>>>>> ad292a3b6deac3998a5f00a0c9b5b6088cce62b6
	 */
	public double getCanvasGridWidth() {
		return this.getWidth() * 0.5;
	}

	/**
	 * <<<<<<< HEAD Recupère la hauteur que doit faire la canvasGrid en fonction de
	 * la taille de la fenetre
	 * 
	 * @return la hauteur en fonction de la taille de la fenêtre ======= Recupere la
	 *         hauteur que doit faire la canvasGrid en fonction de la taille de la
	 *         fenetre
	 * @return >>>>>>> ad292a3b6deac3998a5f00a0c9b5b6088cce62b6
	 */
	public double getCanvasGridHeight() {
		return this.getHeight() * 0.9;
	}

	/**
	 * affiche un message dans la console
	 * 
	 * @param txt      le text du message
	 * @param timeInMs duree enventuelle de l'action correspondant au message (en
	 *                 ms)
	 */
	public static void printToConsole(String txt, double timeInMs) {
		console.appendText(txt);
		if (timeInMs >= 0) {
			console.appendText(" [" + timeInMs + " ms]\n");
		} else {
			console.appendText("\n");
		}
	}

	/**
	 * vide la console
	 */
	public static void clearConsole() {
		console.setText("");
	}

	/**
	 * Retire le canvas de la grille des canvas
	 * 
	 * @param canvas le canvas a retirer
	 */
	public static void removeCanvas(CanvasView canvas) {
		canvasGrid.removeCanvas(canvas);
	}

	/**
	 * ouvre le workspace choisi
	 */
	public void openWorkspace() {
		workspaceViewer.openWorkspace();
	}

	/**
	 * ferme la fenetre de la vue
	 */
	public void closeApplication() {
		this.timerSection.stopTimer();
		this.close();
	}

	/**
	 * Recuper le model lie a la fenetre principale
	 * 
	 * @return le model lié a la fenetre principale
	 */
	public Model getModel() {
		return model;
	}

}
