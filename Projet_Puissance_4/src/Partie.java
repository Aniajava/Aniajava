import java.util.Date;

public class Partie extends Puissance{
    Date IdPartie;
    Joueur Joueur1;
    Joueur Joueur2;
    char[][] Grille;

    public Partie(String pseudo1,String Type1,String pseudo2, String Type2){
        IdPartie = new Date();
        Joueur1 = new Joueur(pseudo1,Type1,'X');
        Joueur2 = new Joueur(pseudo2,Type2,'O');
        Grille = super.Grille();
    }
    public Partie(String pseudo1,String pseudo2){
        this(pseudo1,"Humain",pseudo2,"Humain");
    }
    public Partie(String pseudo1){
        this(pseudo1,"Humain","Computer","Ordinateur");
    }

    public void AjoutJeton(int colonne,char Jeton) {
        int IdColonne = 2 * colonne + 1;
        int i = 11;
        while (Grille[i][IdColonne] != ' ' && i > 0) {
            i -= 2;
        }
        if (i > 0) {
            Grille[i][IdColonne] = Jeton;
        }
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
        for(int i= 0;i<7;i++){
            if (IdColonnes-3+i >0 && IdColonnes-3+i < 14){
                if (Grille[IdLignes][IdColonnes-3+i] == J.Jeton){
                    compteurLignes += 1;
                }
                else{
                    compteurLignes = 0;
                }
                if (compteurLignes == 4){
                    return true;
                }
            }
            if (IdLignes-3+i >1 && IdLignes-3+i<13) {
                if (Grille[IdLignes -3+i][IdColonnes] == J.Jeton) {
                    compteurColonnes += 1;
                } else {
                    compteurColonnes = 0;
                }
                if (compteurColonnes == 4) {
                    return true;
                }
            }

        }
        return false;
    }
}
