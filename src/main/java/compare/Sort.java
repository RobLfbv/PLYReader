package compare;

import java.util.List;

import model.Face;

/**
 * Cette classe permet de trier une liste de faces
 */
public class Sort {
	/**
	 * Permets de faire un tri par insertion en utilisant la dichitomie
	 * 
	 * @param c - un objet qui permet de determiner par rapport à quoi le tri va
	 *          etre fait (ex: par rapport à Z, à X ou à Y)
	 */
	static public void insertionSortFace(XYZFacesComp comparator, List<Face> faces) {
		for (int i = 1; i < faces.size(); i++) {
			shiftValueFace(comparator, i, faces);
		}
	}

	/**
	 * Intervertit les valeurs dans la liste avec une recherche dichitomique
	 * 
	 * @param comparator - un objet qui permet de determiner par rapport à quoi le
	 *                   tri va etre fait (ex: par rapport à Z, à X ou à Y)
	 * @param idx        - à quelle indice on se trouve
	 */
	static private void shiftValueFace(XYZFacesComp comparator, int idx, List<Face> faces) {
		Face val = faces.get(idx);
		int start = -1, end = idx, middle;
		while (end - start > 1) {
			middle = (start + end) / 2;
			if (0 > comparator.compare(val, faces.get(middle))) {
				end = middle;
			} else {
				start = middle;
			}
		}
		for (int i = idx - 1; i >= end; i--) {
			faces.set(i + 1, faces.get(i));
		}
		faces.set(end, val);
	}

	/**
	 * Intervertit les valeurs dans la liste
	 * 
	 * @param index1 - l'indice du premier element à intervertir
	 * @param index2 - l'indice du deuxième element à intervertir
	 */
	static void swap(int index1, int index2, List<Face> faces) {
		Face temporary = faces.get(index1);
		faces.set(index1, faces.get(index2));
		faces.set(index2, temporary);
	}

	/**
	 * Partitione avec un pivot la liste de face et la tri dans l'intervalle demande
	 * 
	 * @param comparator - l'objet qui permet de determiner par rapport à quoi on
	 *                   tri
	 * @param beginning  - l'indice de la premiere borne de l'intervalle sur lequel
	 *                   on va trier et creer des partitions
	 * @param end        - l'indice de la derniere borne de l'intervalle sur lequel
	 *                   on va trier et creer des partitions
	 * @return renvoie l'indice du pivot
	 */
	static int partition(XYZFacesComp comparator, int beginning, int end, List<Face> faces) {
		int counter = beginning;
		Face pivot = faces.get(beginning);
		for (int i = beginning + 1; i <= end; i++) {
			if (comparator.compare(faces.get(i), pivot) < 0) {
				counter++;
				swap(counter, i, faces);
			}
		}
		swap(beginning, counter, faces);
		return (counter);
	}

	/**
	 * Fait les plusieurs appelles necesaires pour faire le tri de toutes les
	 * partitions avec le premier qui détermine le pivot à chaque recursivite
	 * 
	 * @param comparator
	 * @param beginning
	 * @param end
	 */
	static public void quickSortFace(XYZFacesComp comparator, int beginning, int end, List<Face> faces) {
		if (beginning < end) {
			int p = partition(comparator, beginning, end, faces);
			quickSortFace(comparator, beginning, p - 1, faces);
			quickSortFace(comparator, p + 1, end, faces);
		}
	}

	/**
	 * Renvoie l'indice de l'enfant de gauche du parent en paramètre
	 * 
	 * @param parentIndex - l'indice du parent
	 * @return renvoie l'indice de l'element de gauche
	 */
	private static int leftChildIndex(int parentIndex) {
		return parentIndex * 2 + 1;
	}

	/**
	 * Renvoie l'indice de l'enfant de droite du parent en paramètre
	 * 
	 * @param parentIndex - l'indice du parent
	 * @return renvoie l'indice de l'element de droite
	 */
	private static int rightChildIndex(int parentIndex) {
		return parentIndex * 2 + 2;
	}

	/**
	 * Vérifie et replace une valeur max à l'index indique afin d'avoir un tas
	 * 
	 * @param comparator - l'objet qui permet de determiner par rapport à quel axe
	 *                   on tri
	 * @param list       - la liste de faces
	 * @param listLength - taille de la liste
	 * @param index      - l'index a verifier et modifier si necessaire
	 */
	private static void bubbleDown(XYZFacesComp comparator, List<Face> list, int listLength, int index) {
		while (index < listLength) {
			int leftIndex = leftChildIndex(index);
			int rightIndex = rightChildIndex(index);

			if (leftIndex >= listLength) {
				break;
			}

			int largerChildIndex = leftIndex;
			if (rightIndex < listLength && comparator.compare(list.get(leftIndex), list.get(rightIndex)) < 0) {
				largerChildIndex = rightIndex;
			}
			if (comparator.compare(list.get(index), list.get(largerChildIndex)) < 0) {
				Face tmp = list.get(largerChildIndex);
				list.set(largerChildIndex, list.get(index));
				list.set(index, tmp);

				index = largerChildIndex;
			} else {

				break;
			}
		}
	}

	/**
	 * Renvoie la valeur max de la liste après l'avoir retire
	 * 
	 * @param comparator - l'objet qui permet de determiner par rapport à quel axe
	 *                   on tri
	 * @param list       - la liste de faces
	 * @param listLength - taille de la liste
	 * @return renvoie la valeur max de la liste
	 */
	private static Face removeMax(XYZFacesComp comparator, List<Face> list, int listLength) {

		Face maxValue = list.get(0);
		list.set(0, list.get(listLength - 1));

		bubbleDown(comparator, list, listLength - 1, 0);

		return maxValue;
	}

	/**
	 * Transforme en tas la liste donné en paramètre en appelant la methode
	 * bubbleDown pour chaque indice de la liste
	 * 
	 * @param comparator - l'objet qui permet de determiner par rapport à quel axe
	 *                   on tri
	 * @param list       - la liste de faces
	 */
	private static void heapify(XYZFacesComp comparator, List<Face> list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			bubbleDown(comparator, list, list.size(), i);
		}
	}

	/**
	 * Transforme en tas la liste donné en paramètre puis la tri
	 * 
	 * @param comparator - l'objet qui permet de determiner par rapport à quel axe
	 *                   on tri
	 * @param list       - la liste de faces
	 */
	public static void heapSortFace(XYZFacesComp comparator, List<Face> list) {

		heapify(comparator, list);

		int listLength = list.size();

		while (listLength > 0) {
			Face largestValue = removeMax(comparator, list, listLength);
			listLength -= 1;
			list.set(listLength, largestValue);
		}
	}

}