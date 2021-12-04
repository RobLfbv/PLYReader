package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.TransformationMatrice;

/**
 * Partie bouttons de transformation de la vue
 */
public class TransformationButtons extends VBox {
	/**
	 * La vue dans laquelle des boutons sont plac�s
	 */
	private View parent;
	/**
	 * matrice de transformation qu'on associe à chaque boutton
	 */
	private TransformationMatrice matrice;
	@SuppressWarnings("unused")
	/**
	 * coefficient de changement de chaque bouton
	 */
	private double minusminus, minus, plus, plusplus;
	/**
	 * Boutons associés aux changements
	 */
	private Button mm, m, p, pp;

	/**
	 * Constructeur d'un set de bouton
	 * 
	 * @param parent
	 * @param label
	 * @param matrice
	 * @param minusminus
	 * @param minus
	 * @param plus
	 * @param plusplus
	 */
	public TransformationButtons(View parent, String label, TransformationMatrice matrice, double minusminus,
			double minus, double plus, double plusplus) {
		this.parent = parent;

		this.matrice = matrice;
		this.minusminus = minusminus;
		this.minus = minus;
		this.plus = plus;
		this.plusplus = plusplus;

		HBox buttons = new HBox();
		this.mm = new Button("--");
		mm.setOnAction(e -> {
			apply(minusminus);
		});
		this.m = new Button("-");
		m.setOnAction(e -> {
			apply(minus);
		});
		this.p = new Button("+");
		p.setOnAction(e -> {
			apply(plus);
		});
		this.pp = new Button("++");
		pp.setOnAction(e -> {
			apply(plusplus);
		});
		buttons.getChildren().addAll(mm, m, p, pp);

		this.getChildren().addAll(new Label(label), buttons);
	}

	/**
	 * applique la transformation lors du clic du bouton
	 * 
	 * @param value
	 */
	private void apply(double value) {
		parent.getModel().applyTransformation(matrice.get(value));

	}
}
