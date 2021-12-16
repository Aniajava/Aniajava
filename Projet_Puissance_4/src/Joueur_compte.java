import java.io.File;

public class Joueur_compte extends Joueur{
    String MotDePasse;
    File Dossier;

    public Joueur_compte(String pseudo,char Jeton,String MotDePasse){
        super(pseudo,Jeton);
        this.MotDePasse = MotDePasse;
        File dossier = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/"+pseudo);
    }
}
