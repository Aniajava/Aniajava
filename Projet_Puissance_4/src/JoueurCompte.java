import java.io.*;
import java.util.Scanner;

public class JoueurCompte extends Joueur implements Serializable {
    private static final long serialVersionUID = 6368595323281647196L;
    String MotDePasse;
    File Dossier;
    String adresse;

    public JoueurCompte(String pseudo,char Jeton,String MotDePasse){
        super(pseudo,Jeton);
        this.MotDePasse = MotDePasse;
        File f = new File("./Sauvegarde_partie");
        if (!f.isDirectory()) {
            f.mkdir();
        }
        adresse = "./Sauvegarde_partie/" + pseudo;
    }

    public static JoueurCompte ChargeCompte(String adresse){
        try {
            FileInputStream fis = new FileInputStream(adresse);
            ObjectInputStream ois = new ObjectInputStream(fis);
            JoueurCompte J = (JoueurCompte) ois.readObject();
            return J;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void SauvegardeCompte(JoueurCompte J, String adresse){
        try{
            FileOutputStream fos = new FileOutputStream(adresse);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(J);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JoueurCompte CreationCompte(){
        JoueurCompte J = null;
        System.out.println("Choisissez votre pseudo : ");
        Scanner sc = new Scanner(System.in);
        // Récupère l'identifiant
        String pseudo = sc.nextLine();
        System.out.println("Choisissez votre mot de passe : ");
        sc = new Scanner(System.in);
        // Récupère le mot de passe
        String MotDePasse = sc.nextLine();
        // Création du compte s'il n'existe pas déjà
        File test = new File("./Sauvegarde_joueur");
        if(!test.isDirectory()){
            test.mkdir();
        }
        test = new File("./Sauvegarde_joueur/"+pseudo+".txt");
        if(!test.exists()) {
            J = new JoueurCompte(pseudo, 'X', MotDePasse);
            SauvegardeCompte(J, "./Sauvegarde_joueur/" + pseudo + ".txt");
            File f = new File("./Sauvegarde_partie/" + pseudo);
            f.mkdir();
            System.out.println("Votre compte a été créé !");
            Jeu.connecte = 1;
        }
        else{
            System.out.println("Ce nom de compte est déjà pris, veuillez en choisir un autre : ");
            J = CreationCompte();
        }
        return J;
    }
    // On a créé Creation Compte bis pour la méthode Connexion Compte pour pouvoir relancer la méthode si la réponse est incorect
    public static JoueurCompte CreationComptebis() {
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
    public static JoueurCompte ConnexionCompte(){
        System.out.println("Quel est votre pseudo ?");
        Scanner sc = new Scanner(System.in);
        // Récupère l'identifiant
        String pseudo = sc.nextLine();
        System.out.println("Quel est votre mot de passe ?");
        sc = new Scanner(System.in);
        // Récupère le mot de passe
        String MotDePasse = sc.nextLine();
        JoueurCompte J;
        File F = new File("./Sauvegarde_partie/"+pseudo);
        // Chaque Joueur à un dossier créé lors de la création de son compte , ici File f est l'adresse
        // Ou est censé se trouver se dossier, ensuite F.exist() vérifie qu'il y ait bien un dossier à cette adresse
        // donc qu'il y ait bien un compte de créer
        if(F.exists() && F.isDirectory()){
            J = ChargeCompte("./Sauvegarde_joueur/"+pseudo+".txt");
            if(J.MotDePasse.equals(MotDePasse)){
                System.out.println("Vous êtes connectés !");
                Jeu.connecte = 1;
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

    public static JoueurCompte CreationConnexionCompte(){
        System.out.println("Avez vous déjà un compte ? O/N");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        switch (reponse){
            case "O": return JoueurCompte.ConnexionCompte();
            case "N": return JoueurCompte.CreationCompte();
            default: System.out.println("Réponse invalide :");return CreationConnexionCompte();
        }
    }
    public String toString(){
        return adresse;
    }
}
