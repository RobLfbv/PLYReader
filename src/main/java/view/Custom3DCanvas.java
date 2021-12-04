package view;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import model.Face;
import model.PLYData;
import model.Point;
import utils.Matrice;
import utils.TransformationMatrice;
import utils.TransformationType;

/**
 * 
 * Canvas inclu dans les scèns où l'on dessine les modèles 3D
 *
 */
public class Custom3DCanvas extends Canvas {

	/**
	 * La vue dans laquelle le canvas est situé
	 */
	private IView parent;

	/**
	 * le facteur de zoom du canvas
	 */
	private double scaleFactor;

	/**
	 * Coordonnée x du début d'un drag sur le canvas
	 */
	private double mouseDragX;

	/**
	 * Coordonnée y du début d'un drag sur le canvas
	 */
	private double mouseDragY;

	/**
	 * Crï¿½e un nouveau canvas
	 * 
	 * @param parent la vue ï¿½ laquelle appartient en canvas
	 * @param width  largeur du canvas
	 * @param height hauteur du canvas
	 */
	public Custom3DCanvas(IView parent, int width, int height) {
		this(parent, width, height, 1);
	}

	/**
	 * Crï¿½e un nouveau canvas
	 * 
	 * @param parent      la vue ï¿½ laquelle appartient en canvas
	 * @param width       largeur du canvas
	 * @param height      hauteur du canvas
	 * @param scaleFactor niveau de zoom de la fenetre
	 */
	public Custom3DCanvas(IView parent, int width, int height, int scaleFactor) {
		super(width, height);
		this.parent = parent;
		this.scaleFactor = scaleFactor;

		this.setOnScroll((ScrollEvent event) -> {
			TransformationMatrice transformationMatrice = new TransformationMatrice(TransformationType.SCALE);
			if (event.getDeltaY() > 0) {
				this.parent.getModel().applyTransformation(transformationMatrice.get(1.1));
			} else {
				this.parent.getModel().applyTransformation(transformationMatrice.get(0.9));
			}
		});

		this.setOnMousePressed(e -> {
			mouseDragX = e.getX();
			mouseDragY = e.getY();
		});

		this.setOnMouseDragged(e -> {
			double xDelta = e.getX() - mouseDragX;
			double yDelta = e.getY() - mouseDragY;

			final double dragIncrement = 3;
			if (Math.abs(xDelta) > dragIncrement || Math.abs(yDelta) > dragIncrement) {
				if (e.getButton() == MouseButton.PRIMARY) {
					Matrice transformationMatrice = this.parent.getSide().getTranslationFromDrag(xDelta, yDelta);
					this.parent.getModel().applyTransformation(transformationMatrice);
				} else if (e.getButton() == MouseButton.SECONDARY) {
					Matrice transformationMatrice = this.parent.getSide().getRotationFromDrag(xDelta / 100,
							yDelta / 100);
					this.parent.getModel().applyTransformation(transformationMatrice);
				}

				mouseDragX = e.getX();
				mouseDragY = e.getY();
			}
		});

	}

	/**
	 * Fait un rendu du modele 3D stocke dans le modele lie a cette vue. Ce rendu
	 * depend du mode de rendu de la vue
	 */
	public void render3DModel(boolean sortFaces) {
		// System.out.println("======== Rendu de
		// "+parent.getModel().getTransformedData().getFaces().size()+" faces et de
		// "+parent.getModel().getTransformedData().getPoints().size()+" points
		// ========");
		// double time=System.currentTimeMillis();

		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setTransform(new Affine());
		gc.translate(this.getWidth() / 2, this.getHeight() / 2);
		gc.scale(1, -1);

		// System.out.println("Transformation des axes du canvas :
		// "+(System.currentTimeMillis()-time)+" ms");
		// time=System.currentTimeMillis();

		if (sortFaces && parent.getRenderMode() != RenderMode.EDGESONLY) {

			PLYData.heapSortFace(parent.getSide().getComparator(parent.getModel().getTransformedData()),
					parent.getModel().getTransformedData().getFaces());

			// System.out.println("Tri des faces "+(System.currentTimeMillis()-time)+" ms");

		}
		// time=System.currentTimeMillis();

		gc.setFill(parent.getBackgroundColor());
		gc.fillRect(-this.getWidth() / 2, -this.getHeight() / 2, this.getWidth(), this.getHeight());

		// System.out.println("Rendu du fond "+(System.currentTimeMillis()-time)+" ms");
		// time=System.currentTimeMillis();

		gc.setStroke(parent.getEdgesColor());

		int len = parent.getModel().getTransformedData().getFaces().size();
		for (int idx = 0; idx < len; idx++) {
			Face face = parent.getModel().getTransformedData().getFaces().get(idx);
			List<Point> points = face.getPointsFromData(parent.getModel().getTransformedData());
			double[] xs = new double[points.size()];
			double[] ys = new double[points.size()];

			for (int i = 0; i < points.size(); i++) {
				xs[i] = parent.getSide().getXOfPoint(points.get(i)) * scaleFactor;
				ys[i] = parent.getSide().getYOfPoint(points.get(i)) * scaleFactor;
			}
			model.Color faceColor = face.getColor();
			if (faceColor == null) {
				gc.setFill(parent.getDefaultFaceColor());
			} else {
				gc.setFill(Color.color(faceColor.getR() / 255.0, faceColor.getG() / 255.0, faceColor.getB() / 255.0,
						faceColor.getA() / 255.0));
			}

			if (parent.getRenderMode() == RenderMode.EDGESFACES || parent.getRenderMode() == RenderMode.FACESONLY)
				gc.fillPolygon(xs, ys, points.size());
			if (parent.getRenderMode() == RenderMode.EDGESFACES || parent.getRenderMode() == RenderMode.EDGESONLY)
				gc.strokePolygon(xs, ys, points.size());
		}

		// System.out.println("Rendu des faces "+(System.currentTimeMillis()-time)+"
		// ms");
		// time=System.currentTimeMillis();
	}

	/**
	 * Change le zoom de l'affichage
	 * 
	 * @param scaleFactor le zoom
	 */
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
		this.render3DModel(false);
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
		this.render3DModel(false);
	}

}
