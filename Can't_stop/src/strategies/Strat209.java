package strategies;

import cantstop.Jeu;
/**
 * Votre Stratégie (copie de la Strat0 pour l'instant)
 *
 * Expliquez votre stratégie en une 20aine de lignes maximum.
 *
 * RENDU: Ce fichier, correctement nommé et rempli.
 * Votre Stratégie aura un numéro (pour être similaire à Strat0 qui sera votre position dans l'alphabet de la promo + 14. 
 * (attention calcul compliqué) Le premier aura donc pour numéro 15 et le dernier 334
 * Pour "préparer" votre stratégie, sur le fichier StratX.java, vous cliquez sur Bouton Droit, Refactor, Rename et vous 
 * nommez bien votre stratégie genre Strat245.java (pour le 231e).
 *
 * @author VOTRE NOM
 */

public class Strat209 implements Strategie {

    // tableau des poids de chaque colone du jeu
    private int[] poids = {0, 0, 900,250,425,600,725,900,725,600,425,250,900}; 
    // tableau des gains réaliser lors de l'avancement d'un bonze sur une colone
    private int[] gagne = {0, 0, 1,2,3,4,5,6,5,4,3,2,1};
    // variable général du gain réaliser lors d'un tour 
    static int gain = 0;

    private int nombreBonzeChoix(Jeu j, int c){
        int somme = 1;
        for(int i = 0; i < 3; i++){
            if((j.getLesChoix()[c][0] == j.getBonzes()[i][0]) || (j.getLesChoix()[c][1] == j.getBonzes()[i][0])){
                somme +=1;
            }
        }
        return somme;
    }

    public int choix(Jeu j) {
       
    int meilleur = 0;
    int c = 0;
    int somme = 0;

    for(int i =0; i<j.getNbChoix(); i++){
        // regarde si on peut finir une ligne ( priorité )
        for(int y = 0; y<2; y++){
            if((j.getLesChoix()[i][y] - 2) >= 2 ){
                if((j.avancementJoueurEnCours()[j.getLesChoix()[i][y] - 2] + 1) == j.getMaximum()[j.getLesChoix()[i][y] - 2] ){
                    return i;
                }
            }
        }
 
        if(j.getLesChoix()[i][0] == j.getLesChoix()[i][1]){ 
            somme = poids[j.getLesChoix()[i][0]] * 4 * nombreBonzeChoix(j, i);
        }else{
            somme = (poids[j.getLesChoix()[i][0]] + poids[j.getLesChoix()[i][1]]) * nombreBonzeChoix(j, i); 
        }
        
        if(somme >= meilleur){
            meilleur = somme;
            c = i;   
        }
    }

    changeGain(j, c);

    return c;

   }

    private void changeGain(Jeu j, int c){
        if(j.getNbCoup() <= 0){
            gain = 0;
        }

        for(int i = 0; i<2; i++){
            if((j.getLesChoix()[c][i] - 2) >= 2 ){
                if(j.avancementJoueurEnCours()[j.getLesChoix()[c][i] - 2] <= 0 ){
                    gain += gagne[j.getLesChoix()[c][i]];
                }else{
                    gain += gagne[j.getLesChoix()[c][i]] * 2;
                }
            }
        }
    }

    public boolean stop(Jeu j) {

        boolean end = false ;
        
        for(int i = 0; i < j.getBloque().length; i++){
            if(j.getBloque()[i]){
                end = true;
            }
        }

        if((j.getBonzesRestants() <= 0) && ((gain >= 28) || (end))){
            return true;
        }else{
            return false;
        }

   }

   public String getName() {
       
       return "MAXIME LIGAULT)";
   }
}
