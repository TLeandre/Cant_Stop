package strategies;

import cantstop.Jeu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
        
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

public class Strat65 implements Strategie {
    
   /**
    * @param j le jeu
    * @return toujours le 1er choix
    */
   
   public int choix(Jeu j) {
      //déclaration des variables  
   
  //--------------------------------------------------------FONCTION TRI TABLEAU DE GAINS-----------------------------
/*public static void triGains(double gains[][]){
         double[][] nouveauxGains = new double[10][2];
         int k,l,valeurAInserer;
           for (l=0 ; l < gains[][].length ; l++){
            valeurAInserer = gains[l][1];
            k=l-1;
            while (0<=k && gains[k][1]>valeurAInserer){
                gains[k+1][1]=gains[k][1];
                k=k-1;
                
           }
            gains[k+1][1]=ValeurAInserer;
           }
        }*/
           
//---------------------------------------------------------------------------------------------------------------------------------
      double [] tabProbaGroupement = {0.02777778 , 0.05555556 , 0.08333333 , 0.11111111 , 0.13888889 , 0.16666667 , 0.13888889 , 0.11111111 , 0.08333333 , 0.05555556 , 0.02777778 };
       int[]valeurAvancementColonne = {6,5,4,3,2,1,2,3,4,5,6}; // on définit la valeur relative de faire avancer un bonze sur la colonne i+2 (6 pour la colonne 2, 5 pour la 3, etc.)
       int [][]lesChoix=j.getLesChoix();
       int nbChoix = j.getNbChoix();
       List<Integer>  colonnesPrioritaires = new ArrayList<Integer>();// on définit les colonnes qui sont les plus importantes  à jouer
       double[][]gainsOrdonnes= new double [10][2];
       List<Double>  gainsOrdonnesListe = new ArrayList<Double>(); //on rangera dans cette liste  les colonnes dans l'ordre de gain décroissant
       
       for(int i=0;i<nbChoix;i++){// ici on calcule le gain potentiel représenté par l'avancement sur une colonne en fonction de notre avancée passée et de la probabilité de pouvoir jouer sur cette colonne
           for (int m=0; m<2;m++){
            double gain = (j.getBonzes()[lesChoix[i][m]][1]/j.getMaximum()[lesChoix[i][m]])/tabProbaGroupement[i];
            if (gain>4.15){ 
                colonnesPrioritaires.add(lesChoix[i][1]);
            }
            gainsOrdonnes[i][0]=lesChoix[i][m];
            gainsOrdonnes[i][1]=gain;
            gainsOrdonnesListe.add(gain);
            } 
      }
      Collections.sort(gainsOrdonnesListe);
      Collections.reverse(gainsOrdonnesListe);// on classe les gains dans l'ordre décroissant 
       if (j.getBonzesRestants()>0){ //tant qu'on a un bonze qui n'est pas posé, on jouera tjrs puisqu'on aura tjrs un choix de nouvelle colonne
          for (int i=0; i<nbChoix;i++ ){//on parcourt la matrice lesChoix
             if (lesChoix[i][0] >10 ||lesChoix[i][0] <4 ||lesChoix[i][1] >10 ||lesChoix[i][1]<4 ){// si on a possibilité de jouer sur une colonne <2 ou > 10 on le fait
                  return i;
          }
               else if (lesChoix[i][0]==lesChoix[i][1]&& lesChoix[i][0]!=0){// on vérifie s'il existe un choix d'avancer 2 fois  sur la même colonne
                  return i;
              }
             if(colonnesPrioritaires.contains(lesChoix[i][0])||colonnesPrioritaires.contains(lesChoix[i][1])){ // si un des choix correspond à une colonne prioritaire
                 return i;
             }
             if (j.getBonzes()[i][1]==0){
                 return i; // si on n'a pas encore de bonze sur cette colonne on place un bonze dessus puisqu'il nous en reste un à poser
             }
          }
       }
       //s'il ne nous reste  pas de bonzes on teste les mêmes possibilités pour déterminer la meilleure colonne sur laquelle jouer
       for (int i=0; i<nbChoix;i++ ){//on parcourt la matrice lesChoix
             if (lesChoix[i][0] >10 ||lesChoix[i][0] <4 ||lesChoix[i][1] >10 ||lesChoix[i][1]<4 ){// si on a possibilité de jouer sur une colonne <4 ou > 10 on le fait
                  return i;
          }
               else if (lesChoix[i][0]==lesChoix[i][1]&& lesChoix[i][0]!=0){// on vérifie s'il existe un choix d'avancer 2 fois  sur la même colonne
                  return i;
              }
             if(colonnesPrioritaires.contains(lesChoix[i][0])||colonnesPrioritaires.contains(lesChoix[i][0])){ // si un des choix correspond à une colonne prioritaire
                 return i;
             }
             for (int z=0 ; z<nbChoix;z++){ // dernière possibilité : on renvoie le choix avec la colonne ayant le gain le plus élevé
                 if (gainsOrdonnes[lesChoix[z][1]][1]==gainsOrdonnesListe.get(0)||gainsOrdonnes[lesChoix[z][1]][2]==gainsOrdonnesListe.get(0)){
                     return z;
                 }
             }
           /* Stratégie écrite
           tant qu'on a un bonze restant
                si on a un choix double
                        si on a aussi un choix sur une colonne <4 ou >10 -> on l'utilise
                        sinon on joue le choix double
                on regarde l'avancement pour chaque colonne proposée par le tableau de choix
                        on prend le choix pour lequel (nb de cases - avancement / proba de la colonne) est le plus élevé
                        si on a une égalité on joue au random
           si on a un choix sur une colonne qui n'est pas utilisée, ni par moi ni par l'IA, on se place dessus
                 
           */
        
         //if (j.getLesChoix()[][]
       }
       return 0;
   }

   /**
    * @param j le jeu
    * @return toujours vrai (pour s'arrêter)
    */
   int tour=0;
   public boolean stop(Jeu j) {
       tour+=1;
       int toursRestants = 100;
       int colonnesJouees [][]=new int [3][1];
       for (int i=0;i<3;i++){
           colonnesJouees[i][0]=j.getBonzes()[i][0];
       }
      /* for (int i=0;i<10;i++){          ici on rentrera les tableaux de probas 
           for (int k=0; k<10;k++){
               for (int l=0;l<10;l++){
                   if (colonnesJouees == [i][k][l]){
                        toursRestants = proba[i][k][l][0];
               }
               }
           }
       }*/
       int proba[][][][]= new int [10][10][10][10];
       proba[0][0][0][0] = 2;
       if (j.getBonzesRestants()>0){ // on rejoue forcément s'il nous reste un bonze à poser
           return false;
       }
       for (int i =0; i<j.getBloque().length;i++){
              if(j.getBloque()[i]==true){
                  return true;
              }
          }
       for (int i=0;i<10;i++){ // si on arrive à une case de valider une colonne on s'arrête
           for(int k=0;k<3;k++){
                if(j.getBonzes()[k][0]==i)
                     if(j.getMaximum()[i]-j.getBonzes()[k][1] <=1){
                         return true;
                     }
             }
       }
       if (tour==4){
           return true;
       }
       /*
       on regarde le tableau de proba sur la case de nos 3 colonnes 
            on crée une variable du nombre tours à jouer avant de s'arrêter et on l'incrémente à chaque tour
                une fois qu'il arrive à 0 on s'arrête, à moins de compléter une colonne avant.
       */
       return false;
   }

   /**
    * @return vos noms
    */
   public String getName() {
       return "MARIN CARDO";
   }

}
