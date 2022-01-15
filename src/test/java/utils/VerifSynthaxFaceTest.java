package utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.NoPLYHeaderInFileException;
import exceptions.PLYSynthaxPointFaceException;
import exceptions.PropertyException;
import model.Model;
import model.plyreader.Header;
/**
 * Classe de test pour la verification de syntaxe de face
 */
class VerifSynthaxFaceTest {
	/**
	 * fichier skull
	 */
	private File file1;
	/**
	 * header avec  properties
	 */
	private Header h1;
	/**
	 * Model 1
	 */
	private Model m1;
	/**
	 * ligne fictive
	 */
	private int cptLecture = 0;
	/**
	 * ligne correcte
	 */
	private String line1;
	/**
	 * ligne correcte
	 */
	private String line2;
	/**
	 *pas assez de point
	 */
	private String line3;
	/**
	 * trop de point
	 */
	private String line4;
	/**
	 * lettres
	 */
	private String line5;
	/**
	 * wrong id
	 */
	private String line6;
	/**
	 * model vierge
	 */
	private Model model;
	/**
	 * Liste des lignes correct pour les tests 
	 */
	private List<String> listeLigneCorrect = new ArrayList<>();
	/**
	 * Liste des lignes fausse pour les tests 
	 */
	private List<String> listeLigneFausses = new ArrayList<>();
	/**
	 * initialisation
	 * @throws PropertyException 
	 * @throws NoPLYHeaderInFileException 
	 */
	@BeforeEach
	void init() throws NoPLYHeaderInFileException, PropertyException{
		line1 = "3 92919 92858 92918";
		listeLigneCorrect.add(line1);
		line2 = "3 122236 122231 122230";
		listeLigneCorrect.add(line2);
		line3 = "4 122236 122231 122230";
		listeLigneFausses.add(line3);
		line4 = "3 122236 122231 122230 43221";
		listeLigneFausses.add(line4);
		line5 = "3 122vd236 122231 122230";
		listeLigneFausses.add(line5);
		line6 = "3 12299236 122231 122230";
		listeLigneFausses.add(line6);
		file1 = new File("src/test/resources/TestSkull.ply");
		h1 = new Header();
		m1 = new Model();
		h1.init(m1, file1, true);
		model = new Model();
	}
	/**
	 * Test
	 */
	@Test
	void testFace() {
		for(String s : listeLigneCorrect) {
			try{
				VerifSynthaxFace.verifSynthaxFace(s, h1.getNbPoints(), cptLecture, model);
				assertTrue(true);
			}catch(PLYSynthaxPointFaceException e) {
				assertTrue(false);
			}
		}
		for(String s : listeLigneFausses) {
			try{
				VerifSynthaxFace.verifSynthaxFace(s, h1.getNbPoints(), cptLecture, model);
				assertTrue(false);
			}catch(PLYSynthaxPointFaceException e) {
				assertTrue(true);
			}
		}
	}

}
