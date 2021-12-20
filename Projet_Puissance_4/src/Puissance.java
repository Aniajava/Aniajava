public abstract class Puissance {
    public int largeur;
    public int hauteur;
    private char Jeton1;
    private char Jeton2;
    private int NbMaxJeton;

    public Puissance(){
        this.largeur =7;
        this.hauteur =6;
        this.Jeton1 = 'R';
        this.Jeton2 = 'J';
        NbMaxJeton = 42;
    }

    //Crée la grille du puissance4
    public char[][] grille(){
        int lignes = 2*hauteur +2;
        int colonnes = 2*largeur +1;
        char[][] grille = new char[lignes][colonnes];
        for (int i = 0;i<lignes;i++){
            for(int j=0;j<colonnes;j++){
                if(i%2 == 0){
                    if(j%2 == 0) {
                        grille[i][j] = '+';
                    }
                    else{
                        grille[i][j] = '_';
                    }
                }
                else{
                    if(j%2 == 0){
                        grille[i][j] = '|';
                    }
                    else{
                        grille[i][j] = ' ';
                    }
                }
            }
        }
        for(int i = 0;i<colonnes;i++){
            if(i%2 == 0){
                grille[lignes-1][i] = ' ';
            }
            else{
                int n = (i-1)/2;
                grille[lignes-1][i] = Character.forDigit(n,10);
            }
        }
        return grille;
    }

    //Renvoie true s'il y a 4 Jetons d'affilé
    public static boolean detectionVictoire(int IdLignes, int IdColonnes, Joueur J, Partie p){
        int compteurLignes = 0;
        int compteurColonnes = 0;
        int compteurDiagGauche = 0;
        int compteurDiagDroite = 0;
        //Detection Lignes
        for(int i= 0;i<20;i=i+2){
            if (IdColonnes-6+i >0 && IdColonnes-6+i < 14){
                if (p.Grille[IdLignes][IdColonnes-6+i] == J.Jeton){
                    compteurLignes += 1;
                }
                else{
                    compteurLignes = 0;
                }
                if (compteurLignes == 4){
                    p.setVictoire(1);
                    return true;
                }
            }
            if (IdLignes+6-i >1 && IdLignes+6-i<13) {
                if (p.Grille[IdLignes +6-i][IdColonnes] == J.Jeton) {
                    compteurColonnes += 1;
                } else {
                    compteurColonnes = 0;
                }
                if (compteurColonnes == 4) {
                    p.setVictoire(1);
                    return true;
                }
            }
            if(IdColonnes+6-i > 0 && IdLignes-6+i > 0 && IdColonnes+6-i<14 && IdLignes-6+i<13){
                if (p.Grille[IdLignes -6+i][IdColonnes+6-i]== J.Jeton) {
                    compteurDiagDroite += 1;
                } else {
                    compteurDiagDroite = 0;
                }
                if (compteurDiagDroite == 4) {
                    p.setVictoire(1);
                    return true;
                }
            }
            if(IdColonnes-6+i > 0 && IdLignes-6+i > 0 && IdColonnes-6+i<14 && IdLignes-6+i <13){
                if (p.Grille[IdLignes -6+i][IdColonnes-6+i] == J.Jeton) {
                    compteurDiagGauche += 1;
                } else {
                    compteurDiagGauche = 0;
                }
                if (compteurDiagGauche == 4) {
                    p.setVictoire(1);
                    return true;
                }
            }
        }
        return false;
    }

    //Ajoute un Jeton a la colonne souhaité
    public int[] ajoutJeton(int colonne, char Jeton, Partie p) {
        int IdColonne = 2 * colonne + 1;
        int i = 11;
        try {
            while (p.Grille[i][IdColonne] != ' ' && i > 0) {
                i -= 2;
            }
            p.ajoutGrille(i,IdColonne,Jeton);
        }catch(ArrayIndexOutOfBoundsException e){
            i =-1;
        }
        if (i != -1){
            p.incrementeJeton();
        }
        return new int[]{i, IdColonne};
    }

    //Ajoute de manière aléatoire un jeton à la grille
    public int[] ajoutComputer(Joueur J,Partie p){
        int colonne = (int)(Math.random()*7);
        int[] coordonnées = ajoutJeton(colonne,J.Jeton,p);
        if(coordonnées[0] == -1){
            ajoutComputer(J,p);
        }
        return coordonnées;
    }
}
