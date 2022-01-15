package model.plyreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.CustomException;
import exceptions.NoPLYHeaderInFileException;
import exceptions.NotStartPlyException;
import exceptions.PlyFormatException;
import javafx.scene.paint.Color;
import model.Face;
import model.Model;
import model.PLYData;
import model.Point;
import utils.VerifSynthaxFace;
import utils.VerifSynthaxPoint;
import view.utils.ColorConstrain;

/**
 * Permet de charger un modele a partir d'un fichier ply
 */
public class PLYReader {
	/**
	 * header contenant des informations sur la maniere dont doit etre lu le fichier
	 */
	private Header header;
	/**
	 * Contient les id de colonne de x, y, z, red, green, et blue
	 */
	private IdxReader idxReader;

	/**
	 * Methode principale pour charger un modele a partir d'un fichier
	 * 
	 * @param model - le modele a changer
	 * @param file  - le fichier qui va changer le modele
	 * @return true si c'est correct, false si ça ne l'est pas
	 */
	public boolean readFilePLY(Model model, File file) {
		boolean correct = true;
		int cptLecture = 0;
		List<Face> listFace = new ArrayList<>();
		Map<Integer, Point> listPoint = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			if (!line.contains("ply")) {
				throw new NotStartPlyException();
			}
			header = new Header();
			if (!header.init(model, file, true)) {
				throw new NoPLYHeaderInFileException();
			}
			if (!header.getFormat().contains("ascii")) {
				throw new PlyFormatException();
			}
			idxReader = new IdxReader(header);
			cptLecture++;
			while (!(line.contains("end_header") && !line.contains("comment"))) {
				line = br.readLine();
				cptLecture++;
			}
			int cpt = 0;
			line = br.readLine();
			cptLecture++;
			while (line != null && cpt < header.getNbPoints() && correct) {
				if (!line.isBlank() && !line.isEmpty()) {
					if (!VerifSynthaxPoint.verifSynthaxPoint(line, header, cptLecture, model)) {
						correct = false;
					}
					listPoint.put(cpt, getPointFromPLY(line, idxReader));
					cpt++;
				}
				line = br.readLine();
				cptLecture++;
			}
			cpt = 0;
			while (line != null && cpt < header.getNbFaces() && correct) {
				if (!line.isBlank() && !line.isEmpty()) {
					if (!VerifSynthaxFace.verifSynthaxFace(line, header.getNbPoints(), cptLecture, model)) {
						correct = false;
					}
					listFace.add(getFaceFromPLY(line, listPoint));
					cpt++;
				}
				line = br.readLine();
				cptLecture++;
			}
			PLYData plyData = new PLYData(listPoint, listFace);

			if (model != null && correct) {
				model.setData(plyData);
				return true;
			} else {
				return false;
			}
		} catch (CustomException e) {
			model.notifyObservers(e);
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * @return Format
	 */
	public String getFormat() {
		return header.getFormat();
	}

	/**
	 * @return Nombre de points
	 */
	public int getNbPoints() {
		return header.getNbPoints();
	}

	/**
	 * @return Nombre de faces
	 */
	public int getNbFaces() {
		return header.getNbFaces();
	}

	/**
	 * @return modele color� ou non
	 */
	public boolean hasColor() {
		return idxReader.getRed() != -1 || idxReader.getGreen() != -1 || idxReader.getBlue() != -1;
	}

	/**
	 * @param line      - la string de la ligne
	 * @param listPoint - la map de points
	 * @return Face a partir d'une ligne
	 */
	private Face getFaceFromPLY(String line, Map<Integer, Point> listPoint) {
		String[] sline = line.split(" ");
		int[] idxPoints = new int[Integer.parseInt(sline[0])];
		for (int idx = 0; idx < Integer.parseInt(sline[0]); idx++) {
			idxPoints[idx] = Integer.parseInt(sline[idx + 1]);
		}
		Face f = new Face(idxPoints);
		return f;
	}

	/**
	 * @param line      - la string de la ligne
	 * @param idxReader - le lecteur d'indice
	 * @return Point a partir d'une ligne
	 */
	private Point getPointFromPLY(String line, IdxReader idxReader) {
		String[] sline = line.split(" ");
		double x = Double.parseDouble(sline[idxReader.getX()]);
		double y = Double.parseDouble(sline[idxReader.getY()]);
		double z = Double.parseDouble(sline[idxReader.getZ()]);
		Color color = null;
		if ((idxReader.getBlue() + idxReader.getGreen() + idxReader.getRed()) > 0) {
			color = ColorConstrain.color(Integer.parseInt(sline[idxReader.getRed()]) / 255.0,
					Integer.parseInt(sline[idxReader.getGreen()]) / 255.0,
					Integer.parseInt(sline[idxReader.getBlue()]) / 255.0);

		}
		Point point = new Point(x, y, z, color);
		return point;
	}

	@Override
	public String toString() {
		return "PLYReader [header=" + header + "]";
	}

}
