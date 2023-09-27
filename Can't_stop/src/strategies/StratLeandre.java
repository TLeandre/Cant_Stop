package strategies;

import cantstop.Jeu;
        
/**
 * Strategie numéro 315 
 *
 * Préambule :
 * Après de nombreuses recherches de strategie sur internet j'ai noté que 3 elements important ressortaient : le choix de la colonne, les conditions d'arrêts et l'avancement de l'adversaire.
 * 
 * La première strategie à été de réaliser une selection sur le choix de la colonne en fonction des probabilités de finir la colonne et de s'arrêter en utilisant la strategie des 28 ou lorsque 
 * une des colonnes est complété. Ce que j'ai remarqué est que le stop dû à la strategie des 28 est réalisé que très peu de fois, j'ai trouvé par conséquent inutile de laisser cette conditions
 * 
 * Vue qu'il est important de regarder l'avancement de l'adversaire une des strategies alternative été de ne pas jouer les colonnes que l'adversaire jouainet ( dans notre cas les colonnes les 
 * moins utilisé c'est à dire la 3-4-5-9-10-11) malheureusement cette strategie ne c'est pas avéré efficace. 
 * 
 * L'idée inverse été donc de ce positioner sur les colonnes où l'adversaire ce situe. Couplé au faite que le stop ce réalise uniquement quand une des colonnes est finis, cela permettrait 
 * d'empecher l'adversaire de finir sa colonne et lui couper l'herbe sous le pied.
 * 
 * Strategie : 
 * Nous avons donc une strategie hyper offensive qui prend en compte 3 paramètres, un coefficient attribué à chaque colonne ( privilégiant 2-6-7-8-12), la distance restant à l'adversaire avant 
 * de finir sa colonne ( plus il est proche de finir la colonne plus le coeffient sera élevé), et le faite que si un choix est en double qui sera privilégié ( afin d'avancer plus vite sur une 
 * colonne en particulié )
 *
 * @author TUESTA Léandre
 */

public class StratLeandre implements Strategie {

    // nombre de choix pouvant être réalsé
    private int nbChoix;
    // Les choix possibles [Somme1][Somme2 (ou 0 si une seule colone possible)]
    private int[][] lesChoix;
    // nombre de coup en cours 
    private int[] avancement;
    // nombre de coup en cours 
    private int[] avancementAdverse;
    // le nombre de case de chaque colone
    private int[] max;
    // indication des colones finies mais pas encore validées
    private boolean[] bloque;
    // le positionnement des bonzes [numéro de colone 2 à 12][avancement sur la colone]
    private int[][] bonzes;
    // indique le nombre de bonze restant 
    private int bonzesRestant;

    /**
     * Permet de trouver le choix à réaliser lors de ce tour
     * @return ind, l'indice du choix à réaliser 
     */
    private int indice(int[][] lesChoix, int nbChoix, int [] max, int [] avancement, int[][] bonzes, boolean[] bloque, int[] avancementAdverse, int bonzesRestant){
        double best = 0.0;
        int ind = 0;
        double sum = 0.0;

        // regarde pour l'ensemble des choix
        for(int i = 0; i<nbChoix; i++){
            // regarde si est une extremité ( 2 et 12 )
            sum = extremity(lesChoix, i);

            if(bonzesRestant == 0){
                // regarde la distance restant à parcourir pour l'adversaire
                sum += distance(lesChoix, i, max, avancement, avancementAdverse, bloque);

            }else{
                // regarde les coefficients attribué a chaque colonne
                sum += proba(lesChoix, i);

                // cas ou dans un choix les deux colonnes sont les mêmes
                if (lesChoix[i][0] == lesChoix[i][1]) {
                    sum += 100.0;
                }
            }
            // retourne l'indice du choix ayant la plus grosse somme  
            if(sum >= best){
                best = sum;
                ind = i;   
            }
        }
        return ind;
    }

    /**
     * Calcul du score de la voie à l'aide d'un coefficient
     * @return score, score de la voie valeur entre 0 et 100 
     */
    private double proba(int[][] lesChoix, int choice){
        double[] coef = {0.0, 20.0, 40.0, 60.0, 80.0, 100.0, 80.0, 60.0, 40.0, 20.0, 0.0};
        double score = 0;

        score += coef[lesChoix[choice][0] - 2];
        if (lesChoix[choice][1] != 0) {
            score += coef[lesChoix[choice][1] - 2];
            score /= 2;
        }
        return score;
    }

    /**
     * Score augmente dans le cas ou le choix est un 2 ou un 12, choix réaliser afin de privilégier ces deux colonnes
     * @return score, fct du nombre d'extremité dans le choix 
     */
    private double extremity(int[][] lesChoix, int choice){
        double score = 0.0;

        for(int i = 0; i < 2; i++){
            if(lesChoix[choice][i] == 2 || lesChoix[choice][i] == 12){
                score += 50;
            }
        }
        return score;
    }

    /**
     *  Score augmente en fonction de la distance à parcourir de l'Adversaire, plus il lui reste peu de distance à parcourir plus le score sera grand
     *  Cette strategie est adopté afin de bloquer son adversaire
     *  @return score en fonction des distances
     */
    private double distance(int[][] lesChoix, int choice, int[] max, int[] avancement, int[] avancementAdverse, boolean[] bloque){
        double score = 0.0;

        for(int i = 0; i < 2; i++){
            if((distanceIA(lesChoix, choice, i, max, avancement, bonzes) != 0) && (lesChoix[choice][i] != 0)){
                score += 150 - 10 * distanceAdversaire(lesChoix, choice, i, bloque, max, avancementAdverse);
            }
        }
        return score*100;
    }

    /**
     * Regarde la distance restant à parcourir sur une colonne pour l'Adversaire
     * @return distance avant de finir la colonne pour l'Adversaire
     */
    private int distanceAdversaire(int[][] lesChoix, int choice, int ind, boolean[] bloque, int[] max, int[] avancementAdverse){
        if((lesChoix[choice][ind] != 0) && (!bloque[lesChoix[choice][ind] - 2])){
            return max[lesChoix[choice][ind] - 2] - avancementAdverse[lesChoix[choice][ind] - 2];
        }
        return 20;
    }

    /**
     * Regarde la distance restant à parcourir sur une colonne pour IA
     * @return distance avant de finir la colonne pour l'IA
     */
    private int distanceIA(int[][] lesChoix, int choice, int ind, int[] max, int[] avancement,int[][] bonzes){
        if((lesChoix[choice][ind] != 0) && (!bloque[lesChoix[choice][ind] - 2])){
            for(int i = 0; i < 3; i++){
                if(lesChoix[choice][ind] == bonzes[i][0]){
                    return max[lesChoix[choice][ind] - 2] - bonzes[i][1];
                }
            }
            return max[lesChoix[choice][ind] - 2] - avancement[lesChoix[choice][ind] - 2];
        }
        return 15;
    }

    /**
     * @param j le jeu
     * @return le choix réalisé
     */
    public int choix(Jeu j) {
        // implémentation de l'ensemble des variables nécessaire
        nbChoix = j.getNbChoix();
        lesChoix = j.getLesChoix();
        bonzes = j.getBonzes();
        avancement = j.avancementJoueurEnCours();
        avancementAdverse = j.avancementAutreJoueur();
        max = j.getMaximum();
        bloque = j.getBloque();
        bonzesRestant = j.getBonzesRestants();

        return indice(lesChoix, nbChoix, max, avancement, bonzes, bloque, avancementAdverse, bonzesRestant);
    }

    /**
     * @param j le jeu
     * @return true or false 
     */
    public boolean stop(Jeu j) {
        // S'arrete uniquement lorsqu'une colonne est finit et que tous les bonzes on été placé 
        for(int i = 0; i < bloque.length; i++){
            if(bloque[i] && j.getBonzesRestants() <= 0){
                return true;
            }
        }
        return false;

    }

    /**
     * @return vos noms
     */
    public String getName() {
        return "Leandre TUESTA";
    }
}
