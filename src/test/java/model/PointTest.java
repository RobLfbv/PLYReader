package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests de la classe Point
 */
public class PointTest {
	/**
	 * Points utilisés pour les tests
	 */
	Point p1, p2, p3;

	@BeforeEach
	private void init() {
		p1 = new Point(5, 6, 7);
		p2 = new Point(10, 11, 12);
		p3 = new Point(15, 16, 17);
	}

	/**
	 * Test de get pour l'élément X
	 */
	@Test
	private void testGetX() {
		assertEquals(5, p1.get(0));
		assertEquals(10, p1.get(0));
		assertEquals(15, p1.get(0));
	}

	/**
	 * Test de get pour l'élément Y
	 */
	@Test
	private void testGetY() {
		assertEquals(6, p1.get(1));
		assertEquals(11, p1.get(1));
		assertEquals(16, p1.get(1));
	}

	/**
	 * Test de get pour l'élément Z
	 */
	@Test
	private void testGetZ() {
		assertEquals(7, p1.get(2));
		assertEquals(12, p1.get(2));
		assertEquals(17, p1.get(2));
	}
}
