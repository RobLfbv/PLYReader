package compare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Face;
import model.Model;
import model.PLYData;
import model.Point;

/**
 * Test des comparateurs
 *
 */
class XYZFacesCompTest {
	/**
	 * Les points utilisés pour faire les faces
	 */
	Point p1, p2, p3, p4, p5, p6;
	/**
	 * ply - le data utilisé pour faire les tests
	 */
	PLYData ply;
	/**
	 * Liste des id des points pour chaque face
	 */
	int[] stk1, stk2;
	/**
	 * Les faces utilisés pour faire les tests
	 */
	Face f1, f2;
	/**
	 * m - Le model utilisé pour faire les tests
	 */
	Model m;

	@BeforeEach
	void init() {
		p1 = new Point(2, 3, 3);
		p2 = new Point(2, 3, 3);
		p3 = new Point(2, 3, 3);

		p4 = new Point(1, 2, 1);
		p5 = new Point(1, 2, 1);
		p6 = new Point(1, 2, 1);
		ply = new PLYData();
		ply.putPoint(1, p1);
		ply.putPoint(2, p2);
		ply.putPoint(3, p3);
		ply.putPoint(4, p4);
		ply.putPoint(5, p5);
		ply.putPoint(6, p6);
		stk1 = new int[3];
		stk1[0] = 1;
		stk1[1] = 2;
		stk1[2] = 3;
		f1 = new Face(stk1);
		stk2 = new int[3];
		stk2[0] = 4;
		stk2[1] = 5;
		stk2[2] = 6;
		f2 = new Face(stk2);
		ply.addFace(f1);
		ply.addFace(f2);
		m = new Model();
		m.setData(ply);
	}

	/**
	 * Test de la comparaison de deux faces par rapport à Z
	 */
	@Test
	public void testZComparatorBetweenTwoFaces() {
		assertEquals(1, new XYZFacesComp(ply, 2).compare(f1, f2));
		assertEquals(-1, new XYZFacesComp(ply, 2).compare(f2, f1));
		assertEquals(0, new XYZFacesComp(ply, 2).compare(f2, f2));
		assertEquals(0, new XYZFacesComp(ply, 2).compare(f1, f1));
	}

	/**
	 * Test de la comparaison de deux faces par rapport à Y
	 */
	@Test
	public void testYComparatorBetweenTwoFaces() {
		assertEquals(1, new XYZFacesComp(ply, 1).compare(f1, f2));
		assertEquals(-1, new XYZFacesComp(ply, 1).compare(f2, f1));
		assertEquals(0, new XYZFacesComp(ply, 1).compare(f2, f2));
		assertEquals(0, new XYZFacesComp(ply, 1).compare(f1, f1));
	}

	/**
	 * Test de la comparaison de deux faces par rapport à X
	 */
	@Test
	public void testXComparatorBetweenTwoFaces() {
		assertEquals(1, new XYZFacesComp(ply, 0).compare(f1, f2));
		assertEquals(-1, new XYZFacesComp(ply, 0).compare(f2, f1));
		assertEquals(0, new XYZFacesComp(ply, 0).compare(f2, f2));
		assertEquals(0, new XYZFacesComp(ply, 0).compare(f1, f1));
	}

}
