package view;

import java.io.File;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.LoadingFilePLY;

/**
 * Repr�sente un item dans la liste des fichiers
 * 
 */
public class ListViewItem extends Canvas {
	/**
	 * Le fichier correspondant � cet item dans la liste
	 */
	private File file;

	/**
	 * Instantie un element de la liste � partir d'un fichier
	 * 
	 * @param file le fichier
	 */
	public ListViewItem(File file) {
		super(500, 50);

		this.file = file;
		GraphicsContext gc = this.getGraphicsContext2D();

		gc.setFill(Color.BLACK);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font("Roboto", 20));
		gc.fillText(this.getName(), 0, this.getHeight() * 1 / 4);
		gc.setFont(new Font("Roboto", 15));
		LoadingFilePLY.updatePLYParameters(file);
		gc.fillText((LoadingFilePLY.hasColor() ? "Colored," : "") + " Points : " + LoadingFilePLY.getNbPoint()
				+ ", Faces : " + LoadingFilePLY.getNbFace(), 0, this.getHeight() * 3 / 4);
	}

	/**
	 * r�cup�re le fichier associ�
	 * 
	 * @return le fichier
	 */
	public File getFile() {
		return file;
	}

	/**
	 * r�cup�re le nom du fichier associ�
	 * 
	 * @return le nom
	 */
	public String getName() {
		String name = this.getFile().getName();
		return name.substring(0, name.length() - 4);
	}
}
