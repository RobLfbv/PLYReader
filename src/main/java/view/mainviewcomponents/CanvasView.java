package view.mainviewcomponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import model.Face;
import model.Model;
import model.PLYData;
import model.Point;
import sides.Side;
import utils.Observer;
import utils.Subject;
import utils.transformations.Transformation;
import utils.transformations.TransformationFabric;
import utils.transformations.TransformationScale;
import utils.transformations.TransformationType;
import view.utils.Light;
import view.utils.Parameters;
import view.utils.RenderMode;
import view.utils.Shadow;
import view.utils.SmoothPolygonRenderer;
import view.utils.SmoothingMode;
import view.windows.MainWindow;

/**
 * Canvas qui affiche le model
 *
 */
public class CanvasView extends Canvas implements Observer {

	/**
	 * model auquel le canvas est liee
	 */
	private Model model;

	/**
	 * parametres de rendu du canvas
	 */
	private CanvasParametersMenu parameters;

	/**
	 * la data transformee (stockee dans le canvas pour accelerer les calculs,
	 * notament les differents tris)
	 */
	private PLYData transformedData;

	/**
	 * le facteur de zoom du canvas
	 */
	private double scaleFactor;

	/**
	 * Coordonnee x du debut d'un drag sur le canvas
	 */
	private double startMouseDragX;

	/**
	 * Coordonnee y du debut d'un drag sur le canvas
	 */
	private double startMouseDragY;

	/**
	 * Coordonnee x du debut d'un drag sur le canvas mise a jour a chaque nouvelle
	 * tentative de rendu pendant le drag
	 */
	private double mouseDragX;

	/**
	 * Coordonnee y du debut d'un drag sur le canvas mise a jour a chaque nouvelle
	 * tentative de rendu pendant le drag
	 */
	private double mouseDragY;

	/**
	 * les parametres par defaut d'un canvas
	 * 
	 */
	public final Parameters defaultParameters;

	/**
	 * Cree un nouveau canvas
	 * 
	 * @param model            modele auquelle le canvas est lie
	 * @param width            largeur du canvas
	 * @param height           hauteur du canvas
	 * @param scaleFactor      niveau de zoom de la fenetre
	 * @param renderParameters parametres de rendu du canvas
	 */
	public CanvasView(Model model, int width, int height, double scaleFactor, Parameters renderParameters) {
		super(width, height);
		this.defaultParameters = new Parameters(renderParameters);

		this.model = model;
		this.scaleFactor = scaleFactor;
		this.parameters = new CanvasParametersMenu(this, renderParameters);

		this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				final double dragIncrement = 10;
				double xDelta = event.getX() - startMouseDragX;
				double yDelta = event.getY() - startMouseDragY;
				if (Math.abs(xDelta) < dragIncrement && Math.abs(yDelta) < dragIncrement)
					parameters.show(CanvasView.this, event.getScreenX(), event.getSceneY());
			}
		});

		this.setOnScroll((ScrollEvent event) -> {
			parameters.hide();
			List<Transformation> transformations = new ArrayList<>();

			if (event.getDeltaY() > 0) {
				transformations.add(new TransformationScale(1.1));
			} else {
				transformations.add(new TransformationScale(0.9));
			}
			model.applyTransformation(TransformationFabric.getCentered(model.getRepere(), transformations));

		});

		this.setOnMousePressed(e -> {
			parameters.hide();
			startMouseDragX = e.getX();
			startMouseDragY = e.getY();
			mouseDragX = e.getX();
			mouseDragY = e.getY();
		});

		this.setOnMouseDragged(e -> {
			parameters.hide();
			double xDelta = e.getX() - mouseDragX;
			double yDelta = e.getY() - mouseDragY;

			final double dragIncrement = 10;
			if (Math.abs(xDelta) > dragIncrement || Math.abs(yDelta) > dragIncrement) {
				if (e.getButton() == MouseButton.PRIMARY) {
					List<Transformation> transformationMatrice = parameters.getSide().getTranslationFromDrag(xDelta,
							yDelta);
					model.applyTransformation(transformationMatrice);
				} else if (e.getButton() == MouseButton.SECONDARY) {
					List<Transformation> transformationMatrice = parameters.getSide()
							.getCenteredRotationFromDrag(model.getRepere(), xDelta / 100, yDelta / 100);
					model.applyTransformation(transformationMatrice);
				} else if (parameters.computeLight() && e.getButton() == MouseButton.MIDDLE) {
					List<Transformation> transformationMatrice = parameters.getSide().getRotationFromDrag(xDelta / 100,
							yDelta / 100);
					Light.applyTranformations(transformationMatrice);
					Set<TransformationType> types = new HashSet<TransformationType>();
					types.add(TransformationType.CHANGELIGHT);
					model.notifyObservers(types);
				}

				mouseDragX = e.getX();
				mouseDragY = e.getY();
			}
		});

		model.attach(this);

		this.update(model, model.getTransformedData());
	}

	/**
	 * applique des transformation a la data du canvas
	 * 
	 * @param transformationMatrices les transformation a effectuer
	 */
	public void applyTranformations(List<Transformation> transformationMatrices) {
		if (transformedData == null)
			return;

		Set<TransformationType> types = new HashSet<>();
		for (Transformation transformationMatrice : transformationMatrices) {
			this.transformedData.applyTransformationWithMatrix(transformationMatrice.get());
			types.add(transformationMatrice.getType());
		}

		render3DModel(types);
	}

	/**
	 * Fait un rendu du modele 3D stocke dans le modele lie a cette vue. Ce rendu
	 * depend du mode de rendu de la vue
	 * 
	 * @param types - set des types de transformation
	 */

	public void render3DModel(Set<TransformationType> types) {
		if (parameters == null)
			return;
		Side side = parameters.getSide();
		if (transformedData == null || !side.hasToRender(types))
			return;

		double time;

		MainWindow.printToConsole("", -1);
		MainWindow.printToConsole("Rendering " + transformedData.getNbFaces() + " faces", -1);
		if (types != null)
			MainWindow.printToConsole(types.toString(), -1);

		Color defaultCouleur = parameters.getDefaultFaceColor();

		if (side.hasToCalculateLight(types)) {
			time = System.currentTimeMillis();
			Light.getInstance().resetColorsOfFaceIfNoLight(defaultCouleur, transformedData);
			if ((parameters.onlyRenderVisibleFaces() || parameters.computeLight())) {
				Light.getInstance().shadeOnColor(side.getI(), defaultCouleur, transformedData,
						parameters.getShadowStrength(), parameters.getLightStrength(), parameters.computeLight(),
						parameters.onlyRenderVisibleFaces());
			}
			MainWindow.printToConsole("Light Calculations done !", System.currentTimeMillis() - time);
		}

		if (side.hasToSort(types)) {
			time = System.currentTimeMillis();
			Collections.sort(transformedData.getFaces(), side.getComparator(transformedData));
			MainWindow.printToConsole("Face sorting !", System.currentTimeMillis() - time);
		}

		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(parameters.getBackgroundColor());
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (parameters.renderShadow()) {
			time = System.currentTimeMillis();
			int renders = renderFaces(gc,
					Shadow.getInstance(transformedData).applyShadow(transformedData, model.getRepere()),
					RenderMode.FACESONLY, SmoothingMode.DISABLED);
			MainWindow.printToConsole("Shadow rendering done ! (" + renders + " faces)",
					System.currentTimeMillis() - time);
		}
		time = System.currentTimeMillis();
		int renders = renderFaces(gc, transformedData, parameters.getRenderMode(), parameters.getSmoothingMode());
		MainWindow.printToConsole("3D Model rendering done ! (" + renders + " faces)",
				System.currentTimeMillis() - time);
	}

	/**
	 * Transforme la coodornee x d'un point sur le canvas pour le centrer
	 */
	private double canvasTransformationX(double x) {
		return x + this.getWidth() / 2;
	}

	/**
	 * Transforme la coodornee y d'un point sur le canvas pour le centrer et invese
	 * l'axe y
	 */
	private double canvasTransformationY(double y) {
		return -1 * y + this.getHeight() / 2;
	}

	/**
	 * Fait le rendu des faces du model sur le canvas
	 * 
	 * @param gc            le context graphique du canvas
	 * @param data          la data dont on fait le rendu
	 * @param renderMode    le mode de rendu
	 * @param smoothingMode le mode de lissage
	 * @return le nombre de face dont on a fait le rendu
	 */
	public int renderFaces(GraphicsContext gc, PLYData data, RenderMode renderMode, SmoothingMode smoothingMode) {
		if (smoothingMode != SmoothingMode.DISABLED)
			data.computePointsColorsFromFacesColors();

		gc.setStroke(parameters.getEdgesColor());

		int len = data.getNbFaces();
		int howManyRenders = 0;
		for (int idx = 0; idx < len; idx++) {
			Face face = data.getFace(idx);
			if (!face.isVisible())
				continue;
			howManyRenders++;

			List<Point> points = face.getPointsFromData(data);
			double[] xs = new double[points.size()];
			double[] ys = new double[points.size()];
			Color[] cs = new Color[points.size()];

			for (int i = 0; i < points.size(); i++) {
				xs[i] = this
						.canvasTransformationX(parameters.getSide().getXOfPoint(points.get(i)) * this.getScaleFactor());
				ys[i] = this
						.canvasTransformationY(parameters.getSide().getYOfPoint(points.get(i)) * this.getScaleFactor());

				cs[i] = (points.get(i).getColor() == null ? parameters.getDefaultFaceColor()
						: points.get(i).getColor());

			}

			Color faceColorjfx = (face.getColor() == null ? parameters.getDefaultFaceColor()
					: (parameters.computeLight() ? face.getColor() : face.getColorInit()));

			if (renderMode == RenderMode.EDGESFACES || renderMode == RenderMode.FACESONLY)
				if (smoothingMode != SmoothingMode.DISABLED) {
					SmoothPolygonRenderer renderer = new SmoothPolygonRenderer(this, smoothingMode);
					renderer.fillPolygonWithGradient(xs, ys, cs);
				} else {
					gc.setFill(faceColorjfx);
					gc.fillPolygon(xs, ys, points.size());
				}
			if (renderMode == RenderMode.EDGESFACES || renderMode == RenderMode.EDGESONLY)
				gc.strokePolygon(xs, ys, points.size());
		}
		return howManyRenders;

	}

	/**
	 * Change le zoom de l'affichage
	 * 
	 * @param scaleFactor le zoom
	 */
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
		Set<TransformationType> types = new HashSet<>();
		types.add(TransformationType.CHANGECANVAS);
		this.render3DModel(types);
	}

	/**
	 * Retourne le zoom de l'affichage
	 * 
	 * @return le zoom
	 */
	public double getScaleFactor() {
		return scaleFactor;
	}

	/**
	 * copie les parametres de rendu du canvas dans le presse papier global de
	 * l'application
	 */
	public void copyParametersToClipboard() {
		model.setParametersClipboard(parameters.getParameters());
	}

	/**
	 * colle les parametres du presse papier global de l'application dans le canvas
	 */
	public void pasteParametersFromClipboard() {
		parameters.setParameters(model.getParametersClipboard());
	}

	/**
	 * redimensionne le canvas
	 * 
	 * @param width       la largueur
	 * @param height      la hauteur
	 * @param scaleFactor le facteur de zoom
	 */
	public void setSize(int width, int height, double scaleFactor) {
		this.scaleFactor = scaleFactor;
		this.setWidth(width);
		this.setHeight(height);

		Set<TransformationType> types = new HashSet<>();
		types.add(TransformationType.CHANGECANVAS);
		this.render3DModel(types);
	}

	/**
	 * Modifie la data du canvas
	 * 
	 * @param transformedData la nouvelle data
	 */
	public void setTransformedData(PLYData transformedData) {
		this.transformedData = new PLYData(transformedData);
		Set<TransformationType> types = new HashSet<>();
		types.add(TransformationType.LOADMODEL);
		this.render3DModel(types);
	}

	/**
	 * Update du canvas a chaque modification du model
	 */
	@Override
	public void update(Subject subj) {
		this.render3DModel(null);

	}

	/**
	 * Update du canvas a chaque modification du model, avec passage de parametres
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Subject subj, Object data) {
		if (data instanceof List<?>) {
			this.applyTranformations((List<Transformation>) data);
		} else if (data instanceof PLYData) {
			this.setTransformedData((PLYData) data);
		} else if (data instanceof Set<?>) {
			this.render3DModel((Set<TransformationType>) data);
		}
	}

}
