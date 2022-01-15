package view.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui represente les presets de la grille des canvas
 */
public class CanvasGridPreset {

	/**
	 * Liste des parametres de tous les canvas
	 */
	private List<Parameters> parameters;
	/**
	 * Largeur et hauteur de la grille pour ce preset
	 */
	private int gridWidth, gridHeight;

	/**
	 * Cree un preset
	 * 
	 * @param gridWidth  - largeur de la grille
	 * @param gridHeight - hauteur de la grille
	 */
	public CanvasGridPreset(int gridWidth, int gridHeight) {
		this.parameters = new ArrayList<>();
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

	}

	/**
	 * Cree un preset
	 * 
	 * @param gridWidth  - largeur de la grille
	 * @param gridHeight - hauteur de la grille
	 * @param parameters - les parametres
	 */
	public CanvasGridPreset(int gridWidth, int gridHeight, Parameters parameters) {
		this(gridWidth, gridHeight);
		addParameters(parameters);
	}

	/**
	 * Ajoute un nouveau parametre pour un nouveau canvas
	 * 
	 * @param parameters - le parametres
	 */
	protected void addParameters(Parameters parameters) {
		this.parameters.add(parameters);
	}

	/**
	 * largeur de la grille
	 * 
	 * @return la largeur de la grille
	 */
	public int getGridWidth() {
		return gridWidth;
	}

	/**
	 * hauteur de la grille
	 * 
	 * @return la hauteur de la grille
	 */
	public int getGridHeight() {
		return gridHeight;
	}

	/**
	 * parametres des differents canvas
	 * 
	 * @return les parametres des differents canvas
	 */
	public List<Parameters> getParameters() {
		return parameters;
	}
}
