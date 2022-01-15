package view.utils;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import model.Model;
import utils.transformations.Transformation;
import utils.transformations.TransformationFabric;
import utils.transformations.TransformationXRotation;
import utils.transformations.TransformationYRotation;
import utils.transformations.TransformationZRotation;
import view.mainviewcomponents.TimerSection;

/**
 * Clase qui gere la rotation automatique du model
 *
 */
public class Timer extends AnimationTimer {

	/**
	 * Le modele sur lequel on applique les rotations
	 */
	private Model model;

	/**
	 * La section sur l'interface auquel le timer est lie
	 */
	private TimerSection timerSection;

	/**
	 * Permet de savoir si le timer est en train de tourner ou pas
	 */
	private boolean running;

	/**
	 * Constructeur
	 * 
	 * @param model        - le model
	 * @param timerSection - La section sur l'interface auquel le timer est lie
	 */
	public Timer(Model model, TimerSection timerSection) {
		this.model = model;
		this.timerSection = timerSection;
		this.running = false;
	}

	/**
	 * Permet de savoir si le timer est en train de tourner ou pas
	 * 
	 * @return vrai si le timer est lance, faux sinon
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Demarre le timer
	 */
	@Override
	public void start() {
		super.start();
		running = true;
	}

	/**
	 * Arrête le timer
	 */
	@Override
	public void stop() {
		super.stop();
		running = false;
	}

	/**
	 * Methode lancee a chaque tour du timer
	 */
	@Override
	public void handle(long now) {
		List<Transformation> transformations = new ArrayList<>();

		if (timerSection.getXAmount() != 0)
			transformations.add(new TransformationXRotation(timerSection.getXAmount()));
		if (timerSection.getYAmount() != 0)
			transformations.add(new TransformationYRotation(timerSection.getYAmount()));
		if (timerSection.getZAmount() != 0)
			transformations.add(new TransformationZRotation(timerSection.getZAmount()));

		model.applyTransformation(TransformationFabric.getCentered(model.getRepere(), transformations));
	}
}
