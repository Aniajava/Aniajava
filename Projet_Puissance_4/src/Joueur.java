public class Joueur {
    String IdJoueur;
    String Type;// HUMAIN ou ORDI;
    char Jeton;

    public Joueur(String pseudo,String Type,char Jeton){
        IdJoueur = pseudo;
        this.Type = Type;
        this.Jeton = Jeton;
    }
}
