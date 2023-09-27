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

public class Strat3158 implements Strategie {

    // nombre de choix pouvant être réalsé
    private int nbChoix;
    // Les choix possibles [Somme1][Somme2 (ou 0 si une seule colone possible)]
    private int[][] lesChoix;
    // nombre de coup en cours 
    private int[] avancement;
    // le nombre de case de chaque colone
    private int[] max;
    // indication des colones finies mais pas encore validées
    private boolean[] bloque;
    // le positionnement des bonzes [numéro de colone 2 à 12][avancement sur la colone]
    private int[][] bonzes;

    // tableau des poids de chaque colone du jeu
    private float[] weight = {0, 0, 0.13f, 0.24f ,0.37f ,0.48f,0.61f, 0.71f, 0.61f,0.48f,0.37f,0.24f,0.13f}; // 100,250,425,600,725,900,725,600,425,250,100


    /**
    * Permet de trouver le choix à ce tour
    * @return ind, l'indice du choix à réaliser 
    */
    private int indice(int[][] lesChoix, int nbChoix, int [] max, int [] avancement, int[][] bonzes){
        float best = 0;
        int ind = 0;
        float sum = 0;

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
            sum = gainProba(weight[lesChoix[i][0]], (max[lesChoix[i][0] - 2] - avancement[lesChoix[i][0] - 2]));
            if((lesChoix[i][1] - 2) >= 2 ){
                sum += gainProba(weight[lesChoix[i][1]], (max[lesChoix[i][1] - 2] - avancement[lesChoix[i][1] - 2]));
            }
            
            // retourne l'indice du choix du plus gros poids 
            if(sum <= best){
                best = sum;
                ind = i;   
            }
        }

        return ind;
    }

    private float gainProba(float prob, int puiss){
        float sum = prob;
        for(int i =0; i<puiss; i++){
            sum *= prob;
        }

        return sum;
    }

    /**
    * Permet de mettre à jour les gains réaliser sur un tour 
    * @return rien (change la valeur de gain)
    */
    private boolean Progress(int[][] bonzes, int[] avancement){
        float[] probi = {weight[bonzes[0][0]], weight[bonzes[1][0]], weight[bonzes[2][0]], weight[bonzes[0][0]] * weight[bonzes[1][0]], weight[bonzes[0][0]] * weight[bonzes[2][0]], weight[bonzes[1][0]] * weight[bonzes[2][0]], weight[bonzes[0][0]] * weight[bonzes[0][0]], weight[bonzes[1][0]] * weight[bonzes[1][0]], weight[bonzes[2][0]] * weight[bonzes[2][0]]};
        float[] wei = {1.0f/weight[bonzes[0][0]], 1.0f/weight[bonzes[1][0]], 1.0f/weight[bonzes[2][0]]};
        
        float[] c = {bonzes[0][1] - avancement[bonzes[0][0] - 2], bonzes[1][1] - avancement[bonzes[1][0] - 2], bonzes[2][1] - avancement[bonzes[2][0] - 2]};

        float expected = probi[0]*(wei[0]*(c[0]+1) + wei[1]*c[1] + wei[2]*c[2]) + probi[1]*(wei[0]*c[0] + wei[1]*(c[1]+1) + wei[2]*c[2]) + probi[2]*(wei[0]*c[0] + wei[1]*c[1] + wei[2]*(c[2]+1));
        expected += probi[3]*(wei[0]*(c[0]+1) + wei[1]*(c[1]+1) + wei[2]*c[2]) + probi[4]*(wei[0]*(c[0]+1) + wei[1]*c[1] + wei[2]*(c[2]+1)) + probi[5]*(wei[0]*c[0] + wei[1]*(c[1]+1) + wei[2]*(c[2]+1));
        expected += probi[6]*(wei[0]*(c[0]+2) + wei[1]*c[1] + wei[2]*c[2]) + probi[7]*(wei[0]*c[0] + wei[1]*(c[1]+2) + wei[2]*c[2]) + probi[8]*(wei[0]*c[0] + wei[1]*c[1] + wei[2]*(c[2]+2));

        float actual = wei[0]*c[0] + wei[1]*c[1] + wei[2]*c[2];

        if(actual >= expected){
            return true;
        }else{
            return false;
        }
    } 

    private boolean test(int[][] bonzes){
        for(int i = 0; i<3; i++){
            if(bonzes[i][0] == 12 && bonzes[i][0] == 2){
                return true;
            }
        }
        return false;
    }

    /**
    * @param j le jeu
    * @return le choix réalisé
    */
    public int choix(Jeu j) {
        
        nbChoix = j.getNbChoix();
        lesChoix = j.getLesChoix();
        bonzes = j.getBonzes();
        avancement = j.avancementJoueurEnCours();
        max = j.getMaximum();
        bloque = j.getBloque();

        int ind = indice(lesChoix, nbChoix, max, avancement, bonzes);

        return ind;

    }

   /**
    * @param j le jeu
    * @return true or false 
    */
    public boolean stop(Jeu j) {

        boolean stop = false;

        bloque = j.getBloque();
        bonzes = j.getBonzes();
        avancement = j.avancementJoueurEnCours();
        
        // verifie si une ligne est finis 
        for(int i = 0; i < bloque.length; i++){
            if(bloque[i]){
                stop = true;
            }
        }

        boolean haveStop = false;
        if(j.getBonzesRestants() <= 0){
            haveStop = Progress(bonzes, avancement);
        }

        boolean testStop = false;
        if(j.getBonzesRestants() <= 0){
            testStop = test(bonzes);
        }
        
        // si nous avons placé tous nos bonzes et que nos gain dépasse 28, on passe notre tour
        if( testStop || haveStop || (stop && j.getBonzesRestants() <= 0)){
            return true;
        }else{
            return false;
        }

    }

   /**
    * @return vos noms
    */
    public String getName() {
        return "Leandre TUESTA v8";
    }
}
