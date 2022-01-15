package view.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 * Cette classe permet de lire et modifier les informations a propos du fichier
 * PLY
 */
public class PlyFileComment {
	/**
	 * Nom du fichier
	 */
	private ObservableValue<String> nom;
	/**
	 * Description du fichier
	 */
	private ObservableValue<String> description;
	/**
	 * Date de creation du fichier
	 */
	private ObservableValue<String> dateCreation;
	/**
	 * Auteur du fichier
	 */
	private ObservableValue<String> auteur;

	/**
	 * Constructeur avec parametres
	 * 
	 * @param nom          - nom du fichier
	 * @param description  - description du fichier
	 * @param dateCreation - la date de création
	 * @param auteur       - l'auteur du fichier
	 */
	public PlyFileComment(String nom, String description, String dateCreation, String auteur) {
		this.nom = new SimpleStringProperty(nom);
		this.description = new SimpleStringProperty(description);
		this.dateCreation = new SimpleStringProperty(dateCreation);
		this.auteur = new SimpleStringProperty(auteur);
	}

	/**
	 * Constructeur completant les attributs a partir du fichier
	 * 
	 * @param file - le fichier
	 */
	public PlyFileComment(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while (line != null && !line.contains("end_header")) {
				String lineLower = line.toLowerCase();
				if (lineLower.contains("comment")) {
					if (auteur == null || (auteur != null && auteur.getValue().isEmpty()))
						updateFromLine(line, PlyCommentEnum.AUTEUR);
					if (description == null || (description != null && description.getValue().isEmpty()))
						updateFromLine(line, PlyCommentEnum.DESCRIPTION);
					if (dateCreation == null || (dateCreation != null && dateCreation.getValue().isEmpty()))
						updateFromLine(line, PlyCommentEnum.DATECREATION);
					if (nom == null || (nom != null && nom.getValue().isEmpty()))
						updateFromLine(line, PlyCommentEnum.NOM);
				}
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {

		}
	}

	/**
	 * Ne pas utiliser, permet de modifier les informations du fichier de maniere
	 * generique, utilisez plutot les methodes specifiques
	 * 
	 * @param choix   - determine c'est quelle élément à modifier
	 * @param file    - le fichier à modifier
	 * @param s       - le contenue à insérer
	 * @param estNull - si la string est null
	 */
	private void editPLY(PlyCommentEnum choix, File file, String s, boolean estNull) {
		try {
			ArrayList<String> lines = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			boolean dejaEcrit = false;
			String line = br.readLine();
			int cpt = 1;
			while (line != null) {
				String lineLower = line.toLowerCase();
				if (dejaEcrit == false && lineLower.contains("comment") && !estNull) {
					if (lineLower.contains(choix.getKeyWord())) {
						lines.add("comment " + choix.getKeyWord() + " " + s);
					} else {
						lines.add(line);
					}
				} else if (cpt >= 2 && estNull && !dejaEcrit) {
					lines.add(line);
					lines.add("comment " + choix.getKeyWord() + " " + s);
					dejaEcrit = true;
				} else {
					lines.add(line);
				}
				line = br.readLine();
				cpt++;
			}
			br.close();

			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			for (String l : lines)
				out.write(l + "\n");
			out.flush();
			out.close();
		} catch (IOException e) {

		}
	}

	/**
	 * Cette methode permet d'editer le nom du fichier, en renseignant le fichier
	 * File, et le nouveau nom s
	 * 
	 * @param file - le fichier
	 * @param s    - le nom
	 */
	public void setNom(File file, String s) {
		this.editPLY(PlyCommentEnum.NOM, file, s, this.nom == null);
		this.nom = new SimpleStringProperty(s);
	}

	/**
	 * Cette methode permet d'editer la description du fichier, en renseignant le
	 * fichier File, et la nouvelle description s
	 * 
	 * @param file - le fichier
	 * @param s    - la description
	 */
	public void setDescription(File file, String s) {
		this.editPLY(PlyCommentEnum.DESCRIPTION, file, s, this.description == null);
		this.description = new SimpleStringProperty(s);
	}

	/**
	 * Cette methode permet d'editer la date de creation du fichier, en renseignant
	 * le fichier File, et la nouvelle date de creation s
	 * 
	 * @param file - le fichier
	 * @param s    - la date de creation
	 */
	public void setDateCreation(File file, String s) {
		this.editPLY(PlyCommentEnum.DATECREATION, file, s, this.dateCreation == null);
		this.dateCreation = new SimpleStringProperty(s);
	}

	/**
	 * Cette methode permet d'editer le nom du fichier, en renseignant le fichier
	 * File, et le nouvel auteur s
	 * 
	 * @param file - le fichier
	 * @param s    - l'auteur
	 */
	public void setAuteur(File file, String s) {
		this.editPLY(PlyCommentEnum.AUTEUR, file, s, this.auteur == null);
		this.auteur = new SimpleStringProperty(s);
	}

	/**
	 * Met a jour les commentaires
	 * 
	 * @param line - la ligne
	 * @param pce  - le type de commentaire
	 */
	public void updateFromLine(String line, PlyCommentEnum pce) {
		if (line.contains(pce.getKeyWord())) {
			if (line.contains(PlyCommentEnum.AUTEUR.getKeyWord())) {
				if (this.auteur == null)
					this.auteur = new SimpleStringProperty("");
				((SimpleStringProperty) this.auteur)
						.set(line.substring("comment ".length() + pce.getKeyWord().length() + 1));
			} else if (line.contains(PlyCommentEnum.DESCRIPTION.getKeyWord())) {
				if (this.description == null)
					this.description = new SimpleStringProperty("");
				((SimpleStringProperty) this.description)
						.set(line.substring("comment ".length() + pce.getKeyWord().length() + 1));
			} else if (line.contains(PlyCommentEnum.NOM.getKeyWord())) {
				if (this.nom == null)
					this.nom = new SimpleStringProperty("");
				((SimpleStringProperty) this.nom)
						.set(line.substring("comment ".length() + pce.getKeyWord().length() + 1));
			} else if (line.contains(PlyCommentEnum.DATECREATION.getKeyWord())) {
				if (this.dateCreation == null)
					this.dateCreation = new SimpleStringProperty("");
				((SimpleStringProperty) this.dateCreation)
						.set(line.substring("comment ".length() + pce.getKeyWord().length() + 1));
			}
		}

	}

	/**
	 * @return le nom
	 */
	public ObservableValue<String> getNom() {
		return nom;
	}

	/**
	 * @return la valeur du nom
	 */
	public String getValueNom() {
		return nom.getValue();
	}

	/**
	 * @return la description
	 */
	public ObservableValue<String> getDescription() {
		return description;
	}

	/**
	 * @return la valeur de la description
	 */
	public String getValueDescription() {
		return description.getValue();
	}

	/**
	 * @return la dateCreation
	 */
	public ObservableValue<String> getDateCreation() {
		return dateCreation;
	}

	/**
	 * @return la valeur de la date de creation
	 */
	public String getValueDateCreation() {
		return dateCreation.getValue();
	}

	/**
	 * @return l'auteur
	 */
	public ObservableValue<String> getAuteur() {
		return auteur;
	}

	/**
	 * @return la valeur de l'auteur
	 */
	public String getValueAuteur() {
		return auteur.getValue();
	}

}
