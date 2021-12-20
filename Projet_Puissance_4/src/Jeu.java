public class Jeu extends Partie{

    static int connecte = 0;
    static JoueurCompte joueurCompte;

    public static void main(String args[]) {
        Partie p = lancementPartieBis();
        System.out.println(joueurCompte);
        Joueur J = deroulementPartie(p);
        finPartie(p,J);
    }
}
