package compare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Face;
import model.Model;
import model.PLYData;
import model.Point;

/**
 * Test de la classe Sort
 */
public class SortTest {
	/**
	 * ply - le data utilisé pour faire les tests
	 */
	PLYData ply;
	/**
	 * Les points utilisés pour faire les faces
	 */
	Point p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
	/**
	 * Liste des id des points pour chaque face
	 */
	int[] k1, k2, k3, k4;
	/**
	 * m - Le model utilisé pour faire les tests
	 */
	Model m;
	/**
	 * Les faces utilisés pour faire les tests
	 */
	Face f1, f2, f3, f4;
	/**
	 * expected - L'odre de la liste attendue après le tri
	 */
	List<Face> expected;

	@BeforeEach
	void init() {
		ply = new PLYData();
		p1 = new Point(1, 3, 3);
		p2 = new Point(2, 3, 3);
		p3 = new Point(3, 3, 3);
		k1 = new int[3];
		k1[0] = 1;
		k1[1] = 2;
		k1[2] = 3;
		p4 = new Point(1, 3, 1);
		p5 = new Point(2, 3, 1);
		p6 = new Point(3, 3, 1);
		k2 = new int[3];
		k2[0] = 4;
		k2[1] = 5;
		k2[2] = 6;

		p7 = new Point(1, 3, -1);
		p8 = new Point(2, 3, 3);
		p9 = new Point(3, 3, -2);
		k3 = new int[3];
		k3[0] = 7;
		k3[1] = 8;
		k3[2] = 9;

		p10 = new Point(1, 3, -80);
		p11 = new Point(2, 3, 80);
		p12 = new Point(3, 3, 12);
		k4 = new int[3];
		k4[0] = 10;
		k4[1] = 11;
		k4[2] = 12;

		ply.putPoint(1, p1);
		ply.putPoint(2, p2);
		ply.putPoint(3, p3);
		ply.putPoint(4, p4);
		ply.putPoint(5, p5);
		ply.putPoint(6, p6);
		ply.putPoint(7, p7);
		ply.putPoint(8, p8);
		ply.putPoint(9, p9);
		ply.putPoint(10, p10);
		ply.putPoint(11, p11);
		ply.putPoint(12, p12);

		m = new Model();
		f1 = new Face(k1);
		f2 = new Face(k2);
		f3 = new Face(k3);
		f4 = new Face(k4);
		ply.addFace(f1);
		ply.addFace(f2);
		ply.addFace(f3);
		ply.addFace(f4);

		m.setData(ply);

		expected = new ArrayList<Face>();
		expected.add(f4);
		expected.add(f3);
		expected.add(f2);
		expected.add(f1);

		expected.get(3).getMin(m.getData(), 2);
	}

	/**
	 * Test du tri dichotomique
	 */
	@Test
	void testDichotomicSortFaces() {

		Sort.insertionSortFace(new XYZFacesComp(m.getData(), 2), m.getData().getFaces());
		assertEquals(expected.get(0).getMin(m.getData(), 2), m.getData().getFaces().get(0).getMin(m.getData(), 2));
		assertEquals(expected.get(1).getMin(m.getData(), 2), m.getData().getFaces().get(1).getMin(m.getData(), 2));
		assertEquals(expected.get(2).getMin(m.getData(), 2), m.getData().getFaces().get(2).getMin(m.getData(), 2));
		assertEquals(expected.get(3).getMin(m.getData(), 2), m.getData().getFaces().get(3).getMin(m.getData(), 2));
	}

	/**
	 * Test du tri rapide
	 */
	@Test
	void testQuickSortFaces() {

		PLYData.quickSortFace(new XYZFacesComp(m.getData(), 2), 0, m.getData().getFaces().size() - 1,
				m.getData().getFaces());
		assertEquals(expected.get(0).getMin(m.getData(), 2), m.getData().getFaces().get(0).getMin(m.getData(), 2));
		assertEquals(expected.get(1).getMin(m.getData(), 2), m.getData().getFaces().get(1).getMin(m.getData(), 2));
		assertEquals(expected.get(2).getMin(m.getData(), 2), m.getData().getFaces().get(2).getMin(m.getData(), 2));
		assertEquals(expected.get(3).getMin(m.getData(), 2), m.getData().getFaces().get(3).getMin(m.getData(), 2));
	}

	/**
	 * Test du tri par tas
	 */
	@Test
	void testHeapSortFaces() {

		PLYData.heapSortFace(new XYZFacesComp(m.getData(), 2), m.getData().getFaces());
		assertEquals(expected.get(0).getMin(m.getData(), 2), m.getData().getFaces().get(0).getMin(m.getData(), 2));
		assertEquals(expected.get(1).getMin(m.getData(), 2), m.getData().getFaces().get(1).getMin(m.getData(), 2));
		assertEquals(expected.get(2).getMin(m.getData(), 2), m.getData().getFaces().get(2).getMin(m.getData(), 2));
		assertEquals(expected.get(3).getMin(m.getData(), 2), m.getData().getFaces().get(3).getMin(m.getData(), 2));
	}
}
