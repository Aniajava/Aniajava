import java.io.File;
import java.util.Date;
public class Test {
    public static void  main(String args[]) {
        File f = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/aa");

        Joueur_compte joueur = Joueur_compte.CreationCompte();
        joueur = Joueur_compte.ConnexionCompte();

    }
}
