package strategies;

import cantstop.Jeu;
        
/**
 * 
 * La stratégie est de selectionné la colonne qui a le plus de chance d'être finis en 1 tour 
 *
 * @author SIMON CUNY
 */

public class StratSimon implements Strategie {

    // tab proba colonne
    private double[] probabilites = {0, 0, 0.13, 0.23,0.36,0.45,0.56,0.64,0.56,0.45,0.36,0.23,0.13}; 

    public int choix(Jeu j) {
        double meilleur = 0;
        int choix = 0;
        double possibilité = 0;

        for(int i =0; i<j.getNbChoix(); i++){
            // get les proba pour chaque colonne par rapport au tableau et la distance avant finir ligne 
            double proba1 = probabilites[j.getLesChoix()[i][0]];
            int distance1 = j.getMaximum()[j.getLesChoix()[i][0] - 2] - j.avancementJoueurEnCours()[j.getLesChoix()[i][0] - 2];
            

            // proba de finir la ligne
            possibilité = puiss(proba1, distance1);
            if((j.getLesChoix()[i][1] - 2) >= 2 ){

                double proba2 = probabilites[j.getLesChoix()[i][1]];
                int distance2 = j.getMaximum()[j.getLesChoix()[i][1] - 2] - j.avancementJoueurEnCours()[j.getLesChoix()[i][1] - 2];

                possibilité += puiss(proba2, distance2);
            }

            // proba la plus grande
            if(possibilité >= meilleur){
                meilleur = possibilité;
                choix = i;   
            }
        }
        return choix;
    }

    // calcul de puissance
    private double puiss(double a, int p){
        double sum = a;
        for(int i = 0; i<p; i++){
            sum = sum*a;
        }
        return sum;
    }

    public boolean stop(Jeu j) {
        boolean stop = false;
        // stop quand finis une colonne
        for(int i = 0; i < j.getBloque().length; i++){
            if(j.getBloque()[i]){
                stop = true;
            }
        }

        if(j.getBonzesRestants() <= 0 && stop){
            return true;
        }   
        return false;
    }

    public String getName() {
        return "SIMON CUNY";
    }
}