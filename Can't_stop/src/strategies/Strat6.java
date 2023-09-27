// 
// Decompiled by Procyon v0.5.36
// 

package strategies;

import cantstop.Jeu;

public class Strat6 implements Strategie
{
    private double computeScore(final Jeu jeu, final int _choix) {
        int somme = 0;
        int nb = 1;
        if (jeu.getLesChoix()[_choix][1] > 0) {
            nb = 2;
        }
        for (int i = 0; i < nb; ++i) {
            somme += Math.abs(7 - jeu.getLesChoix()[_choix][i]);
        }
        return 100 - somme;
    }
    
    public int choix(final Jeu jeu) {
        double max = 0.0;
        int choix = 0;
        for (int i = 0; i < jeu.getNbChoix(); ++i) {
            final double score = this.computeScore(jeu, i);
            if (score > max) {
                max = score;
                choix = i;
            }
        }
        return choix;
    }
    
    public boolean stop(final Jeu jeu) {
        boolean res = false;
        for (int i = 0; i < 11; ++i) {
            if (jeu.getBloque()[i]) {
                res = true;
            }
        }
        return res;
    }
    
    public String getName() {
        return "Strat\u00e9gie basique";
    }
    
    public int getGroupe() {
        return 7;
    }
}