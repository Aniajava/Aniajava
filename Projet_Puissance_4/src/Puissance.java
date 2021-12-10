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

    public char[][] Grille(){
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
}
