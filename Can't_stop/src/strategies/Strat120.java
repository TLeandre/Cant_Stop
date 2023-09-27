package strategies;

import cantstop.Jeu;
import java.util.Arrays;
import java.util.stream.IntStream;

        
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
 * @author DUBOURG LUDIVINE 
 */

public class Strat120 implements Strategie {
    // récupérer le bombre d'avancement, le tableau de choix, l'avancement des bonzes, et le principal c'est d'utiliser les fonctions get()
   /**
    * @param j le jeu
    * @return toujours le 1er choix
    */
    float[] frequence = calculerFrequence();
    int[] maximum = null;


    //Tableau de la fréquence d'apparition des pions
    public int[] compterColonne(int v1, int v2, int[] tab){
        tab[v1]++;
        tab[v2]++;
        return tab;
    }

    public float[] calculerFrequence(){
        int[] frequence = new int[13];
        int apparition =0; 
        for(int de1 =1; de1 <7; de1++){
            for(int de2 =1; de2 <7; de2++){
                for(int de3 =1; de3 <7; de3++){
                    for(int de4 =1; de4 <7; de4++){
                        compterColonne(de1+de2, de3+de4, frequence);
                        compterColonne(de1+de3, de2+de4, frequence);
                        compterColonne(de1+de4, de2+de3, frequence);
                    }
                }
            }
        }
        for(int i=2; i<frequence.length; i++){
            apparition += frequence[i];
        }
        float[] freq = new float[13];
        for(int i=2; i<freq.length; i++){
            freq[i] = (float)frequence[i]/(float)apparition;
        }
        float total =0;
        for(int i=2; i<freq.length; i++){
            /* Debug : Affichage de la fréquence d'appariition
            System.out.println("frequence :"+ i + " "+ freq[i]);
            */
            total+= freq[i];
        }   
        //System.out.println("total :" + total);   
        return freq; 
    }
    
 
    //Calcul du gain en fonction des meilleures possibilités que l'on a choisi
    public int evaluergroupement(int v1, int v2, int c1, int c2, int c3, int total){
        int compteur = 0;
        if(v1 == c1 || v1 == c2 || v1 == c3){
            compteur += 1;
        }
        if(v2 == c1 || v2 == c2 || v2 == c3){
            compteur += 1;
        }
        if(compteur == 0){
            return (-total);
        }
        return compteur;
    }
    
    // calcul du meilleur gain possible avec 4 dés 
    public int evaluerde(int a, int b, int c, int d, int c1, int c2, int c3, int total){
        // mettre c1, c2, c3 au début dans les choix 
        int g0 = evaluergroupement(a+b, c+d, c1,c2,c3, total);
        int g1 = evaluergroupement(a+c, b+d, c1,c2,c3, total);
        int g2 = evaluergroupement(a+d, b+c, c1,c2,c3, total);
        // retourne le gain maximal
        if(g0< g1){
            if(g1< g2){
                return g2;
            }
            else{
                return g1;
            }
        }
        else if(g0< g2){
            return g2;
        }
        else{
            return g0;
        }
    }
    
    // Calcul des combinaisons possibles avec tous les dés et évaluation de l'espérance
    // Je peux me permettre d'effectuer ce calcul car on a que 6^4 tirages de dés possibles 
    public int evaluerEsperance(int c1, int c2, int c3, int total){
        int esperance = 0;
        for(int de1 =1; de1 <7; de1++){
            for(int de2 =1; de2 <7; de2++){
                for(int de3 =1; de3 <7; de3++){
                    for(int de4 =1; de4 <7; de4++){
                        esperance += evaluerde(de1,de2,de3,de4,c1,c2,c3, total);
                    }
                }
            }
        }
        /* Debug : Affichage de l'espérance 
        System.out.println("esperance =" + esperance);
        */
        return esperance;
    }

        
    public int choix(Jeu j){
        if(maximum == null){
            maximum =j.getMaximum();
        }
        /* Debug : Affichage des maximum 
        for(int i=0; i< maximum.length; i++){
            System.out.println("Maximum de" + i +" est "+ maximum[i]);
        }
        */

        /* Debug : Affichage du jeu en cours  
        int[] avancement = j.avancementJoueurEnCours();
        for(int k =0; k<(avancement.length); k++){
            System.out.println("A" + k + ": " +avancement[k]);
        }
        */
        int[][] bonzes = j.getBonzes();
        int[][] leschoix = j.getLesChoix();

        /*Debug : AFfichage des bonzes et des choix 
        for(int i =0; i<bonzes.length; i++){
            System.out.print(i + " :");
            for(int p = 0; p< bonzes[i].length; p++){
                System.out.print(bonzes[i][p] + " ");
            }
            System.out.println(" ");
            
        }
        System.out.println("------ ");
        
        for(int i =0; i<leschoix.length; i++){
            System.out.print(i + " :");
            for(int p = 0; p< leschoix[i].length; p++){
                System.out.print(leschoix[i][p] + " ");
            }
            System.out.println(" ");
            
        }
        System.out.println("+++++ ");
        */
        eliminerCreateur(leschoix,bonzes);  
        return choisir(j,leschoix, bonzes);
    }
    // Calcule du meilleur choix
    private int choisir(Jeu j,int[][] leschoix, int[][] bonzes){
        float min = 0; 
        int minIndex = -1;
        float[] effort = calculerEffort(j,leschoix, bonzes);
        for(int i= 0; i<leschoix.length; i++){
            if(leschoix[i][0] ==0) continue;
            if(minIndex == -1 || effort[i]< min){
                min = effort[i];
                minIndex = i;
            }
        }
        /* Debug : AFfichage des efforts
        for(int i=0; i<effort.length; i++){
            System.out.println("effort ["+ i + "] : " + effort[i]);
        }
        */

        /* Stratégie de test : on prend le premier choix possible 
        for(int i= 0; i<leschoix.length; i++){
            if(leschoix[i][0] != 0){
                System.out.println("choix :" + i);
                return i;
            }
        }
        */

        /* Debug : Affichage du choix 
        System.out.println("minimum : "  + min + "d'indice " + minIndex);
        */
        return minIndex;
    }

    //Calcule le nombre de coups moyens pour remplir chaque colonne possible.
    //Quand un choix propose de remplir 2 colonnes, on choisit le plus petit mnombre de coups entre les deux colonnes 
    private float[] calculerEffort(Jeu j,int[][] leschoix, int[][] bonzes) {
        float[] effort = new float[leschoix.length];
        float effortCombi;
        for(int i =0; i<leschoix.length; i++){
            if(leschoix[i][0] == 0) continue;
            boolean doublet = leschoix[i][0]==  leschoix[i][1];
            effort[i] = evaluerEffort(j,leschoix[i][0], bonzes,doublet);
            if(! doublet && leschoix[i][1] != 0){
                effortCombi = evaluerEffort(j,leschoix[i][1], bonzes, doublet);
                // On favorise la colonne la plus facile à remplir 
                if(effortCombi < effort[i]){
                    effort[i] = effortCombi;
                }
            }
        }
        return effort;
    }

    // Evalue le nombre de coups moyens pour remplir la colonne col
    private float evaluerEffort(Jeu j, int col, int[][] bonzes, boolean doublet) {
        int restant;
        float resultat;
        int[] avancement = j.avancementJoueurEnCours();
        restant = maximum[col-2]; 

        boolean inBonze = false;
        for(int i =0; i< bonzes.length; i++){
            if(bonzes[i][0]== col){
                restant -= bonzes[i][1];
                inBonze = true;
                break;
            } 
        }
        if( !inBonze ) restant -= avancement[col-2];
        // On ajoute un ou deux bonzes dans la colonne 
        restant --;
        if(doublet && restant!=0){
            restant--;
        }
        resultat = (float)restant/frequence[col];
        /* Debug : Affichage des efforts, des cases restantes à remplir
        System.out.println("effort de la col " + col + ": "  + resultat + ", maximum " + maximum[col-2]+
        "doublet "+ doublet +" et restant "+restant +" avancement " + avancement[col-2]);
        */
        return resultat;
    }

    // Elimine les choix qui créer le plus de colonnes 
    private void eliminerCreateur(int[][] leschoix, int[][] bonzes) {
        if(compterBonzes(bonzes) ==3){
            return ;
        }
        int[] creations = new int[leschoix.length];
        for(int i =0; i<leschoix.length; i++){
            creations[i] =0;
            if(leschoix[i][0] == 0) continue;

            if(pasDedans(leschoix[i][0], bonzes)){
                creations[i]++;
            }
            if(leschoix[i][0] != leschoix[i][1] && pasDedans(leschoix[i][1], bonzes)){
                creations[i]++;
            }
        }
        //Calcule du minimum de création de colonne
        int minChoix = 2;
        for(int i =0; i<leschoix.length; i++){
            if(leschoix[i][0] == 0) continue;
            if (creations[i]< minChoix){
                minChoix = creations[i];
            }
        }
        /*Debug : Affichage du choix minimum
        System.out.println("minimum choix : "+ minChoix);
        */
        for(int i =0; i<leschoix.length; i++){
            if(leschoix[i][0] == 0) continue;
            if (creations[i] > minChoix){
                leschoix[i][0] = 0;
                leschoix[i][1] = 0;
            }
        }
        //Supprime les choix qui posent le moins de bonzes sur le jeu 
        boolean deux = false;
        for(int i =0; i<leschoix.length; i++){
            if(leschoix[i][0] == 0) continue;
            if(leschoix[i][1] != 0){
                deux = true;
                break;
            }
        }
        if(!deux){
            return ;
        }
        for(int i =0; i<leschoix.length; i++){
            if(leschoix[i][0] == 0) continue;
            if(leschoix[i][1] == 0){
                leschoix[i][0] =0;
            }
        }
    }

    private boolean pasDedans(int col, int[][] bonzes){
        for(int i =0; i<bonzes.length; i++){
            if(bonzes[i][0] == col){
                return false;
            }     
        }
        return true;
    }

    // Calcule le gain potentiel 
    private int getTotal(Jeu j) {
        int compteur = 0;
        int[][] bonzes = j.getBonzes();
        int[] avancement = j.avancementJoueurEnCours();
        for(int i =0; i<bonzes.length; i++){
            if(bonzes[i][0] != 0 ){
                compteur += bonzes[i][1] -avancement[bonzes[i][0]-2];
            }
        }
        /* Debug : Affichage du gain potentiel d'un choix
        System.out.println("gain poteniel =" + compteur);
        */
        return compteur;
    }

    //Y a-t-il une colonne potentiellement terminée ? 
    private boolean colonneCompletee(Jeu j) {
        boolean[] bloque = j.getBloque();
        for(int i=0; i< bloque.length; i++){
            /*System.out.println("B"+i+" : "+ bloque[i]);*/
            if(bloque[i]){
                return true;
            }
        }
        return false;
    }

    // Tant qu'on a pas touché 3 colonnes, on ne prend aucun risque si on continue (donc on continue)
    private boolean aucunRisque(Jeu j) {
        int[][] bonzes = j.getBonzes(); 
        int cpt = compterBonzes(bonzes);
        return cpt<3;
    }

    private int compterBonzes(int[][] bonzes) {
        int cpt =0;
        for(int i =0; i<bonzes.length; i++){
            if(bonzes[i][0] != 0){
                cpt++;
            }     
        }
        return cpt;
    }

    //int compteur = 0;
    /**
    * @param j le jeu
    * @return toujours vrai (pour s'arrêter)
    */

    public boolean stop(Jeu j) {
        int[][] bonzes = j.getBonzes();

        /* Debug: Affichage des bonzes
        for(int i =0; i<bonzes.length; i++){
            System.out.print(i + " :");
            for(int p = 0; p< bonzes[i].length; p++){
                System.out.print(bonzes[i][p] + " ");
            }
            System.out.println(" ");
            
        }
        System.out.println("&&&&& ");
        */


        if(aucunRisque(j)){
            return false;
        }

        //Ici nous avons 3 colonnes en cours de jeu
        if(colonneCompletee(j)){
            /*System.out.println("STOP COLONNE COMPLETE");*/
            return true;
        }
                
        if(evaluerEsperance(bonzes[0][0],bonzes[1][0],bonzes[2][0], getTotal(j))<0){
            /*System.out.println("STOP");*/
            return true;
        }
        return false;

        /* Stratégie pour les tests
        compteur++;
        if(compteur == 5){
            compteur = 0;
            System.out.println("STOP");
            return true;

        }
        return false;
        */ 
    }
    

/**
    * @return vos noms
    */
    public String getName() {
        return "DUBOURG LUDIVINE";
    }

}

