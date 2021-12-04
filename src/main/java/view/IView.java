package view;

import javafx.scene.paint.Color;
import model.Model;
import sides.Side;

/**
 * 
 * Interface qui est impl�ment�e par toutes les vues
 *
 */
public interface IView {
	/**
	 * R�cup�re le mod�le associ� � la vue
	 * 
	 * @return le mod�le
	 */
	public Model getModel();

	/**
	 * r�cup�re le mode de rendu de la vue
	 * 
	 * @return le mode de rendu
	 */
	public RenderMode getRenderMode();

	/**
	 * r�cup�re le cot� par lequel est vu le mod�le sur la vue
	 * 
	 * @return le cot�
	 */
	public Side getSide();

	/**
	 * r�cup�re le canvas de la vue
	 * 
	 * @return le canvas
	 */
	public Custom3DCanvas getCanvas();

	/**
	 * Change le cot� par lequel est vu le mod�le sur la vue
	 * 
	 * @param side   le nouveau cot�
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
	 * Recup�re la couleur des faces du canvas de cette vue
	 * 
	 * @return la couleur
	 */
	public Color getDefaultFaceColor();

	/**
	 * r�cup�re la couleur de fond du canvas de cette vue
	 * 
	 * @return la couleur
	 */
	public Color getBackgroundColor();

	/**
	 * Recup�re la couleur des arr�tes du canvas de cette vue
	 * 
	 * @return la couleur
	 */
	public Color getEdgesColor();
}
