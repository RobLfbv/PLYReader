package view.mainviewcomponents;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Model;
import view.utils.Timer;

/**
 * 
 * Inteface pour la rotation automatique du model
 *
 */
public class TimerSection extends VBox {
	/**
	 * Timer qui applique la rotation au modele a intervales reguliers
	 */
	private Timer timer;

	/**
	 * Slider pour le niveau de rotation sur chaque axe (x,y et z)
	 */
	private Slider xRotationSlider, yRotationSlider, zRotationSlider;

	/**
	 * Creation de la section
	 * 
	 * @param model model sur lequel on applique les rotation
	 */
	public TimerSection(Model model) {
		super();

		this.timer = new Timer(model, this);

		Label titleTimer = new Label("Timer : ");
		titleTimer.setFont(new Font(15));
		titleTimer.setStyle("-fx-font-weight: bold");
		this.getChildren().addAll(titleTimer, new Separator());

		Button playButton = new Button("Start");
		playButton.setOnAction(e -> {
			if (!timer.isRunning()) {
				timer.start();
				playButton.setText("Stop");
			} else {
				timer.stop();
				playButton.setText("Start");
			}
		});
		this.getChildren().add(playButton);

		xRotationSlider = new Slider(-1, 1, 0);
		xRotationSlider.setBlockIncrement(1);
		xRotationSlider.setMajorTickUnit(1);
		xRotationSlider.setMinorTickCount(10);
		xRotationSlider.setShowTickLabels(true);
		xRotationSlider.setShowTickMarks(true);
		xRotationSlider.setSnapToTicks(true);
		this.getChildren().addAll(new Label("X Rotation"), xRotationSlider);

		yRotationSlider = new Slider(-1, 1, 0);
		yRotationSlider.setBlockIncrement(1);
		yRotationSlider.setMajorTickUnit(1);
		yRotationSlider.setMinorTickCount(10);
		yRotationSlider.setShowTickLabels(true);
		yRotationSlider.setShowTickMarks(true);
		yRotationSlider.setSnapToTicks(true);
		this.getChildren().addAll(new Label("Y Rotation"), yRotationSlider);

		zRotationSlider = new Slider(-1, 1, 0);
		zRotationSlider.setBlockIncrement(1);
		zRotationSlider.setMajorTickUnit(1);
		zRotationSlider.setMinorTickCount(10);
		zRotationSlider.setShowTickLabels(true);
		zRotationSlider.setShowTickMarks(true);
		zRotationSlider.setSnapToTicks(true);
		this.getChildren().addAll(new Label("Z Rotation"), zRotationSlider);
	}

	/**
	 * Arrête le timer
	 */
	public void stopTimer() {
		timer.stop();
	}

	/**
	 * Recupere le niveau de rotation sur l'axe x
	 * 
	 * @return la valeur
	 */
	public double getXAmount() {
		return xRotationSlider.getValue() / 10;
	}

	/**
	 * Recupere le niveau de rotation sur l'axe y
	 * 
	 * @return la valeur
	 */
	public double getYAmount() {
		return yRotationSlider.getValue() / 10;
	}

	/**
	 * Recupere le niveau de rotation sur l'axe z
	 * 
	 * @return la valeur
	 */
	public double getZAmount() {
		return zRotationSlider.getValue() / 10;
	}

}
