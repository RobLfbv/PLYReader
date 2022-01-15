package view.mainviewcomponents;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Model;
import utils.transformations.TransformationFabric;
import utils.transformations.TransformationType;

/**
 * Spinner customise pour appliquer des transformation au model
 */
public class CustomSpinner extends HBox {

	/**
	 * le model auquel les transformation serront appliquees
	 */
	private Model model;

	/**
	 * le type de transformation du spinner
	 */
	private TransformationType type;
	/**
	 * boutton -- du spinner
	 */
	private Button minusminus;
	/**
	 * bouton - du spinner
	 */
	private Button minus;
	/**
	 * textField pour rentrer une valeur
	 */
	private TextField input;
	/**
	 * boutton + du spinner
	 */
	private Button plus;
	/**
	 * boutton ++ du spinner
	 */
	private Button plusplus;

	/**
	 * Cree un custom spinner pour être ajoute a la fenetre de l'application
	 * 
	 * @param model      le model auquel on applique le transformation
	 * @param type       type de transformation du spinner
	 * @param minusminus valeur pour le boutton --
	 * @param minus      valeur pour le boutton -
	 * @param plus       valeur pour le boutton +
	 * @param plusplus   valeur pour le boutton ++
	 */
	public CustomSpinner(Model model, TransformationType type, double minusminus, double minus, double plus,
			double plusplus) {
		super();
		this.model = model;
		this.type = type;

		this.minusminus = new Button("--");
		this.minusminus.setOnAction(e -> {
			model.applyTransformation(TransformationFabric.get(type, minusminus));
		});
		this.minus = new Button("-");
		this.minus.setOnAction(e -> {
			model.applyTransformation(TransformationFabric.get(type, minus));
		});

		this.input = new TextField("" + model.getRepere().getParameter(type));
		this.input.setOnAction(e -> {
			if (input.getText().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
				model.getRepere().setParameter(type, Double.parseDouble(input.getText()));
			}

		});

		this.plus = new Button("+");
		this.plus.setOnAction(e -> {
			model.applyTransformation(TransformationFabric.get(type, plus));
		});
		this.plusplus = new Button("++");
		this.plusplus.setOnAction(e -> {
			model.applyTransformation(TransformationFabric.get(type, plusplus));
		});

		this.getChildren().addAll(this.minusminus, this.minus, this.input, this.plus, this.plusplus);
	}

	/**
	 * Mets a jour le textField en fonction des valeur de transformation du model
	 */
	public void update() {
		double value = model.getRepereParameter(type);
		input.setText("" + Math.floor(value * 100) / 100);
	}
}
