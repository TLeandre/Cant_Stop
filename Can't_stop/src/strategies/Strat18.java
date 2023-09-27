package strategies;

import cantstop.Jeu;



/**
 * Votre Stratégie (copie de la Strat0 pour l'instant)
 *
 * Expliquez votre stratégie en une 20aine de lignes maximum.
 *
 * RENDU: Ce fichier, correctement nommé et rempli. Votre Stratégie aura un
 * numéro (pour être similaire à Strat0 qui sera votre position dans l'alphabet
 * de la promo + 14. (attention calcul compliqué) Le premier aura donc pour
 * numéro 15 et le dernier 334 Pour "préparer" votre stratégie, sur le fichier
 * StratX.java, vous cliquez sur Bouton Droit, Refactor, Rename et vous nommez
 * bien votre stratégie genre Strat245.java (pour le 231e).
 *
 * @author VOTRE NOM
 */
public class Strat18 implements Strategie {
    

    /**
     * @param j le jeu
     * @return toujours le 1er choix
     */
     int x;
     int c=0;
     int y;
     double s=0;
     double somme;
//classe qui renvoit le ratio entre l'avancement sur la colonne et son nombre total de case

    public int choix(Jeu j) {
        
        

        double tableau[][]=new double[11][2];
        tableau[0][0] = 2;
        tableau[1][0] = 3;
        tableau[2][0] = 4;
        tableau[3][0] = 5;
        tableau[4][0] = 6;
        tableau[5][0] = 7;
        tableau[6][0] = 8;
        tableau[7][0] = 9;
        tableau[8][0] = 10;
        tableau[9][0] = 11;
        tableau[10][0] =12;

        tableau[0][1] = 0.02;
        tableau[1][1] = 0.0555;
        tableau[2][1] = 0.0733;
        tableau[3][1] = 0.1011;
        tableau[4][1] =0.1388;
        tableau[5][1] =0.15;
        tableau[6][1] =0.1388;
        tableau[7][1] =0.1011;
        tableau[8][1] =0.0733;
        tableau[9][1] =0.0555;
        tableau[10][1] =0.02;
        
        
       
        //int sommeavancement=0;
        // for (int i=0;i<j.avancementJoueurEnCours().length;i++){
        //     sommeavancement=sommeavancement+j.avancementJoueurEnCours()[i];
        // }
        //System.out.println("somme "+sommeavancement);
        // 1er lancer et donc 1er choix qui prend le double sinon la proba la plus haute
        switch (j.getBonzesRestants()) {
            
            case 3:
                //System.out.println("nombre de choix "+j.getNbChoix()+" pour 3 bonzes");
                for (int i = 0; i<j.getNbChoix(); i++) {
                    x = j.getLesChoix()[i][0];
                    y= j.getLesChoix()[i][1];
                    
                    //System.out.println(x+","+y); 
                    if(y!=0){
                        double tabx=tableau[x-2][1];
                        double taby=tableau[y-2][1];
                        //System.out.println("dkjqskdjhqkjds" + tableau[x-2][1]);
                        //System.out.println(tabx+","+taby);
                        if(x==y){
                        c=i;
                        }else if((tabx+taby)>s){
                            s=tabx+taby;
                            c=i;
                        } 
                        c=0;
                        
                        //System.out.println(s);
                    }else{
                        double ratiox=j.avancementJoueurEnCours()[x-2]/j.getMaximum()[x-2];
                        double sc=0;
                        if(ratiox>sc){
                            sc=ratiox;
                            c=i;
                        }
                        
                        
                        
                    }
                    c=0;
                }
//i<j.getNbChoix(
            break;
            case 2:
                //System.out.println("nombre de choix "+j.getNbChoix()+" pour 2 bonzes");
                for (int i=0; i<j.getNbChoix();i++){//  Le principe est de faire des ratios des chiffres selon l'avancement sur leurs case et prendre la colonne où on est le plus proche de la fin
                    //on prend le choix de la 2ème paire de dés
//Si nous n'avons qu'un chiffre comme choix (pas 2) on fait le ratio de ce chiffre entre avancement initial et case max de ce chiffre
                    
                    
                    
                    if(y==0){
                      double ratiox=j.avancementJoueurEnCours()[x-2]/j.getMaximum()[x-2];
                        for (int[] bonze : j.getBonzes()) {
                            double sc=0;
                            if (bonze[0] == x) {
                                if(ratiox>sc){
                                    sc=ratiox;
                                    c=i;
                                }
                            }
                            return c;
                        }
      
                            if(ratiox>s){
                                s=ratiox;
                                c=i;
                            }
                           
                    } else {//Si on a 2 chiffre on prend la somme des ratios des 2
                        double ratiox= j.avancementJoueurEnCours()[x-2]/j.getMaximum()[x-2];
                        double ratioy=j.avancementJoueurEnCours()[y-2]/j.getMaximum()[y-2];
                        double sommeratio=ratiox+ratioy;
                        double sc=0;
                        //System.out.println("le ratio est "+ratiox+","+ratioy);
                        // On regarde si parmis nos choix on a déjà des bonzes dessus
                        
                       
                        if(sommeratio>sc){
                            sc=sommeratio;
                            c=i;
                        }
                        c=0;
                        
                    }
                }  
            break;
            case 1:
                for (int i=0; i<j.getNbChoix();i++){//  Le principe est de faire des ratios des chiffres selon l'avancement sur leurs case et prendre la colonne où on est le plus proche de la fin
                    x = j.getLesChoix()[i][0];//on prend le choix de la première paire de dés
                    // y= j.getLesChoix()[i][1];//on prend le choix de la 2ème paire de dés
                    //Si nous n'avons qu'un chiffre comme choix (pas 2) on fait le ratio de ce chiffre entre avancement initial et case max de ce chiffre
                    double ratiox=(double)j.avancementJoueurEnCours()[x-2]/j.getMaximum()[x-2];
                    if(j.avancementJoueurEnCours()[x-2]!=0 && x==y){
                         
                        return c=i;    
                    }
                    for (int[] bonze : j.getBonzes()) {
                        double sc=0;
                        if (bonze[0] == x) {
                            if(ratiox>sc){
                                sc=ratiox;
                                c=i;
                            }
                        }
                            return c;
                    }
                   
                    //System.out.println(x); 
                    if(s>=ratiox){
                        c=i;
                    }else {
                        s=ratiox;
                        c=0;  
                    }
                    c=0;
                }  
                
            break;
            default:
                //System.out.println("nombre de choix "+j.getNbChoix()+" pour 1 bonze");
                for (int i=0; i<j.getNbChoix();i++){//  Le principe est de faire des ratios des chiffres selon l'avancement sur leurs case et prendre la colonne où on est le plus proche de la fin
                    x = j.getLesChoix()[i][0];//on prend le choix de la première paire de dés
                    y= j.getLesChoix()[i][1];//on prend le choix de la 2ème paire de dés
                    
//Si nous n'avons qu'un chiffre comme choix (pas 2) on fait le ratio de ce chiffre entre avancement initial et case max de ce chiffre
                  
                    if(y==0){
                      double ratiox= j.avancementJoueurEnCours()[x-2]/j.getMaximum()[x-2];
      
                        for (int z=0;z<j.getBonzes().length;z++) {
                            double sc=0;
                            if ( j.getBonzes()[z][0]== x) {
                                if(ratiox>sc){
                                    sc=ratiox;
                                    c=z;
                                }
                            }
                        }
                           return c;
                    }else {
                        double ratiox= j.avancementJoueurEnCours()[x-2]/j.getMaximum()[x-2];
                        double ratioy=j.avancementJoueurEnCours()[y-2]/j.getMaximum()[y-2];
                        double sommeratio=ratiox+ratioy;
                        
                        for (int[] bonze : j.getBonzes()) {
                            double sc=0;
                            if (bonze[0] == x && bonze[0] == y) {
                                return i;
                            } else if (bonze[0] == x || bonze[0] == y) {
                                if(sommeratio>sc){
                                    sc=sommeratio;
                                    c=i;
                                }
                                c=0;
                            }
                        } 
                    }
                }   
            break;
            
        }
              
        //System.out.println("c= "+c);   
       
           return c;
    }
    
    
      
/**
 * @param j le jeu
 * @return toujours vrai (pour s'arrêter)
 */
    public boolean stop(Jeu j) {
        //int y=j.getLesChoix()[c][1];
        //int z=j.getLesChoix()[c][0];
      
        
        //System.out.println("somme= "+ somme+ "nmb de coup= "+ j.getNbCoup());
        
        //System.out.println("z= "+z+" y= "+y);
        //System.out.println("nombre de coups"+j.getNbCoup());
        
        for(int i=0;i<11;i++){
            if(j.getBloque()[i]==true){
                return true;
            }
        }
        
        if(j.getNbCoup()>8){
         
           return true;
        }else if(j.getBonzesRestants()>0){
         return false;
        }       
        
        
      return false;
    }

   /**
    * @return vos noms
    */
    public String getName() {
       return " Jonathan ALBECQ ";
   }
}