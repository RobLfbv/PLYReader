package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.NoPLYHeaderInFileException;
import exceptions.PropertyException;
import model.Model;
import model.plyreader.Header;
import model.plyreader.IdxReader;
/**
 * Classe de test pour la lecture des index dans les fichiers ply
 */
class IdxReaderTest {
	/**
	 * Fichier 1
	 */
	private File file1;
	/**
	 * Header 1
	 */
	private Header h1;
	/**
	 * IdxReader 1
	 */
	private IdxReader i1;
	/**
	 * Model 1
	 */
	private Model m1;
	/**
	 * Fichier 2
	 */
	private File file2;
	/**
	 * Header 2
	 */
	private Header h2;
	/**
	 * IdxReder 2
	 */
	private IdxReader i2;
	/**
	 * Model 2
	 */
	private Model m2;
	/**
	 * Fichier 3
	 */
	private File file3;
	/**
	 * Header 3
	 */
	private Header h3;
	/** 
	 * IdxReader 3
	 */
	private IdxReader i3;
	/**
	 * Model 3
	 */
	private Model m3;
	
	/**
	 * Initialisation
	 * @throws PropertyException 
	 * @throws NoPLYHeaderInFileException 
	 */
	@BeforeEach
	public void init() throws NoPLYHeaderInFileException, PropertyException {
		file1 = new File("src/test/resources/TestSkull.ply");
		h1 = new Header();
		m1 = new Model();
		h1.init(m1, file1, true);
		i1 = new IdxReader(h1);
		file2 = new File("src/test/resources/TestAvion.ply");
		h2 = new Header();
		m2 = new Model();
		h2.init(m2, file2, true);
		i2 = new IdxReader(h2);
		file3 = new File("src/test/resources/bun_zipper.ply");
		h3 = new Header();
		m3 = new Model();
		h3.init(m3, file3, true);
		i3 = new IdxReader(h3);
	}
	
	/**
	 * Test getX
	 */
	@Test
	public void xTest() {
		assertEquals(0, i1.getX());
		assertEquals(0, i2.getX());
		assertEquals(0, i3.getX());
	}
	
	/**
	 * Test getY
	 */
	@Test
	public void yTest() {
		assertEquals(1, i1.getY());
		assertEquals(1, i2.getY());
		assertEquals(1, i3.getY());
	}
	
	/**
	 * test getZ
	 */
	@Test
	public void zTest() {
		assertEquals(2, i1.getZ());
		assertEquals(2, i2.getZ());
		assertEquals(2, i3.getZ());
	}
	
	/**
	 * test getRed
	 */
	@Test
	public void redTest() {
		assertEquals(3, i1.getRed());
		assertEquals(-1, i2.getRed());
		assertEquals(-1, i3.getRed());
	}
	
	/**
	 * test getGreen
	 */
	@Test
	public void greenTest() {
		assertEquals(4, i1.getGreen());
		assertEquals(-1, i2.getGreen());
		assertEquals(-1, i3.getGreen());
	}
	
	/**
	 * test getBlue
	 */
	@Test
	public void blueTest() {
		assertEquals(5, i1.getBlue());
		assertEquals(-1, i2.getBlue());
		assertEquals(-1, i3.getBlue());
	}

}
