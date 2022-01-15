package utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.PropertyException;
/**
 * Classe de test pour la verification de syntaxe de point
 */
class VerifSynthaxPointTest {
	/**
	 * exemple de coordonnee correcte
	 */
	private String s1;
	/**
	 * exemple de coordonnee correcte
	 */
	private String s2;
	/**
	 * exemple de coordonnee correcte
	 */
	private String s3;
	/**
	 * exemple de coordonnee correcte
	 */
	private String s4;
	/**
	 * exemple de coordonnee correcte
	 */
	private String s5;

	/**
	 * exemple de coordonnee fausse
	 */
	private String s6;
	/**
	 * exemple de coordonnee fausse
	 */
	private String s7;

	/**
	 * exemple de couleur correcte
	 */
	private String c1;
	/**
	 * exemple de couleur correcte
	 */
	private String c2;
	/**
	 * exemple de couleur correcte
	 */
	private String c3;
	/**
	 * exemple de couleur fausse
	 */
	private String c4;
	/**
	 * exemple de couleur fausse
	 */
	private String c5;
	/**
	 * float
	 */
	private String fl;
	/**
	 * char
	 */
	private String ch;
	/**
	 * Initialisation
	 */
	@BeforeEach
	void init() {		
		s1 = "-0.000000";
		s2 = "-34.811508";
		s3 = "63.747543";
		s4 = "40";
		s5 = "-1.79482e-08";
		s6 = "abc";
		s7 = "50-e-12";
		
		c1 = "233";
		c2 = "1";
		c3 = "255";
		c4 = "-1";
		c5 = "256";
		
		fl = "float32";
		ch = "uchar";
	}
	/**
	 * test avec float
	 * @throws PropertyException 
	 */
	@Test
	void testNumFloat() throws PropertyException {
		assertTrue(VerifSynthaxPoint.verifNumber(s1, fl));
		assertTrue(VerifSynthaxPoint.verifNumber(s2, fl));
		assertTrue(VerifSynthaxPoint.verifNumber(s3, fl));
		assertTrue(VerifSynthaxPoint.verifNumber(s4, fl));
		assertTrue(VerifSynthaxPoint.verifNumber(s5, fl));
		assertFalse(VerifSynthaxPoint.verifNumber(s6, fl));
		assertFalse(VerifSynthaxPoint.verifNumber(s7, fl));
	}
	/**
	 * test avec couleur
	 * @throws PropertyException 
	 */
	@Test
	void testNumCouleur() throws PropertyException {
		assertTrue(VerifSynthaxPoint.verifNumber(c1, ch));
		assertTrue(VerifSynthaxPoint.verifNumber(c2, ch));
		assertTrue(VerifSynthaxPoint.verifNumber(c3, ch));
		assertFalse(VerifSynthaxPoint.verifNumber(c4, ch));
		assertFalse(VerifSynthaxPoint.verifNumber(c5, ch));
	}

}
