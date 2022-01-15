package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.PLYFileInvalidException;
import view.utils.PlyFileComment;

/**
 * Classe de test pour la gestion des commentaires des fichiers ply
 */
class PlyCommentTest {
	/**
	 * objet qui gère les commentaires
	 */
	private PlyFileComment pc1;
	/**
	 * chemin vers les fichiers de test
	 */
	private String path;
	/**
	 * fichier de test
	 */
	private File f;

	@BeforeEach
	void init() throws PLYFileInvalidException, IOException {
		path = "src/test/resources/TestComment.ply";
		f = new File(path);
		pc1 = new PlyFileComment(f);
	}

	@Test
	void testGet() {
		assertEquals("tartenpion", pc1.getAuteur().getValue());
		assertEquals("11/12/2021", pc1.getDateCreation().getValue());
		assertEquals("fichier de test servant a tester les differentes classes", pc1.getDescription().getValue());
		assertEquals("Test.ply", pc1.getNom().getValue());
	}

	@Test
	void testSetter() throws PLYFileInvalidException, IOException {
		pc1.setAuteur(f, "noe");
		pc1.setDateCreation(f, "15/02/2002");
		pc1.setDescription(f, "ceci est la nouvelle description");
		pc1.setNom(f, "nouveau nom");
		assertEquals("nouveau nom", pc1.getNom().getValue());
		assertEquals("15/02/2002", pc1.getDateCreation().getValue());
		assertEquals("ceci est la nouvelle description", pc1.getDescription().getValue());
		assertEquals("noe", pc1.getAuteur().getValue());
		pc1.setAuteur(f, "tartenpion");
		pc1.setDateCreation(f, "11/12/2021");
		pc1.setDescription(f, "fichier de test servant a tester les differentes classes");
		pc1.setNom(f, "Test.ply");
	}
}
