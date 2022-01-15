package view.utils;

import javafx.scene.paint.Color;
import sides.Side;

/**
 * 
 * Classe qui représente les parametres de rendu d'un canvas
 *
 */
public class Parameters {
	/**
	 * le coté du rendu
	 */
	private Side side;
	/**
	 * le mode de rendu
	 */
	private RenderMode renderMode;
	/**
	 * la couleur par defaut des faces
	 */
	private Color defaultFaceColor;
	/**
	 * la couleur des arretes
	 */
	private Color edgesColor;
	/**
	 * la couleur de fond
	 */
	private Color backgroundColor;

	/**
	 * le fait de faire le rendu seulement des faces visibles
	 */
	private boolean onlyRenderVisibleFaces;
	/**
	 * le fait de faire le calcul on non de la lumiere
	 */
	private boolean computeLight;
	/**
	 * intensité de la lumiere
	 */
	private double lightStrength;
	/**
	 * intensité de l'ombre
	 */
	private double shadowStrength;
	/**
	 * le fait de faire le rendu de l'ombre
	 */
	private boolean renderShadow;
	/**
	 * le mode de lissage du model
	 */
	private SmoothingMode smoothingMode;

	/**
	 * crée un objet de parameres
	 * 
	 * @param side                   le coté du rendu
	 * @param renderMode             le mode de rendu
	 * @param defaultFaceColor       la couleur par defaut des faces
	 * @param edgesColor             la couleur des arretes
	 * @param backgroundColor        la couleur de fond
	 * @param onlyRenderVisibleFaces le fait de faire le rendu seulement des faces
	 *                               visibles
	 * @param computeLight           le fait de faire le calcul on non de la lumiere
	 * @param lightStrength          intensité de l'ombre
	 * @param shadowStrength         intensité de l'ombre
	 * @param renderShadow           le fait de faire le rendu de l'ombre
	 * @param smoothingMode          le mode de lissage du model
	 */
	public Parameters(Side side, RenderMode renderMode, Color defaultFaceColor, Color edgesColor, Color backgroundColor,
			boolean onlyRenderVisibleFaces, boolean computeLight, double lightStrength, double shadowStrength,
			boolean renderShadow, SmoothingMode smoothingMode) {
		this.side = side;
		this.renderMode = renderMode;
		this.defaultFaceColor = defaultFaceColor;
		this.edgesColor = edgesColor;
		this.backgroundColor = backgroundColor;
		this.onlyRenderVisibleFaces = onlyRenderVisibleFaces;
		this.computeLight = computeLight;
		this.lightStrength = lightStrength;
		this.shadowStrength = shadowStrength;
		this.renderShadow = renderShadow;
		this.smoothingMode = smoothingMode;
	}

	/**
	 * copi un objet parametres
	 * 
	 * @param parameters les parametres a copier
	 */
	public Parameters(Parameters parameters) {
		this(parameters.getSide(), parameters.getRenderMode(), parameters.getDefaultFaceColor(),
				parameters.getEdgesColor(), parameters.getBackgroundColor(), parameters.isOnlyRenderVisibleFaces(),
				parameters.isComputeLight(), parameters.getLightStrength(), parameters.getShadowStrength(),
				parameters.isRenderShadow(), parameters.getSmoothingMode());
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public RenderMode getRenderMode() {
		return renderMode;
	}

	public void setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
	}

	public Color getDefaultFaceColor() {
		return defaultFaceColor;
	}

	public void setDefaultFaceColor(Color defaultFaceColor) {
		this.defaultFaceColor = defaultFaceColor;
	}

	public Color getEdgesColor() {
		return edgesColor;
	}

	public void setEdgesColor(Color edgesColor) {
		this.edgesColor = edgesColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isOnlyRenderVisibleFaces() {
		return onlyRenderVisibleFaces;
	}

	public void setOnlyRenderVisibleFaces(boolean onlyRenderVisibleFaces) {
		this.onlyRenderVisibleFaces = onlyRenderVisibleFaces;
	}

	public boolean isComputeLight() {
		return computeLight;
	}

	public void setComputeLight(boolean computeLight) {
		this.computeLight = computeLight;
	}

	public double getLightStrength() {
		return lightStrength;
	}

	public void setLightStrength(double lightStrength) {
		this.lightStrength = lightStrength;
	}

	public double getShadowStrength() {
		return shadowStrength;
	}

	public void setShadowStrength(double shadowStrength) {
		this.shadowStrength = shadowStrength;
	}

	public boolean isRenderShadow() {
		return renderShadow;
	}

	public void setRenderShadow(boolean renderShadow) {
		this.renderShadow = renderShadow;
	}

	public SmoothingMode getSmoothingMode() {
		return smoothingMode;
	}

	public void setSmoothingMode(SmoothingMode smoothingMode) {
		this.smoothingMode = smoothingMode;
	}

}
