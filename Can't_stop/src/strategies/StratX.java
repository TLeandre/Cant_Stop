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

public class StratX implements Strategie {

   /**
    * @param j le jeu
    * @return toujours le 1er choix
    */
   public int choix(Jeu j) {
       return 0;
   }

   /**
    * @param j le jeu
    * @return toujours vrai (pour s'arrêter)
    */
   public boolean stop(Jeu j) {
       return true;
   }

   /**
    * @return vos noms
    */
   public String getName() {
       return "VOTRE NOM (SOUS FORME PRENOM NOM)";
   }
}
