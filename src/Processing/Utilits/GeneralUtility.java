package Processing.Utilits;

import java.io.Serializable;

public class GeneralUtility implements Serializable {
    static final long serialVersionUID = 29L;
    static public int Round(double toRound){

        int IntPart = (int)toRound;
        if(toRound - IntPart < 0.5D){
            return IntPart;
        }
        else{
            return IntPart+1;
        }
    }

    static public void initAllProcessingNeeds(){
        WhereCanBe.initTypes();
    }


}
