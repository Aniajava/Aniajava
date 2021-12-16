import java.io.Serializable;
import java.util.Date;

public class Partie extends Puissance implements Serializable {
    Date IdPartie;
    Joueur Joueur1;
    Joueur Joueur2;
    char[][] Grille;
    int nbJeton;
    int victoire;

    public Partie(String pseudo1,String Type1,String pseudo2, String Type2){
        IdPartie = new Date();
        Joueur1 = new Joueur(pseudo1,Type1,'X');
        Joueur2 = new Joueur(pseudo2,Type2,'O');
        Grille = super.Grille();
        nbJeton = 0;
        victoire = 0;
    }
    public Partie(String pseudo1,String pseudo2){
        this(pseudo1,"Humain",pseudo2,"Humain");
    }
    public Partie(String pseudo1){
        this(pseudo1,"Humain","Computer","Ordinateur");
    }
    public Partie(){
        this("Computer1","Ordinateur","Computer2","Ordinateur");
    }

    public int[] AjoutJeton(int colonne,char Jeton) {
        int IdColonne = 2 * colonne + 1;
        int i = 11;
        try {
            while (Grille[i][IdColonne] != ' ' && i > 0) {
                i -= 2;
            }
            Grille[i][IdColonne] = Jeton;
        }catch(ArrayIndexOutOfBoundsException e){
            i =-1;
        }
        if (i != -1){
            nbJeton += 1;
        }
        return new int[]{i, IdColonne};
    }
//Afficher Partie
    public void AfficherPartie(){
        int lignes = 2*hauteur +2;
        int colonnes = 2*largeur +1;
        for (int i = 0;i<lignes;i++){
            if(i != 4 && i != 5){
                System.out.println(Grille[i]);
            }
            else {
                if(i == 4){
                    System.out.print(Grille[i]);
                    System.out.println("   " + Joueur1.IdJoueur + " : " + Joueur1.Jeton);
                }
                else{
                    System.out.print(Grille[i]);
                    System.out.println("   " + Joueur2.IdJoueur + " : " + Joueur2.Jeton);
                }
            }
        }
    }

    public boolean DetectionVictoire(int IdLignes,int IdColonnes,Joueur J){
        int compteurLignes = 0;
        int compteurColonnes = 0;
        int compteurDiagGauche = 0;
        int compteurDiagDroite = 0;
        //Detection Lignes
        for(int i= 0;i<20;i=i+2){
            if (IdColonnes-6+i >0 && IdColonnes-6+i < 14){
                if (Grille[IdLignes][IdColonnes-6+i] == J.Jeton){
                    compteurLignes += 1;
                }
                else{
                    compteurLignes = 0;
                }
                if (compteurLignes == 4){
                    victoire =1;
                    return true;
                }
            }
            if (IdLignes+6-i >1 && IdLignes+6-i<13) {
                if (Grille[IdLignes +6-i][IdColonnes] == J.Jeton) {
                    compteurColonnes += 1;
                } else {
                    compteurColonnes = 0;
                }
                if (compteurColonnes == 4) {
                    victoire = 1;
                    return true;
                }
            }
            if(IdColonnes+6-i > 0 && IdLignes-6+i > 0 && IdColonnes+6-i<14 && IdLignes-6+i<13){
                if (Grille[IdLignes -6+i][IdColonnes+6-i]== J.Jeton) {
                    compteurDiagDroite += 1;
                } else {
                    compteurDiagDroite = 0;
                }
                if (compteurDiagDroite == 4) {
                    victoire =1;
                    return true;
                }
            }
            if(IdColonnes-6+i > 0 && IdLignes-6+i > 0 && IdColonnes-6+i<14 && IdLignes-6+i <13){
                if (Grille[IdLignes -6+i][IdColonnes-6+i] == J.Jeton) {
                    compteurDiagGauche += 1;
                } else {
                    compteurDiagGauche = 0;
                }
                if (compteurDiagGauche == 4) {
                    victoire =1;
                    return true;
                }
            }
        }
        return false;
    }
    public int[] ajoutComputer(Joueur J){
        int colonne = (int)(Math.random()*7);
        int[] coordonnées = AjoutJeton(colonne,J.Jeton);
        if(coordonnées[0] == -1){
            ajoutComputer(J);
        }
        return coordonnées;
    }
}
