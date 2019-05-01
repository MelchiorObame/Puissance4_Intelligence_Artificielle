package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;


/**
	Classe impl√©mentant l'algorithme Minimax
*/
public class Minimax extends Algorithm {
	
	

	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
	}

	@Override
	public int choisirCoup() {
		/*
		 * cette condition if permet de jouer en situation centrale
		 */
		if( this.tourDepart==1) {
			return (Constantes.NB_COLONNES-1)/2;
		}
		
		int colonneAjouer=0;
		double maxValueGrille=Constantes.SCORE_MAX_NON_DEFINI;
		double valeur;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			if (this.grille.isCoupPossible(col)) {
				Grille grilleCopie = grille.clone();
				grilleCopie.ajouterCoup(col, symboleMax);
				valeur=minValue(grilleCopie, tourDepart);
				if (valeur > maxValueGrille) {
					colonneAjouer=col;
					maxValueGrille = valeur;
				}
			}
		}	
		return colonneAjouer;
	}
	
	/**
	 * renvoie l'evaluation maximale de la grille
	 * @param grilleDepart
	 * @param tour
	 * @return
	 */
	private double maxValue(Grille grilleDepart, int tour){
		int etatActuel = grilleDepart.getEtatPartie(symboleMin, tour);
		//V√©rification que la partie n'est pas terminÈe ou si on depasse la profondeur
		if (etatActuel != Constantes.PARTIE_EN_COURS || (tour-tourDepart>=levelIA) ) {
			return grilleDepart.evaluer(symboleMax)-grilleDepart.evaluer(symboleMin);
		}
		double utilityValue=Constantes.SCORE_MAX_NON_DEFINI;
		//prise de la plus grande valeur de la grille.
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			if (grilleDepart.isCoupPossible(col)) {
				// copie de la grille de depart pour faire des test pour trouver le max
				Grille grilleCopie = grilleDepart.clone();
				grilleCopie.ajouterCoup(col,symboleMax);
				utilityValue = Math.max(utilityValue, minValue(grilleCopie, tour+1));
			}	
		}
		return utilityValue;
	}
	
	/**
	 * renvoie l'evaluation minimale de la grille.
	 * @param grilleDepart
	 * @param tour
	 * @return
	 */
	private double minValue(Grille grilleDepart, int tour) {
		int etatActuel = grilleDepart.getEtatPartie(symboleMax, tour);
		//V√©rification que la partie n'est pas terminÈe ou si on depasse la profondeur
		if (etatActuel != Constantes.PARTIE_EN_COURS || (tour-tourDepart>=levelIA) ) {
			return grilleDepart.evaluer(symboleMax)-grilleDepart.evaluer(symboleMin);
		}
		double utilityValue=Constantes.SCORE_MIN_NON_DEFINI;
		//prise de la plus petite valeur de la grille.
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			if (grilleDepart.isCoupPossible(col)) {
				// copie de la grille de depart pour faire des test pour trouver le max
				Grille grilleCopie = grilleDepart.clone();
				grilleCopie.ajouterCoup(col,symboleMin);
				utilityValue = Math.min(utilityValue, maxValue(grilleCopie, tour+1));
			}	
		}
		return utilityValue;
	}
	
	
}
