package Processing.Utilits.Wrapers;

import java.io.Serializable;

public class ThreeTTT<AAA, TTT, VVV> implements Serializable {
    static final long serialVersionUID = 26L;
    public AAA first;
    public TTT second;
    public VVV third;

    public ThreeTTT(AAA first, TTT second, VVV third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    ThreeTTT(){

    }

}