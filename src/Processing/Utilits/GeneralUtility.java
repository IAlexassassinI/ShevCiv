package Processing.Utilits;

public class GeneralUtility {

    static public int Round(double toRound){
        int IntPart = (int)toRound;
        if(toRound - IntPart < 0.5D){
            return IntPart;
        }
        else{
            return IntPart+1;
        }
    }


}
