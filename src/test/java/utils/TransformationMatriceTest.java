package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test de l'application des transformations sur une matrice
 */
class TransformationMatriceTest {
	/**
	 * liste des tranformations testées
	 */
	final TransformationType[] li = new TransformationType[] { TransformationType.XTRANSLATION,
			TransformationType.YTRANSLATION, TransformationType.ZTRANSLATION, TransformationType.TRANSLATION,
			TransformationType.XROTATION, TransformationType.YROTATION, TransformationType.ZROTATION,
			TransformationType.SCALE };
	/**
	 * matrice de transformation type
	 */
	TransformationMatrice test;

	/**
	 * Test de la matrice de translation en X
	 */
	@Test
	void testXTranslation() {
		test = new TransformationMatrice(li[0]);
		assertEquals(test.get(1),
				new Matrice(new double[][] { { 1, 0, 0, 1 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(10),
				new Matrice(new double[][] { { 1, 0, 0, 10 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de translation en Y
	 */
	@Test
	void testYTranslation() {
		test = new TransformationMatrice(li[1]);
		assertEquals(test.get(1),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 1 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(10),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 10 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de translation en Z
	 */
	@Test
	void testZTranslation() {
		test = new TransformationMatrice(li[2]);
		assertEquals(test.get(1),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 1 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(10),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 10 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de translation
	 */
	@Test
	void testTranslation() {
		test = new TransformationMatrice(li[3]);
		assertEquals(test.get(10, 10, 10),
				new Matrice(new double[][] { { 1, 0, 0, 10 }, { 0, 1, 0, 10 }, { 0, 0, 1, 10 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(-10, 5, 8),
				new Matrice(new double[][] { { 1, 0, 0, -10 }, { 0, 1, 0, 5 }, { 0, 0, 1, 8 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(1, 2, 3),
				new Matrice(new double[][] { { 1, 0, 0, 1 }, { 0, 1, 0, 2 }, { 0, 0, 1, 3 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de rotation en X
	 */
	@Test
	void testXRotation() {
		test = new TransformationMatrice(li[4]);
		assertEquals(test.get(1), new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, Math.cos(1), -Math.sin(1), 0 },
				{ 0, Math.sin(1), Math.cos(1), 0 }, { 0, 0, 0, 1 } }));

		assertEquals(test.get(10), new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, Math.cos(10), -Math.sin(10), 0 },
				{ 0, Math.sin(10), Math.cos(10), 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de rotation en Y
	 */
	@Test
	void testYRotation() {
		test = new TransformationMatrice(li[5]);
		assertEquals(test.get(1), new Matrice(new double[][] { { Math.cos(1), 0, -Math.sin(1), 0 }, { 0, 1, 0, 0 },
				{ Math.sin(1), 0, Math.cos(1), 0 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(10), new Matrice(new double[][] { { Math.cos(10), 0, -Math.sin(10), 0 }, { 0, 1, 0, 0 },
				{ Math.sin(10), 0, Math.cos(10), 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de rotation en Z
	 */
	@Test
	void testZRotation() {
		test = new TransformationMatrice(li[6]);
		assertEquals(test.get(1), new Matrice(new double[][] { { Math.cos(1), -Math.sin(1), 0, 0 },
				{ Math.sin(1), Math.cos(1), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));

		assertEquals(test.get(10), new Matrice(new double[][] { { Math.cos(10), -Math.sin(10), 0, 0 },
				{ Math.sin(10), Math.cos(10), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice d'homothétie
	 */
	@Test
	void testScale() {
		test = new TransformationMatrice(li[7]);
		assertEquals(test.get(10),
				new Matrice(new double[][] { { 10, 0, 0, 0 }, { 0, 10, 0, 0 }, { 0, 0, 10, 0 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(-10),
				new Matrice(new double[][] { { -10, 0, 0, 0 }, { 0, -10, 0, 0 }, { 0, 0, -10, 0 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(100),
				new Matrice(new double[][] { { 100, 0, 0, 0 }, { 0, 100, 0, 0 }, { 0, 0, 100, 0 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(42),
				new Matrice(new double[][] { { 42, 0, 0, 0 }, { 0, 42, 0, 0 }, { 0, 0, 42, 0 }, { 0, 0, 0, 1 } }));
		assertEquals(test.get(678),
				new Matrice(new double[][] { { 678, 0, 0, 0 }, { 0, 678, 0, 0 }, { 0, 0, 678, 0 }, { 0, 0, 0, 1 } }));
	}
}
