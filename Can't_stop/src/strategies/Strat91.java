package strategies;

import java.util.ArrayList;
import cantstop.Jeu;
        
/**
 * Votre StratÃ©gie
 *
 * Expliquez votre stratÃ©gie en une 20aine de lignes maximum.
 *
 * RENDU: L'algorithme calcule la combinaison de dés pour avoir la probabilité. Ensuite,
 * calcule la distance restante à parcourir pour cree un score selon ces critères. Le meilleur score
 * sera choisie.
 * Pour le stop, l'algorithme verifie si une case est bloquer/ terminer par ces bonzes, dans ce cas il s'arrete.
 * Sinon il calcule la probabilité d'avoir une des bonzes placés et le nombre de coups deja jouer pour
 * calculer un score, ce score sera comparer par le "risk threshold" ou si le score est inferieur, il
 * decide d'arreter
 *
 * @author Alice Chambet
 */

public class Strat91 implements Strategie {

    private int coupCounter = 0;
    private float coeff_odds = 80.0f;
    private float coeff_counter = 60.0f;
    private float riskTreashold = 0.3f ; 
   /**
    * @param j le jeu
    * @return choisie le dé plus proche de 7
    */
   public int choix(Jeu j) {
	  ArrayList<int[]> values = removeImpossibleChoix(j.getLesChoix());
	  
	  float combinaisonOdds[] = new float[j.getNbChoix()];
	  int boardDistance[] = new int[2*j.getNbChoix()];
	  for(int i=0 ; i< j.getNbChoix() ; i++ ) {
		  for(int k=0 ; k < 2 ; k++ ) {
			  combinaisonOdds[i]+=this.calculateOdds(values.get(i)[k]);
			  //System.out.println(this.calculateOdds(values.get(i)[k]));
			  boardDistance[(i*2)+k] = calculateDistanceRestante(values.get(i)[k],j);
		  }
	  }
	  int [] combDistanceLeft = new int[combinaisonOdds.length];
	  float [] numOfDie = new float[combinaisonOdds.length];


	 
	  for(int i = 0 ; i < combinaisonOdds.length ; i++ ) {
		  combDistanceLeft[i] = boardDistance[2*(i+1)-2] + boardDistance[2*(i+1)-1];
		  int d1 = (boardDistance[2*(i+1)-2] != 0) ? 1 : 0;
		  int d2 = (boardDistance[2*(i+1)-1] != 0 ) ? 1 : 0;
		  numOfDie[i] = d1 + d2 ;
	  }
	  
	  int indexMaxScore = 0 ;
	  float maxScore = 0;
	  
	  for(int i = 0 ; i < combinaisonOdds.length ; i++) {
		  float bestOption =  (float)combinaisonOdds[i] * numOfDie[i] *this.coeff_odds / (float)combDistanceLeft[i];
		  //System.out.println("Combo : "+Arrays.toString(values.get(i))+" Odds : "+combinaisonOdds[i]+" Distance : "+combDistanceLeft[i]+" Score: "+bestOption);
		  if(bestOption > maxScore) {
			  indexMaxScore = i;
			  maxScore = bestOption;
		  }
	  }
	  
      return indexMaxScore;
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
	   if(j.getBonzesRestants() != 0) {
		   return false;
	   }
	   float combinaisonOdds = 0.0f ;


	   for( int i = 0 ; i < j.getBonzes().length ; i++ ) {
		 
		   // Une colonne est completer alors arreter
		   //System.out.println("Die Value : "+j.getBonzes()[i][0]+" : "+(j.getBonzes()[i][1] ));
		   if(j.getBloque()[j.getBonzes()[i][0] - 2] ) {
			   this.coupCounter = 0 ;
			   return true;
		   }
		   combinaisonOdds+=this.calculateOdds(j.getBonzes()[i][0]);
		   
	   }
	   
	   this.coupCounter ++;
	   float score = (float) (combinaisonOdds - Math.exp(this.coupCounter/this.coeff_counter));
	   //System.out.println("ComboOdds :"+combinaisonOdds+ " Score idea : "+score+" Counter :"+this.coupCounter+" Exp :"+Math.exp(this.coupCounter/this.coeff_counter));
	   if(score < this.riskTreashold) {
		   this.coupCounter = 0 ;
	       return true;
	   }
	   return false;
   }
   

   private  float calculateOdds(int number) {
	    // Total number of possible combinations with two dice
	    float total = 36.0f;
	    
	    // Calculate the number of possible combinations that add up to the target number
	    float numCombinations = 0;
	    for (int j = 1; j <= 6; j++) {
	      for (int k = 1; k <= 6; k++) {
	        if (j + k == number) {
	          numCombinations++;
	        }
	      }
	    }
	    
	    // Calculate and return the odds of rolling the target number
	    float odds = numCombinations / total;
	    return odds;
	  }
   
   private int calculateDistanceRestante(int value, Jeu jeu) {
	   int[][] bonzes = jeu.getBonzes();
	   int reducedDistance = 0;
	   for(int i = 0 ; i < bonzes.length ; i++) {
		   if(bonzes[i][0] == value ) {
			   reducedDistance = bonzes[i][1];
		   }
	   }
	   if(value!=0) {
			  return jeu.getMaximum()[value-2] - reducedDistance;
		  } 
	return 0;
		
   }

   /**
    * @return vos noms
    */
   public String getName() {
       return "Alice Chambet";
   }
}