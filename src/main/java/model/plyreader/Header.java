package model.plyreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoPLYHeaderInFileException;
import exceptions.PropertyException;
import model.Model;

/**
 * Permet d'instancier les information contenues dans le header
 */
public class Header {
	/**
	 * Format de lecture du fichier
	 */
	private String format;
	/**
	 * Liste des proprietes des points
	 */
	private List<Property> propertiesPoint = new ArrayList<>();
	/**
	 * Liste des proprietes des faces
	 */
	private List<Property> propertiesFace = new ArrayList<>();
	/**
	 * Nombre de points
	 */
	private int nbPoints;
	/**
	 * Nombre de faces
	 */
	private int nbFaces;

	/**
	 * Constructeur
	 */
	public Header() {
		nbPoints = -1;
		nbFaces = -1;
	}

	/**
	 * instancie le header avec des valeurs
	 * 
	 * @param model        - le modele qui permet d'instancier les valeurs
	 * @param file         - le fichier qui permet d'instancier les valeurs
	 * @param ouvreFichier -
	 * @return reussite ou non
	 * @throws NoPLYHeaderInFileException - Excpetion qui permet d'avertir lorsque
	 *                                    le fichier PLY ne contient pas de "header"
	 * @throws PropertyException          - Excepetion qui permet d'avertir
	 *                                    lorsqu'il y a une erreur dans les
	 *                                    parametres (avant le "header") + donne
	 *                                    l'indice de la ligne d'erreur
	 */
	public boolean init(Model model, File file, boolean ouvreFichier)
			throws NoPLYHeaderInFileException, PropertyException {
		List<String> header = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			while (line != null && !(line.contains("end_header") && !line.contains("comment"))) {
				if (!line.isBlank() && !line.isEmpty()) {
					header.add(line);
				}
				line = br.readLine();
			}
			if (line != null) {
				process(header, ouvreFichier);
				return true;
			} else {
				throw new NoPLYHeaderInFileException();
			}
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Permet d'instancier les attributs de la classe
	 * 
	 * @param line
	 * @param br
	 * @throws PropertyException
	 */
	private void process(List<String> header, boolean ouvreFichier) throws PropertyException {
		int cpt = 0;
		for (String line : header) {
			String[] splittedLine = line.split(" ");
			if (splittedLine.length > 1 && splittedLine[1].contains("vertex")) {
				this.nbPoints = Integer.parseInt(splittedLine[2]);
				propertyReader(cpt, header, false, ouvreFichier);
			} else if (splittedLine.length > 1 && splittedLine[1].contains("face")) {
				this.nbFaces = Integer.parseInt(splittedLine[2]);
				propertyReader(cpt, header, true, ouvreFichier);
			} else if (splittedLine.length > 0 && splittedLine[0].contains("format")) {
				this.format = splittedLine[1];
			}
			cpt++;
		}
	}

	/**
	 * Permet de lire et d'instancier la partie property du header, pour les points
	 * et pour les faces (preciser avec le parametre isFace)
	 * 
	 * @param line
	 * @param br
	 * @param isFace
	 * @throws PropertyException
	 */
	private void propertyReader(int cpt, List<String> header, boolean isFace, boolean ouvreFichier)
			throws PropertyException {
		String current = "";
		while (!current.contains("format") && !current.contains("vertex") && !current.contains("face")) {
			cpt++;
			current = header.get(cpt);
			if (current.contains("property") && !current.contains("comment")) {
				Property property = new Property();
				String[] splittedLine = current.split(" ");
				for (String s : splittedLine) {
					property.addParam(s);
				}
				if (isFace) {
					this.propertiesFace.add(property);
				} else {
					if (splittedLine.length != 3 && ouvreFichier) {
						throw new PropertyException();
					}
					this.propertiesPoint.add(property);
				}
			}
		}
	}

	/**
	 * @return Format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @return Nombre de points
	 */
	public int getNbPoints() {
		return nbPoints;
	}

	/**
	 * @return Nombre de faces
	 */
	public int getNbFaces() {
		return nbFaces;
	}

	/**
	 * @param numProperty - l'indice de la propriete
	 * @param idxColonne  - l'indice de la colonne
	 * @return chaine correspondant a la propriete demandee
	 */
	public String getPointProperty(int numProperty, int idxColonne) {
		return this.propertiesPoint.get(numProperty).getParam(idxColonne);
	}

	/**
	 * 
	 * @return s'il y a des couleurs
	 */
	public boolean hasColor() {
		if (propertiesPoint.size() > 3 || propertiesFace.size() > 3)
			return true;
		return false;
	}

	/**
	 * @param numProperty - l'indice de la propriete
	 * @param idxColonne  - l'indice de la colonne
	 * @return chaine correspondant a la propriete demandee
	 */
	public String getFaceProperty(int numProperty, int idxColonne) {
		return this.propertiesFace.get(numProperty).getParam(idxColonne);
	}

	/**
	 * @return size of propertiesPoint
	 */
	public int getSizePropertiesPoint() {
		return propertiesPoint.size();
	}

	@Override
	public String toString() {
		return "Header [format=" + format + ", propertiesPoint=" + propertiesPoint + ", propertiesFace="
				+ propertiesFace + ", nbPoints=" + nbPoints + ", nbFaces=" + nbFaces + "]";
	}

}