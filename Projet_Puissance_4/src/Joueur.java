import java.io.Serializable;

public class Joueur extends Puissance implements Serializable {
    String IdJoueur;
    String Type;// Humain ou Ordinateur;
    char Jeton;

    public Joueur(String pseudo,String Type,char Jeton){
        IdJoueur = pseudo;
        this.Type = Type;
        this.Jeton = Jeton;
    }
    public Joueur(String pseudo,char Jeton){
        IdJoueur = pseudo;
        Type = "Humain";
        this.Jeton = Jeton;
    }
    public String getType(){
        return Type;
    }
    public char getJeton(){
        return Jeton;
    }
    public void setType(String s){
        Type = s;
    }
    public void setJeton(char c){
        Jeton = c;
    }
    public void setIdJoueur(String IdJoueur){
        this.IdJoueur = IdJoueur;
    }
}
