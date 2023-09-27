package strategies;

import cantstop.Jeu;
import java.util.Random;

/**
 * Stratégie aléatoire
 * @author humeau
 */
public class Strat1 implements Strategie {
    Random rand = new Random();
    /**
     * @param j le jeu
     * @return un choix tiré au hasard de manière équiprobable dans [0,j.nbChoix[
     */
    public int choix(Jeu j){
        int res=rand.nextInt(j.getNbChoix());
        return res;
    }

    /**
     * @param j le jeu
     * @return au hasard true ou false (équiprobabilité)
     */
    public boolean stop(Jeu j){
        boolean res=rand.nextBoolean();
        return res;
    }

    /**
     * @return "Stratégie aléatoire"
     */
    public String getName(){
        return "Stratégie aléatoire";
    }
}
