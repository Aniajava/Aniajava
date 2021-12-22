import java.io.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Partie extends Puissance implements Serializable {
    Date IdPartie;
    Joueur Joueur1;
    Joueur Joueur2;
    char[][] Grille;
    int nbJeton;
    int victoire;
    int end;

    public Partie(String pseudo1,String Type1,String pseudo2, String Type2){
        IdPartie = new Date();
        Joueur1 = new Joueur(pseudo1,Type1,'X');
        Joueur2 = new Joueur(pseudo2,Type2,'O');
        Grille = super.grille();
        victoire = 0;
        end = 0;
    }
    public Partie(String pseudo1,String pseudo2){
        this(pseudo1,"Humain",pseudo2,"Humain");
    }
    public Partie(String pseudo1){
        this(pseudo1,"Humain","Computer","Ordinateur");
    }
    public Partie(){
        this("Computer1","Ordinateur","Computer2","Ordinateur");
    }

    public void setVictoire(int victoire){
        this.victoire = victoire;
    }

    public void ajoutGrille(int ligne,int colonne,char c){
        Grille[ligne][colonne] = c;
    }

    public void incrementeJeton(){
        nbJeton += 1;
    }

    //Afficher Partie
    public void afficherPartie(){
        int lignes = 2*hauteur +2;
        int colonnes = 2*largeur +1;
        for (int i = 0;i<lignes;i++){
            if(i != 4 && i != 5){
                System.out.println(Grille[i]);
            }
            else {
                if(i == 4){
                    System.out.print(Grille[i]);
                    System.out.println("   " + Joueur1.IdJoueur + " : " + Joueur1.Jeton);
                }
                else{
                    System.out.print(Grille[i]);
                    System.out.println("   " + Joueur2.IdJoueur + " : " + Joueur2.Jeton);
                }
            }
        }
    }

    // Initialise la partie : Nombre de Joueur / Mise en place de la Grille / ...
    public static Partie initialisationPartie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Combien de joueur humain ?");
        int nbHumain;
        try {
            nbHumain = sc.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Veuillez saisir un nombre :");
            return initialisationPartie();
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
                return initialisationPartie();
        }
    }

    // Initialisation avec l'ajout de la sauvegarde de la dernière partie joué sans connection
    public static Partie lancementPartie(){
        Partie p = null;
        File monFichier = new File("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/Sauvegarde_jeu.txt");
        if(monFichier.exists()){
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez vous reprendre la partie précédente ? O/N");
            String r = sc.nextLine();
            if(r.equals("O")){
                p = Jeu.charge("/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/Sauvegarde_jeu.txt");
            }else if(r.equals("N")){
                p = initialisationPartie();
            }else{
                System.out.println("Veuillez réessayer :");
                p = lancementPartie();
            }
        }
        else{
            p = initialisationPartie();
        }
        return p;
    }

    //Sauvegarde la Partie : p à l'adresse : adresse
    public static void sauvegarde(Partie p, String adresse){
        try{
            FileOutputStream fos = new FileOutputStream(adresse);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(p);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Charge la partie à l'adresse : adresse;
    public static Partie charge(String adresse) {
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

    //Affiche le nom des fichier de la liste de fichier
    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isFile()) {
                System.out.println("File: " + file.getName());
            }
        }
    }

    // Retourne la partie chargé souhaité par le joueur
    public static Partie chargementPartie(JoueurCompte J){
        Partie p = null;
        if(J == null){
            return lancementPartie();
        }
        else{
            File[] files = new File("./Sauvegarde_partie/"+J.IdJoueur).listFiles();
            if(files.length == 0){
                return initialisationPartie();
            }
            else{
                showFiles(files);
                System.out.println("Quel partie voulez vous lancer ?");
                Scanner sc = new Scanner(System.in);
                String reponse = sc.nextLine();
                try {
                    p = charge("./Sauvegarde_partie/" + J.IdJoueur + "/" + reponse);
                }catch (GameNotFoundException e){
                    System.out.println("Le fichier n'existe pas, veuillez reessayer !");
                    p = chargementPartie(J);
                }
            }
        }
        return p;
    }

    //Affiche le gagnant ou l'egalite ou a tres vite si la partie et sauvegardée mais pas finie
    public static void finPartie(Partie p, Joueur J){
        if(p.victoire == 1 && p.end == 0) {
            System.out.println("Le Joueur : " + J.IdJoueur + " à gagné !");
        }
        else if(p.end == 0){
            System.out.println("Match nul");
        }else{
            System.out.println("A très vite !");
        }
    }

    // Ajout de la connection à un compte
    public static Partie lancementPartieBis(){
        System.out.println("Voulez vous vous connecter ou créer un compte? O/N");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        switch (reponse){
            case "N": return initialisationPartie();
            case "O":
                Jeu.joueurCompte = JoueurCompte.CreationConnexionCompte();
                return chargementPartie(Jeu.joueurCompte);
            default: System.out.println("Réponse invalide");return lancementPartieBis();
        }
    }

    //Met a jour la partie : demande la colonne à joué et ajoute le jeton ou arret de la partie ou ...
    public static int[] maJPartie(Partie p, Joueur J){
        int[] coordonnées = null;
        Scanner sc = new Scanner(System.in);
        if(J.Type.equals("Humain")){
            System.out.print("Entrez la colonne souhaitée pour votre jeton ou Exit pour quitter la partie : ");
            String str = sc.nextLine();
            try{
                int colonne = Integer.parseInt(str);
                coordonnées = p.ajoutJeton(colonne,J.Jeton,p);
                p.afficherPartie();
                if(coordonnées[0] == -1){
                    System.out.println("La colonne choisie n'est pas valide");
                    coordonnées = maJPartie(p,J);

                }
            }
            catch (NumberFormatException ex){
                if (str.equals("Exit")){
                    p.end = 1;
                    sauvegarde(p,"/Users/thibautmaurel/Documents/Projet/Sauvegarde_partie/Sauvegarde_jeu.txt");
                }
                else{
                    System.out.println("La colonne demandée est invalide, réessayez une autre :");
                    coordonnées = maJPartie(p,J);
                }
            }
        }
        else {
            coordonnées = p.ajoutComputer(J,p);
            p.afficherPartie();
        }
        return coordonnées;
    }

    //Sauvegarde la partie dans le dossier du Joueur J
    public static void sauvegardePartie(JoueurCompte J, Partie p){
        System.out.println("Quelle nom voulez vous donnez à cette Partie ?");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        String adresse = J.adresse + "/" + reponse + ".txt";
        sauvegarde(p,adresse);
    }

    //Propose au Joueur s'il veut sauvegarder
    public static void sauvegardePartieJoueur(JoueurCompte J, Partie p){
        System.out.println("Voulez vous sauvegarder la partie ? O/N");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        switch (reponse){
            case "O": sauvegardePartie(J,p);
            case "N": return;
            default: System.out.println("Reponse invalide");
                sauvegardePartieJoueur(J,p);
        }
    }

    //Décris le déroulement d'une partie
    public static Joueur deroulementPartie(Partie p){
        Joueur J = new Joueur(p.Joueur1.IdJoueur, p.Joueur1.Type, p.Joueur1.Jeton);
        int Indice = 1;
        int[] coordonnées = {11, 7};
        p.afficherPartie();
        while (p.nbJeton < 42 && p.end == 0) {
            coordonnées = maJPartie(p, J);
            if(p.end == 0) {
                if (detectionVictoire(coordonnées[0], coordonnées[1], J,p)){
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
        if (p.end == 1 && Jeu.connecte == 1){
            sauvegardePartieJoueur(Jeu.joueurCompte,p);
        }
        return J;
    }
}
