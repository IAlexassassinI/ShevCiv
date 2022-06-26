package Processing.Utilits.Wrapers;

import java.io.Serializable;

public class TwoTTT<AAA,VVV> implements Serializable {
    static final long serialVersionUID = 27L;
    public AAA first;
    public VVV second;

    public TwoTTT(AAA first, VVV second){
        this.first = first;
        this.second = second;
    }

    public TwoTTT(){

    }

}
