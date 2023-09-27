package cantstop;

/**
 * Lancement d'un tournoi ou d'une partie
 * @author afleury 
 */
public class Main {
    public static void main(String[] args) {
        // Mettez dans ce tableau les strats à tester
        // Vous pouvez ajouter la vôtre avec votre numéro de stratégie (cf StratX.java pour voir comment le calculer)
        int[] stratToTest = {17,17};
        // On instancie le jeu
        Jeu j = new Jeu();
        
        // On lance un tournoi avec 10 000 exécutions à chaque fois (entre chaque couple d'IA).
        j.runIA(stratToTest,100);
        
        ////// Humain vs IA avec le numéro d'IA en paramètre
        //j.runHvsIA(0);
        
        ////// Partie "normale"
        //j.run();
    }
}
