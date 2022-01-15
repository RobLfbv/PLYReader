package model.plyreader;

/**
 * Permet d'instancier un objet qui trouvera la localisation des differents
 * parametres d'une ligne Point, afin de savoir quelle colonne correspond a quoi
 */
public class IdxReader {
	/**
	 * numero de la colonne, commence a zero
	 */
	private int x, y, z, red, green, blue;

	/**
	 * Constructeur de IdxReader
	 * 
	 * @param header - header qui permet l'initialisation des parametres
	 */
	public IdxReader(Header header) {
		this.x = -1;
		this.y = -1;
		this.z = -1;
		this.red = -1;
		this.green = -1;
		this.blue = -1;
		instanciate(header);
	}

	/*
	 * trouve l'indice de chaque attribut et l'instancie
	 * 
	 * @param header - header qui permet l'initialisation des parametres
	 */
	private void instanciate(Header header) {
		for (int idx = 0; idx < header.getSizePropertiesPoint(); idx++) {
			String element = header.getPointProperty(idx, 2);
			switch (element) {
			case "x":
				x = idx;
				break;
			case "y":
				y = idx;
				break;
			case "z":
				z = idx;
				break;
			case "red":
				red = idx;
				break;
			case "green":
				green = idx;
				break;
			case "blue":
				blue = idx;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * @return the red
	 */
	public int getRed() {
		return red;
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return blue;
	}

}
