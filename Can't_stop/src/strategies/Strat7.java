package strategies;

import cantstop.Jeu;

public class Strat7 implements Strategie
{
    
    public int choix(final Jeu j) {
        double max = 0.0;
        int choix = 0;
        for (int i = 0; i < j.getNbChoix(); ++i) {
            final double score = this.computeScore(j, i);
            if (score > max) {
                max = score;
                choix = i;
            }
        }
        return choix;
    }
    
    private double computeScore(final Jeu j, final int _choix) {
        double somme = 0.0;
        final boolean duo = j.getLesChoix()[_choix][0] == j.getLesChoix()[_choix][1];
        final int moi0 = this.resteAparcourir(j, _choix, 0);
        final int lui0 = this.resteAdversaire(j, _choix, 0);
        final int moi2 = this.resteAparcourir(j, _choix, 1);
        final int lui2 = this.resteAdversaire(j, _choix, 1);
        if (j.getBonzesRestants() == 0) {
            double acc = 0.0;
            if (moi0 != 0) {
                acc += 150 - 10 * lui0;
            }
            if (moi2 != 0 && j.getLesChoix()[_choix][1] != 0) {
                acc += 150 - 10 * lui2;
            }
            somme += acc * 100.0;
            acc = 0.0;
            if (j.getLesChoix()[_choix][0] == 2 || j.getLesChoix()[_choix][0] == 12) {
                acc += 0.5;
            }
            if (j.getLesChoix()[_choix][1] == 2 || j.getLesChoix()[_choix][1] == 12) {
                acc += 0.5;
            }
            somme += acc * 100.0;
        }
        else {
            double acc = 0.0;
            if (j.getLesChoix()[_choix][0] == 2 || j.getLesChoix()[_choix][0] == 12) {
                acc += 0.5;
            }
            if (j.getLesChoix()[_choix][1] == 2 || j.getLesChoix()[_choix][1] == 12) {
                acc += 0.5;
            }
            somme += acc * 100.0;
            acc = 0.0;
            
            acc += 1.0 - Math.abs(7 - j.getLesChoix()[_choix][0]) * 0.2;
            if (j.getLesChoix()[_choix][1] != 0) {
                acc += 1.0 - Math.abs(7 - j.getLesChoix()[_choix][1]) * 0.2;
                acc /= 2.0;
            }
            somme += acc * 100.0;
            acc = 0.0;
            if (!this.newBonze(j, _choix, 0)) {
                acc += 0.5;
            }
            if (!this.newBonze(j, _choix, 1)) {
                acc += 0.5;
            }
            somme += acc * 10.0;
            acc = 0.0;
            if (j.getLesChoix()[_choix][0] == j.getLesChoix()[_choix][1]) {
                acc = 1.0;
            }
            somme += acc * 100.0;
        }
        return somme;
    }
    
    private int resteAdversaire(final Jeu j, final int _choix, final int nb) {
        int res = 20;
        if (j.getLesChoix()[_choix][nb] != 0 && !j.getBloque()[j.getLesChoix()[_choix][nb] - 2]) {
            res = j.getMaximum()[j.getLesChoix()[_choix][nb] - 2] - j.avancementAutreJoueur()[j.getLesChoix()[_choix][nb] - 2];
        }
        return res;
    }
    
    private int resteAparcourir(final Jeu j, final int _choix, final int nb) {
        int res = 15;
        if (j.getLesChoix()[_choix][nb] != 0 && !j.getBloque()[j.getLesChoix()[_choix][nb] - 2]) {
            if (j.getBonzes()[0][0] == j.getLesChoix()[_choix][nb]) {
                res = j.getMaximum()[j.getLesChoix()[_choix][nb] - 2] - j.getBonzes()[0][1];
            }
            else if (j.getBonzes()[1][0] == j.getLesChoix()[_choix][nb]) {
                res = j.getMaximum()[j.getLesChoix()[_choix][nb] - 2] - j.getBonzes()[1][1];
            }
            else if (j.getBonzes()[2][0] == j.getLesChoix()[_choix][nb]) {
                res = j.getMaximum()[j.getLesChoix()[_choix][nb] - 2] - j.getBonzes()[2][1];
            }
            else {
                res = j.getMaximum()[j.getLesChoix()[_choix][nb] - 2] - j.avancementJoueurEnCours()[j.getLesChoix()[_choix][nb] - 2];
            }
        }
        return res;
    }
    
    private boolean newBonze(final Jeu j, final int _choix, final int nb) {
        boolean res = false;
        if (j.getBonzesRestants() != 0 && j.getLesChoix()[_choix][nb] != j.getBonzes()[0][0] && j.getLesChoix()[_choix][nb] != j.getBonzes()[1][0]) {
            res = true;
        }
        return res;
    }
    
    public boolean stop(final Jeu j) {
        boolean res = false;
        if (j.getBonzesRestants() == 0) {
            for (int i = 0; i < 11; ++i) {
                if (j.getBloque()[i]) {
                    res = true;
                }
            }
        }
        return res;
    }
    
    public String getName() {
        return "Humeau Minimale";
    }
}