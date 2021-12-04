package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test de la classe PLYData
 */
class PLYDataTest {

	/**
	 * Test de getMaxX
	 */
	@Test
	void testGetMaxX() {
		Model m = new Model();
		PLYData ply = new PLYData();
		m.setData(ply);
		for (int i = 0; i < 10; i++) {

			m.getData().putPoint(i, new Point(10 - i, 10 - i, 10 - i));
		}
		assertEquals(10, m.getData().getMax(0));
	}

	/**
	 * Test de getMaxY
	 */
	@Test
	void testGetMaxY() {
		Model m = new Model();
		PLYData ply = new PLYData();
		m.setData(ply);
		for (int i = 0; i < 10; i++) {

			m.getData().putPoint(i, new Point(10 - i, 10 - i, 10 - i));
		}
		assertEquals(10, m.getData().getMax(1));
	}

	/**
	 * Test de getMaxZ
	 */
	@Test
	void testGetMaxZ() {
		Model m = new Model();
		PLYData ply = new PLYData();
		m.setData(ply);
		for (int i = 0; i < 10; i++) {

			m.getData().putPoint(i, new Point(10 - i, 10 - i, 10 - i));
		}
		assertEquals(10, m.getData().getMax(2));
	}

	/**
	 * Test de getMinX
	 */
	@Test
	void testGetMinX() {
		Model m = new Model();
		PLYData ply = new PLYData();
		m.setData(ply);
		for (int i = 0; i < 10; i++) {

			m.getData().putPoint(i, new Point(10 - i, 10 - i, 10 - i));
		}
		assertEquals(1, m.getData().getMin(0));
	}

	/**
	 * Test de getMinY
	 */
	@Test
	void testGetMinY() {
		Model m = new Model();
		PLYData ply = new PLYData();
		m.setData(ply);
		for (int i = 0; i < 10; i++) {

			m.getData().putPoint(i, new Point(10 - i, 10 - i, 10 - i));
		}
		assertEquals(1, m.getData().getMin(1));
	}

	/**
	 * Test de getMinZ
	 */
	@Test
	void testGetMetZ() {
		Model m = new Model();
		PLYData ply = new PLYData();
		m.setData(ply);
		for (int i = 0; i < 10; i++) {

			m.getData().putPoint(i, new Point(10 - i, 10 - i, 10 - i));
		}
		assertEquals(1, m.getData().getMin(2));
	}

}
