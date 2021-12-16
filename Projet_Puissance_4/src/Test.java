import java.util.Date;
public class Test {

    public static void  main(String args[]) {
        Partie p = new Partie("JeanJacques");
        p.AfficherPartie();
        p.AjoutJeton(3,'X');
        p.AfficherPartie();
        if(p.DetectionVictoire(2,1,p.Joueur1)){
            System.out.print("C'est gagné");
        }
        p.AjoutJeton(3,'X');
        p.AjoutJeton(3,'X');
        p.AjoutJeton(3,'X');
        p.AfficherPartie();
        if(p.DetectionVictoire(11,7,p.Joueur1)){
            System.out.println("C'est gagné");
        }
        else{
            System.out.println("C'est perdu");
        }
        System.out.println(p.Grille[11][7]);
        int y = (int)6.9;
        System.out.println(y);
        Joueur J = new Joueur("a","HUMAIN",'*');
        Joueur J2 = J;
        System.out.println(J2 == J);
    }
}
