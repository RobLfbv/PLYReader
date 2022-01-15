package view.mainviewcomponents;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import exceptions.NoPLYHeaderInFileException;
import exceptions.PropertyException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import model.Model;
import model.plyreader.Header;
import view.utils.PlyFileComment;

/**
 * Objet qui represente un fichier ply dans le tableau de visualisation des
 * models
 *
 */
public class PLYFile {

	/**
	 * Le fichier en lui même
	 */
	private File file;
	/**
	 * nom du fichier
	 */
	private ObservableValue<String> fileName;

	/**
	 * nombre de points du fichier
	 */
	private ObjectProperty<Integer> nbPoints;
	/**
	 * nombre de faces du fichier
	 */
	private ObservableValue<Integer> nbFaces;
	/**
	 * vrai si le fichier ply contient des couleurs
	 */
	private ObservableValue<Boolean> colored;
	/**
	 * Object utilisee pour lire et ecrire des commentaires dans le fichier
	 */
	private PlyFileComment comment;

	/**
	 * cree une instance du'un fichier
	 * 
	 * @param file  - le fichier reel auquel il est lie
	 * @param model - modele qui permet l'initialisation
	 */
	public PLYFile(File file, Model model) {
		this.file = file;
		this.fileName = new SimpleStringProperty(this.getFileName());

		Header header = new Header();

		try {
			header.init(model, file, false);
		} catch (NoPLYHeaderInFileException | PropertyException e) {

		}

		this.nbPoints = new SimpleIntegerProperty(header.getNbPoints()).asObject();
		this.nbFaces = new SimpleIntegerProperty(header.getNbFaces()).asObject();
		this.colored = new SimpleBooleanProperty(header.hasColor());

		this.comment = new PlyFileComment(file);

	}

	/**
	 * recupere le nombre de points du model
	 * 
	 * @return le nombre de points
	 */

	public int getNbPoints() {
		return nbPoints.getValue();
	}

	/**
	 * Recupere la propriete observable du nombre de points afin de l'afficher dans
	 * le tableau
	 * 
	 * @return la propriete
	 */
	public ObservableValue<Integer> nbPointsProperty() {
		return nbPoints;
	}

	/**
	 * recupere le nombre de faces du model
	 * 
	 * @return le nombre de faces
	 */
	public int getNbFaces() {
		return nbFaces.getValue();
	}

	/**
	 * Recupere la propriete observable du nombre de faces afin de l'afficher dans
	 * le tableau
	 * 
	 * @return la propriete
	 */
	public ObservableValue<Integer> nbFacesProperty() {
		return nbFaces;
	}

	/**
	 * recupere le fait que le fichier ait de la couleur ou non
	 * 
	 * @return vrai si le fichier contient des couleurs
	 */
	public boolean isColored() {
		return colored.getValue();
	}

	/**
	 * recupere la propriete correspondant au fait que le fichier ait de la couleur
	 * afin de l'afficher dans le tableau
	 * 
	 * @return la propriete
	 */
	public ObservableValue<Boolean> coloredProperty() {
		return colored;
	}

	/**
	 * @return le nom du fichier
	 */
	public ObservableValue<String> plyNameProperty() {
		return comment.getNom();
	}

	/**
	 * @return la description du fichier
	 */
	public ObservableValue<String> plyDescriptionProperty() {
		return comment.getDescription();
	}

	/**
	 * @return la dateCreation du fichier
	 */
	public ObservableValue<String> plyDateCreationProperty() {
		return comment.getDateCreation();
	}

	/**
	 * @return l'auteur du fichier
	 */
	public ObservableValue<String> plyAuteurProperty() {
		return comment.getAuteur();
	}

	/**
	 * recupere le nom du fichier sans son extension
	 * 
	 * @return le nom du fichier
	 */
	public String getFileName() {
		String name = this.file.getName();
		return name.substring(0, name.length() - 4);
	}

	/**
	 * la propriete du nom du fichier afin de l'afficher dans le tableau
	 * 
	 * @return la propriete
	 */
	public ObservableValue<String> fileNameProperty() {
		return this.fileName;
	}

	/**
	 * recupere la taille du fichier en byte
	 * 
	 * @return la taille
	 */
	public int getFileSize() {
		Path path = Paths.get(file.getPath());
		try {
			return (int) Files.size(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * recupere la propriete liee la taille du fichier
	 * 
	 * @return la propriete
	 */
	public ObservableValue<Integer> fileSizeProperty() {
		return new SimpleIntegerProperty(this.getFileSize()).asObject();
	}

	/**
	 * recupere le fichier liee a l'objet
	 * 
	 * @return le fichier
	 */
	public File getFile() {
		return file;
	}

	/**
	 * permet de renommer le fichier
	 * 
	 * @param newValue son nouveau nom
	 * @throws IOException                - Exception provenant de la librairie IO
	 * @throws FileAlreadyExistsException - Exception si le fichier existe deja
	 */
	public void renameFile(String newValue) throws IOException, FileAlreadyExistsException {
		File newFile = new File(file.getParent(), newValue + ".ply");
		if (newFile.equals(file)) {
			throw new FileAlreadyExistsException(newFile.getPath());
		} else {
			Files.move(file.toPath(), newFile.toPath());
			this.file = newFile;
			((SimpleStringProperty) this.fileName).set(newValue);
		}

	}

	/**
	 * Cette methode permet d'editer le nom du fichier, en renseignant le fichier
	 * File, et le nouveau nom s
	 * 
	 * @param s nouveau nom
	 */
	public void setNom(String s) {
		comment.setNom(this.file, s);
	}

	/**
	 * Cette methode permet d'editer la description du fichier, en renseignant le
	 * fichier File, et la nouvelle description s
	 * 
	 * @param s nouvelle description
	 */
	public void setDescription(String s) {
		comment.setDescription(this.file, s);
	}

	/**
	 * Cette methode permet d'editer la date de creation du fichier, en renseignant
	 * le fichier File, et la nouvelle date de creation s
	 * 
	 * @param s nouvelle date
	 */
	public void setDateCreation(String s) {
		comment.setDateCreation(this.file, s);
	}

	/**
	 * Cette methode permet d'editer le nom du fichier, en renseignant le fichier
	 * File, et le nouvel auteur s
	 * 
	 * @param s nouvel auteur
	 */
	public void setAuteur(String s) {
		comment.setAuteur(this.file, s);
	}

}
