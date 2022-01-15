package utils;

/**
 * 
 * Classe utilitaire pour des calculs mathematiques
 *
 */
public final class MathUtils {

	/**
	 * Map un nombre d'un intervalle [itart;istop] a intervalle [ostart;ostop]
	 * 
	 * @param value  la valeur
	 * @param istart debut de l'intervalle de base
	 * @param istop  fin de l'intervalle de base
	 * @param ostart debut du nouvel intervalle
	 * @param ostop  debut du nouvel intervalle
	 * @return le nombre mappe sur le nouvel intervalle
	 */
	static public double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

	/**
	 * Contrain un chiffre entre deux borne definies
	 * 
	 * @param value  la valeur a contraindre
	 * @param istart debut de l'intervale
	 * @param istop  fin de l'intervale
	 * @return le chiffre contrain
	 */
	static public double constrain(double value, double istart, double istop) {
		return Math.max(istart, Math.min(value, istop));
	}
}
