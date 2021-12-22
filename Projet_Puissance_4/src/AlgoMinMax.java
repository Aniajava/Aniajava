public class AlgoMinMax {

    //Renvoie le max d'un tableau à une dimension
    public static int min(int[] t){
        int n = t.length;
        int m = t[0];
        for(int i = 1; i < n ; i++){
            if(t[i] < m){
                m = t[i];
            }
        }
        return m;
    }

    //Renvoie le min d'un tableau à une dimension
    public static int max(int[] t){
        int n = t.length;
        int M = t[0];
        for (int i = 0; i < n ; i++){
            if(t[i] > M){
                M = t[i];
            }
        }
        return M;
    }
}
