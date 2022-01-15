package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.PLYFileInvalidException;
import view.utils.PlyFileComment;

/**
 * 
 * Classe de test des commentaires des fichiers ply
 *
 */
class PlyFileCommentVoidTest {
	/**
	 * objet qui gère les commentaires
	 */
	private PlyFileComment pc1;
	/**
	 * chemin vers les fichiers de test
	 */
	private String path;
	/**
	 * le fichier
	 */
	private File f;

	@BeforeEach
	void init() throws PLYFileInvalidException, IOException {
		path = "src/test/resources/TestCommentVoid.ply";
		f = new File(path);
		pc1 = new PlyFileComment(f);
	}

	@Test
	void getterTest() {
		assertEquals("", pc1.getValueAuteur());
		assertEquals("", pc1.getValueDateCreation());
		assertEquals("", pc1.getValueDescription());

		assertEquals("Test.ply", pc1.getNom().getValue());
	}

	@Test
	void settersTest() {
		pc1.setAuteur(f, "tartenpion");
		pc1.setDateCreation(f, "11/12/2021");
		pc1.setDescription(f, "fichier de test servant a tester les differentes classes");
		pc1.setNom(f, "Test.ply");
		assertEquals("tartenpion", pc1.getAuteur().getValue());
		assertEquals("11/12/2021", pc1.getDateCreation().getValue());
		assertEquals("fichier de test servant a tester les differentes classes", pc1.getDescription().getValue());
		assertEquals("Test.ply", pc1.getNom().getValue());
		pc1.setAuteur(f, "");
		pc1.setDateCreation(f, "");
		pc1.setDescription(f, "");
		pc1.setNom(f, "Test.ply");
	}

}
