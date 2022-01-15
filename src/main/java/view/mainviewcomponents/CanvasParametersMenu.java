package view.mainviewcomponents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import sides.Side;
import sides.SidesFabrique;
import utils.MathUtils;
import utils.transformations.TransformationType;
import view.utils.Parameters;
import view.utils.RenderMode;
import view.utils.SmoothingMode;
import view.windows.MainWindow;

/**
 * Menu qui s'affiche au clic droit sur un canvas pour modifier ses parametres
 *
 */
public class CanvasParametersMenu extends ContextMenu {
	/**
	 * le canvas auquel appartiennent les parametres
	 */
	private CanvasView view;
	/**
	 * objet qui stocke les parametres du canvas
	 */
	private Parameters parameters;
	/**
	 * Menu pour choisir le cote duquel on regarde l'objet sur le canvas
	 */
	private Menu sideMenu;
	/**
	 * Menu de choix du mode de rendu
	 */
	private Menu renderModeMenu;
	/**
	 * Colorpicker pour la couleur du fond
	 */
	private CustomColorPicker backgroundColorPicker;
	/**
	 * Colorpicker pour la couleur des arretes
	 */
	private CustomColorPicker edgesColorPicker;
	/**
	 * colorpicker pour la couleur des faces
	 */
	private CustomColorPicker defaultFaceColorPicker;
	/**
	 * Radiobutton pour le choix d'afficher seulement les faces visibles
	 */
	private RadioMenuItem onlyRenderVisibleFacesMenuItem;
	/**
	 * Radiobutton pour le choix de calculer la lumieres
	 */
	private RadioMenuItem computeLightMenuItem;
	/**
	 * List des elements de menu qui sont cache si le choix du rendu de lumiere est
	 * desactive
	 */
	private List<MenuItem> lightParametersDisabled;
	/**
	 * Slider pour le niveau d'ombre du clacul de lumiere
	 */
	private Slider shadowStrengthSlider;
	/**
	 * Slider pour le niveau de lumiere du clacul de lumiere
	 */
	private Slider lightStrengthSlider;
	/**
	 * RadioButton pour le choix du rendu de l'ombre portee
	 */
	private RadioMenuItem renderShadowMenuItem;
	/**
	 * Choix du mode de lissage du rendu
	 */
	private Menu smoothingModeMenu;

	/**
	 * Creation d'un menu pour les parametres du canvas
	 * 
	 * @param view       le canvas auquel le menu est attache
	 * @param parameters l'objet parametres utilise pour son initialisation
	 *                   (parametres par defaut)
	 */
	public CanvasParametersMenu(CanvasView view, Parameters parameters) {
		super();

		this.view = view;
		this.parameters = parameters;

		MenuItem copyParametersMenuItem = new MenuItem("Copy Parameters");
		copyParametersMenuItem.setOnAction(e -> {
			view.copyParametersToClipboard();
		});
		MenuItem pasteParametersMenuItem = new MenuItem("Paste Parameters");
		pasteParametersMenuItem.setOnAction(e -> {
			view.pasteParametersFromClipboard();
		});
		MenuItem resetParametersMenuItem = new MenuItem("Reset Parameters");
		resetParametersMenuItem.setOnAction(e -> {
			setParameters(view.defaultParameters);
		});

		MenuItem deleteCanvasMenu = new MenuItem("Delete canvas");
		deleteCanvasMenu.setOnAction(e -> {
			MainWindow.removeCanvas(view);
		});

		sideMenu = new Menu("View from ");
		sideMenu.getItems().addAll(new RadioMenuItem("Positive X"), new RadioMenuItem("Positive Y"),
				new RadioMenuItem("Positive Z"));
		ToggleGroup sideGroup = new ToggleGroup();
		for (MenuItem item : sideMenu.getItems()) {
			((RadioMenuItem) item).setToggleGroup(sideGroup);
		}
		sideGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
			setSide(new SidesFabrique().create((((RadioMenuItem) newValue).getText())), false, true);
		});

		renderModeMenu = new Menu("Rendering Mode ");
		renderModeMenu.getItems().addAll(new RadioMenuItem("Edges Only"), new RadioMenuItem("Faces Only"),
				new RadioMenuItem("Both Edges and Faces"));
		ToggleGroup renderModeGroup = new ToggleGroup();
		for (MenuItem item : renderModeMenu.getItems()) {
			((RadioMenuItem) item).setToggleGroup(renderModeGroup);
		}
		renderModeGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
			setRenderMode(RenderMode.getFromString(((RadioMenuItem) newValue).getText()), false, true);
		});

		Menu colorsMenu = new Menu("Colors");

		defaultFaceColorPicker = new CustomColorPicker(Color.RED);
		MenuItem defaultFaceColor = new MenuItem("Default Face Color");
		defaultFaceColor.setGraphic(defaultFaceColorPicker);
		defaultFaceColorPicker.setOnAction(e -> {
			setDefaultFaceColor(defaultFaceColorPicker.getValue(), false, true);
		});

		edgesColorPicker = new CustomColorPicker(Color.GREEN);
		MenuItem edgesColor = new MenuItem("Edges Color");
		edgesColor.setGraphic(edgesColorPicker);
		edgesColorPicker.setOnAction(e -> {
			setEdgesColor(edgesColorPicker.getValue(), false, true);
		});

		backgroundColorPicker = new CustomColorPicker(Color.BLUE);
		MenuItem backgroundColor = new MenuItem("Background Color");
		backgroundColor.setGraphic(backgroundColorPicker);
		backgroundColorPicker.setOnAction(e -> {
			setBackgroundColor(backgroundColorPicker.getValue(), false, true);
		});

		colorsMenu.getItems().addAll(defaultFaceColor, edgesColor, backgroundColor);

		onlyRenderVisibleFacesMenuItem = new RadioMenuItem("Only render visible faces");
		onlyRenderVisibleFacesMenuItem.setOnAction(e -> {
			setOnlyRenderVisibleFaces(onlyRenderVisibleFacesMenuItem.isSelected(), false, true);
		});

		Menu lightMenu = new Menu("Light Parameters");

		computeLightMenuItem = new RadioMenuItem("Compute Light");
		computeLightMenuItem.setOnAction(e -> {
			setComputeLight(computeLightMenuItem.isSelected(), false, true);
		});

		lightParametersDisabled = new ArrayList<>();

		MenuItem shadowStrengthItem = new MenuItem("Shadow Stength");
		shadowStrengthSlider = new Slider(0, 100, 42);
		shadowStrengthSlider.setShowTickLabels(true);
		shadowStrengthSlider.setShowTickMarks(true);
		shadowStrengthSlider.setMajorTickUnit(25);
		shadowStrengthSlider.setBlockIncrement(10);
		shadowStrengthItem.setGraphic(shadowStrengthSlider);
		lightParametersDisabled.add(shadowStrengthItem);
		shadowStrengthSlider.setOnMouseReleased(e -> {
			setShadowStrength(MathUtils.map(shadowStrengthSlider.getValue(), 0, 100, 1, 0), false, true);
		});

		MenuItem lightStrengthItem = new MenuItem("Light Stength");
		lightStrengthSlider = new Slider(0, 100, 42);
		lightStrengthSlider.setShowTickLabels(true);
		lightStrengthSlider.setShowTickMarks(true);
		lightStrengthSlider.setMajorTickUnit(25);
		lightStrengthSlider.setBlockIncrement(10);
		lightStrengthItem.setGraphic(lightStrengthSlider);
		lightParametersDisabled.add(lightStrengthItem);
		lightStrengthSlider.setOnMouseReleased(e -> {
			setLightStrength(MathUtils.map(lightStrengthSlider.getValue(), 0, 100, 1, 2), false, true);
		});

		renderShadowMenuItem = new RadioMenuItem("Render Shadow");
		renderShadowMenuItem.setOnAction(e -> {
			setRenderShadow(renderShadowMenuItem.isSelected(), false, true);
		});
		lightParametersDisabled.add(renderShadowMenuItem);

		lightMenu.getItems().addAll(computeLightMenuItem, shadowStrengthItem, lightStrengthItem, renderShadowMenuItem);

		smoothingModeMenu = new Menu("Smoothing ");
		smoothingModeMenu.getItems().addAll(new RadioMenuItem("Disabled"), new RadioMenuItem("Old School"),
				new RadioMenuItem("Smooth"));
		ToggleGroup smoothingModeGroup = new ToggleGroup();
		for (MenuItem item : smoothingModeMenu.getItems()) {
			((RadioMenuItem) item).setToggleGroup(smoothingModeGroup);
		}
		smoothingModeGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
			setSmoothingMode(SmoothingMode.getFromString(((RadioMenuItem) newValue).getText()), false, true);
		});

		this.getItems().addAll(copyParametersMenuItem, pasteParametersMenuItem, resetParametersMenuItem,
				new SeparatorMenuItem(), deleteCanvasMenu, new SeparatorMenuItem(), sideMenu, renderModeMenu,
				colorsMenu, onlyRenderVisibleFacesMenuItem, lightMenu, smoothingModeMenu);
		updateEveryMenuItem();
	}

	/**
	 * Change les parametres du menu
	 * 
	 * @param parameters les nouveaux parametres
	 */
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
		updateEveryMenuItem();
		callRender(TransformationType.UPDATEEVERYPARAMETER);
	}

	/**
	 * Recuperes les parametres
	 * 
	 * @return les parametres
	 */
	public Parameters getParameters() {
		return parameters;
	}

	/**
	 * Mets a jours tous les elements javafx du menu en fonction de l'objet
	 * parameters stocke dans la classe
	 */
	private void updateEveryMenuItem() {
		this.setSide(getSide(), true, false);
		this.setRenderMode(getRenderMode(), true, false);
		this.setDefaultFaceColor(getDefaultFaceColor(), true, false);
		this.setEdgesColor(getEdgesColor(), true, false);
		this.setBackgroundColor(getBackgroundColor(), true, false);
		this.setOnlyRenderVisibleFaces(onlyRenderVisibleFaces(), true, false);
		this.setComputeLight(computeLight(), true, false);
		this.setLightStrength(getLightStrength(), true, false);
		this.setShadowStrength(getShadowStrength(), true, false);
		this.setRenderShadow(renderShadow(), true, false);
		this.setSmoothingMode(getSmoothingMode(), true, false);
	}

	/**
	 * Fait le rendu du canvas auquel le menu est attache
	 * 
	 * @param type le type de changement effectue dans le menu qui necessite un
	 *             nouveau rendu
	 */
	public void callRender(TransformationType type) {
		Set<TransformationType> types = new HashSet<>();
		types.add(type);
		view.render3DModel(types);

	}

	/**
	 * Retourne le cote
	 * 
	 * @return le cote
	 */
	public Side getSide() {
		return parameters.getSide();
	}

	/**
	 * change le cote
	 * 
	 * @param side         le nouveau cote
	 * @param updateMenu   vrai s'il faut update les elements du menu
	 * @param updateCanvas vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setSide(Side side, boolean updateMenu, boolean updateCanvas) {
		parameters.setSide(side);
		if (updateMenu) {
			for (MenuItem item : sideMenu.getItems()) {
				if (new SidesFabrique().create(item.getText()).equals(parameters.getSide()))
					((RadioMenuItem) item).setSelected(true);
			}
		}
		if (updateCanvas)
			callRender(TransformationType.CHANGESIDE);
	}

	/**
	 * retourne le mode de rendu
	 * 
	 * @return le mode de rendu
	 */
	public RenderMode getRenderMode() {
		return parameters.getRenderMode();
	}

	/**
	 * change le mode de rendu
	 * 
	 * @param renderMode   nouveau mode de rendu
	 * @param updateMenu   vrai s'il faut update les elements du menu
	 * @param updateCanvas vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setRenderMode(RenderMode renderMode, boolean updateMenu, boolean updateCanvas) {
		parameters.setRenderMode(renderMode);
		if (updateMenu) {
			for (MenuItem item : renderModeMenu.getItems()) {
				if (RenderMode.getFromString(item.getText()).equals(renderMode))
					((RadioMenuItem) item).setSelected(true);
			}
		}
		if (updateCanvas)
			callRender(TransformationType.CHANGEAPPEARANCE);
	}

	/**
	 * Recupere la couleur par defaut des faces
	 * 
	 * @return la couleur
	 */
	public Color getDefaultFaceColor() {
		return parameters.getDefaultFaceColor();
	}

	/**
	 * change la couleur par defaut des faces
	 * 
	 * @param defaultFaceColor la nouvelle couleur
	 * @param updateMenu       vrai s'il faut update les elements du menu
	 * @param updateCanvas     vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setDefaultFaceColor(Color defaultFaceColor, boolean updateMenu, boolean updateCanvas) {
		parameters.setDefaultFaceColor(defaultFaceColor);
		if (updateMenu)
			defaultFaceColorPicker.setValue(defaultFaceColor);
		if (updateCanvas)
			callRender(TransformationType.CHANGEAPPEARANCE);
	}

	/**
	 * recupere la couleur des arretes
	 * 
	 * @return la couleur
	 */
	public Color getEdgesColor() {
		return parameters.getEdgesColor();
	}

	/**
	 * 
	 * @param edgesColor   - la couleur des arêtes
	 * @param updateMenu   - indique s'il faut mettre a jour le menu
	 * @param updateCanvas - indique s'il faut mettre a jour le canvas
	 */
	public void setEdgesColor(Color edgesColor, boolean updateMenu, boolean updateCanvas) {
		parameters.setEdgesColor(edgesColor);
		if (updateMenu)
			edgesColorPicker.setValue(edgesColor);
		if (updateCanvas)
			callRender(TransformationType.CHANGEAPPEARANCE);
	}

	/**
	 * recupere la couleur du fond
	 * 
	 * @return la couleur
	 */
	public Color getBackgroundColor() {
		return parameters.getBackgroundColor();
	}

	/**
	 * change la couleur de fond du canvas
	 * 
	 * @param backgroundColor la nouvelle couleur
	 * @param updateMenu      vrai s'il faut update les elements du menu
	 * @param updateCanvas    vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setBackgroundColor(Color backgroundColor, boolean updateMenu, boolean updateCanvas) {
		parameters.setBackgroundColor(backgroundColor);
		if (updateMenu)
			backgroundColorPicker.setValue(backgroundColor);
		if (updateCanvas)
			callRender(TransformationType.CHANGEAPPEARANCE);
	}

	/**
	 * recupere le fait de ne faire le rendu que des faces visible
	 * 
	 * @return vrai s'il ne faut faire que le rendu des faces visibles
	 */
	public boolean onlyRenderVisibleFaces() {
		return parameters.isOnlyRenderVisibleFaces();
	}

	/**
	 * change le fait de ne faire le rendu que des faces visible
	 * 
	 * @param onlyRenderVisibleFaces vrai s'il ne faut faire que le rendu des faces
	 *                               visibles
	 * @param updateMenu             vrai s'il faut update les elements du menu
	 * @param updateCanvas           vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setOnlyRenderVisibleFaces(boolean onlyRenderVisibleFaces, boolean updateMenu, boolean updateCanvas) {
		parameters.setOnlyRenderVisibleFaces(onlyRenderVisibleFaces);
		if (updateMenu)
			onlyRenderVisibleFacesMenuItem.setSelected(onlyRenderVisibleFaces);
		if (updateCanvas)
			callRender(TransformationType.CHANGEONLYRENDERVISIBLEFACES);
	}

	/**
	 * recupere le fait de devoir ou non calculer la lumiere
	 * 
	 * @return vrai s'il faut calculer la lumiere
	 */
	public boolean computeLight() {
		return parameters.isComputeLight();
	}

	/**
	 * change le fait de devoir ou non calculer la lumiere
	 * 
	 * @param computeLight vrai s'il faut calculer la lumiere
	 * @param updateMenu   vrai s'il faut update les elements du menu
	 * @param updateCanvas vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setComputeLight(boolean computeLight, boolean updateMenu, boolean updateCanvas) {
		parameters.setComputeLight(computeLight);
		if (updateMenu) {
			computeLightMenuItem.setSelected(computeLight);
		}
		for (MenuItem item : lightParametersDisabled) {
			item.setVisible(computeLight);
		}
		if (updateCanvas)
			callRender(TransformationType.CHANGELIGHT);
	}

	/**
	 * recupere le niveau de lumiere du calcul de lumiere
	 * 
	 * @return le niveau
	 */
	public double getLightStrength() {
		return parameters.getLightStrength();
	}

	/**
	 * change le niveau de lumiere du calcul de lumiere
	 * 
	 * @param lightStrength le niveau de lumiere
	 * @param updateMenu    vrai s'il faut update les elements du menu
	 * @param updateCanvas  vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setLightStrength(double lightStrength, boolean updateMenu, boolean updateCanvas) {
		parameters.setLightStrength(lightStrength);
		if (updateMenu)
			lightStrengthSlider.setValue(MathUtils.map(lightStrength, 1, 2, 0, 100));
		if (updateCanvas)
			callRender(TransformationType.CHANGELIGHT);
	}

	/**
	 * recupere le niveau d'ombre du calcul de lumiere
	 * 
	 * @return le niveau
	 */
	public double getShadowStrength() {
		return parameters.getShadowStrength();
	}

	/**
	 * change le niveau d'ombre du calcul de lumiere
	 * 
	 * @param shadowStrength le niveau d'ombre
	 * @param updateMenu     vrai s'il faut update les elements du menu
	 * @param updateCanvas   vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setShadowStrength(double shadowStrength, boolean updateMenu, boolean updateCanvas) {
		parameters.setShadowStrength(shadowStrength);
		if (updateMenu)
			shadowStrengthSlider.setValue(MathUtils.map(shadowStrength, 0, 1, 100, 0));
		if (updateCanvas)
			callRender(TransformationType.CHANGELIGHT);
	}

	/**
	 * recupere le fait de devoir ou non faire le rendu de l'ombre portee
	 * 
	 * @return vrai s'il faut faire le rendu
	 */
	public boolean renderShadow() {
		return parameters.isRenderShadow();
	}

	/**
	 * change le fait de devoir ou non faire le rendu de l'ombre portee
	 * 
	 * @param renderShadow vrai s'il faut faire le rendu
	 * @param updateMenu   vrai s'il faut update les elements du menu
	 * @param updateCanvas vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setRenderShadow(boolean renderShadow, boolean updateMenu, boolean updateCanvas) {
		parameters.setRenderShadow(renderShadow);
		if (updateMenu)
			renderShadowMenuItem.setSelected(renderShadow);
		if (updateCanvas)
			callRender(TransformationType.CHANGEAPPEARANCE);
	}

	/**
	 * recupere le mode de lissage
	 * 
	 * @return le mode de lissage
	 */
	public SmoothingMode getSmoothingMode() {
		return parameters.getSmoothingMode();
	}

	/**
	 * change le mode de lissage
	 * 
	 * @param smoothingMode le nouveau mode de lissage
	 * @param updateMenu    vrai s'il faut update les elements du menu
	 * @param updateCanvas  vrai s'il faut refaire un rendu sur le canvas
	 */
	public void setSmoothingMode(SmoothingMode smoothingMode, boolean updateMenu, boolean updateCanvas) {
		parameters.setSmoothingMode(smoothingMode);
		if (updateMenu) {
			for (MenuItem item : smoothingModeMenu.getItems()) {
				if (SmoothingMode.getFromString(item.getText()).equals(smoothingMode))
					((RadioMenuItem) item).setSelected(true);
			}
		}
		if (updateCanvas)
			callRender(TransformationType.CHANGEAPPEARANCE);
	}
}
