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
 * @author TUESTA Léandre
 */

public class Strat315 implements Strategie {

    // nombre de choix pouvant être réalsé
    private int nbChoix;
    // Les choix possibles [Somme1][Somme2 (ou 0 si une seule colone possible)]
    private int[][] lesChoix;
    // nombre de choix possible
    private int nbCoup;
    // nombre de coup en cours 
    private int[] avancement;
    // le nombre de case de chaque colone
    private int[] max;
    // indication des colones finies mais pas encore validées
    private boolean[] bloque;

    // tableau des poids de chaque colone du jeu
    private int[] weight = {0, 0, 100,250,425,600,725,900,725,600,425,250,100}; // 100,250,425,600,725,900,725,600,425,250,100
    // tableau des gains réaliser lors de l'avancement d'un bonze sur une colone
    private int[] earn = {0, 0, 1,2,3,4,5,6,5,4,3,2,1};
    // variable général du gain réaliser lors d'un tour 
    static int gain = 0;

    /**
    * Permet de trouver le choix à ce tour
    * @return ind, l'indice du choix à réaliser 
    */
    private int indice(int[][] lesChoix, int nbChoix, int [] max, int [] avancement){
        int best = 0;
        int ind = 0;
        int sum = 0;

        // regarde pour l'ensemble des choix
        for(int i =0; i<nbChoix; i++){
            // cas ou l'on pourrait finir une ligne, choisi directement cette indice
            for(int y = 0; y<2; y++){
                if((lesChoix[i][y] - 2) >= 2 ){
                    if((avancement[lesChoix[i][y] - 2] + 1) == max[lesChoix[i][y] - 2] ){
                        return i;
                    }
                }
            }

            // calcul des poids 
            if(lesChoix[i][0] == lesChoix[i][1]){ // gain multiplié par deux dans le cas ou l'on avance deux fois sur la meme ligne 
                sum = weight[lesChoix[i][0]] * 4;
            }else{
                sum = weight[lesChoix[i][0]] + weight[lesChoix[i][1]]; 
            }
            
            // retourne l'indice du choix du plus gros poids 
            if(sum >= best){
                best = sum;
                ind = i;   
            }
        }

        return ind;
    }

    /**
    * Permet de mettre à jour les gains réaliser sur un tour 
    * @return rien (change la valeur de gain)
    */
    private void changeGain(int nbCoup, int[][] lesChoix, int ind, int[] avancement){
        // si c'est la première fois que l'on joue, remet le gain à zero
        if(nbCoup <= 0){
            gain = 0;
        }

        // incrémentation des gains réaliser sur ce tour 
        for(int i = 0; i<2; i++){
            if((lesChoix[ind][i] - 2) >= 2 ){
                if(avancement[lesChoix[ind][i] - 2] <= 0 ){
                    gain += earn[lesChoix[ind][i]];
                }else{
                    gain += earn[lesChoix[ind][i]] * 2;
                }
            }
        }
    }

    /**
    * @param j le jeu
    * @return le choix réalisé
    */
    public int choix(Jeu j) {
        
        nbCoup = j.getNbCoup();
        nbChoix = j.getNbChoix();
        lesChoix = j.getLesChoix();
        avancement = j.avancementJoueurEnCours();
        max = j.getMaximum();
        bloque = j.getBloque();

        int ind = indice(lesChoix, nbChoix, max, avancement);

        changeGain(nbCoup, lesChoix, ind, avancement);

        return ind;

    }

   /**
    * @param j le jeu
    * @return true or false 
    */
    public boolean stop(Jeu j) {

        boolean stop = false;

        bloque = j.getBloque();
        
        // verifie si une ligne est finis 
        for(int i = 0; i < bloque.length; i++){
            if(bloque[i]){
                stop = true;
            }
        }
        
        // si nous avons placé tous nos bonzes et que nos gain dépasse 28, on passe notre tour
        if((j.getBonzesRestants() <= 0) && (gain >= 28) && (stop)){
            return true;
        }else{
            return false;
        }

    }

   /**
    * @return vos noms
    */
    public String getName() {
        return "Leandre TUESTA";
    }
}
