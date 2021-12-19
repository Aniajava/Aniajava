public class GameNotFoundException extends RuntimeException {
    String adresse;
    public GameNotFoundException(String adresse){
        this.adresse = adresse;
    }
    public String toString(){
        return "Game not found : " + adresse;
    }
}
