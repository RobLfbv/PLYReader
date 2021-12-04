package utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.PLYSynthaxPointFaceException;

/**
 * Tests de vérification de la synthaxes des lignes points et faces lors de la
 * lecture
 */
class VerifSynthaxTest {
	/**
	 * nombre de point pour tester la cohérence des ID
	 */
	int nbPoint = 8;
	/**
	 * liste de points valides
	 */
	List<String> pointsValide;
	/**
	 * liste de points invalides
	 */
	List<String> pointsInvalide;
	/**
	 * liste de faces valides
	 */
	List<String> faceValide;
	/**
	 * liste de faces invalide
	 */
	List<String> faceInvalide;

	/**
	 * insancie deux listes respectivement 8 points et 8 faces
	 */
	@BeforeEach
	public void beforeEach() {
		pointsInvalide = new ArrayList<String>();
		pointsInvalide.add("25 46 ");
		pointsInvalide.add("100 200 300 4");
		pointsInvalide.add("b 2 3");
		pointsInvalide.add("-1 -100 4-6");
		pointsInvalide.add("25 46 42 7 201 ");
		pointsInvalide.add("100 200 300 5 6 -1");
		pointsInvalide.add("1 2 3 8 6 b");
		pointsInvalide.add("-1 -100 46 0 0 256");

		faceInvalide = new ArrayList<String>();
		faceInvalide.add("3 5 3");
		faceInvalide.add("4 5 16 27 4 5");
		faceInvalide.add("5 6 41 2");
		faceInvalide.add("3 -2 23 -1");
		faceInvalide.add("3 25 0 49 12 15");
		faceInvalide.add("4 5 16 27 4 25 -65 140");
		faceInvalide.add("5 6 41 28 47 2 256 255 255");
		faceInvalide.add("5 6 41 60 47 2 0 0 0  ");
	}

	/**
	 * Test de la méthode qui vérifie la synthaxe d'un point et ses exceptions
	 */
	@Test
	public void testSynthaxPointException() {
		for (String s : pointsInvalide) {
			Assertions.assertThrows(PLYSynthaxPointFaceException.class, () -> {
				VerifSynthaxPoint.verifSynthaxPoint(s, 0);
			}, "PLYSynthaxPointFaceException was expected");
		}
	}

	/**
	 * Test de la méthode qui vérifie la synthaxe d'une face et ses exceptions
	 */
	@Test
	public void testSynthaxFaceException() {
		for (String s : faceInvalide) {
			Assertions.assertThrows(PLYSynthaxPointFaceException.class, () -> {
				VerifSynthaxFace.verifSynthaxFace(s, nbPoint, 0);
			}, "PLYSynthaxPointFaceException was expected");
		}
	}
}
