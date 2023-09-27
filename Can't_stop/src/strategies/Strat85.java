package strategies;

import java.util.ArrayList;

import cantstop.Jeu;
        
/**
 * Votre StratÃ©gie (copie de la Strat0 pour l'instant)
 *
 * Expliquez votre stratÃ©gie en une 20aine de lignes maximum.
 *
 * RENDU: L'algorithme va choisir le dé plus proche de 7 afin d'avoir plus
 * d'opportunité d'etre jouer selon les combinaisons possible.
 * Le moment d'arret sera en fonction du nombre de coup donner,
 * avec 5 etant la limite.
 *
 * @author Alice Chambet
 */

public class Strat85 implements Strategie {

	int coupCounter = 0 ;
   /**
    * @param j le jeu
    * @return choisie le dé plus proche de 7
    */
   public int choix(Jeu j) {
	  ArrayList<int[]> values = removeImpossibleChoix(j.getLesChoix());
	  int[] choixOptions = new int[j.getNbChoix()];
	  for(int i=0 ; i< j.getNbChoix() ; i++ ) {
		  for(int k=0 ; k < 2 ; k++ ) {
			  if(values.get(i)[k] < 7) {
				  choixOptions[i] += 7 - values.get(i)[k];
			  }else if(values.get(i)[k] > 7) {
				  choixOptions[i] += values.get(i)[k] - 7;
			  }else {
				  choixOptions[i] -= 1;
			  }
		  }
	  }
	  //Choix par défaut, le premier choix legal
	  int choix = 0;
	  //Valeur par défaut qui va etre remplacer par le plus petit score
	  // petit score = meilleur choix
	  int value = 10;
	  for(int i=0 ; i< j.getNbChoix() ; i++ ) {
		  if(choixOptions[i] < value) {
			  choix = i ;
			  value = choixOptions[i];
		  }
	  }
       return choix;
   }

   /**
    * @param lesChoix : choix disponible pour le coup
    * @return enleve les possibilité impossible, et retourne sous forme de liste
    */
   private ArrayList<int[]> removeImpossibleChoix(int[][] lesChoix) {
	   ArrayList<int[]> values = new ArrayList<int[]>();
		for(int i = 0; i < lesChoix.length ; i++) {
			boolean addToList = true;
			if(lesChoix[i][0]==0 && lesChoix[i][1]==0) {
					addToList = false;
			}
			if(addToList) {
				values.add(lesChoix[i]);
			}
		}
	return values;
}

/**
    * @param j le jeu
    * @return retourne vrai ou faux selon le nombre de coup lancer
    */
   public boolean stop(Jeu j) {
	   if(coupCounter/5.0 < Math.random()) {
		   this.coupCounter ++;
		   return false;
	   }
	   this.coupCounter = 0 ;
       return true;
   }

   /**
    * @return vos noms
    */
   public String getName() {
       return "Alice Chambet";
   }
}