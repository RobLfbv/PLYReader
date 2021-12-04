package view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;
import sides.PositiveXSide;
import sides.PositiveYSide;
import sides.PositiveZSide;
import sides.Side;
import sides.SidesFabrique;
import utils.Observer;
import utils.Subject;

/**
 * 
 * Classe qui représente les fenetres secondaire de l'application
 *
 */
public class SecondaryView extends Stage implements IView, Observer {
	/**
	 * Le modèle lié a cette vue
	 */
	private Model model;

	/**
	 * le canvas contenu dans la vue
	 */
	private Custom3DCanvas canvas;
	/**
	 * le choix du mode de rendu de la vue
	 */
	private ChoiceBox<RenderMode> renderMode;
	/**
	 * le choix du coté de la vue
	 */
	private ChoiceBox<Side> side;
	/**
	 * La vue principale à laquelle elle est reliée
	 */
	private View mainView;

	/**
	 * Instantie une vue secondaire
	 * 
	 * @param mainViewcla vue principale a partir de laquelle elle est crée
	 * @param model       le modèle qui lui est lié
	 */
	public SecondaryView(View mainView, Model model) {
		this.model = model;
		this.mainView = mainView;

		VBox root = new VBox();

		canvas = new Custom3DCanvas(this, 300, 300);

		ToolBar toolbar = new ToolBar();

		VBox rootRenderMode = new VBox();

		renderMode = new ChoiceBox<>();
		renderMode.getItems().add(RenderMode.EDGESONLY);
		renderMode.getItems().add(RenderMode.FACESONLY);
		renderMode.getItems().add(RenderMode.EDGESFACES);
		renderMode.setValue(mainView.getRenderMode());

		renderMode.setOnAction(e -> {
			canvas.render3DModel(false);
		});

		rootRenderMode.getChildren().addAll(new Label("Rendering Mode : "), renderMode);

		VBox rootSide = new VBox();

		side = new ChoiceBox<>();
		side.getItems().add(new PositiveXSide());
		side.getItems().add(new PositiveYSide());
		side.getItems().add(new PositiveZSide());
		for (Side s : side.getItems()) {
			if (s.equals(mainView.getSide())) {
				side.setValue(s);
			}
		}

		side.setOnAction(e -> {
			canvas.render3DModel(true);
		});

		rootSide.getChildren().addAll(new Label("View From : "), side);

		Button makeMainView = new Button("Set as main view");
		makeMainView.setOnAction(e -> {

			boolean swapRenderModes = this.getRenderMode() != mainView.getRenderMode();
			boolean swapSides = !this.getSide().equals(mainView.getSide());

			if (swapRenderModes) {
				RenderMode tempRenderMode = this.getRenderMode();
				this.setRenderMode(mainView.getRenderMode(), !swapSides);
				mainView.setRenderMode(tempRenderMode, !swapSides);
			}

			if (swapSides) {
				SidesFabrique fabrique = new SidesFabrique();

				Side tempSide = fabrique.create(this.getSide().toString());
				this.setSide(fabrique.create(mainView.getSide().toString()), true);
				mainView.setSide(tempSide, true);
			}
		});

		toolbar.getItems().addAll(rootRenderMode, new Separator(), rootSide, new Separator(), makeMainView);

		root.getChildren().addAll(toolbar, canvas);

		Scene scene = new Scene(root, 300, 300);

		this.setX(mainView.getX());
		this.setY(mainView.getY());

		this.setScene(scene);
		this.setTitle("Secondary View");
		this.setAlwaysOnTop(true);
		this.show();

		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				removeFromMainView();
			}
		});

		this.widthProperty().addListener((obs, oldVal, newVal) -> {
			resizeContent();
		});
		this.heightProperty().addListener((obs, oldVal, newVal) -> {
			resizeContent();
		});
		resizeContent();

		model.attach(this);

	}

	/**
	 * Methode appelée a chaque redimensionnement de la fenetre
	 */
	private void resizeContent() {
		canvas.setSize((int) (this.getWidth()), (int) (this.getHeight() - 70),
				Math.min(canvas.getWidth(), canvas.getHeight())
						/ Math.min(mainView.getCanvas().getWidth(), mainView.getCanvas().getHeight()));

	}

	/**
	 * Enlève le lien entre la vue principale et cette vue
	 */
	public void removeFromMainView() {
		this.mainView.getSecondaryViews().remove(this);
	}

	/**
	 * récupère le modèle lié à la vue
	 */
	@Override
	public Model getModel() {
		return this.model;
	}

	/**
	 * récupère le mode de rendu de la vue
	 */
	@Override
	public RenderMode getRenderMode() {
		return renderMode.getValue();
	}

	/**
	 * change le mode de rendu de la vue
	 */
	@Override
	public void setRenderMode(RenderMode renderMode, boolean render) {
		this.renderMode.setValue(renderMode);
		if (render)
			this.canvas.render3DModel(false);
	}

	/**
	 * récupère le coté de la vue
	 */
	@Override
	public Side getSide() {
		return side.getValue();
	}

	/**
	 * change le coté de la vue
	 */
	@Override
	public void setSide(Side side, boolean render) {
		this.side.setValue(side);
		if (render)
			this.canvas.render3DModel(true);
	}

	/**
	 * met a jour le canvas de la vue
	 */
	@Override
	public void update(Subject subj) {
		canvas.render3DModel(true);
	}

	/**
	 * met a jour le canvas de la vue (avec paramètres eventuels)
	 */
	@Override
	public void update(Subject subj, Object data) {
		this.update(subj);
	}

	/**
	 * récupère le canvas de cette vue
	 */
	@Override
	public Custom3DCanvas getCanvas() {
		return this.canvas;
	}

	/**
	 * recupère la couleur des faces du canvas de cette vue
	 */
	@Override
	public javafx.scene.paint.Color getDefaultFaceColor() {
		return mainView.getDefaultFaceColor();
	}

	/**
	 * recupère la couleur du fond du canvas de cette vue
	 */
	@Override
	public javafx.scene.paint.Color getBackgroundColor() {
		return mainView.getBackgroundColor();
	}

	/**
	 * recupère la couleur des arrêtes du canvas de cette vue
	 */
	@Override
	public javafx.scene.paint.Color getEdgesColor() {
		return mainView.getEdgesColor();
	}

}
