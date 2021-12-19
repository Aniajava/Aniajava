import java.io.File;
import java.io.*;
import java.util.Date;
public class Test {
    public static void  main(String args[]) {
        Joueur_compte J = Joueur_compte.ChargeCompte("/Users/thibautmaurel/Documents/Projet/Sauvegarde_joueur/aa.txt");
        System.out.println(J);
        Joueur_compte joueur = Joueur_compte.CreationCompte();
        joueur = Joueur_compte.ConnexionCompte();
        System.out.println(joueur);

    }
}
