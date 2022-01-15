package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.Vecteur;

/**
 * Classe de tests pour les vecteurs
 *
 */
class VecteurTest {
	/**
	 * Points utilisés pour les tests
	 */
	private Point p1, p2, p3, p4;

	@BeforeEach
	void init() {
		p1 = new Point(1, 1, 1);
		p2 = new Point(2, 3, 4);
		p3 = new Point(2, 1, 02);
		p4 = new Point(8, 8, 10);
	}

	@Test
	void testCreationVecteurAPartirDePoints() {

		Vecteur v1 = new Vecteur(p1, p2);
		assertEquals(new Vecteur(1, 2, 3), v1);
		Vecteur v2 = new Vecteur(p3, p4);
		assertEquals(new Vecteur(6, 7, 8), v2);
	}

	@Test
	void testCreationVecteurAPartirDeVecteurs() {
		Vecteur v1 = new Vecteur(p1, p2);
		Vecteur v2 = new Vecteur(p3, p4);
		Vecteur v3 = Vecteur.produitVectoriel(v1, v2);
		assertEquals(new Vecteur(-5, 10, -5), v3);
	}

	@Test
	void testProduitsVectorielle() {
		Vecteur v1 = new Vecteur(p1, p2);
		Vecteur v2 = new Vecteur(p3, p4);
		assertEquals(new Vecteur(-5, 10, -5), Vecteur.produitVectoriel(v1, v2));
	}

	@Test
	void testProduitsScalaire() {
		Vecteur v1 = new Vecteur(p1, p2);
		Vecteur v2 = new Vecteur(p3, p4);
		assertEquals(44, Vecteur.produitScalaire(v1, v2));
	}

	@Test
	void testGetNorme() {
		Vecteur v1 = new Vecteur(p1, p2);
		assertEquals(Math.sqrt(14), Vecteur.getNorme(v1));
	}

}
