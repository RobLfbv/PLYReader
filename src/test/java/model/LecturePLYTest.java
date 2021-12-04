package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Cette classe est une classe de test qui sert à vérifier le bon
 * fonctionnement de la lecture des fichiers PLY de test (créer spécialement
 * pour les tests)
 */
class LecturePLYTest {
	/**
	 * Test de la lecture d'un fichier
	 */
	@Test
	void testLectureFichier() {
		String path = "src/test/resources/Test.ply";
		File f = new File(path);
		Model model = new Model();
		model.loadFromFile(f, false);

		assertEquals(LoadingFilePLY.getIdxEndHeader(), 9);
		assertEquals(LoadingFilePLY.getNbPoint(), 4);
		assertEquals(LoadingFilePLY.getNbFace(), 1);
		assertFalse(LoadingFilePLY.hasColor());

		for (int i = 0; i < 4; i++) {
			assertTrue(model.getData().getPoints().get(i).equals(new Point(i, i, i)));
		}
	}

	/**
	 * Test de la lecture des couleurs dans un fichier ply
	 */
	@Test
	public void testLectureDeFichierCouleur() {
		Model model = new Model();

		String path = "src/test/resources/Test.ply";
		File f = new File(path);
		model.loadFromFile(f);
		assertFalse(LoadingFilePLY.hasColor());

		path = "src/test/resources/TestCouleurPoint.ply";
		f = new File(path);
		model.loadFromFile(f);
		assertTrue(LoadingFilePLY.hasColor());
		for (Point p : model.getData().getPoints().values()) {
			assertEquals(p.getColor(), new Color(42, 42, 42));
		}

		path = "src/test/resources/TestCouleurFace.ply";
		f = new File(path);
		model.loadFromFile(f);
		assertTrue(LoadingFilePLY.hasColor());
		for (Face face : model.getData().getFaces()) {
			assertEquals(face.getColor(), new Color(42, 42, 42));
		}
	}

	/**
	 * Test de la lecture d'un fichier avec des nombre �crits en �criture
	 * scientifique
	 */
	@Test
	public void testLectureDeFichierExposant() {
		Model model = new Model();

		String path = "src/test/resources/TestExposant.ply";
		File f = new File(path);
		model.loadFromFile(f, false);

		Point[] listPoints = new Point[] { new Point(-1.79482e-08, 0.0012, -0.0048),
				new Point(-0.0030854, 0.0012, -0.003677), new Point(-0.000833458, 0.0012, 0.00472709),
				new Point(-0.0048, 0.0012, 3.70407e-08) };

		for (int i = 0; i < 4; i++) {
			assertTrue(model.getData().getPoints().get(i).equals(listPoints[i]));
		}
	}

}
