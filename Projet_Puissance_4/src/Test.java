import java.util.Date;
public class Test {

    public static void  main(String args[]) {
        Partie p = new Partie("JeanJacques");
        p.AfficherPartie();
        p.AjoutJeton(3,'X');
        p.AfficherPartie();
    }
}
