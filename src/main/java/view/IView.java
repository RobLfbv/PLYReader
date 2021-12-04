package view;

import javafx.scene.paint.Color;
import model.Model;
import sides.Side;

/**
 * 
 * Interface qui est implémentée par toutes les vues
 *
 */
public interface IView {
	/**
	 * Récupère le modèle associé à la vue
	 * 
	 * @return le modèle
	 */
	public Model getModel();

	/**
	 * récupère le mode de rendu de la vue
	 * 
	 * @return le mode de rendu
	 */
	public RenderMode getRenderMode();

	/**
	 * récupère le coté par lequel est vu le modèle sur la vue
	 * 
	 * @return le coté
	 */
	public Side getSide();

	/**
	 * récupère le canvas de la vue
	 * 
	 * @return le canvas
	 */
	public Custom3DCanvas getCanvas();

	/**
	 * Change le coté par lequel est vu le modèle sur la vue
	 * 
	 * @param side   le nouveau coté
	 * @param render vrai s'il faut refaire le rendu, faux sinon
	 */
	public void setSide(Side side, boolean render);

	/**
	 * Change le mode de rendu de la vue
	 * 
	 * @param renderMode le nouveau mode de rendu
	 * @param render     vrai s'il faut refaire le rendu, faux sinon
	 */
	public void setRenderMode(RenderMode renderMode, boolean render);

	/**
	 * Recupère la couleur des faces du canvas de cette vue
	 * 
	 * @return la couleur
	 */
	public Color getDefaultFaceColor();

	/**
	 * récupère la couleur de fond du canvas de cette vue
	 * 
	 * @return la couleur
	 */
	public Color getBackgroundColor();

	/**
	 * Recupère la couleur des arrêtes du canvas de cette vue
	 * 
	 * @return la couleur
	 */
	public Color getEdgesColor();
}
