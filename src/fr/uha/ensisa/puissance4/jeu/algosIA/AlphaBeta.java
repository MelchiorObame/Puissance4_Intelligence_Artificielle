package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;

/**
	Classe impl√©mentant l'algorithme Alpha-Beta
*/
public class AlphaBeta extends Algorithm {

	public AlphaBeta(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}
	
	

	@Override
	public int choisirCoup() {
		/*
		 * cette condition if permet de jouer en situation centrale
		 */
		if(this.tourDepart==1) {
			return (Constantes.NB_COLONNES-1)/2;
		}
		else{
			int colonneAjouer=0;
			double maxValueGrille=Constantes.SCORE_MAX_NON_DEFINI;
			double valeur;
			for (int col = 0; col < Constantes.NB_COLONNES; col++) {
				if (this.grille.isCoupPossible(col)) {
					Grille grilleCopie = grille.clone();
					grilleCopie.ajouterCoup(col, symboleMax);
					valeur=minValue(grilleCopie, tourDepart,Constantes.SCORE_MAX_NON_DEFINI,Constantes.SCORE_MIN_NON_DEFINI);
					if (valeur > maxValueGrille) {
						colonneAjouer=col;
						maxValueGrille = valeur;
					}
				}
			}	
			return colonneAjouer;
		}
		
	}
	
	
	
	
	/**
	 * renvoie la valeur maximale par l'algorithme AlphaBeta
	 * @param grilleDepart
	 * @param tour
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public double maxValue(Grille grilleDepart,int tour,double alpha,double beta) {
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
				grilleCopie.ajouterCoup(col,symboleMax );
				utilityValue=Math.max(utilityValue, minValue(grilleCopie,tour+1,alpha, beta));
				if (utilityValue>=beta) {
					return utilityValue;
				}
				alpha=Math.max(alpha, utilityValue);
			}	
		}
		return utilityValue;
	}
	

	
	/**
	 * renvoie la valeur maximale par l'algorithme AlphaBeta
	 * @param grilleDepart
	 * @param tour
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public double minValue(Grille grilleDepart,int tour,double alpha,double beta) {
		int etatActuel = grilleDepart.getEtatPartie(symboleMax, tour);
		//V√©rification que la partie n'est pas terminÈe ou si on depasse la profondeur
		if (etatActuel != Constantes.PARTIE_EN_COURS || (tour-tourDepart>=levelIA) ) {
			return grilleDepart.evaluer(symboleMax)-grilleDepart.evaluer(symboleMin);
		}
		double utilityValue=Constantes.SCORE_MIN_NON_DEFINI;
		//prise de la plus grande valeur de la grille.
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			if (grilleDepart.isCoupPossible(col)) {
				// copie de la grille de depart pour faire des test pour trouver le max
				Grille grilleCopie = grilleDepart.clone();
				grilleCopie.ajouterCoup(col,symboleMin);
				utilityValue=Math.min(utilityValue, maxValue(grilleCopie,tour+1,alpha, beta));
				if (utilityValue <= alpha) {
					return utilityValue;
				}
				beta=Math.min(beta, utilityValue);
			}	
		}
		return utilityValue;
	}
	
	
}
