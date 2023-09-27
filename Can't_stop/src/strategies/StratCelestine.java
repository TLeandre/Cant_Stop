package strategies;

import cantstop.Jeu;
        
/**
 * reference de la strategie https://www.cs.umd.edu/~gasarch/REU/TheCantStopGame.pdf
 * 
 * La stratégie va choisir la colonne où nous avons le plus de chance de la finir lors d'un tour 
 * La stratégie s'arrête uniquement lorsque nous avons fini une ligne, ou lorsque l'on calcule la progression 
 * et que la progression réalisée lors du tour est supérieure à la progression espérée
 *
 * @author CELESTINE BRES
 */

public class StratCelestine implements Strategie {

    // tableau des probabilités de tomber sur une colonne lors d'un lancé
    private double[] probabilites = {0, 0, 0.13, 0.24,0.37,0.48,0.61,0.71,0.61,0.48,0.37,0.24,0.13}; 

    // calcul du progress fait lors du tour 
    private boolean Progress(Jeu j){
        double[] probi = {probabilites[j.getBonzes()[0][0]], probabilites[j.getBonzes()[1][0]], probabilites[j.getBonzes()[2][0]], probabilites[j.getBonzes()[0][0]] * probabilites[j.getBonzes()[1][0]], probabilites[j.getBonzes()[0][0]] * probabilites[j.getBonzes()[2][0]], probabilites[j.getBonzes()[1][0]] * probabilites[j.getBonzes()[2][0]], probabilites[j.getBonzes()[0][0]] * probabilites[j.getBonzes()[0][0]], probabilites[j.getBonzes()[1][0]] * probabilites[j.getBonzes()[1][0]], probabilites[j.getBonzes()[2][0]] * probabilites[j.getBonzes()[2][0]]};
        double[] wei = {1.0/probabilites[j.getBonzes()[0][0]], 1.0/probabilites[j.getBonzes()[1][0]], 1.0/probabilites[j.getBonzes()[2][0]]};
        double[] c = {j.getBonzes()[0][1] - j.avancementJoueurEnCours()[j.getBonzes()[0][0] - 2], j.getBonzes()[1][1] - j.avancementJoueurEnCours()[j.getBonzes()[1][0] - 2], j.getBonzes()[2][1] - j.avancementJoueurEnCours()[j.getBonzes()[2][0] - 2]};
        // calcul de la progression attendue
        double expected = probi[0]*(wei[0]*(c[0]+1) + wei[1]*c[1] + wei[2]*c[2]) + probi[1]*(wei[0]*c[0] + wei[1]*(c[1]+1) + wei[2]*c[2]) + probi[2]*(wei[0]*c[0] + wei[1]*c[1] + wei[2]*(c[2]+1));
        expected += probi[3]*(wei[0]*(c[0]+1) + wei[1]*(c[1]+1) + wei[2]*c[2]) + probi[4]*(wei[0]*(c[0]+1) + wei[1]*c[1] + wei[2]*(c[2]+1)) + probi[5]*(wei[0]*c[0] + wei[1]*(c[1]+1) + wei[2]*(c[2]+1));
        expected += probi[6]*(wei[0]*(c[0]+2) + wei[1]*c[1] + wei[2]*c[2]) + probi[7]*(wei[0]*c[0] + wei[1]*(c[1]+2) + wei[2]*c[2]) + probi[8]*(wei[0]*c[0] + wei[1]*c[1] + wei[2]*(c[2]+2));
        // calcul de la progression actuel
        double actual = wei[0]*c[0] + wei[1]*c[1] + wei[2]*c[2];
        // verification de la condition
        if(actual >= expected){
            return true;
        }else{
            return false;
        }
    } 

    public int choix(Jeu j) {
        double best = 0;
        int a = 0;
        double total = 0;
        for(int i =0; i<j.getNbChoix(); i++){
            // calcul de la probabilité de finir la ligne 
            total = Math.pow(probabilites[j.getLesChoix()[i][0]], (j.getMaximum()[j.getLesChoix()[i][0] - 2] - j.avancementJoueurEnCours()[j.getLesChoix()[i][0] - 2]));
            if((j.getLesChoix()[i][1] - 2) >= 2 ){
                total += Math.pow(probabilites[j.getLesChoix()[i][1]], (j.getMaximum()[j.getLesChoix()[i][1] - 2] - j.avancementJoueurEnCours()[j.getLesChoix()[i][1] - 2]));
            }
            //selectionne le meilleur choix
            if(total >= best){
                best = total;
                a = i;   
            }
        }
        return a;
    }

    public boolean stop(Jeu j) {
        
        for(int i = 0; i < j.getBloque().length; i++){
            if(j.getBloque()[i]){
                return true;
            }
        }
        if(j.getBonzesRestants() <= 0){
            if( Progress(j) ){
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return "CELESTINE BRES";
    }
}