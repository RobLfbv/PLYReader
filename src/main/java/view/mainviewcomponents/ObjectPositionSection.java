package view.mainviewcomponents;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Model;
import model.PLYData;
import utils.Observer;
import utils.Subject;
import utils.transformations.TransformationType;

/**
 * Section des parametres de l'interface
 */
public class ObjectPositionSection extends VBox implements Observer {

	/**
	 * les differents spinners pour les transformations
	 */
	private CustomSpinner xTranslation, yTranslation, zTranslation, xRotation, yRotation, zRotation, scale;

	/**
	 * Cree la section des parametres
	 * 
	 * @param model le modele sur lequels les transformations vont être appliquees
	 */
	public ObjectPositionSection(Model model) {
		super();
		Label titleTransformationLabel = new Label("Object Position : ");
		titleTransformationLabel.setFont(new Font(15));
		titleTransformationLabel.setStyle("-fx-font-weight: bold");
		this.getChildren().addAll(titleTransformationLabel, new Separator());

		xTranslation = new CustomSpinner(model, TransformationType.XTRANSLATION, -25, -1, 1, 25);
		this.getChildren().addAll(new Label("X Translation"), xTranslation);
		yTranslation = new CustomSpinner(model, TransformationType.YTRANSLATION, -25, -1, 1, 25);
		this.getChildren().addAll(new Label("Y Translation"), yTranslation);
		zTranslation = new CustomSpinner(model, TransformationType.ZTRANSLATION, -25, -1, 1, 25);
		this.getChildren().addAll(new Label("Z Translation"), zTranslation);
		xRotation = new CustomSpinner(model, TransformationType.XROTATION, -Math.PI / 2, -0.05, 0.05, Math.PI / 2);
		this.getChildren().addAll(new Label("X Rotation"), xRotation);
		yRotation = new CustomSpinner(model, TransformationType.YROTATION, -Math.PI / 2, -0.05, 0.05, Math.PI / 2);
		this.getChildren().addAll(new Label("Y Rotation"), yRotation);
		zRotation = new CustomSpinner(model, TransformationType.ZROTATION, -Math.PI / 2, -0.05, 0.05, Math.PI / 2);
		this.getChildren().addAll(new Label("Z Rotation"), zRotation);
		scale = new CustomSpinner(model, TransformationType.SCALE, 0.5, 0.9, 1.1, 2);
		this.getChildren().addAll(new Label("Scale"), scale);

		Button resetTransformationButton = new Button("Reset Transformation");
		resetTransformationButton.setOnAction(e -> {
			model.resetTransformation();
		});

		this.getChildren().addAll(resetTransformationButton);
		model.attach(this);
	}

	/**
	 * update des spinners en fonction du modele
	 */
	@Override
	public void update(Subject subj) {
		update(subj, null);
	}

	/**
	 * update des spinners en fonction du modele
	 */
	@Override
	public void update(Subject subj, Object data) {
		if (data instanceof List<?> || data instanceof PLYData) {
			xTranslation.update();
			yTranslation.update();
			zTranslation.update();
			xRotation.update();
			yRotation.update();
			zRotation.update();
			scale.update();
		}
	}
}
