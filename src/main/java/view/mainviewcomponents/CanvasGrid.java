package view.mainviewcomponents;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Model;
import sides.individualsides.PositiveZSide;
import view.utils.CanvasGridPreset;
import view.utils.CanvasGridPresetFabric;
import view.utils.ColorConstrain;
import view.utils.Parameters;
import view.utils.RenderMode;
import view.utils.SmoothingMode;
import view.windows.MainWindow;

/**
 * Element javafx qui stocke tous les canvas sous la forme d'une grille
 * modulable dans l'interface
 */
public class CanvasGrid extends VBox {

	/**
	 * Le model auquel les canvas ajoutees/enleves vont etres ataches
	 */
	private Model model;

	/**
	 * Fenetre dans laquelle la grille est placee
	 */
	private MainWindow window;
	/**
	 * Slider pour la largeur de la grille
	 */
	private Slider gridWidthSlider;
	/**
	 * Slider pour la hauteur de la grille
	 */
	private Slider gridHeightSlider;
	/**
	 * List de tous les canvas ajoutes a la grille
	 */
	private List<CanvasView> canvasList;
	/**
	 * Gridpane qui va afficher les canvas
	 */
	private GridPane canvasGrid;

	/**
	 * Cree la grille
	 * 
	 * @param model  - Le model auquel les canvas ajoutees/enleves vont etres
	 *               ataches
	 * @param window - la fenêtre principale
	 */
	public CanvasGrid(Model model, MainWindow window) {
		this.model = model;
		this.canvasList = new ArrayList<>();
		this.window = window;

		ToolBar toolBar = new ToolBar();

		Button addButton = new Button("Add a new canvas");
		addButton.setOnAction(e -> {
			addCanvas();
		});
		Button removeButton = new Button("Remove last added canvas");

		removeButton.setOnAction(e -> {
			removeCanvas();
		});

		gridWidthSlider = new Slider(1, 5, 1);
		gridWidthSlider.setBlockIncrement(1);
		gridWidthSlider.setMajorTickUnit(1);
		gridWidthSlider.setMinorTickCount(0);
		gridWidthSlider.setShowTickLabels(true);
		gridWidthSlider.setShowTickMarks(true);
		gridWidthSlider.setSnapToTicks(true);
		gridWidthSlider.setOnMouseReleased(e -> {
			updateCanvasPositionsAndSizes();
		});

		gridHeightSlider = new Slider(1, 5, 1);
		gridHeightSlider.setBlockIncrement(1);
		gridHeightSlider.setMajorTickUnit(1);
		gridHeightSlider.setMinorTickCount(0);
		gridHeightSlider.setShowTickLabels(true);
		gridHeightSlider.setShowTickMarks(true);
		gridHeightSlider.setSnapToTicks(true);
		gridHeightSlider.setOnMouseReleased(e -> {
			updateCanvasPositionsAndSizes();
		});

		canvasGrid = new GridPane();

		ChoiceBox<String> presets = new ChoiceBox<>();
		presets.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				loadPreset(CanvasGridPresetFabric.get(newValue));
			}
		});
		presets.getItems().addAll("Fullscreen", "3 Sides", "Checkerboard");

		presets.getSelectionModel().selectFirst();

		toolBar.getItems().addAll(new Label("Grid Presets : "), presets, new Separator(), addButton, removeButton,
				new Separator(), new Label("Grid Width "), gridWidthSlider, new Label("Grid Height "),
				gridHeightSlider);
		this.getChildren().addAll(toolBar, canvasGrid);
	}

	/**
	 * Charge la grille a partir d'un preset
	 * 
	 * @param preset le preset
	 */
	private void loadPreset(CanvasGridPreset preset) {
		if (preset == null)
			return;

		this.gridWidthSlider.setValue(preset.getGridWidth());
		this.gridHeightSlider.setValue(preset.getGridHeight());

		for (CanvasView canvasView : canvasList) {
			model.detach(canvasView);
		}
		canvasList.clear();

		for (Parameters parameters : preset.getParameters()) {
			this.canvasList.add(new CanvasView(model, 200, 200, 1, parameters));
		}
		updateCanvasPositionsAndSizes();
	}

	/**
	 * Ajoute un canvas a la grille avec des parametres par defaut
	 */
	private void addCanvas() {
		addCanvas(new Parameters(new PositiveZSide(), RenderMode.FACESONLY, ColorConstrain.color(0.4, 0.4, 0.4),
				ColorConstrain.color(0, 0, 0), ColorConstrain.color(0.8, 0.8, 0.8), true, true, 1.1, 0.5, false,
				SmoothingMode.SMOOTH));
	}

	/**
	 * Ajoute un canvas a la grille avec des parametres definis
	 * 
	 * @param parameters les parametres du nouveau canvas
	 */
	private void addCanvas(Parameters parameters) {
		if (canvasList.size() >= gridHeightSlider.getMax() * gridWidthSlider.getMax())
			return;

		this.canvasList.add(new CanvasView(model, 200, 200, 1, parameters));

		this.updateCanvasPositionsAndSizes();
	}

	/**
	 * Positionne les canvas stockes dans la liste dans le grille et les
	 * redimensionne
	 */
	public void updateCanvasPositionsAndSizes() {
		updateCanvasPositionsAndSizes(window.getCanvasGridWidth(), window.getCanvasGridHeight());
	}

	/**
	 * Positionne les canvas stockes dans la liste dans le grille et les
	 * redimensionne
	 * 
	 * @param width  largeur du parent
	 * @param height hauteur du parent
	 */
	public void updateCanvasPositionsAndSizes(double width, double height) {
		if (width == 0 && height == 0)
			return;

		int gridWidth = (int) gridWidthSlider.getValue(), gridHeight = (int) gridHeightSlider.getValue();

		if (canvasList.size() > gridWidth * gridHeight) {
			if (gridWidth == gridWidthSlider.getMax()) {
				gridHeight++;
				gridHeightSlider.setValue(gridHeight);
			} else {
				gridWidth++;
				gridWidthSlider.setValue(gridWidth);
			}
		}

		int canvasWidth = (int) (width / gridWidth), canvasHeight = (int) (height / gridHeight);
		double scaleFactor = Math.max(canvasWidth / width, canvasHeight / height);

		this.canvasGrid.getChildren().clear();

		for (int y = 0; y < gridHeight; y++) {
			for (int x = 0; x < gridWidth; x++) {
				int canvasIdx = y * gridWidth + x;
				if (canvasIdx >= canvasList.size())
					return;
				CanvasView canvas = canvasList.get(canvasIdx);

				canvas.setSize(canvasWidth, canvasHeight, scaleFactor);

				canvasGrid.add(canvas, x, y);
			}
		}
	}

	/**
	 * Enleve le canvas passe en parametre
	 * 
	 * @param canvas - le canvas a retire
	 */
	public void removeCanvas(CanvasView canvas) {
		final int minimumCanvasNumber = 1;
		if (canvasList.size() == minimumCanvasNumber)
			return;
		model.detach(canvas);
		canvasList.remove(canvas);
		this.updateCanvasPositionsAndSizes();
	}

	/**
	 * Enleve le derniere canvas de la liste
	 */
	private void removeCanvas() {
		final int minimumCanvasNumber = 1;
		if (canvasList.size() == minimumCanvasNumber)
			return;
		CanvasView removed = canvasList.get(canvasList.size() - 1);
		model.detach(removed);
		canvasList.remove(removed);

		this.updateCanvasPositionsAndSizes();
	}
}
