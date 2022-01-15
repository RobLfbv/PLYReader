package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utils.transformations.Transformation;
import utils.transformations.TransformationScale;
import utils.transformations.TransformationXRotation;
import utils.transformations.TransformationXTranslation;
import utils.transformations.TransformationYRotation;
import utils.transformations.TransformationYTranslation;
import utils.transformations.TransformationZRotation;
import utils.transformations.TransformationZTranslation;

/**
 * Test de l'application des transformations sur une matrice
 */
class TransformationMatriceTest {
	/**
	 * matrice de transformation type
	 */
	private Transformation test;

	/**
	 * Test de la matrice de translation en X
	 */
	@Test
	void testXTranslation() {
		test = new TransformationXTranslation(1);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 1, 0, 0, 1 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationXTranslation(10);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 1, 0, 0, 10 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de translation en Y
	 */
	@Test
	void testYTranslation() {
		test = new TransformationYTranslation(1);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 1 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationYTranslation(10);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 10 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de translation en Z
	 */
	@Test
	void testZTranslation() {
		test = new TransformationZTranslation(1);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 1 }, { 0, 0, 0, 1 } }));
		test = new TransformationZTranslation(10);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 10 }, { 0, 0, 0, 1 } }));
	}


	/**
	 * Test de la matrice de rotation en X
	 */
	@Test
	void testXRotation() {
		test = new TransformationXRotation(1);
		assertEquals(test.get(), new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, Math.cos(1), -Math.sin(1), 0 },
				{ 0, Math.sin(1), Math.cos(1), 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationXRotation(10);
		assertEquals(test.get(), new Matrice(new double[][] { { 1, 0, 0, 0 }, { 0, Math.cos(10), -Math.sin(10), 0 },
				{ 0, Math.sin(10), Math.cos(10), 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de rotation en Y
	 */
	@Test
	void testYRotation() {
		test = new TransformationYRotation(1);
		assertEquals(test.get(), new Matrice(new double[][] { { Math.cos(1), 0, -Math.sin(1), 0 }, { 0, 1, 0, 0 },
				{ Math.sin(1), 0, Math.cos(1), 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationYRotation(10);
		assertEquals(test.get(), new Matrice(new double[][] { { Math.cos(10), 0, -Math.sin(10), 0 }, { 0, 1, 0, 0 },
				{ Math.sin(10), 0, Math.cos(10), 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice de rotation en Z
	 */
	@Test
	void testZRotation() {
		test = new TransformationZRotation(1);
		assertEquals(test.get(), new Matrice(new double[][] { { Math.cos(1), -Math.sin(1), 0, 0 },
				{ Math.sin(1), Math.cos(1), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationZRotation(10);
		assertEquals(test.get(), new Matrice(new double[][] { { Math.cos(10), -Math.sin(10), 0, 0 },
				{ Math.sin(10), Math.cos(10), 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
	}

	/**
	 * Test de la matrice d'homothétie
	 */
	@Test
	void testScale() {
		test = new TransformationScale(10);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 10, 0, 0, 0 }, { 0, 10, 0, 0 }, { 0, 0, 10, 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationScale(-10);
		assertEquals(test.get(),
				new Matrice(new double[][] { { -10, 0, 0, 0 }, { 0, -10, 0, 0 }, { 0, 0, -10, 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationScale(100);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 100, 0, 0, 0 }, { 0, 100, 0, 0 }, { 0, 0, 100, 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationScale(42);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 42, 0, 0, 0 }, { 0, 42, 0, 0 }, { 0, 0, 42, 0 }, { 0, 0, 0, 1 } }));
		test = new TransformationScale(678);
		assertEquals(test.get(),
				new Matrice(new double[][] { { 678, 0, 0, 0 }, { 0, 678, 0, 0 }, { 0, 0, 678, 0 }, { 0, 0, 0, 1 } }));
	}
}
