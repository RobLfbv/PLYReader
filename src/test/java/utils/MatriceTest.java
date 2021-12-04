package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * Classe de test pour les matrices
 *
 */
class MatriceTest {

	/**
	 * matrice 1 pour les tests
	 */
	public Matrice m1;
	/**
	 * matrice 2 pour les tests
	 */
	public Matrice m2;
	/**
	 * matrice 3 pour les tests
	 */
	public Matrice m3;

	/**
	 * initialsation des matrices
	 */
	@BeforeEach
	public void init() {
		m1 = new Matrice(new double[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } });
		m2 = new Matrice(new double[][] { { 2, 1, 2 }, { 3, 1, 3 }, { 5, 8, 5 } });

		m3 = new Matrice(new double[][] { { 1, 2, 3 }, { 2, 3, 4 }, { 3, 4, 5 }, { 4, 5, 6 }, { 5, 6, 7 } });
	}

	/**
	 * teste la fonction get
	 */
	@Test
	public void testGet() {
		assertEquals(m1.get(1, 2), 6);
		assertEquals(m2.get(1, 2), 3);
		assertEquals(m1.get(0, 0), 1);
	}

	/**
	 * teste la fonction set
	 */
	@Test
	public void testSet() {
		assertEquals(m1.get(1, 2), 6);
		m1.set(1, 2, 42);
		assertEquals(m1.get(1, 2), 42);
	}

	/**
	 * teste la fonction getCols
	 */
	@Test
	public void testGetCols() {
		assertEquals(m1.getCols(), 3);
		assertEquals(m3.getCols(), 3);
	}

	/**
	 * teste la fonction getRows
	 */
	@Test
	public void testGetRows() {
		assertEquals(m1.getRows(), 3);
		assertEquals(m3.getRows(), 5);
	}

	/**
	 * teste la fonction copy
	 */
	@Test
	public void testCopy() {
		Matrice copyM1 = Matrice.copy(m1);

		assertTrue(m1.equals(copyM1));
		assertTrue(Matrice.sameSize(m1, copyM1));

		for (int y = 0; y < m1.getRows(); y++) {
			for (int x = 0; x < m1.getCols(); x++) {
				assertEquals(m1.get(y, x), copyM1.get(y, x));
			}
		}
	}

	/**
	 * teste la fonction multiply
	 */
	@Test
	void testMultiply() {
		assertEquals(new Matrice(new double[][] { { 23, 27, 23 }, { 53, 57, 53 }, { 83, 87, 83 } }),
				Matrice.multiply(m1, m2));
		assertEquals(new Matrice(new double[][] { { 20, 25, 30 }, { 28, 35, 42 }, { 72, 90, 108 } }),
				Matrice.multiply(m2, m1));

		assertEquals(null, Matrice.multiply(m1, m3));
		assertEquals(new Matrice(
				new double[][] { { 30, 36, 42 }, { 42, 51, 60 }, { 54, 66, 78 }, { 66, 81, 96 }, { 78, 96, 114 } }),
				Matrice.multiply(m3, m1));

		assertEquals(null, Matrice.multiply(m2, m3));
		assertEquals(new Matrice(
				new double[][] { { 23, 27, 23 }, { 33, 37, 33 }, { 43, 47, 43 }, { 53, 57, 53 }, { 63, 67, 63 } }),
				Matrice.multiply(m3, m2));

	}
}
