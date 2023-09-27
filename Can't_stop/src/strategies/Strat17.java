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
 * @author Zaynab Ait Ahmed
 */

public class Strat17 implements Strategie {

    private int[] columnValue = {12, 10, 8, 6, 4, 2, 4, 6, 8, 10, 12};
    private int[][] lesBonzes;
    private int valueTour = 0;

    /**
     * 
     * @param somme1 la somme 1 d'une combinaison de dé
     * @param somme2 la somme 2 de la meme combinaison
     * @return la  valeur calculée pour cette combinaison
     */
    private int getValue(Jeu j, int somme1, int somme2){
        int value = valueTour;
        boolean somme1Presente = false;
        boolean somme2Presente= false;
        boolean areAllOdd = false;
        boolean areAllEven = false;
        boolean lesserEight = false;
        boolean greaterSix = false;
        
        //On regarde si un bonze est deja dans la colonne de la somme
        for (int i=0; i < lesBonzes.length; i++){
            if (somme1 != 0 && somme1 == lesBonzes[i][0]){
                somme1Presente = true;
            }
            
            if (somme2 != 0 && (somme2 == lesBonzes[i][0] || somme2 == somme1)){
                somme2Presente = true;
            }
        }
        //Si somme1 n'est pas nulle on ajoute sa valeur ou la moitié si un bonze est deja placé dans la colonne
        if (somme1 != 0 && somme1Presente)
            value += columnValue[somme1 - 2] / 2;
        else if (somme1 != 0)
            value += columnValue[somme1 - 2];
        //Si somme2 n'est pas nulle on ajoute sa valeur ou la moitié si un bonze est deja placé dans la colonne
        if (somme2 != 0 && somme2Presente)
            value += columnValue[somme2 - 2] / 2;
        else if (somme2 != 0)
            value += columnValue[somme2 - 2];

        
        // On regarde si lorsque les trois bonzes sont placés, ils sont tous pairs et on enlève 2 au score du tour si c'est le cas
        if (somme1%2 == 0 && somme2%2 == 0){
            for (int i=0; i < lesBonzes.length; i++){
                if(lesBonzes[i][0]%2 == 0 && lesBonzes[i][0] != 0){
                    areAllOdd = true;
                }
                else{
                    areAllOdd = false;
                }
            }
        }
        // On regarde si lorsque les trois bonzes sont placés, ils sont tous impairs et on ajoute 2 au score du tour si c'est le cas
        if (somme1%2 == 1 && (somme2%2 == 1 || somme2 == 0)){
            for (int i=0; i < lesBonzes.length; i++){
                if(lesBonzes[i][0]%2 == 1 && lesBonzes[i][0] != 0){
                    areAllEven = true;
                }
                else{
                    areAllEven = false;
                }
            }
        }

        // On regarde si lorsque les trois bonzes sont placés, ils sont tous inférieurs à 8 et on ajoute 4 au score du tour si c'est le cas
        if (somme1 < 8 && somme2 < 8){
            for (int i=0; i < lesBonzes.length; i++){
                if(lesBonzes[i][0] < 8 && lesBonzes[i][0] != 0){
                    lesserEight = true;
                }
                else{
                    lesserEight = false;
                }
            }
        }
        // On regarde si lorsque les trois bonzes sont placés, ils sont tous supérieurs à 6 et on ajoute 4 au score du tour si c'est le cas
        if (somme1 > 6 && (somme2 > 6 || somme2 == 0)){
            for (int i=0; i < lesBonzes.length; i++){
                if(lesBonzes[i][0] > 6 && lesBonzes[i][0] != 0){
                    greaterSix = true;
                }
                else{
                    greaterSix = false;
                }
            }
        }

        // On ajoute les points au score du tour en fonction de la décision prise et des 4 conditions ci-dessus
        if (areAllOdd == true){
            value -= 2;
            }
        if (areAllEven == true){
            value += 2;
        }
        /*if (lesserEight == true || greaterSix == true){
            value += 4;
        }*/
        
        return value;
    }
    

    /**
    * @param j le jeu
    * @return toujours le 1er choix
    */    
    public int choix(Jeu j) {
        System.out.println(1.0f/0.34f);
        int choixFinal = 0;

        //On initialise 4 variable temporaires qui nous permettront de savoir quel choix on va préférer en fonction de si la colonne a déjà été avancée au tour précédent et si un bonze a déjà été posé dans le tour
        int avanceEtBonze = 0;
        int avanceSansBonze = 0;
        int nulEtBonze = 0;
        int nulSansBonze = 0;

        int[] choixValue = new int[6];
        int[] avancement = j.avancementJoueurEnCours();
        int[][] lesChoix = j.getLesChoix();
        lesBonzes = new int[3][2];
        lesBonzes = j.getBonzes();

        for (int i=0; i < lesChoix.length; i++){
            choixValue[i] = getValue(j, lesChoix[i][0], lesChoix[i][1]);

            if(lesChoix[i][0] != 0){
                if(lesChoix[i][1] != 0){
                    // On regarde si la colonne du choix est déjà avancée
                    if (avancement[lesChoix[i][0]-2] != 0 || avancement[lesChoix[i][1]-2] != 0){
                        // On regarde si un bonze est déjà posé sur la colonne
                        for (int k=0; k < lesBonzes.length; k++){
                            if (lesChoix[i][0] == lesBonzes[k][0]){
                                // On regarde si la valeur du choix vaut plus que celui choisit précédemment 
                                if (choixValue[avanceEtBonze] < choixValue[i]){
                                    avanceEtBonze = i;
                                }
                            }
                            else{
                                if (choixValue[avanceSansBonze] < choixValue[i]){
                                    avanceSansBonze = i;
                                }
                            }
                        }
                    }
                }
                else if (avancement[lesChoix[i][0]-2] != 0){
                    // On regarde si un bonze est déjà posé sur la colonne
                    for (int k=0; k < lesBonzes.length; k++){
                        if (lesChoix[i][0] == lesBonzes[k][0]){
                            // On regarde si la valeur du choix vaut plus que celui choisit précédemment 
                            if (choixValue[avanceEtBonze] < choixValue[i]){
                                avanceEtBonze = i;
                            }
                        }
                        else{
                            if (choixValue[avanceSansBonze] < choixValue[i]){
                                avanceSansBonze = i;
                            }
                        }
                    }  
                }
                else {
                    for (int k=0; k < lesBonzes.length; k++){
                        if (lesChoix[i][0] == lesBonzes[k][0]){
                            // On regarde si la valeur du choix vaut plus que celui choisit précédemment 
                            if (choixValue[nulEtBonze] < choixValue[i]){
                                nulEtBonze = i;
                            }
                        }
                        else{
                            if (choixValue[avanceSansBonze] < choixValue[i]){
                                nulSansBonze = i;
                            }
                        }
                    }
                }
            }
        }

        // Apres avoir examiné tous les choix, on préfère dans l'ordre avanceEtBonze puis nulEtBonze puis avanceSansBonze et enfin nulSansBonze
        if (avanceEtBonze != 0)
            choixFinal = avanceEtBonze;
        else if (nulEtBonze != 0)
            choixFinal = nulEtBonze;
        else if (avanceSansBonze != 0)
            choixFinal = avanceSansBonze;
        else
            choixFinal = nulSansBonze;

        //On ajoute à la valeur du tour la valeur de la décision final
        valueTour += choixValue[choixFinal];

        return choixFinal;
    }

    /**
    * @param j le jeu
    * @return toujours vrai (pour s'arrêter)
    */
    public boolean stop(Jeu j) {
        if (valueTour < 28 && j.getBonzes()[2][0] == 0){
            return false;
        }
        else{
            //On réinitialise la valeur du tour à 0 pour le prochain tour
            valueTour=0;
            return true;
        }
    }

    /**
    * @return vos noms
    */
    public String getName() {
        return "Zaynab Ait Ahmed";
    }
}
