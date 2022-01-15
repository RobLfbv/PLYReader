package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.NoPLYHeaderInFileException;
import exceptions.PropertyException;
import model.plyreader.Header;

/**
 * Classe de test des headers ply
 */
class HeaderTest {
	/**
	 * Fichier 1
	 */
	private File file1;
	/**
	 * Header 1
	 */
	private Header h1;
	/**
	 * Model 1
	 */
	private Model m1;
	/**
	 * Fichier 2
	 */
	private File file2;
	/**
	 * header 2
	 */
	private Header h2;
	/**
	 * Model 2
	 */
	private Model m2;
	/**
	 * fichier 3
	 */
	private File file3;
	/**
	 * header 3
	 */
	private Header h3;
	/**
	 * Model 3
	 */
	private Model m3;

	/**
	 * Initialisation des variables
	 * 
	 * @throws PropertyException
	 * @throws NoPLYHeaderInFileException
	 */
	@BeforeEach
	public void init() throws NoPLYHeaderInFileException, PropertyException {
		file1 = new File("src/test/resources/TestSkull.ply");
		h1 = new Header();
		m1 = new Model();
		h1.init(m1, file1, true);
		file2 = new File("src/test/resources/TestAvion.ply");
		h2 = new Header();
		m2 = new Model();
		h2.init(m2, file2, true);
		file3 = new File("src/test/resources/bun_zipper.ply");
		h3 = new Header();
		m3 = new Model();
		h3.init(m3, file3, true);
	}

	/**
	 * Test getNbPoints
	 */
	@Test
	public void nbPointTest() {
		assertEquals(133009, h1.getNbPoints());
		assertEquals(1335, h2.getNbPoints());
		assertEquals(35947, h3.getNbPoints());
	}

	/**
	 * Test getNbFaces
	 */
	@Test
	public void nbFaceTest() {
		assertEquals(248999, h1.getNbFaces());
		assertEquals(2452, h2.getNbFaces());
		assertEquals(69451, h3.getNbFaces());
	}

	/**
	 * Test getFormats
	 */
	@Test
	public void formatTest() {
		assertEquals("ascii", h1.getFormat());
		assertEquals("ascii", h2.getFormat());
		assertEquals("ascii", h3.getFormat());
	}

}
