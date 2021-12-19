import java.io.*;
import java.net.ConnectException;
import java.util.Scanner;

public class Joueur_compte extends Joueur implements Serializable {
    private static final long serialVersionUID = 6368595323281647196L;
    String MotDePasse;
    File Dossier;

    public Joueur_compte(String pseudo,char Jeton,String MotDePasse){
        super(pseudo,Jeton);
        this.MotDePasse = MotDePasse;
        File dossier = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/"+pseudo);
    }

    public static Joueur_compte ChargeCompte(String adresse){
        try {
            FileInputStream fis = new FileInputStream(adresse);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Joueur_compte J = (Joueur_compte) ois.readObject();
            return J;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void SauvegardeCompte(Joueur_compte J,String adresse){
        try{
            FileOutputStream fos = new FileOutputStream(adresse);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(J);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Joueur_compte CreationCompte(){
        System.out.println("Choisissez votre pseudo : ");
        Scanner sc = new Scanner(System.in);
        // Récupère l'identifiant
        String pseudo = sc.nextLine();
        System.out.println("Choisissez votre mot de passe : ");
        sc = new Scanner(System.in);
        // Récupère le mot de passe
        String MotDePasse = sc.nextLine();
        // Création du compte
        Joueur_compte J = new Joueur_compte(pseudo,'X',MotDePasse);
        SauvegardeCompte(J,"/Users/thibautmaurel/Documents/Projet/Sauvegarde_joueur/"+pseudo+".txt");
        File f = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/"+pseudo);
        f.mkdir();
        System.out.println("Votre compte a été créé !");
        return J;
    }

    // On a créé Creation Compte bis pour la méthode Connexion Compte pour pouvoir relancer la méthode si la réponse est incorect
    public static Joueur_compte CreationComptebis() {
        Scanner sc;
        System.out.println("Le Compte n'existe pas, voulez vous en créer un ? O/N");
        sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        if (reponse.equals("O")) {
            return CreationCompte();
        } else if (reponse.equals("N")) {
            return null;
        } else {
            System.out.println("Réponse invalide");
            return CreationComptebis();
        }
    }

    public static Joueur_compte ConnexionCompte(){
        System.out.println("Quel est votre pseudo ?");
        Scanner sc = new Scanner(System.in);
        // Récupère l'identifiant
        String pseudo = sc.nextLine();
        System.out.println("Quel est votre mot de passe ?");
        sc = new Scanner(System.in);
        // Récupère le mot de passe
        String MotDePasse = sc.nextLine();
        Joueur_compte J;
        File F = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/"+pseudo);
        // Chaque Joueur à un dossier créé lors de la création de son compte , ici File f est l'adresse
        // Ou est censé se trouver se dossier, ensuite F.exist() vérifie qu'il y ait bien un dossier à cette adresse
        // donc qu'il y ait bien un compte de créer
        if(F.exists() && F.isDirectory()){
            J = ChargeCompte("/Users/thibautmaurel/Documents/Projet/Sauvegarde_joueur/"+pseudo+".txt");
            if(J.MotDePasse.equals(MotDePasse)){
                System.out.println("Vous êtes connectés !");
                return J;
            }
            else{
                System.out.println("Votre indentifiant ou votre mot de passe saisi est incorrect");
                return ConnexionCompte();
            }
        }
        else{
                return CreationComptebis();
        }
    }

    public String toString(){
        return IdJoueur + MotDePasse;
    }
}
