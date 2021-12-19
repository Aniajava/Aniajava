import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Jeu extends Partie{

    public static int end = 0;

    // Initialise la partie : Nombre de Joueur / Mise en place de la Grille / ...
    public static Partie InitialisationPartie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Combien de joueur humain ?");
        int nbHumain;
        try {
            nbHumain = sc.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Veuillez saisir un nombre :");
            return InitialisationPartie();
        }
        String pseudo1 ;
        String pseudo2 ;
        switch (nbHumain) {
            case 0:
                return new Partie();
            case 1:
                sc = new Scanner(System.in);
                System.out.println("Saisissez votre pseudo : ");
                pseudo1 = sc.nextLine();
                return new Partie(pseudo1);
            case 2:
                sc = new Scanner(System.in);
                System.out.println("Saisissez pseudo Joueur1 : ");
                pseudo1 = sc.nextLine();
                sc = new Scanner(System.in);
                System.out.println("Saisissez pseudo Joueur2 : ");
                pseudo2 = sc.nextLine();
                return new Partie(pseudo1, pseudo2);
            default:
                System.out.println("Nombre de Joueurs saisi incorrect veuillez réessayer");
                return InitialisationPartie();
        }
    }

    // Initialisation avec l'ajout de la sauvegarde de la dernière partie joué sans connection
    public static Partie LancementPartie(){
        Partie p = null;
        File monFichier = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/Sauvegarde_jeu.txt");
        if(monFichier.exists()){
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez vous reprendre la partie précédente ? O/N");
            String r = sc.nextLine();
            if(r.equals("O")){
                p = Charge("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/Sauvegarde_jeu.txt");
            }else if(r.equals("N")){
                p = InitialisationPartie();
            }else{
                System.out.println("Veuillez réessayer :");
                p = LancementPartie();
            }
        }
        else{
            p = InitialisationPartie();
        }
        return p;
    }

    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isFile()) {
                System.out.println("File: " + file.getName());
            }
        }
    }

    // Retourne la partie chargé souhaité par le joueur
    public static Partie chargementPartie(Joueur_compte J){
        Partie p = null;
        if(J == null){
            return LancementPartie();
        }
        else{
            File[] files = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/"+J.IdJoueur).listFiles();
            if(files.length == 0){
                return LancementPartie();
            }
            else{
                showFiles(files);
                System.out.println("Quel partie voulez vous lancer ?");
                Scanner sc = new Scanner(System.in);
                String reponse = sc.nextLine();
                try {
                    p = Charge("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/" + J.IdJoueur + "/" + reponse);
                }catch (GameNotFoundException e){
                    System.out.println("Le fichier n'existe pas, veuillez reessayer !");
                    p = chargementPartie(J);
                }
            }
        }
        return p;
    }

// Ajout de la connection à un compte
   public static Partie LancementPartieBis(){
        System.out.println("Voulez vous vous connecter ? O/N");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        switch (reponse){
            case "N": return LancementPartie();
            case "O": Joueur_compte J = Joueur_compte.ConnexionCompte();return chargementPartie(J);
            default: System.out.println("Réponse invalide");return LancementPartieBis();
        }
   }


    public static int[] MaJPartie(Partie p,Joueur J){
        int[] coordonnées = null;
        Scanner sc = new Scanner(System.in);
        if(J.Type.equals("Humain")){
            System.out.print("Entrez la colonne souhaitée pour votre jeton : ");
            String str = sc.nextLine();
            try{
                int colonne = Integer.parseInt(str);
                coordonnées = p.AjoutJeton(colonne,J.Jeton);
                p.AfficherPartie();
                if(coordonnées[0] == -1){
                    System.out.println("La colonne choisie n'est pas valide");
                    coordonnées = MaJPartie(p,J);

                }
            }
            catch (NumberFormatException ex){
                if (str.equals("exit")){
                    end = 1;
                    Sauvegarde(p,"/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/Sauvegarde_jeu.txt");
                }
                else{
                    System.out.println("La colonne demandée est invalide, réessayez une autre :");
                    coordonnées = MaJPartie(p,J);
                }
            }
        }
        else {
            coordonnées = p.ajoutComputer(J);
            p.AfficherPartie();
        }
        return coordonnées;
    }

    public static void Sauvegarde(Partie p,String adresse){
        try{
            FileOutputStream fos = new FileOutputStream(adresse);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(p);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Partie Charge(String adresse) {
        try {
            File f = new File(adresse);
            FileInputStream fis = new FileInputStream(adresse);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Partie p = (Partie) ois.readObject();
            return p;
        } catch (Exception e) {
            throw new GameNotFoundException(adresse);
        }
    }

    public static void Fin(Partie p,Joueur J){
        if(p.victoire == 1 && end == 0) {
            System.out.println("Le Joueur : " + J.IdJoueur + " à gagné !");
        }
        else if(end == 0){
            System.out.println("Match nul");
        }else{
            System.out.println("A très vite !");
        }
    }

    public static void main(String args[]) {
        Partie p = null;
        p = LancementPartieBis();
        Joueur J = new Joueur(p.Joueur1.IdJoueur, p.Joueur1.Type, p.Joueur1.Jeton);
        int Indice = 1;
        int[] coordonnées = {11, 7};
        p.AfficherPartie();
        while (p.nbJeton < 42 && end == 0) {
            coordonnées = MaJPartie(p, J);
            if(end == 0) {
                if (p.DetectionVictoire(coordonnées[0], coordonnées[1], J)) {
                    break;
                }
                if (Indice == 1) {
                    J.setIdJoueur(p.Joueur2.IdJoueur);
                    J.setJeton(p.Joueur2.Jeton);
                    J.setType(p.Joueur2.Type);
                } else {
                    J.setIdJoueur(p.Joueur1.IdJoueur);
                    J.setJeton(p.Joueur1.Jeton);
                    J.setType(p.Joueur1.Type);
                }
                Indice *= -1;
            }
        }
        Fin(p,J);
    }
}
